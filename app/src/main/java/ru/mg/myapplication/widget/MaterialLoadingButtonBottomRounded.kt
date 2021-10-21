package ru.mg.myapplication.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import ru.mg.myapplication.R

class MaterialLoadingButtonBottomRounded @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val button: MaterialButton
    private val progressBar: ProgressBar

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.material_loading_button_bottom_rounded, this, true)
        button = findViewById(R.id.m_button)
        progressBar = findViewById(R.id.m_progress_bar)

        context.obtainStyledAttributes(attrs, R.styleable.MaterialLoadingButtonBottomRounded)
            .apply {
                text = getString(R.styleable.MaterialLoadingButtonBottomRounded_text).orEmpty()
                val isWhiteButton =
                    getBoolean(R.styleable.MaterialLoadingButtonBottomRounded_isWhite, false)

                if (isWhiteButton) {
                    button.apply {
                        setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                        setStrokeWidthResource(R.dimen.stroke_width)
                        setStrokeColorResource(R.color.blazeOrange)
                        setTextColor(ContextCompat.getColor(context, R.color.blazeOrange))
                    }
                }
                recycle()
            }
    }


    var text: CharSequence
        get() = button.text
        set(value) {
            button.text = value
        }

    var isLoading: Boolean
        get() = progressBar.isVisible
        set(loading) {
            button.setTextColor(
                if (loading) {
                    ContextCompat.getColor(context, R.color.transparent)
                } else {
                    ContextCompat.getColor(context, R.color.white)
                }
            )
            button.isEnabled = !loading
            progressBar.isVisible = loading
        }

    override fun setEnabled(enabled: Boolean) {
        button.isEnabled = enabled
    }

    override fun isEnabled(): Boolean = button.isEnabled

    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }

}