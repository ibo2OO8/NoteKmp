package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.entity.Model

interface NoteRepository {
    fun getAllItem(): Flow<List<Model>>

    fun insert(item: Model)
    fun deleteItem(id: Long)

    fun getItemById(id: Long): Model?

    fun updateItem(item: Model)

}