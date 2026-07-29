package org.example.project.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.entity.Model
import org.example.project.domain.repository.NoteRepository

class GetAllItemUseCase(private val repository: NoteRepository) {

    operator fun invoke(): Flow<List<Model>> {
        return repository.getAllItem()
    }
}