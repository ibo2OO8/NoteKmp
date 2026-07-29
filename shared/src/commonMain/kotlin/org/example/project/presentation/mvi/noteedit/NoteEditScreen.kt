package org.example.project.presentation.mvi.noteedit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import org.example.project.presentation.mvi.component.NoteEditComponent
import org.example.project.ui.BorderColor
import org.example.project.ui.TextColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NoteEditScreen(component: NoteEditComponent) {
    val state by component.viewModel.state.collectAsState()

    LaunchedEffect(component.noteId) {
        component.viewModel.onIntent(NoteEditIntent.LoadNote(component.noteId))
    }
    var titleText by remember { mutableStateOf(false) }
    var descriptionText by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val interactionSource2 = remember { MutableInteractionSource() }

    val isPressed by interactionSource.collectIsPressedAsState()
    val isPressed2 by interactionSource2.collectIsPressedAsState()

    val textSize = if (isPressed) 22 else 19
    val textSize2 = if (isPressed2) 22 else 19

    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(18.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Отмена",
                modifier = Modifier
                    .weight(1F)
                    .clickable(interactionSource = interactionSource, indication = null) {
                        component.onCancel()
                    },
                fontSize = textSize.sp,
                color = TextColor,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = if (state.isNew) "Новая заметка" else "Заметка",
                fontSize = 22.sp,
                modifier = Modifier.weight(1F)
            )

            Text(
                text = "Сохранить",
                modifier = Modifier
                    .weight(1F)
                    .clickable(interactionSource = interactionSource2, indication = null) {
                        if(state.title.isNotBlank() and state.description.isNotBlank()){
                            component.onSave()
                        }
                        titleText = if (state.title.isBlank()) true
                        else false
                        descriptionText = if (state.description.isBlank()) true
                        else false
                    },
                fontSize = textSize2.sp,
                color = TextColor,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "Заголовок",
            color = TextColor,
            modifier = Modifier.padding(top = 80.dp, bottom = 10.dp),
            fontSize = 20.sp
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { component.viewModel.onIntent(NoteEditIntent.TitleChanged(it)) },
            value = state.title,
            shape = RoundedCornerShape(size = 10.dp),
            placeholder = {
                if (!titleText)
                    Text("title", color = Color.Gray)
                else
                    Text("must not be empty", color = Color.Red)
            },
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = BorderColor
            ),
        )

        Text(
            text = "Текст заметки",
            color = TextColor,
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
            fontSize = 20.sp
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(350.dp),
            onValueChange = { component.viewModel.onIntent(NoteEditIntent.DescriptionChanged(it)) },
            value = state.description,
            shape = RoundedCornerShape(size = 10.dp),
            placeholder = {

                if (!descriptionText)
                    Text("description", color = Color.Gray)
                else
                    Text("must not be empty", color = Color.Red)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = BorderColor
            ),
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
            onClick = {
                if(state.title.isNotBlank() and state.description.isNotBlank()){
                    component.onSave()
                }
                titleText = if (state.title.isBlank()) true
                else false
                descriptionText = if (state.description.isBlank()) true
                else false
            }
        ) {
            Text("Сохранить", modifier = Modifier.padding(10.dp))
        }
    }
}
