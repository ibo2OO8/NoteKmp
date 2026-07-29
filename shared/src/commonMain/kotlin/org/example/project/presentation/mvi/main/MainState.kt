package org.example.project.presentation.mvi.main

import org.example.project.domain.entity.Model

data class MainState(
    val list: List<Model> = emptyList()
)