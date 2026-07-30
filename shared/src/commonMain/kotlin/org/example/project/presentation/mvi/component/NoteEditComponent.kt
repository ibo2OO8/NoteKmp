package org.example.project.presentation.mvi.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.example.project.domain.usecase.GetItemByIdUseCase
import org.example.project.domain.usecase.InsertItemUseCase
import org.example.project.domain.usecase.UpdateItemUseCase
import org.example.project.presentation.mvi.notedetail.NoteDetailIntent
import org.example.project.presentation.mvi.notedetail.NoteDetailLabel
import org.example.project.presentation.mvi.notedetail.NoteDetailState
import org.example.project.presentation.mvi.notedetail.NoteDetailStoreFactory
import org.example.project.presentation.mvi.noteedit.NoteEditIntent
import org.example.project.presentation.mvi.noteedit.NoteEditLabel
import org.example.project.presentation.mvi.noteedit.NoteEditState
import org.example.project.presentation.mvi.noteedit.NoteEditStoreFactory

interface NoteEditComponent {
    val store: Store<NoteEditIntent, NoteEditState, NoteEditLabel>
    val noteId: Long
    fun onCancel()
}

class DefaultNoteEditComponent(
    componentContext: ComponentContext,
    storeFactory: NoteEditStoreFactory,
    override val noteId: Long,
    private val toMainScreen: () -> Unit,
) : NoteEditComponent, ComponentContext by componentContext {
    override val store: Store<NoteEditIntent, NoteEditState, NoteEditLabel> =
        instanceKeeper.getStore {
            storeFactory.create()
        }

    private val scope = coroutineScope()

    init {
        store.labels
            .onEach { label ->
                when (label) {
                    is NoteEditLabel.Save -> toMainScreen()
                }
            }
            .launchIn(scope)
    }

    override fun onCancel() {
        toMainScreen()
    }
}