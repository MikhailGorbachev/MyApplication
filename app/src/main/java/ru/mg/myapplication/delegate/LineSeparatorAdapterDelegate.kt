package ru.mg.myapplication.delegate

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import ru.mg.myapplication.R


class LineSeparator

fun lineSeparatorDelegate() =
    adapterDelegateLayoutContainer<LineSeparator, Any>(R.layout.item_line_separator) {}