package com.example.kidapp.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class WatchedVideoReq(
    @SerialName("child_id")
    val childId: Int,
    @SerialName("video_id")
    val videoId: String,
    @SerialName("watched_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val watchedAt: LocalDateTime
)

@Serializable
data class WatchedVideoRes(
    val id: Int,
    @SerialName("child_id")
    val childId: Int,
    @SerialName("video_id")
    val videoId: String,
    @SerialName("watched_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val watchedAt: LocalDateTime
)

