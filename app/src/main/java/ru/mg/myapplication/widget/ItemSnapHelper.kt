package ru.mg.myapplication.widget


import android.content.Context
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import ru.mg.myapplication.R

class ItemSnapHelper : LinearSnapHelper() {

    private var context: Context? = null
    private var helper: OrientationHelper? = null
    private var scroller: Scroller? = null
    private var maxScrollDistance: Int = 0

    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        if (recyclerView != null) {
            context = recyclerView.context
            scroller = Scroller(context, DecelerateInterpolator())
        } else {
            scroller = null
            context = null
        }
        super.attachToRecyclerView(recyclerView)
    }

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val out = IntArray(2)
        out[0] = distanceToStart(targetView, helper(layoutManager))
        return out
    }

    override fun calculateScrollDistance(velocityX: Int, velocityY: Int): IntArray {
        val out = IntArray(2)
        val helper = helper ?: return out

        if (maxScrollDistance == 0) {
            maxScrollDistance = (helper.endAfterPadding - helper.startAfterPadding) / 2
        }

        scroller?.fling(0, 0, velocityX, velocityY, -maxScrollDistance, maxScrollDistance, 0, 0)
        out[0] = scroller?.finalX ?: 0
        out[1] = scroller?.finalY ?: 0
        return out
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper): Int {
        val childStart = helper.getDecoratedStart(targetView)
        val containerStart = helper.startAfterPadding

        return childStart - containerStart - (context?.resources?.getDimensionPixelOffset(R.dimen.banner_list_padding)
            ?: 0)
    }

    private fun helper(layoutManager: RecyclerView.LayoutManager?): OrientationHelper {
        if (helper == null) {
            helper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return helper!!
    }
}
