package ru.mg.myapplication.di.api

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthApiModule {

    @Singleton
    @Provides
    fun authApi(@SimpleApiBuilder apiBuilder: ApiBuilder): AuthApi =
        apiBuilder.create()

}