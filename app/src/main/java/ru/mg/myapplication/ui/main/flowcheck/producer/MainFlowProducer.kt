package ru.mg.myapplication.ui.main.flowcheck.producer

import io.reactivex.Observable
import ru.mg.myapplication.ui.main.FlowType
import ru.mg.myapplication.ui.main.flowcheck.Flow
import ru.mg.myapplication.ui.main.flowcheck.mainFlowPriority
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainFlowProducer @Inject constructor() : FlowProducer {

    override val priority: Int = mainFlowPriority

    override fun flow(): Observable<Flow> =
        Observable.fromCallable { Flow.CurrentFlow(FlowType.MAIN) }
}