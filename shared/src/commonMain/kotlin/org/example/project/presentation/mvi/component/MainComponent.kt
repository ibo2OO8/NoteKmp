package org.example.project.presentation.mvi.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.Store
import org.example.project.presentation.mvi.main.MainIntent
import org.example.project.presentation.mvi.main.MainState
import org.example.project.presentation.mvi.main.MainStoreFactory

interface MainComponent {
    val store: Store<MainIntent, MainState, Nothing>
    fun onClick(id: Long)
    fun onClick2(id: Long)
}

class DefaultMainComponent(
    componentContext: ComponentContext,
    storeFactory: MainStoreFactory,
    private val toNoteEditScreen: (id: Long) -> Unit,
    private val toNoteDetailScreen: (id: Long) -> Unit,
) : MainComponent, ComponentContext by componentContext {

    override val store: Store<MainIntent, MainState, Nothing> = instanceKeeper.getStore {
        storeFactory.create()
    }

    override fun onClick(id: Long) {
        toNoteEditScreen(id)
    }

    override fun onClick2(id: Long) {
        toNoteDetailScreen(id)
    }
}