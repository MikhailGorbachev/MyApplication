package ru.mg.myapplication.ui.main.flowcheck.producer

import io.reactivex.Observable
import ru.mg.myapplication.ui.main.flowcheck.Flow

interface FlowProducer {

    val priority: Int

    fun flow(): Observable<Flow>
}