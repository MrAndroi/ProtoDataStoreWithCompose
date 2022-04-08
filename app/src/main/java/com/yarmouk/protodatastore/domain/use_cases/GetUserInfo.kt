package com.yarmouk.protodatastore.domain.use_cases

import androidx.datastore.core.DataStore
import com.yarmouk.protodatastore.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfo @Inject constructor(private val userDataStore:DataStore<UserInfo>) {

    operator fun invoke(): Flow<UserInfo> {
        return userDataStore.data
    }

}