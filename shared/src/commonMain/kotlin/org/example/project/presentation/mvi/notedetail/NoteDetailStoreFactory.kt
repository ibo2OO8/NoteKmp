package org.example.project.presentation.mvi.notedetail

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import org.example.project.domain.usecase.DeleteItemUseCase
import org.example.project.domain.usecase.GetItemByIdUseCase

class NoteDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase
) {
    fun create(): Store<NoteDetailIntent, NoteDetailState, NoteDetailLabel> {
        return storeFactory.create(
            name = "NoteDetailStore", initialState = NoteDetailState(), executorFactory = {
                NoteDetailExecutor(
                    deleteItemUseCase = deleteItemUseCase, getItemByIdUseCase = getItemByIdUseCase
                )
            }, reducer = NoteDetailReducer
        )
    }
}

sealed interface NoteDetailLabel {
    data object ItemDeleted : NoteDetailLabel
}

sealed interface NoteDetailMessage {
    data class GetList(var note: NoteDetailState) : NoteDetailMessage
}

object NoteDetailReducer : Reducer<NoteDetailState, NoteDetailMessage> {
    override fun NoteDetailState.reduce(msg: NoteDetailMessage): NoteDetailState {
        return when (msg) {
            is NoteDetailMessage.GetList -> copy(
                id = msg.note.id,
                title = msg.note.title,
                description = msg.note.description
            )
        }
    }

}

sealed interface NoteDetailIntent {
    data class LoadNote(val id: Long) : NoteDetailIntent
    data class DeleteItem(val id: Long) : NoteDetailIntent
}
