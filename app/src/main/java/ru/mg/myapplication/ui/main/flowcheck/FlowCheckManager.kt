package ru.mg.myapplication.ui.main.flowcheck

import io.reactivex.Observable
import io.reactivex.Single
import ru.mg.myapplication.ui.main.FlowType
import ru.mg.myapplication.ui.main.flowcheck.producer.FlowProducer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlowCheckManager @Inject constructor(
    _flowProducers: Set<@JvmSuppressWildcards FlowProducer>
) {

    private val flowProducers = _flowProducers.sortedBy { it.priority }

    fun flow(): Single<FlowType> =
        Observable.fromIterable(flowProducers)
            .concatMap {
                it.flow()
                    .onErrorReturnItem(Flow.EmptyFlow)
            }
            .filter {
                it is Flow.CurrentFlow
            }
            .map { (it as Flow.CurrentFlow).flowType }
            .firstOrError()


}
