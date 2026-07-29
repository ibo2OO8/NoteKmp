package org.example.project.presentation.mvi.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.GetAllItemUseCase

class MainViewModel(private val getAllItemUseCase: GetAllItemUseCase) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    var state = _state.asStateFlow()

    fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.GetAllItem -> getAllItem()
        }
    }
    private fun getAllItem(){
        viewModelScope.launch {
            getAllItemUseCase.invoke().collect {list ->
                _state.update {
                    it.copy(list = list)
                }
            }
        }
    }
}

sealed interface MainIntent {
    data object GetAllItem : MainIntent
}