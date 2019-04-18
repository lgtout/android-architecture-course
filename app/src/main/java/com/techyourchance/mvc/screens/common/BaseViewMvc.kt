package com.techyourchance.mvc.screens.common

import android.content.Context
import android.view.View

abstract class BaseViewMvc : ViewMvc {

    private var mRootView: View? = null

    protected val context: Context
        get() = rootView!!.context

    override fun getRootView(): View? {
        return mRootView
    }

    protected fun setRootView(rootView: View) {
        mRootView = rootView
    }

    protected fun <T : View> findViewById(id: Int): T {
        return rootView!!.findViewById(id)
    }
}
