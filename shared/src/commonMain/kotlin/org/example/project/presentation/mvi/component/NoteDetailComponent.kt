package org.example.project.presentation.mvi.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.example.project.presentation.mvi.notedetail.NoteDetailIntent
import org.example.project.presentation.mvi.notedetail.NoteDetailLabel
import org.example.project.presentation.mvi.notedetail.NoteDetailState
import org.example.project.presentation.mvi.notedetail.NoteDetailStoreFactory

interface NoteDetailComponent {
    val store: Store<NoteDetailIntent, NoteDetailState, NoteDetailLabel>
    val noteId: Long
    fun onEditClick()
    fun onBackClick()
}

class DefaultNoteDetailComponent(
    componentContext: ComponentContext,
    storeFactory: NoteDetailStoreFactory,
    override val noteId: Long,
    private val toNoteEditScreen: (Long) -> Unit,
    private val toMainScreen: () -> Unit,
) : NoteDetailComponent, ComponentContext by componentContext {
    override val store: Store<NoteDetailIntent, NoteDetailState, NoteDetailLabel> =
        instanceKeeper.getStore { storeFactory.create() }
    private val scope = coroutineScope()

    init {

        store.labels
            .onEach { label ->
                when (label) {
                    NoteDetailLabel.ItemDeleted -> toMainScreen()
                }
            }
            .launchIn(scope)
    }

    override fun onEditClick() = toNoteEditScreen(noteId)


    override fun onBackClick() = toMainScreen()

}