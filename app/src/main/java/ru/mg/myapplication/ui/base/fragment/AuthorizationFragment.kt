package ru.mg.myapplication.ui.base.fragment

import android.view.View
import androidx.annotation.LayoutRes
import ru.mg.myapplication.R

abstract class AuthorizationFragment(@LayoutRes contentLayoutId: Int) :
    BaseFragment(contentLayoutId) {

    override fun statusBarColorRes(): Int {
        activity?.window?.decorView?.apply {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                systemUiVisibility =
                    systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            }
        }
        return R.color.white
    }

    override fun statusBarColor(): Int = 0
}
