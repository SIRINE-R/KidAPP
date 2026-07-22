package com.example.kidapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChildUpdate(
    val name: String,
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("icon_profile")
    val iconProfile: String
)
