package ru.mg.myapplication.ui.mainflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainFlowViewModel @Inject constructor(
    cartRepository: CartRepository,
    orderRepository: OrderRepository,
    addressRepository: AddressRepository,
    appExit: AppExit
) : BaseViewModel() {

    private val _viewState = MutableLiveData<Int>()
    val viewState: LiveData<Int> = _viewState

    private val _error = LiveEvent<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _requirePaymentOrder: LiveEvent<Long> = LiveEvent()
    val requirePaymentOrder: LiveData<Long> = _requirePaymentOrder

    init {
        val cartObs = if (!isGuest()) {
            cartRepository.syncCart()
        } else {
            cartRepository.productsByAddress()
        }

        cartObs.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                when (it) {
                    is CartRepository.State.Ok -> _viewState.value =
                        it.content.orderProducts.size
                    is CartRepository.State.Error -> _error.value = it.ex
                }
            }
            .addToDisposable()
        if (!isGuest()) {
            orderRepository.unpaidOrder()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = { _error.value = it },
                    onNext = { state ->
                        when (state) {
                            is ResponseState.Ok -> {
                                _requirePaymentOrder.value = state.content
                            }
                            is ResponseState.Error -> {
                                _error.value = state.ex
                            }
                        }
                    }
                )
                .addToDisposable()

            val timerDisposable = Observable.interval(1, 1, TimeUnit.MINUTES)
                .flatMap {
                    orderRepository.unpaidOrder()
                }
                .subscribe()
                .addToDisposable()

            appExit.showEnterPhoneScreen.flatMap {
                addressRepository.clearAddress()
                    .andThen(Observable.empty<Unit>())
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    if (!timerDisposable.isDisposed) {
                        timerDisposable.dispose()
                    }
                }.addToDisposable()
        }
    }
}