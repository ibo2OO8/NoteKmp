package org.example.project.presentation.mvi.main

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.GetAllItemUseCase

class MainExecutor(private val getAllItemUseCase: GetAllItemUseCase) :
    CoroutineExecutor<MainIntent, Nothing, MainState, MainMessage, Nothing>() {
    override fun executeIntent(intent: MainIntent) {

        when (intent) {
            MainIntent.GetAllItem -> getAllList()
        }
    }

    fun getAllList() {
        scope.launch {
            getAllItemUseCase.invoke().collect {
                dispatch(MainMessage.UpdateList(it))
            }
        }
    }
}