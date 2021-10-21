package ru.mg.myapplication.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import ru.mg.myapplication.App
import ru.mg.myapplication.R
import javax.inject.Inject

class MainActivity : BaseActivity() {

    companion object {
        const val ACTION_KEY = "type"
        const val ORDER_ID_KEY = "orderId"
        const val OPEN_ORDER_LIST_ACTION = "ORDER_STATUS_CHANGE"
    }

    @Inject
    lateinit var vmFactory: MainViewModelFactory

    private val vm: MainViewModel by lazy {
        viewModels<MainViewModel> { vmFactory }.value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        AppSignatureHelper(this).appSignatures

        App.get(this).appComponent.inject(this)
        vm.onCreate()

        vm.flow.observe(this, { flowType ->
            App.get(this).cleanComponents()

            when (flowType) {
                FlowType.NO_FLOW -> {
                }

                FlowType.MAIN -> {
                    openMainFlow()
                }
            }
        })
    }


}