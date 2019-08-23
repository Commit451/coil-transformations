@file:Suppress("NOTHING_TO_INLINE")

package com.commit451.coiltransformations.sample

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

inline fun <reified V : View> ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): V {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot) as V
}

inline fun <T> unsafeLazy(noinline initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

inline fun <reified T : ViewModel> FragmentActivity.bindViewModel() = unsafeLazy {
    ViewModelProviders.of(this).get(T::class.java)
}

inline val AndroidViewModel.context: Context
    get() = getApplication()

fun <V : View> Activity.bindView(@IdRes id: Int) = unsafeLazy { findViewById<V>(id) }
