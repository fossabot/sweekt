@file:Suppress("NOTHING_TO_INLINE")

package com.meowool.toolkit.sweekt

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Returns `true` if the array is not empty.
 */
inline fun <T> Array<T>?.isNotNullEmpty(): Boolean {
  contract {
    returns(true) implies (this@isNotNullEmpty != null)
  }
  return this != null && this.isNotEmpty()
}

/**
 * Call the given [action] when this array is not null and not empty.
 */
inline fun <T> Array<T>?.onNotNullEmpty(action: (Array<T>) -> Unit): Array<T>? {
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
 * Call the given [action] when this array is not empty.
 */
inline fun <T> Array<T>.onNotEmpty(action: (Array<T>) -> Unit): Array<T> {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isNotEmpty()) {
    action(this)
  }
  return this
}

/**
 * Call the given [action] when this array is null or empty.
 */
inline fun <T> Array<T>?.onNullOrEmpty(action: (Array<T>?) -> Unit): Array<T>? {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isNullOrEmpty()) {
    action(this)
  }
  return this
}

/**
 * Call the given [action] when this array is empty.
 */
inline fun <T> Array<T>.onEmpty(action: (Array<T>) -> Unit): Array<T> {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isEmpty()) {
    action(this)
  }
  return this
}