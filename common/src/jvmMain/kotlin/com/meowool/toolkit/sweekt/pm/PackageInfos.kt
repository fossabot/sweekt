@file:Suppress("NOTHING_TO_INLINE")
@file:JvmMultifileClass
@file:JvmName("PackageManager")

package com.meowool.toolkit.sweekt.pm

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

/**
 * Returns `true` if this package has been installed.
 */
val PackageInfo.isInstalled: Boolean
  get() = try {
    this.applicationInfo.enabled
  } catch (e: Exception) {
    false
  }

/**
 * Returns the compatible version code of this package info.
 */
val PackageInfo.versionCodeCompat: Long
  get() = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> this.longVersionCode
    else -> @Suppress("DEPRECATION") this.versionCode.toLong()
  }

/**
 * Bridges [PackageManager.getPackageInfo] to make it more convenient to use.
 */
inline fun Context.getPackageInfo(packageName: String, flags: Int = 0): PackageInfo =
  this.packageManager.getPackageInfo(packageName, flags)

/**
 * Bridges [PackageManager.getPackageInfo] to make it more convenient to use.
 */
inline fun PackageManager.getPackageInfo(packageName: String): PackageInfo =
  this.getPackageInfo(packageName, 0)