package ru.mg.myapplication.ui.main.flowcheck

import ru.mg.myapplication.ui.main.FlowType

sealed class Flow {
    object EmptyFlow : Flow()
    class CurrentFlow(val flowType: FlowType): Flow()
}