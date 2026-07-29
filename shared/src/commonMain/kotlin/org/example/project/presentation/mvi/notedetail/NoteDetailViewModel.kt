package org.example.project.presentation.mvi.notedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.DeleteItemUseCase
import org.example.project.domain.usecase.GetItemByIdUseCase

class NoteDetailViewModel(
    private val deleteItemUseCase: DeleteItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NoteDetailState())
    val state = _state.asStateFlow()
    fun onIntent(intent: NoteDetailIntent) {
        when (intent) {
            is NoteDetailIntent.LoadNote -> loadNote(intent.id)
            is NoteDetailIntent.DeleteItem -> deleteItem(intent.id)
        }
    }

    private fun loadNote(id: Long) {
        val item = getItemByIdUseCase.getItemById(id)
        if (item != null) {
            _state.update {
                it.copy(id = item.id, title = item.title, description = item.description)
            }
        }
    }

    private fun deleteItem(id: Long) {
        viewModelScope.launch {
            deleteItemUseCase.deleteItem(id)
        }
    }
}

sealed interface NoteDetailIntent {
    data class LoadNote(val id: Long) : NoteDetailIntent
    data class DeleteItem(val id: Long) : NoteDetailIntent
}