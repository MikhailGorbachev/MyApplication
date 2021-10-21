package ru.mg.myapplication.user

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.mg.myapplication.user.api.model.ClientCardDto
import ru.mg.myapplication.user.api.model.UserDto

interface UserRepository {

    fun user(): Observable<UserState>

    fun reloadUser()

    fun updateUser(user: UserDto): Single<UserDto>

    fun getClientCards(): Single<List<ClientCardDto>>

    fun removeCard(cardId: Long): Completable
}

sealed class UserState {
    object Loading : UserState()
    class Ok(val user: UserDto) : UserState()
    class Error(val error: Throwable) : UserState()
}