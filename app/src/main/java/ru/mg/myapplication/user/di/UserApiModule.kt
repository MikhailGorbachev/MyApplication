package ru.mg.myapplication.user.di

import dagger.Module
import dagger.Provides
import ru.mg.myapplication.user.api.UserApi

@Module
class UserApiModule {

    @ProfileScope
    @Provides
    fun userApi(@AuthorizationApiBuilder apiBuilder: ApiBuilder): UserApi =
        apiBuilder.create()

}