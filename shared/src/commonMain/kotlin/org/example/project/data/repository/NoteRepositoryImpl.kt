package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.local.SettingsDataSource
import org.example.project.domain.entity.Model
import org.example.project.domain.repository.NoteRepository
import kotlin.time.Clock

class NoteRepositoryImpl(private val dataSource: SettingsDataSource) : NoteRepository {
    private val notesState = MutableStateFlow(dataSource.loadNotes())

    override fun getAllItem(): Flow<List<Model>> {
        return notesState.asStateFlow()
    }

    override fun insert(item: Model) {
        val newList = notesState.value.toMutableList()
        val newItem = item.copy(
            id = generateId()
        )
        newList.add(newItem)
        persist(newList)
    }

    override fun deleteItem(id: Long) {
        persist(notesState.value.filterNot { it.id == id })
    }

    override fun getItemById(id: Long): Model? {
        return notesState.value.find { it.id == id }
    }

    override fun updateItem(item: Model) {
        persist(notesState.value.map { if (it.id == item.id) item else it })
    }

    private fun persist(list: List<Model>) {
        notesState.value = list
        dataSource.saveNotes(list)
    }

    private fun generateId(): Long {
        return Clock.System.now()
            .toEpochMilliseconds()
    }
}
