package com.example.kidapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VideoRequest(
    val query: String
)
@Serializable
data class VideoResponse(
    val video_id: String,
    val title: String,
    val description: String,
    val thumbnail_url: String
)

@Serializable
data class VideoListResponse(
    val videos: List<VideoResponse>
)
