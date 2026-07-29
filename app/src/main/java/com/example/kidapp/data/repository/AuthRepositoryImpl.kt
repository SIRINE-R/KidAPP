package com.example.kidapp.data.repository

import com.example.kidapp.data.remote.dto.ChildProfileResponse
import com.example.kidapp.data.remote.dto.ChildRequest
import com.example.kidapp.data.remote.dto.ChildResponse
import com.example.kidapp.data.remote.dto.ChildUpdate
import com.example.kidapp.data.remote.dto.ParentLogInRequest
import com.example.kidapp.data.remote.dto.ParentLoginResponseDto
import com.example.kidapp.data.remote.dto.ParentRequestDto
import com.example.kidapp.data.remote.dto.ParentResponseDto
import com.example.kidapp.data.remote.dto.UpdateParentReq
import com.example.kidapp.data.remote.dto.UpdateParentRes
import com.example.kidapp.data.remote.dto.VerifyReq
import com.example.kidapp.data.remote.dto.VerifyRes
import com.example.kidapp.domain.model.Child
import com.example.kidapp.domain.model.Parent
import com.example.kidapp.domain.model.ParentLogin
import com.example.kidapp.domain.model.ParentUpdate
import com.example.kidapp.domain.model.ParentVerify
import com.example.kidapp.domain.model.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import javax.inject.Inject
import com.example.kidapp.util.Constants.BASE_URL
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse

class AuthRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : AuthRepository {

    override suspend fun parentSignUp(
        name: String,
        email: String,
        password: String
    ): Result<Parent> {
        return try {
            val httpResponse = client.post("$BASE_URL/add_parent") {
                contentType(ContentType.Application.Json)
                setBody(ParentRequestDto(name, email, password))
            }
            if (httpResponse.status.isSuccess()) {
                val response = httpResponse.body<ParentResponseDto>()
                Result.success(
                    Parent(
                        id = response.id,
                        name = response.name,
                        email = response.email
                    )
                )
            } else {
                val errorBody = httpResponse.body<String>()
                val errorMessage = if (errorBody.contains("\"detail\":")) {
                    errorBody.substringAfter("\"detail\":\"").substringBefore("\"")
                } else {
                    "Sign up failed. Please try again."
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Connection failed. Check your server status."))
        }
    }

    override suspend fun parentLogin(email: String, password: String): Result<ParentLogin> {
       return try {
           val response = client.post("$BASE_URL/login_parent"){
               contentType(ContentType.Application.Json)
               setBody(ParentLogInRequest(email, password))
           }
           if(response.status.isSuccess()) {
               val response = response.body<ParentLoginResponseDto>()
               Result.success(
                   ParentLogin(
                       id = response.id,
                       name = response.name,
                       email = response.email
                   )
               )
           } else {
               Result.failure(
                   Exception("Login failed. Please try again.")
               )
           }

       } catch(e: Exception){
           Result.failure(e)
       }
    }
    override suspend fun verifyParent(password: String, email: String?): Result<ParentVerify> {
        return try {
            val response = client.post("$BASE_URL/verify_parent"){
            contentType(ContentType.Application.Json)
            setBody(VerifyReq(password, email))
        }

        if(response.status.isSuccess()) {
            val response = response.body<VerifyRes>()
            Result.success(
                ParentVerify(
                    id = response.id,
                    email = response.email
                )
            )
        } else {
            Result.failure(
                Exception("Login failed. Please try again.")
            )
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
    }

    override suspend fun addChildProfile(
        name: String,
        birthDate: String,
        iconProfile: String,
        parentId: Int?
    ): Result<Child>{

        return try{
            val response = client.post("$BASE_URL/add_child"){
                contentType(ContentType.Application.Json)
                setBody(ChildRequest(name, birthDate, iconProfile, parentId))
            }
            if(response.status.isSuccess()){
                val response = response.body<ChildResponse>()
                Result.success(
                    Child(
                        id = response.id,
                        name = response.name,
                        birthDate = response.birthDate,
                        iconProfile = response.iconProfile,
                        parentId = response.parentId
                    )
                    )
            } else {
                Result.failure(
                    Exception("Login failed. Please try again.")
                )
            }

        } catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getParent(id: Int?): Result<Parent> {
        return try {
            val response: ParentResponseDto = client.get("$BASE_URL/get_parent") {
                contentType(ContentType.Application.Json)
                url {
                    parameters.append("id", id.toString())
                }
            }.body()
            Result.success(
                Parent(
                    id = response.id,
                    name = response.name,
                    email = response.email
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun retrieveChildProfiles(
        parentId: Int?
    ): Result<List<ChildProfileResponse>> {
        return try {
            val response: List<ChildProfileResponse> = client.get("$BASE_URL/get_children") {
                contentType(ContentType.Application.Json)
                url {
                    parameters.append("parent_id", parentId.toString())
                }
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
            }
    }

    override suspend fun updateChild(
        name: String,
        birthDate: String,
        iconProfile: String,
        id: Int
    ): Result<Child> {
        return try {
            val response = client.put("$BASE_URL/update_child/$id") {
                contentType(ContentType.Application.Json)
                setBody(ChildUpdate(name, birthDate,iconProfile))
            }.body<ChildResponse>()
            Result.success(
                Child(
                    id = response.id,
                    name = response.name,
                    birthDate = response.birthDate,
                    iconProfile = response.iconProfile,
                    parentId = response.parentId
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteChild(id: Int, name: String): Result<HttpResponse> {
        return try {
            val response = client.delete("$BASE_URL/delete_child/$id") {
                url {
                    parameters.append("name",name)
                    parameters.append("id", id.toString())
                }
            }
            Result.success(
                response
            )

        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun updateParent(id: Int?, email: String, password: String): Result<ParentUpdate> {
        return try {
            val response = client.put("$BASE_URL/update_parent") {
                contentType(ContentType.Application.Json)
                setBody(UpdateParentReq(id,email, password))
            }.body<UpdateParentRes>()
            Result.success(
                ParentUpdate(
                    id = id,
                    email = response.email
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteParent(email: String): Result<HttpResponse> {
        return try {
            val response = client.delete("$BASE_URL/delete_parent/$email"){
                url {
                    parameters.append("email", email)
                }
            }
            Result.success(
                response
            )
            } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
