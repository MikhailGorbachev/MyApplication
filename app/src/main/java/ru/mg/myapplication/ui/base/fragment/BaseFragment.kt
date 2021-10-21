package ru.mg.myapplication.ui.base.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected val errorResolver: ErrorResolver = providerErrorResolverBuilder().build()

    @ColorRes
    protected abstract fun statusBarColorRes(): Int

    @ColorInt
    protected open fun statusBarColor(): Int = 0

    private val destinationChangeListener = NavController.OnDestinationChangedListener { _, _, _ ->
        activity?.let {
            hideKeyboard(it)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requireActivity().window.statusBarColor = getStatusBarColor()
        }
    }

    private fun getStatusBarColor(): Int {
        val color = statusBarColor()
        return if (color == 0) {
            ContextCompat.getColor(requireContext(), statusBarColorRes())
        } else {
            color
        }
    }

    override fun onStart() {
        super.onStart()
        findNavController().addOnDestinationChangedListener(destinationChangeListener)
    }

    override fun onStop() {
        super.onStop()
        findNavController().removeOnDestinationChangedListener(destinationChangeListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorResolver.view = view
    }

    override fun onDestroyView() {
        errorResolver.view = null
        super.onDestroyView()
    }

    protected open fun providerErrorResolverBuilder(): ErrorResolver.Builder =
        defaultErrorResolver()

}
