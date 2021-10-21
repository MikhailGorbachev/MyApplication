package ru.mg.myapplication.ui.mainflow.di

import dagger.Module
import dagger.Provides

@Module
class MainFlowModule {

    @Provides
    @MainFlowScope
    fun orderHistoryApi(@AuthorizationApiBuilder apiBuilder: ApiBuilder): OrderHistoryApi =
        apiBuilder.create()

}