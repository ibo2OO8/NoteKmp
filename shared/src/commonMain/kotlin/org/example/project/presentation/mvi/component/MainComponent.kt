package org.example.project.presentation.mvi.component

import com.arkivanov.decompose.ComponentContext

interface MainComponent {
    fun onClick(id: Long)
    fun onClick2(id: Long)
}
class DefaultMainComponent(
    componentContext: ComponentContext,
    private val toNoteEditScreen: (id: Long) -> Unit,
    private val toNoteDetailScreen: (id: Long) -> Unit,
) : MainComponent, ComponentContext by componentContext {
    override fun onClick(id: Long) {
        toNoteEditScreen(id)
    }

    override fun onClick2(id: Long) {
        toNoteDetailScreen(id)
    }
}