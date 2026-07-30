package org.example.project.presentation.mvi.notedetail

import org.example.project.domain.usecase.DeleteItemUseCase
import org.example.project.domain.usecase.GetItemByIdUseCase
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
class NoteDetailExecutor(
    private val deleteItemUseCase: DeleteItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase
) : CoroutineExecutor<NoteDetailIntent, Nothing, NoteDetailState, NoteDetailMessage, NoteDetailLabel>() {
    override fun executeIntent(intent: NoteDetailIntent) {
        when (intent) {
            is NoteDetailIntent.LoadNote -> loadNote(intent.id)
            is NoteDetailIntent.DeleteItem -> deleteItem(intent.id)
        }
    }

    fun loadNote(id: Long) {
        val item = getItemByIdUseCase.getItemById(id)
        if (item != null) {
            dispatch(
                NoteDetailMessage.GetList(
                    NoteDetailState(
                        id = item.id, title = item.title, description = item.description
                    )
                )
            )
        }
    }

    fun deleteItem(id: Long) {
        scope.launch {
            deleteItemUseCase.deleteItem(id)
            publish(NoteDetailLabel.ItemDeleted)
        }
    }
}