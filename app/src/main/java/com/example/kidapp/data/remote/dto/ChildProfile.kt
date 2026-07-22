package com.example.kidapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChildRequest(
    val name: String,
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("icon_profile")
    val iconProfile: String,
    @SerialName("parent_id")
    val parentId: Int?
)
@Serializable
data class ChildResponse(
    val id: Int?,
    val name: String,
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("icon_profile")
    val iconProfile: String,
    @SerialName("parent_id")
    val parentId: Int?
)

