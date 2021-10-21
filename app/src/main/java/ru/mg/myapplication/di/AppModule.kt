package ru.mg.myapplication.di

import android.content.Context
import com.google.crypto.tink.Aead
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun context(): Context = context

    @Provides
    @Singleton
    fun aead(): Aead = CryptoUtils.generateAead(context)

}