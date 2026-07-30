package org.example.project.presentation.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.example.project.presentation.mvi.component.DefaultMainComponent
import org.example.project.presentation.mvi.component.DefaultNoteDetailComponent
import org.example.project.presentation.mvi.component.DefaultNoteEditComponent
import org.example.project.presentation.mvi.component.MainComponent
import org.example.project.presentation.mvi.component.NoteDetailComponent
import org.example.project.presentation.mvi.component.NoteEditComponent
import org.example.project.presentation.mvi.main.MainScreen
import org.example.project.presentation.mvi.notedetail.NoteDetailScreen
import org.example.project.presentation.mvi.noteedit.NoteEditScreen
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun RootContent(component: RootComponent) {
    val stack by component.stack.subscribeAsState()
    Children(modifier = Modifier.fillMaxSize().safeDrawingPadding(), stack = stack) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.MainScreen -> MainScreen(instance.component)
            is RootComponent.Child.NoteDetailScreen -> NoteDetailScreen(instance.component)
            is RootComponent.Child.NoteEditScreen -> NoteEditScreen(instance.component)
        }
    }
}

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class MainScreen(val component: MainComponent) : Child()
        class NoteDetailScreen(val component: NoteDetailComponent) : Child()
        class NoteEditScreen(val component: NoteEditComponent) : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent,
    ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.MainScreen,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child =
        when (config) {
            is Config.MainScreen -> RootComponent.Child.MainScreen(
                DefaultMainComponent(
                    componentContext = componentContext,
                    toNoteEditScreen = { id ->
                        navigation.pushNew(Config.NoteEditScreen(id))
                    },
                    toNoteDetailScreen = { id ->
                        navigation.pushNew(Config.NoteDetailScreen(id))
                    },
                    storeFactory = getKoin().get()

                )
            )

            is Config.NoteDetailScreen ->
                RootComponent.Child.NoteDetailScreen(
                    DefaultNoteDetailComponent(
                        componentContext = componentContext,
                        noteId = config.id,
                        toNoteEditScreen = { id ->
                            navigation.pushNew(Config.NoteEditScreen(id))
                        },
                        toMainScreen = {
                            navigation.pop()
                        },
                        storeFactory = getKoin().get()
                    )
                )

            is Config.NoteEditScreen -> RootComponent.Child.NoteEditScreen(
                DefaultNoteEditComponent(
                    componentContext = componentContext,
                    noteId = config.id,
                    toMainScreen = {
                        navigation.replaceAll(Config.MainScreen)
                    },
                    storeFactory = getKoin().get()
                )
            )
        }

    @Serializable
    private sealed class Config {
        @Serializable
        data object MainScreen : Config()

        @Serializable
        data class NoteDetailScreen(val id: Long) : Config()

        @Serializable
        data class NoteEditScreen(val id: Long) : Config()

    }
}


