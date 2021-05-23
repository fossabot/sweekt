package com.meowool.toolkit.sweekt

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable


/**
 * Create a [BitmapDrawable] from this [Bitmap] by the give [context].
 */
fun Bitmap.toDrawable(context: Context): Drawable =
  BitmapDrawable(context.resources, this)


/**
 * Create a [BitmapDrawable] from this [Bitmap].
 */
fun Bitmap.toDrawable(resources: Resources = Resources.getSystem()): Drawable =
  BitmapDrawable(resources, this)
