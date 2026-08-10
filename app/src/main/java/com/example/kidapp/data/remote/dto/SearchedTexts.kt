package com.example.kidapp.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class SearchTextsReq(
    @SerialName(value = "child_id")
    val id: Int,
    @SerialName(value = "search_text")
    val query: String,
    @SerialName(value = "searched_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val searchedAt: LocalDateTime

)

