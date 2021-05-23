@file:JvmMultifileClass
@file:JvmName("PackageManager")

package com.meowool.toolkit.sweekt.pm

import android.content.Context


/**
 * Launch the given [packageName] of package.
 *
 * @param packageName the name of package to launched.
 */
fun Context.launchPackage(packageName: String) =
  this.packageManager.getLaunchIntentForPackage(packageName)?.run(this::startActivity)