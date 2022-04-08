package com.yarmouk.protodatastore.domain.use_cases

import androidx.datastore.core.DataStore
import com.yarmouk.protodatastore.domain.model.Gender
import com.yarmouk.protodatastore.domain.model.InvalidInfoException
import com.yarmouk.protodatastore.domain.model.UserInfo
import javax.inject.Inject

class InsertUserInfo @Inject constructor(private val userDataStore:DataStore<UserInfo>){

    @Throws(InvalidInfoException::class)
    suspend operator fun invoke(name:String,email:String,gender:Gender){
        if(name.isBlank()){
            throw InvalidInfoException("Please enter valid name")
        }
        if(email.isBlank()){
            throw InvalidInfoException("Please enter valid email")
        }
        userDataStore.updateData {
            it.copy(
                name = name,
                email = email,
                gender = gender
            )
        }
    }

}