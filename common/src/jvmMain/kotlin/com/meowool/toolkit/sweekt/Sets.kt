package com.meowool.toolkit.sweekt

import com.meowool.toolkit.sweekt.iteration.asMutableSet

/**
 * Insert the given [element] at the first of the [Set] object and return [Set].
 */
fun <T> Set<T>.insertFirst(element: T): MutableSet<T> =
  mutableSetOf(element).also { it.addAll(this) }

/**
 * Insert the given [element] at the first of the [Set] object and return [Set].
 */
fun <T> Set<T>.insertLast(element: T): MutableSet<T> =
  this.asMutableSet().apply { add(element) }