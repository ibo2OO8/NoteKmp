package org.example.project.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Model (
    val id : Long,
    var title : String,
    var description : String
)