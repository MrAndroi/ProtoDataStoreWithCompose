package com.yarmouk.protodatastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.yarmouk.protodatastore.data.UserInfoSerializer
import com.yarmouk.protodatastore.domain.model.UserInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATA_STORE_FILE_NAME = "user_prefs.pb"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context): DataStore<UserInfo>{
        return DataStoreFactory.create(
            serializer = UserInfoSerializer(),
            produceFile = { context.dataStoreFile(DATA_STORE_FILE_NAME) },
        )
    }

}