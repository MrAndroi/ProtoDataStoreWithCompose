package com.yarmouk.protodatastore.data

import androidx.datastore.core.Serializer
import com.yarmouk.protodatastore.domain.model.UserInfo
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
class UserInfoSerializer:Serializer<UserInfo> {

    override val defaultValue: UserInfo
        get() = UserInfo()

    override suspend fun readFrom(input: InputStream): UserInfo {
        return try{
            Json.decodeFromString(
                deserializer = UserInfo.serializer(),
                string = input.readBytes().decodeToString(),
            )
        }
        catch (e:SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserInfo, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = UserInfo.serializer(),
                value = t,
            ).toByteArray()
        )
    }
}