package org.example.project.domain.usecase

import org.example.project.domain.entity.Model
import org.example.project.domain.repository.NoteRepository

class GetItemByIdUseCase(private val repository: NoteRepository){
    fun getItemById(id: Long): Model? {
        return repository.getItemById(id)
    }
}