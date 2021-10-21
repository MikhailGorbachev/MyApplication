package ru.mg.myapplication.ui.mainflow

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main_flow.*
import ru.mg.myapplication.App
import ru.mg.myapplication.R
import ru.mg.myapplication.ui.base.fragment.AuthorizationFragment
import ru.mg.myapplication.ui.main.MainActivity.Companion.ACTION_KEY
import ru.mg.myapplication.ui.main.MainActivity.Companion.OPEN_ORDER_LIST_ACTION
import ru.mg.myapplication.ui.main.MainActivity.Companion.ORDER_ID_KEY
import ru.mg.myapplication.ui.main.MainViewModel
import javax.inject.Inject


class MainFlowFragment : AuthorizationFragment(R.layout.fragment_main_flow) {

    @Inject
    lateinit var viewModelFactory: MainFlowViewModelFactory

    private val vm: MainFlowViewModel by lazy {
        viewModels<MainFlowViewModel> { viewModelFactory }.value
    }

    private val mainViewModel: MainViewModel by lazy {
        activityViewModels<MainViewModel>().value
    }

    private var snackbar: Snackbar? = null

    fun getNavController() =
        (childFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment).navController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView.setupWithNavController(getNavController())

        bottomNavigationView.menu.findItem(R.id.cartFragment)?.setOnMenuItemClickListener {
            if (it.itemId == R.id.cartFragment) {
                AppMetricHelper.sendEvent(EventType.CART_TRANSITION_EVENT)
            }
            false
        }

        App.get(requireContext())
            .getMainFlowComponent()
            .inject(this)

        vm.viewState.observe(viewLifecycleOwner, { cartSize ->
            setCartSize(cartSize)
        })

        vm.error.observe(viewLifecycleOwner, { errorResolver.resolve(it) })

        requireActivity().intent.let {
            val action = it.getStringExtra(ACTION_KEY).orEmpty()
            if (action.equals(OPEN_ORDER_LIST_ACTION, ignoreCase = true)) {
                val orderId = it.getStringExtra(ORDER_ID_KEY)?.toLongOrNull()
                if (orderId != null) {
                    openOrder(orderId)
                }

                it.removeExtra(ORDER_ID_KEY)
                it.removeExtra(ACTION_KEY)
            }
        }

        mainViewModel.callbackTransition?.let {
            mainViewModel.clearFlowTypeFragment()
            openFragment(it.fragmentId, it.arguments)
        }

    }

    private fun setCartSize(cartSize: Int) {
        bottomNavigationView.getOrCreateBadge(R.id.cartFragment).apply {
            backgroundColor = ContextCompat.getColor(requireContext(), R.color.green)
            number = cartSize
            isVisible = cartSize > 0
        }
    }

}
