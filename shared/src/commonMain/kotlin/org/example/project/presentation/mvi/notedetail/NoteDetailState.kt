package org.example.project.presentation.mvi.notedetail


data class NoteDetailState(
    val id: Long = -1L,
    val title: String = "",
    val description: String = ""
)