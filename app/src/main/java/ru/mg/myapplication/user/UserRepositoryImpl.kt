package ru.mg.myapplication.user

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import ru.mg.myapplication.ui.profile.di.ProfileScope
import ru.mg.myapplication.user.api.UserApi
import ru.mg.myapplication.user.api.model.ClientCardDto
import ru.mg.myapplication.user.api.model.UserDto
import javax.inject.Inject

@ProfileScope
class UserRepositoryImpl @Inject constructor() : UserRepository {

    @Inject
    lateinit var userApi: UserApi

    private val _user = BehaviorSubject.create<UserState>()

    private var userUpdateDisp: Disposable? = null

    override fun user(): Observable<UserState> {
        reloadUser()
        return _user
    }

    override fun reloadUser() {
        if (userUpdateDisp == null) {
            _user.onNext(UserState.Loading)
            userUpdateDisp = userApi.user()
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onSuccess = {
                        _user.onNext(UserState.Ok(it))
                        userUpdateDisp?.dispose()
                        userUpdateDisp = null
                    },
                    onError = {
                        _user.onNext(UserState.Error(it))
                        userUpdateDisp?.dispose()
                        userUpdateDisp = null
                    }
                )
        }
    }

    override fun updateUser(user: UserDto): Single<UserDto> =
        userApi.updateUser(UpdateUserDto(user.email.orEmpty(), user.fullName.orEmpty()))
            .flatMap {
                _user.onNext(UserState.Ok(it))
                Single.just(it)
            }.subscribeOn(Schedulers.io())

    override fun getClientCards(): Single<List<ClientCardDto>> = userApi.getCards()

    override fun removeCard(cardId: Long): Completable = userApi.removeCard(cardId)
}