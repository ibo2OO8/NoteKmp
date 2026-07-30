package org.example.project.presentation.mvi.noteedit

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.example.project.domain.entity.Model
import org.example.project.domain.usecase.GetItemByIdUseCase
import org.example.project.domain.usecase.InsertItemUseCase
import org.example.project.domain.usecase.UpdateItemUseCase

class NoteEditStoreFactory(
    private val storeFactory: StoreFactory,
    private val getItemByIdUseCase: GetItemByIdUseCase,
    private val insertItemUseCase: InsertItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase
) {
    fun create(): Store<NoteEditIntent, NoteEditState, NoteEditLabel> =
        storeFactory.create(
            name = "NoteEditStore",
            initialState = NoteEditState(),
            executorFactory = {
                NoteEditExecutor(getItemByIdUseCase, insertItemUseCase, updateItemUseCase)
            },
            reducer = NoteEditReducer
        )
}

sealed interface NoteEditLabel {
    data object Save : NoteEditLabel
}

sealed interface NoteEditMessage {
    data class NoteLoaded(
        val id: Long,
        val title: String,
        val description: String,
        val isNew: Boolean
    ) : NoteEditMessage

    data class TitleChanged(val title: String) : NoteEditMessage
    data class DescriptionChanged(val description: String) : NoteEditMessage
}

object NoteEditReducer : Reducer<NoteEditState, NoteEditMessage> {
    override fun NoteEditState.reduce(msg: NoteEditMessage): NoteEditState =
        when (msg) {
            is NoteEditMessage.NoteLoaded -> copy(
                id = msg.id,
                title = msg.title,
                description = msg.description,
                isNew = msg.isNew
            )

            is NoteEditMessage.TitleChanged -> copy(title = msg.title)
            is NoteEditMessage.DescriptionChanged -> copy(description = msg.description)
        }
}

sealed interface NoteEditIntent {
    data class LoadNote(val id: Long) : NoteEditIntent
    data class TitleChanged(val title: String) : NoteEditIntent
    data class DescriptionChanged(val description: String) : NoteEditIntent
    data object Save : NoteEditIntent
}