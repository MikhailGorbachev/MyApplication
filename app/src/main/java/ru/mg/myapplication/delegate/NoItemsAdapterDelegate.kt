package ru.mg.myapplication.delegate

import androidx.annotation.StringRes
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_no_items.*
import ru.mg.myapplication.R

class NoItems(@StringRes val messageRes: Int)

fun noItemsAdapterDelegate() =
    adapterDelegateLayoutContainer<NoItems, Any>(R.layout.item_no_items) {
        bind {
            message.text = getString(item.messageRes)
        }
    }