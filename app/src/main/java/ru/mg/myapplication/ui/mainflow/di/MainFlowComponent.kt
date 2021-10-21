package ru.mg.myapplication.ui.mainflow.di

import dagger.Subcomponent
import ru.mg.myapplication.ui.mainflow.MainFlowFragment

@MainFlowScope
@Subcomponent(modules = [MainFlowModule::class])
interface MainFlowComponent {

    fun inject(fragment: MainFlowFragment)
}