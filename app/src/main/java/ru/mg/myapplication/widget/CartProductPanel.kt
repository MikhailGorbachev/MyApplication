package ru.mg.myapplication.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.product_cart_panel_view.view.*
import ru.mg.myapplication.R
import ru.mg.myapplication.ui.maincatalog.PackType
import java.math.BigDecimal

class CartProductPanel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var quantity: BigDecimal = BigDecimal.ONE
    var packType: PackType = PackType.COUNT
    var text: String = ""

    fun initPanel(qnty: BigDecimal, type: PackType, textValue: String = "") {
        quantity = qnty
        packType = type
        text = textValue
        refresh()
    }

    var changeListener: ((v: View, q: BigDecimal) -> Unit)? = null

    var clickListener: ((v: View, q: BigDecimal) -> Unit)? = null

    private val isSticky: Boolean

    private val textColor: Int

    private val panelBackground: Drawable?

    init {
        LayoutInflater.from(context).inflate(R.layout.product_cart_panel_view, this, true)

        context.obtainStyledAttributes(attrs, R.styleable.CartProductPanel).apply {
            isSticky = getBoolean(R.styleable.CartProductPanel_isSticky, false)
            textColor = getColor(
                R.styleable.CartProductPanel_textColor,
                ContextCompat.getColor(context, R.color.white)
            )
            panelBackground = getDrawable(R.styleable.CartProductPanel_background)
            recycle()
        }

        goods_quantity_btn.background = panelBackground ?: ContextCompat.getDrawable(
            context,
            if (isSticky) R.drawable.button_round_bottom_states else R.drawable.button_round_states
        )

        good_add_btn.apply {
            setOnClickListener {
                changeListener?.invoke(it, quantity + getStep())
            }
            setColorFilter(textColor)
        }


        good_remove_btn.apply {
            setOnClickListener {
                changeListener?.invoke(it, quantity - getStep())
            }
            setColorFilter(textColor)
        }

        good_quantity.apply {
            setTextColor(textColor)
            setOnClickListener {
                clickListener?.invoke(it, quantity)
            }
        }
    }

    private fun getStep(): BigDecimal = BigDecimal.ONE


    private fun refresh() {
        if (text.isNotBlank()) {
            good_quantity.text = text
            good_remove_btn.isVisible = false
            good_add_btn.isVisible = false
        } else {
            good_quantity.text = quantity.toInt().toString()
            good_remove_btn.isVisible = true
            good_add_btn.isVisible = true
        }
    }
}