package org.example.project.domain.usecase

import org.example.project.domain.entity.Model
import org.example.project.domain.repository.NoteRepository

class InsertItemUseCase(private val repository: NoteRepository) {
    fun insert(item: Model) {
        repository.insert(item)
    }
}