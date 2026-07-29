package org.example.project.presentation.mvi.noteedit

data class NoteEditState(
    val id: Long = -1L,
    val title: String = "",
    val description: String = "",
    val isNew: Boolean = true
)