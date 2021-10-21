package ru.mg.myapplication.ui.main.flowcheck.producer

import io.reactivex.Observable
import ru.mg.myapplication.ui.main.FlowType
import ru.mg.myapplication.ui.main.flowcheck.Flow
import ru.mg.myapplication.ui.main.flowcheck.addressFlowPriority
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressFlowProducer @Inject constructor() : FlowProducer {

    @Inject
    lateinit var addressRepository: AddressRepository

    override val priority: Int = addressFlowPriority

    override fun flow(): Observable<Flow> =
        addressRepository.addresses()
            .map {
                if (it.isNotEmpty()) {
                    Flow.EmptyFlow
                } else {
                    Flow.CurrentFlow(FlowType.ADDRESS)
                }
            }
}