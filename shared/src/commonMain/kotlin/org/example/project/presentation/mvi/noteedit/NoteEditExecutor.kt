package org.example.project.presentation.mvi.noteedit

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.example.project.domain.entity.Model
import org.example.project.domain.usecase.GetItemByIdUseCase
import org.example.project.domain.usecase.InsertItemUseCase
import org.example.project.domain.usecase.UpdateItemUseCase

class NoteEditExecutor(
    private val getItemByIdUseCase: GetItemByIdUseCase,
    private val insertItemUseCase: InsertItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase
) : CoroutineExecutor<NoteEditIntent, Nothing, NoteEditState, NoteEditMessage, NoteEditLabel>() {

    override fun executeIntent(intent: NoteEditIntent) {
        when (intent) {
            is NoteEditIntent.LoadNote -> loadNote(intent.id)
            is NoteEditIntent.TitleChanged -> dispatch(NoteEditMessage.TitleChanged(intent.title))
            is NoteEditIntent.DescriptionChanged -> dispatch(NoteEditMessage.DescriptionChanged(intent.description))
            is NoteEditIntent.Save -> save()
        }
    }

    private fun loadNote(id: Long) {
        if (id > 0) {
            val item = getItemByIdUseCase.getItemById(id)
            if (item != null) {
                dispatch(
                    NoteEditMessage.NoteLoaded(
                        id = item.id,
                        title = item.title,
                        description = item.description,
                        isNew = false
                    )
                )
            } else {
                dispatch(NoteEditMessage.NoteLoaded(id = -1L, title = "", description = "", isNew = true))
            }
        } else {
            dispatch(NoteEditMessage.NoteLoaded(id = -1L, title = "", description = "", isNew = true))
        }
    }

    private fun save() {
        val current = state()
        scope.launch {
            if (current.isNew) {
                insertItemUseCase.insert(
                    Model(id = 0L, title = current.title, description = current.description)
                )
            } else {
                updateItemUseCase.updateItem(
                    Model(id = current.id, title = current.title, description = current.description)
                )
            }
            publish(NoteEditLabel.Save)
        }
    }
}