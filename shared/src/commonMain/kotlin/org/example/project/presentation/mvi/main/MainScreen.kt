package org.example.project.presentation.mvi.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import noteskmp.shared.generated.resources.Res
import noteskmp.shared.generated.resources.iconSearch
import noteskmp.shared.generated.resources.icon_edit
import noteskmp.shared.generated.resources.icon_plus
import org.example.project.domain.entity.Model
import org.example.project.presentation.mvi.component.MainComponent
import org.example.project.ui.BorderColor
import org.example.project.ui.TextColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    component: MainComponent, viewModel: MainViewModel = koinViewModel()
) {
    var searchText by remember { mutableStateOf("") }
    val state = viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onIntent(MainIntent.GetAllItem)
    }
    Box(
        modifier = Modifier.fillMaxSize().padding(vertical = 15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "Мои заметки",
                fontSize = 24.sp,
                color = TextColor,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            TextField(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp).fillMaxWidth(),
                value = searchText,
                onValueChange = { searchText = it },
                shape = RoundedCornerShape(20.dp),
                placeholder = {
                    Text("Поиск")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.iconSearch),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    state.value.list.filter {
                        it.title.contains(searchText, ignoreCase = true)
                    },
                ) { note ->
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 5.dp).shadow(
                                2.dp, RoundedCornerShape(12.dp)
                            ).background(Color.White).padding(12.dp).clickable {
                                component.onClick2(note.id)
                            }) {

                        Text(
                            text = note.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            maxLines = 1

                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            text = note.description,
                            fontSize = 20.sp,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }


        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            containerColor = BorderColor,
            onClick = {
                component.onClick(-1)
            }) {
            Icon(
                painter = painterResource(Res.drawable.icon_plus),
                contentDescription = "Добавить заметку",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

