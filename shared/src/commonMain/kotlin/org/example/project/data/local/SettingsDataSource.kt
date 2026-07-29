package org.example.project.data.local

import com.russhwolf.settings.Settings
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.example.project.domain.entity.Model

private val json = Json { ignoreUnknownKeys = true }
private const val KEY = "notes"

class SettingsDataSource(
    private val settings: Settings
) {
    fun loadNotes(): List<Model> {
        val value = settings.getStringOrNull(KEY)
            ?: return emptyList()

        return json.decodeFromString(
            ListSerializer(Model.serializer()),
            value
        )
    }

    fun saveNotes(notes: List<Model>) {
        val jsonString = json.encodeToString(
            ListSerializer(Model.serializer()),
            notes
        )

        settings.putString(KEY, jsonString)
    }
}