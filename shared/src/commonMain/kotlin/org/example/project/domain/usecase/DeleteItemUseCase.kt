package org.example.project.domain.usecase

import org.example.project.domain.repository.NoteRepository

class DeleteItemUseCase(private val repository: NoteRepository){
    fun deleteItem(id: Long){
        repository.deleteItem(id)
    }
}