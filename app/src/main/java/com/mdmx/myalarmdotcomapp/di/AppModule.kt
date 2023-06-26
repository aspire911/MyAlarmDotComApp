package com.mdmx.myalarmdotcomapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.mdmx.myalarmdotcomapp.model.alarmdotcomremoterepository.AlarmDotComRemoteDataSource
import com.mdmx.myalarmdotcomapp.util.DispatcherProvider
import com.mdmx.myalarmdotcomapp.model.alarmdotcomremoterepository.AlarmDotComRemoteRepository
import com.mdmx.myalarmdotcomapp.model.localpersistentrepository.LocalPersistentRepository
import com.mdmx.myalarmdotcomapp.model.localpersistentrepository.LocalPersistentDataSource
import com.mdmx.myalarmdotcomapp.util.Constant.SECURE_PREFS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideAlarmDotComRemoteDataSource(gson: Gson): AlarmDotComRemoteDataSource = AlarmDotComRemoteRepository(gson)

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        return EncryptedSharedPreferences.create(
            SECURE_PREFS,
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Singleton
    @Provides
    fun provideLocalPersistentDataSource(securePreferences: SharedPreferences): LocalPersistentDataSource= LocalPersistentRepository(securePreferences)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}