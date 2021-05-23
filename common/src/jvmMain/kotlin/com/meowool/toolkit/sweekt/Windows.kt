@file:Suppress("NOTHING_TO_INLINE")

package com.meowool.toolkit.sweekt

import android.view.ViewGroup
import android.view.Window
import com.meowool.toolkit.sweekt.misc.safeCast

/**
 * Returns the root layout in this window.
 *
 * @see Window.findRootLayout
 */
val Window.rootLayout: ViewGroup
  get() = findRootLayout() ?: error("Can't find window root layout.")

/**
 * Find the root layout from this window, if not found, returns `null`.
 *
 * @see Window.rootLayout
 */
inline fun Window.findRootLayout(): ViewGroup? = decorView.safeCast<ViewGroup>()
  ?: decorView.findViewById(android.R.id.content)