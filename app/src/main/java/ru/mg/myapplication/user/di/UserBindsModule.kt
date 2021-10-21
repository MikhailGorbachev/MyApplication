package ru.mg.myapplication.user.di

import dagger.Binds
import dagger.Module
import ru.mg.myapplication.ui.profile.di.ProfileScope
import ru.mg.myapplication.user.UserRepository
import ru.mg.myapplication.user.UserRepositoryImpl

@Module
abstract class UserBindsModule {

    @Binds
    @ProfileScope
    abstract fun userRepository(repository: UserRepositoryImpl): UserRepository

}