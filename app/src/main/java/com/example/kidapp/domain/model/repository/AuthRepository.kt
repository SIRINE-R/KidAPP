package com.example.kidapp.domain.model.repository
import com.example.kidapp.data.remote.dto.ChildProfileResponse
import com.example.kidapp.data.remote.dto.SearchTextsReq
import com.example.kidapp.data.remote.dto.VideoResponse
import com.example.kidapp.data.remote.dto.WatchedVideoRes
import com.example.kidapp.domain.model.Child
import com.example.kidapp.domain.model.Parent
import com.example.kidapp.domain.model.ParentLogin
import com.example.kidapp.domain.model.ParentUpdate
import com.example.kidapp.domain.model.ParentVerify
import io.ktor.client.statement.HttpResponse
import java.time.LocalDateTime

interface AuthRepository {
    suspend fun parentSignUp(
        name: String,
        email: String,
        password: String
    ): Result<Parent>

    suspend fun verifyParent(
        password: String,
        email: String?
    ): Result<ParentVerify>

    suspend fun parentLogin(
        email: String,
        password: String
    ): Result<ParentLogin>

    suspend fun addChildProfile(
        name: String,
        birthDate: String,
        iconProfile: String,
        parentId: Int?
    ): Result<Child>

    suspend fun retrieveChildProfiles(
        parentId: Int?
    ) : Result<List<ChildProfileResponse>>

    suspend fun getParent(
        id: Int?
    ): Result<Parent>

    suspend fun updateChild(
        name: String,
        birthDate: String,
        iconProfile: String,
        id: Int
    ): Result<Child>

    suspend fun deleteChild(
        id: Int,
        name: String
    ): Result<HttpResponse>

    suspend fun updateParent(
        id: Int?,
        email: String,
        password: String
    ): Result<ParentUpdate>

    suspend fun deleteParent(
        email: String
    ): Result<HttpResponse>

    suspend fun getVideos(
        query: String
    ): Result<List<VideoResponse>>

    suspend fun addWatchedVideos(
        childId: Int,
        videoId: String,
        watchedAt: LocalDateTime
    ): Result<WatchedVideoRes>

    suspend fun getWatchedVideos(
        childId: Int,
        name: String
    ): Result<List<VideoResponse>>

    suspend fun addSearchText(
        id: Int,
        query: String,
        searchedAt: LocalDateTime
    ): Result<HttpResponse>

    suspend fun getSearchTexts(
        childId: Int,
        name: String
    ): Result<List<SearchTextsReq>>

}
