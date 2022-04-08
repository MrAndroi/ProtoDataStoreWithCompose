package com.yarmouk.protodatastore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val name:String = "",
    val email:String = "",
    val gender:Gender = Gender.NONE,
)

enum class Gender{
    MALE,FEMALE,NONE
}

class InvalidInfoException(message:String):Exception(message)
