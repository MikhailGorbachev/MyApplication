package ru.mg.myapplication.ui.main

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import ru.mg.myapplication.ui.main.flowcheck.FlowCheckManager
import javax.inject.Inject

enum class FlowType {
    NO_FLOW,
    LANDING,

    MAIN
}

class MainViewModel @Inject constructor(
    appExit: AppExit,
    private val fcmTokenRepository: FCMTokenRepository
) : BaseViewModel() {

    @Inject
    lateinit var flowCheckManager: FlowCheckManager

    private val _flow = LiveEvent<FlowType>()//.apply { value = FlowType.NO_FLOW }

    val flow: LiveData<FlowType> = _flow

    var callbackTransition: CallbackTransition? = null

    init {
        appExit.showEnterPhoneScreen
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {},
                onNext = { successFlowHandler(FlowType.LANDING) }
            ).addToDisposable()
    }

    fun onCreate() {
        updateFlow(_flow.value)
        fcmTokenRepository.updateFCMToken()
    }

    fun clearFlowTypeFragment() {
        callbackTransition = null
    }

    fun updateFlow(flowType: FlowType? = null, transition: CallbackTransition? = null) {
        if (transition != null) {
            callbackTransition = transition
        }
        flowType?.let {
            successFlowHandler(it)
        } ?: flowCheckManager.flow()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = ::successFlowHandler,
                onError = {}
            ).addToDisposable()
    }

    private fun successFlowHandler(type: FlowType) {
        if (type != _flow.value) {
            when (type) {
                FlowType.MAIN -> AppMetricHelper.sendEvent(EventType.MAIN_CATALOG_TRANSITION_EVENT)
                FlowType.LANDING -> AppMetricHelper.sendEvent(EventType.LOGOUT_EVENT)
            }
        }
        _flow.value = type
    }

}


data class CallbackTransition(@IdRes val fragmentId: Int, val arguments: Bundle)