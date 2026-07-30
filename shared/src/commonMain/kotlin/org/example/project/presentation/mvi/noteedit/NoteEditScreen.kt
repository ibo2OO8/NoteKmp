package org.example.project.presentation.mvi.noteedit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.flow.MutableStateFlow
import org.example.project.presentation.mvi.component.NoteEditComponent
import org.example.project.ui.BorderColor
import org.example.project.ui.TextColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NoteEditScreen(component: NoteEditComponent) {
    val state by component.store.states.collectAsState(NoteEditState())
    LaunchedEffect(component.noteId) {
        component.store.accept(NoteEditIntent.LoadNote(component.noteId))
    }
    var showTitleError by remember { mutableStateOf(false) }
    var showDescriptionError by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }

    val isPressed by interactionSource.collectIsPressedAsState()

    val textSize = if (isPressed) 22 else 19

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Отмена",
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {

                        component.onCancel()
                    },
                fontSize = textSize.sp,
                color = TextColor,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = if (state.isNew) "Новая заметка" else "Заметка",
                fontSize = 22.sp,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        Text(
            text = "Заголовок",
            color = TextColor,
            modifier = Modifier.padding(top = 60.dp, bottom = 10.dp),
            fontSize = 20.sp
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.title,
            onValueChange = {
                component.store.accept(NoteEditIntent.TitleChanged(it))
                if (it.isNotBlank()) {
                    showTitleError = false
                }
            },
            shape = RoundedCornerShape(10.dp),
            placeholder = {
                Text("title", color = Color.Gray)
            },
            isError = showTitleError && state.title.isBlank(),
            supportingText = {
                if (showTitleError && state.title.isBlank()) {
                    Text("Поле не должно быть пустым")
                }
            },
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = BorderColor
            )
        )

        Text(
            text = "Текст заметки",
            color = TextColor,
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
            fontSize = 20.sp
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            value = state.description,
            onValueChange = {
                component.store.accept(NoteEditIntent.DescriptionChanged(it))
                if (it.isNotBlank()) {
                    showDescriptionError = false
                }
            },
            shape = RoundedCornerShape(10.dp),
            placeholder = {
                Text(
                    "description",
                    color = Color.Gray
                )
            },
            isError = showDescriptionError && state.description.isBlank(),
            supportingText = {
                if (showDescriptionError && state.description.isBlank()) {
                    Text("Поле не должно быть пустым")
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = BorderColor
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            onClick = {
                showTitleError = state.title.isBlank()
                showDescriptionError = state.description.isBlank()

                if (state.title.isNotBlank() && state.description.isNotBlank()) {
                    component.store.accept(NoteEditIntent.Save)
                }
            }
        ) {
            Text(text = "Сохранить", modifier = Modifier.padding(10.dp))
        }
    }
}