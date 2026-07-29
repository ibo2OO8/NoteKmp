package org.example.project.presentation.mvi.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.domain.usecase.DeleteItemUseCase
import org.example.project.domain.usecase.GetItemByIdUseCase
import org.example.project.presentation.mvi.notedetail.NoteDetailViewModel

interface NoteDetailComponent {
    val viewModel: NoteDetailViewModel
    val noteId: Long
    fun onEditClick()
    fun onBackClick()
    fun onDeleteClick()
}

class DefaultNoteDetailComponent(
    componentContext: ComponentContext,
    override val noteId: Long,
    private val toNoteEditScreen: (Long) -> Unit,
    private val toMainScreen: () -> Unit,
    deleteItemByIdUseCase: DeleteItemUseCase,
    getItemByIdUseCase: GetItemByIdUseCase,
) : NoteDetailComponent, ComponentContext by componentContext {

    override val viewModel: NoteDetailViewModel = NoteDetailViewModel(
        deleteItemUseCase = deleteItemByIdUseCase,
        getItemByIdUseCase = getItemByIdUseCase
    )

    override fun onEditClick() {
        toNoteEditScreen(noteId)
    }

    override fun onBackClick() {
        toMainScreen()
    }

    override fun onDeleteClick() {
        toMainScreen()
    }
}