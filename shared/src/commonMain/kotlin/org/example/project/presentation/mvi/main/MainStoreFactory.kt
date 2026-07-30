package org.example.project.presentation.mvi.main

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.example.project.domain.entity.Model
import org.example.project.domain.usecase.GetAllItemUseCase

class MainStoreFactory(
    private val storeFactory: StoreFactory,
    private val getAllItemUseCase: GetAllItemUseCase
) {
    fun create(): Store<MainIntent, MainState, Nothing> = storeFactory.create(
        "MainStore",
        initialState = MainState(),
        executorFactory = {
            MainExecutor(getAllItemUseCase)
        },
        reducer = MainReducer
    )
}



sealed interface MainIntent {
    data object GetAllItem : MainIntent
}

sealed interface MainMessage {
    data class UpdateList(val list: List<Model>) : MainMessage
}

object MainReducer : Reducer<MainState, MainMessage> {
    override fun MainState.reduce(msg: MainMessage): MainState {
        return when (msg) {
            is MainMessage.UpdateList ->
                copy(list = msg.list)
        }
    }
}