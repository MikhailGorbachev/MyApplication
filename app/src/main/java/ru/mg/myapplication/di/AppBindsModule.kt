package ru.mg.myapplication.di

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppBindsModule {

    @Binds
    @Singleton
    abstract fun tokenRepository(repository: AppTokenRepository): TokenRepository

    @Binds
    @Singleton
    abstract fun tokenRefresher(tokenRefresher: AuthTokenRefresher): TokenRefresher

    @Binds
    @Singleton
    abstract fun addressRepository(repository: AddressRepositoryImpl): AddressRepository

    @Binds
    @Singleton
    abstract fun cartRepository(repository: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    abstract fun orderRepository(repository: OrderRepositoryImpl): OrderRepository

    @Binds
    @Singleton
    abstract fun fcmTokenRepository(repository: FCMTokenRepositoryImpl): FCMTokenRepository

}