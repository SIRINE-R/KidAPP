package com.example.kidapp.domain.model


data class Parent(
    val id: Int ,
    val name: String,
    val email: String
)
data class ParentLogin(
    val id: Int?,
    val name: String?,
    val email: String
)
data class ParentUpdate(
    val id: Int?,
    val email: String
)

data class Child(
    val id: Int?,
    val name: String,
    val birthDate: String,
    val iconProfile: String,
    val parentId: Int?
)

data class ParentVerify(
    val id: Int,
    val email: String
)
data class ChildProfile(
    val id: Int?,
    val name: String,
    val iconProfile: String?
)