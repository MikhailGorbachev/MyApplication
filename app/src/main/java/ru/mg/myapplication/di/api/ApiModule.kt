package ru.mg.myapplication.di.api

import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.mg.myapplication.BuildConfig
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    @BaseUrl
    fun baseUrl(): String = BuildConfig.BASE_URL

    @Singleton
    @Provides
    @LoggingInterceptor
    fun loggingInterceptor(): Interceptor =
        HttpLoggingInterceptorFactory.create()

    @Provides
    @Singleton
    fun rxJava2CallAdapterWithUpdateTokenFactory(
        rxTokenRefresherWhenNeed: RxTokenRefresherWhenNeed
    ): RxJava2CallAdapterWithUpdateTokenFactory =
        RxJava2CallAdapterWithUpdateTokenFactory(
            RxJava2CallAdapterFactory.create(),
            rxTokenRefresherWhenNeed
        )

    @Provides
    @AuthorizationApiBuilder
    fun apiBuilder(
        @BaseUrl baseUrl: String,
        @LoggingInterceptor loggingInterceptor: Interceptor,
        tokenInterceptor: TokenInterceptor,
        rxJava2CallAdapterWithUpdateTokenFactory: RxJava2CallAdapterWithUpdateTokenFactory
    ): ApiBuilder {
        val converterFactory =
            GsonConverterFactory.create(Converters.registerDateTime(GsonBuilder()).create())
        return ApiBuilder(baseUrl)
            .withCallAdapterFactory(rxJava2CallAdapterWithUpdateTokenFactory)
            .withConverterFactory(converterFactory)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(loggingInterceptor)
    }

    @Provides
    @SimpleApiBuilder
    fun authApiBuilder(
        @BaseUrl baseUrl: String,
        @LoggingInterceptor loggingInterceptor: Interceptor
    ): ApiBuilder {
        return ApiBuilder(baseUrl).addInterceptor(loggingInterceptor)
    }


}