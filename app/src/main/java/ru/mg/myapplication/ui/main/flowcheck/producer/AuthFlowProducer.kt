package ru.mg.myapplication.ui.main.flowcheck.producer

import io.reactivex.Observable
import ru.mg.myapplication.ui.main.FlowType
import ru.mg.myapplication.ui.main.flowcheck.Flow
import ru.mg.myapplication.ui.main.flowcheck.authFlowPriority
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthFlowProducer @Inject constructor() : FlowProducer {

    @Inject
    lateinit var tokenRepository: TokenRepository

    @Inject
    lateinit var appExit: AppExit

    override val priority: Int = authFlowPriority

    override fun flow(): Observable<Flow> =
        Observable.fromCallable {
            try {
                val accessToken = tokenRepository.accessToken
                if (!accessToken.isNullOrBlank() || isGuest()) {
                    Flow.EmptyFlow
                } else {
                    Flow.CurrentFlow(FlowType.LANDING)
                }

            } catch (ex: Throwable) {
                appExit.exit()
                Flow.CurrentFlow(FlowType.LANDING)
            }

        }

}