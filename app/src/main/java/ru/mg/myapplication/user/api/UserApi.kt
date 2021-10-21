package ru.mg.myapplication.user.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import ru.mg.myapplication.user.api.model.ClientCardDto
import ru.mg.myapplication.user.api.model.UserDto

interface UserApi {

    @GET("/api/client")
    fun user(): Single<UserDto>

    @POST("/api/client")
    fun updateUser(@Body user: UpdateUserDto): Single<UserDto>

    @GET("/api/client/card/list")
    fun getCards(): Single<List<ClientCardDto>>

    @DELETE("/api/client/card/{cardId}")
    fun removeCard(@Path("cardId") cardId: Long): Completable

}