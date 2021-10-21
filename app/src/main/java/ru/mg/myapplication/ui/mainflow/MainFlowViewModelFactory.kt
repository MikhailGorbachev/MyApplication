package ru.mg.myapplication.ui.mainflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class MainFlowViewModelFactory @Inject constructor(
    private val vm: Provider<MainFlowViewModel>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return vm.get() as T
    }

}