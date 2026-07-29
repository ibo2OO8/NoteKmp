package org.example.project.presentation.mvi.notedetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import noteskmp.shared.generated.resources.Res
import noteskmp.shared.generated.resources.icon_back
import noteskmp.shared.generated.resources.icon_edit
import noteskmp.shared.generated.resources.icon_trash
import org.example.project.presentation.mvi.component.NoteDetailComponent
import org.example.project.presentation.mvi.noteedit.NoteEditIntent
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NoteDetailScreen(
    component: NoteDetailComponent,
) {
    val state by component.viewModel.state.collectAsState()

    LaunchedEffect(component.noteId) {
        component.viewModel.onIntent(NoteDetailIntent.LoadNote(component.noteId))
    }

    Column(modifier = Modifier.fillMaxSize().padding(18.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                component.onBackClick()
            }) {
                Icon(
                    painter = painterResource(Res.drawable.icon_back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(33.dp)
                )
            }

            Row {
                IconButton(onClick = {
                    component.onEditClick()
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_edit),
                        contentDescription = "Edit",
                        tint = Color.Black,
                        modifier = Modifier.size(25.dp)
                    )
                }

                IconButton(onClick = {
                    component.viewModel.onIntent(NoteDetailIntent.DeleteItem(component.noteId))
                    component.onDeleteClick()
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_trash),
                        contentDescription = "Delete",
                        tint = Color.Black,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
        Column {
            Text(
                text = state.title,
                color = Color.Black,
                modifier = Modifier.padding(top = 80.dp, bottom = 10.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = state.description,
                color = Color.Black,
                modifier = Modifier.padding(top = 80.dp, bottom = 10.dp),
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), onClick = {
            component.onEditClick()
        }) {
            Text("Редактировакать", modifier = Modifier.padding(10.dp))
        }
    }
}

