@file:Suppress("NOTHING_TO_INLINE")

package com.meowool.toolkit.sweekt

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


/**
 * Returns the number of elements in the sequence.
 *
 * The operation is _terminal_.
 */
inline val <T> Sequence<T>.size: Int get() = this.count()

/**
 * Returns `true` if the sequence is empty.
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.isEmpty(): Boolean = this.none()

/**
 * Returns `true` if the sequence is not empty.
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.isNotEmpty(): Boolean = this.any()

/**
 * Returns `true` if this nullable sequence is either null or empty.
 *
 * The operation is _terminal_.
 *
 * @see isNotNullEmpty
 */
inline fun <T> Sequence<T>?.isNullOrEmpty(): Boolean {
  contract {
    returns(false) implies (this@isNullOrEmpty != null)
  }

  return this == null || this.isEmpty()
}

/**
 * Returns `true` if this nullable sequence is either null or empty.
 *
 * The operation is _terminal_.
 *
 * @see isNullOrEmpty
 */
inline fun <T> Sequence<T>?.isNotNullEmpty(): Boolean {
  contract {
    returns(true) implies (this@isNotNullEmpty != null)
  }

  return this?.isNotEmpty() == true
}

/**
 * Call the given [action] when this sequence is not null and not empty.
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>?.onNotNullEmpty(action: (Sequence<T>) -> Unit): Sequence<T>? {
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
 * Call the given [action] when this sequence is not empty.
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.onNotEmpty(action: (Sequence<T>) -> Unit): Sequence<T> {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isNotEmpty()) {
    action(this)
  }
  return this
}

/**
 * Call the given [action] when this sequence is null or empty.
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>?.onNullOrEmpty(action: (Sequence<T>?) -> Unit): Sequence<T>? {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isNullOrEmpty()) {
    action(this)
  }
  return this
}

/**
 * Call the given [action] when this sequence is empty.
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.onEmpty(action: (Sequence<T>) -> Unit): Sequence<T> {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isEmpty()) {
    action(this)
  }
  return this
}