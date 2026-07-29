package org.example.project


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import org.example.project.presentation.root.DefaultRootComponent
import org.example.project.data.local.initSettings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {

            initSettings(applicationContext)

            val root = DefaultRootComponent(
                componentContext = defaultComponentContext()
            )
            App(root)
        }
    }
}
