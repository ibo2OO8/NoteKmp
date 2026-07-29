package org.example.project.presentation.mvi.noteedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.entity.Model
import org.example.project.domain.usecase.GetItemByIdUseCase
import org.example.project.domain.usecase.InsertItemUseCase
import org.example.project.domain.usecase.UpdateItemUseCase

class NoteEditViewModel(
    private val getItemByIdUseCase: GetItemByIdUseCase,
    private val insertItemUseCase: InsertItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(NoteEditState())
    val state = _state.asStateFlow()


    fun onIntent(intent: NoteEditIntent) {
        when (intent) {
            is NoteEditIntent.LoadNote -> loadNote(intent.id)
            is NoteEditIntent.TitleChanged -> _state.update { it.copy(title = intent.title) }
            is NoteEditIntent.DescriptionChanged -> _state.update { it.copy(description = intent.description) }
            is NoteEditIntent.Save -> save()
        }
    }

    private fun loadNote(id: Long) {
        if (id > 0) {
            val item = getItemByIdUseCase.getItemById(id)
            if (item != null) {
                _state.update {
                    it.copy(
                        id = item.id,
                        title = item.title,
                        description = item.description,
                        isNew = false
                    )
                }
            }
        } else {
            _state.update { NoteEditState(id = -1L, isNew = true) }
        }
    }

    private fun save() {
        val current = _state.value
        viewModelScope.launch {
            if (current.isNew) {
                insertItemUseCase.insert(
                    Model(
                        id = 0L,
                        title = current.title,
                        description = current.description
                    )
                )
            } else {
                updateItemUseCase.updateItem(
                    Model(
                        id = current.id,
                        title = current.title,
                        description = current.description
                    )
                )
            }
        }
    }

}

sealed interface NoteEditIntent {
    data class LoadNote(val id: Long) : NoteEditIntent
    data class TitleChanged(val title: String) : NoteEditIntent
    data class DescriptionChanged(val description: String) : NoteEditIntent
    data object Save : NoteEditIntent
}