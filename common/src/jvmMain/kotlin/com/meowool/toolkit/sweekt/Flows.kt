package com.meowool.toolkit.sweekt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.cancellation.CancellationException
import kotlin.experimental.ExperimentalTypeInference

/**
 * Switch the operation upstream of the stream to the main thread.
 *
 * Use this dispatcher to run a coroutine on the main Android thread. This should be used only for
 * interacting with the UI and performing quick work. Examples include calling suspend functions,
 * running Android UI framework operations, and updating LiveData objects.
 *
 * @see Dispatchers.Main
 */
fun <T> Flow<T>.flowOnUI(): Flow<T> = this.flowOn(Dispatchers.Main)

/**
 * Switch the operation upstream of the stream to the default thread.
 *
 * This dispatcher is optimized to perform CPU-intensive work outside of the main thread. Example
 * use cases include sorting a list and parsing JSON.
 *
 * @see Dispatchers.Default
 */
fun <T> Flow<T>.flowOnDefault(): Flow<T> = this.flowOn(Dispatchers.Default)

/**
 * Switch the operation upstream of the stream to the io thread.
 *
 * This dispatcher is optimized to perform disk or network I/O outside of the main thread. Examples
 * include using the Room component, reading from or writing to files, and running any network
 * operations.
 *
 * @see Dispatchers.IO
 */
fun <T> Flow<T>.flowOnIO(): Flow<T> = this.flowOn(Dispatchers.IO)

/**
 * Returns the number of elements in the flow.
 *
 * The operation is _terminal_.
 */
suspend inline fun <T> Flow<T>.size(): Int = this.count()

/**
 * Returns `true` if all elements match the given [predicate].
 *
 * The operation is _terminal_.
 */
suspend fun <T> Flow<T>.all(predicate: (T) -> Boolean): Boolean = try {
  collect { value ->
    if (!predicate(value)) {
      throw CancellationException()
    }
  }
  true
} catch (e: CancellationException) {
  false
}

/**
 * Returns `true` if flow has at least one element.
 *
 * The operation is _terminal_.
 */
suspend fun <T> Flow<T>.any(): Boolean = this.firstOrNull() != null

/**
 * Returns `true` if at least one element matches the given [predicate].
 *
 * The operation is _terminal_.
 */
suspend fun <T> Flow<T>.any(predicate: suspend (T) -> Boolean): Boolean =
  firstOrNull(predicate) != null

/**
 * Returns `true` if the flow has no elements.
 *
 * The operation is _terminal_.
 */
suspend inline fun <T> Flow<T>.none(): Boolean = this.firstOrNull() == null

/**
 * Returns `true` if no elements in flow match the given [predicate].
 *
 * The operation is _terminal_.
 */
suspend fun <T> Flow<T>.none(predicate: suspend (T) -> Boolean): Boolean =
  firstOrNull(predicate) == null

/**
 * Returns `true` if the flow was empty, and then cancels flow's collection.
 *
 * The operation is _terminal_.
 */
suspend inline fun <T> Flow<T>.isEmpty(): Boolean = this.none()

/**
 * Returns `true` if the flow is not empty.
 *
 * The operation is _terminal_.
 */
suspend inline fun <T> Flow<T>.isNotEmpty(): Boolean = this.any()

/**
 * Returns `true` if this nullable flow is either null or empty.
 *
 * The operation is _terminal_.
 *
 * @see isNotNullEmpty
 */
suspend inline fun <T> Flow<T>?.isNullOrEmpty(): Boolean {
  contract {
    returns(false) implies (this@isNullOrEmpty != null)
  }

  return this == null || this.isEmpty()
}

/**
 * Returns `true` if this nullable flow is either null or empty.
 *
 * The operation is _terminal_.
 *
 * @see isNullOrEmpty
 */
suspend inline fun <T> Flow<T>?.isNotNullEmpty(): Boolean {
  contract {
    returns(true) implies (this@isNotNullEmpty != null)
  }

  return this?.isNotEmpty() == true
}

/**
 * Call the given [action] when this flow is not null and not empty.
 *
 * The operation is _terminal_.
 */
suspend inline fun <T> Flow<T>?.onNotNullEmpty(action: (Flow<T>) -> Unit): Flow<T>? {
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
 * Call the given [action] when this flow is not empty.
 *
 * The operation is _terminal_.
 */
suspend inline fun <T> Flow<T>.onNotEmpty(action: (Flow<T>) -> Unit): Flow<T> {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isNotEmpty()) {
    action(this)
  }
  return this
}

/**
 * Call the given [action] when this flow is null or empty.
 *
 * The operation is _terminal_.
 */
suspend inline fun <T> Flow<T>?.onNullOrEmpty(action: (Flow<T>?) -> Unit): Flow<T>? {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isNullOrEmpty()) {
    action(this)
  }
  return this
}

/**
 * Call the given [action] when this flow is empty.
 *
 * The operation is _terminal_.
 */
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
suspend inline fun <T> Flow<T>.onEmpty(action: (Flow<T>) -> Unit): Flow<T> {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  if (this.isEmpty()) {
    action(this)
  }
  return this
}