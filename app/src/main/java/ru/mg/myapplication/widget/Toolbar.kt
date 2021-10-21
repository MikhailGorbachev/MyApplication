package ru.mg.myapplication.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.navigation.NavController
import kotlinx.android.synthetic.main.toolbar_view.view.*
import ru.mg.myapplication.R

class Toolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var title: String = ""
        set(value) {
            field = value
            title_view.text = title
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.toolbar_view, this, true)
    }

    fun setupWithNavController(navController: NavController) {
        title = navController.currentDestination?.label.toString()
        back_btn.setOnClickListener {
            navController.popBackStack()
        }
    }

    fun setNavigationOnClickListener(click: () -> Unit) {
        click()
    }
}