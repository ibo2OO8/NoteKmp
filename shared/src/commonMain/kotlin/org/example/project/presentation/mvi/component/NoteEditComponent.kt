package org.example.project.presentation.mvi.component

import com.arkivanov.decompose.ComponentContext
import org.example.project.domain.usecase.GetItemByIdUseCase
import org.example.project.domain.usecase.InsertItemUseCase
import org.example.project.domain.usecase.UpdateItemUseCase
import org.example.project.presentation.mvi.noteedit.NoteEditIntent
import org.example.project.presentation.mvi.noteedit.NoteEditViewModel
interface NoteEditComponent {
    val viewModel: NoteEditViewModel
    val noteId: Long
    fun onSave()
    fun onCancel()
}

class DefaultNoteEditComponent(
    componentContext: ComponentContext,
    override val noteId: Long,
    private val toMainScreen: () -> Unit,
    getItemByIdUseCase: GetItemByIdUseCase,
    insertItemUseCase: InsertItemUseCase,
    updateItemUseCase: UpdateItemUseCase,
) : NoteEditComponent, ComponentContext by componentContext {

    override val viewModel: NoteEditViewModel = NoteEditViewModel(
        getItemByIdUseCase, insertItemUseCase, updateItemUseCase
    )

    override fun onSave() {
        viewModel.onIntent(NoteEditIntent.Save)
        toMainScreen()
    }

    override fun onCancel() {
        toMainScreen()
    }
}