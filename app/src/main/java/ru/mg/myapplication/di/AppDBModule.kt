package ru.mg.myapplication.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDBModule {

    @Singleton
    @Provides
    fun db(c: Context): AppDb = AppDb.create(c)

    @Singleton
    @Provides
    fun addressDao(db: AppDb): AddressDao = db.addressDao()

    @Singleton
    @Provides
    fun cartDao(db: AppDb): CartDao = db.cartDao()

}