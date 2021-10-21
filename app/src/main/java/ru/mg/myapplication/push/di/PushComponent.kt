package ru.mg.myapplication.push.di

import dagger.Subcomponent
import ru.mg.myapplication.push.FirebaseMessagingServiceCustom

@PushScope
@Subcomponent(modules = [PushBindsModule::class])
interface PushComponent {

    fun inject(service: FirebaseMessagingServiceCustom)
}