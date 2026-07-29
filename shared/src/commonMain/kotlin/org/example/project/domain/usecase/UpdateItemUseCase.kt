package org.example.project.domain.usecase

import org.example.project.domain.entity.Model
import org.example.project.domain.repository.NoteRepository

class UpdateItemUseCase(private val repository: NoteRepository) {
    fun updateItem(item: Model) {
        repository.updateItem(item)
    }
}