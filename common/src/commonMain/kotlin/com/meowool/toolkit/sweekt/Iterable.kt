@file:Suppress("NOTHING_TO_INLINE")

package com.meowool.toolkit.sweekt

import com.meowool.toolkit.sweekt.iteration.asMutableList
import com.meowool.toolkit.sweekt.misc.safeCast
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Returns `true` if the iterable instance is empty.
 */
inline fun <T, I : Iterable<T>> I.isEmpty(): Boolean = this.none()

/**
 * Returns `true` if the iterable instance is null or empty.
 */
inline fun <T, I : Iterable<T>> I?.isNullOrEmpty(): Boolean = this == null || this.none()

/**
 * Returns `true` if the iterable instance is not empty.
 */
inline fun <T, I : Iterable<T>> I.isNotEmpty(): Boolean = this.any()

/**
 * Returns `true` if the iterable instance is not null and not empty.
 */
inline fun <T, I : Iterable<T>> I?.isNotNullEmpty(): Boolean {
  contract {
    returns(true) implies (this@isNotNullEmpty != null)
  }
  return this != null && this.isNotEmpty()
}

/**
 * Call the given [action] when this iterable instance is not null and not empty.
 */
inline fun <T, I : Iterable<T>> I?.onNotNullEmpty(action: (I) -> Unit): I? {
  contract {
    returnsNotNull() implies (this@onNotNullEmpty != null)
    callsInPlace(action, InvocationKind.AT_MOST_ONCE)
  }
  if (this.isNotNullEmpty()) {
    action(this)
  }
  return this
}

/**
 * Call the given [action] when this iterable instance is not empty.
 */
inline fun <T, I : Iterable<T>> I.onNotEmpty(action: (I) -> Unit): I {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isNotEmpty()) {
    action(this)
  }
  return this
}

/**
 * Call the given [action] when this iterable instance is null or empty.
 */
inline fun <T, I : Iterable<T>> I?.onNullOrEmpty(action: (I?) -> Unit): I? {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isNullOrEmpty()) {
    action(this)
  }
  return this
}

/**
 * Call the given [action] when this iterable instance is empty.
 */
inline fun <T, I : Iterable<T>> I.onEmpty(action: (I) -> Unit): I {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isEmpty()) {
    action(this)
  }
  return this
}

/**
 * Insert the given [element] at the first of the [Iterable] object and return [Iterable].
 */
fun <T> Iterable<T>.insertFirst(element: T): MutableList<T> =
  this.safeCast<MutableList<T>>()
    ?.apply { add(0, element) }
    ?: mutableListOf(element).also { it.addAll(this) }

/**
 * Insert the given [element] at the first of the [Iterable] object and return [Iterable].
 */
fun <T> Iterable<T>.insertLast(element: T): MutableList<T> =
  this.asMutableList().apply { add(element) }
