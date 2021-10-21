package ru.mg.myapplication.delegate

import androidx.core.view.updateLayoutParams
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import ru.mg.myapplication.R


const val HEIGHT_4_DP = 4
const val HEIGHT_6_DP = 6
const val HEIGHT_8_DP = 8
const val HEIGHT_12_DP = 8
const val HEIGHT_16_DP = 16
const val HEIGHT_25_DP = 25
const val HEIGHT_32_DP = 32

open class SimpleSeparator(
    val heightDp: Int? = 0,
    val heightPx: Int? = 0
) {

    class Simple4DpSeparator : SimpleSeparator(heightDp = HEIGHT_4_DP)
    class Simple6DpSeparator : SimpleSeparator(heightDp = HEIGHT_6_DP)
    class Simple8DpSeparator : SimpleSeparator(heightDp = HEIGHT_8_DP)
    class Simple16DpSeparator : SimpleSeparator(heightDp = HEIGHT_16_DP)
    class Simple25DpSeparator : SimpleSeparator(heightDp = HEIGHT_25_DP)
    class Simple32DpSeparator : SimpleSeparator(heightDp = HEIGHT_32_DP)
    class Simple12DpSeparator : SimpleSeparator(heightDp = HEIGHT_12_DP)

    fun copy(heightDp: Int? = this.heightDp, heightPx: Int? = this.heightPx): SimpleSeparator =
        SimpleSeparator(heightDp, heightPx)

}

fun simpleSeparatorAdapterDelegate() =
    adapterDelegateLayoutContainer<SimpleSeparator, Any>(R.layout.item_simple_separator) {
        bind {
            itemView.updateLayoutParams {
                height = item.heightDp?.toPx(context)
                    ?: item.heightPx?.toDp(context) ?: 0
            }
        }
    }

fun simpleVerticalSeparatorAdapterDelegate() =
    adapterDelegateLayoutContainer<SimpleSeparator, Any>(R.layout.item_simple_vertical_separator) {
        bind {
            itemView.updateLayoutParams {
                width = item.heightDp?.toPx(context)
                    ?: item.heightPx?.toDp(context) ?: 0
            }
        }
    }