package ru.mg.myapplication.ui.base.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main_flow.*
import kotlinx.android.synthetic.main.fragment_search.*
import ru.mg.myapplication.R

abstract class AuthorizedFragment(@LayoutRes contentLayoutId: Int) :
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

    open fun isNavigationBarVisible() = true

    open fun isMessageLayoutVisible() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar?.setupWithNavController(findNavController())

        requireActivity().apply {
            bottomNavigationView?.isVisible = isNavigationBarVisible()
            message_layout?.isVisible = isMessageLayoutVisible()
        }
    }

}
