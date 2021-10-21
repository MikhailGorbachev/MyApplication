package ru.mg.myapplication.ui.main

import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import kotlinx.android.synthetic.main.activity_main.*
import ru.mg.myapplication.R


fun MainActivity.navigateTo(@IdRes destinationId: Int) {
    if (flow.findNavController().currentDestination?.id != destinationId) {
        flow.findNavController().navigate(destinationId)
    }
}

fun MainActivity.openMainFlow() {
    flow.findNavController()
        .navigate(
            R.id.mainFlowFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.mainFlowFragment, true)
                .setEnterAnim(R.anim.slide_from_right_to_center)
                .setExitAnim(R.anim.constant)
                .build()
        )
}

