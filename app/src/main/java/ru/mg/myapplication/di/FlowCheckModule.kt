package ru.mg.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import ru.mg.myapplication.ui.main.flowcheck.producer.AddressFlowProducer
import ru.mg.myapplication.ui.main.flowcheck.producer.AuthFlowProducer
import ru.mg.myapplication.ui.main.flowcheck.producer.FlowProducer
import ru.mg.myapplication.ui.main.flowcheck.producer.MainFlowProducer
import javax.inject.Singleton

@Module
class FlowCheckModule {

    @Provides
    @IntoSet
    @Singleton
    fun authFlowProducer(producer: AuthFlowProducer): FlowProducer = producer

    @Provides
    @IntoSet
    @Singleton
    fun addressFlowProducer(producer: AddressFlowProducer): FlowProducer = producer

    @Provides
    @IntoSet
    @Singleton
    fun mainFlowProducer(producer: MainFlowProducer): FlowProducer = producer

}