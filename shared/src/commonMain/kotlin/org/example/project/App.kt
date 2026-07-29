package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.example.project.presentation.root.RootComponent
import org.example.project.presentation.root.RootContent

@Composable
fun App(root: RootComponent) {
    MaterialTheme {
        RootContent(root)
    }
}