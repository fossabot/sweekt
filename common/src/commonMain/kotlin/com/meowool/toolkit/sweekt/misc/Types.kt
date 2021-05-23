package com.meowool.toolkit.sweekt.misc

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass


/**
 * Cast this object type.
 *
 * @param T result type after conversion
 *
 * @throws ClassCastException [T] type does not match with this object.
 */
@Throws(ClassCastException::class)
inline fun <reified T> Any?.cast(): T {
  contract {
    returns() implies (this@cast is T)
  }
  return this as T
}

/**
 * Safe convert this object to [T] type.
 *
 * @param T result type after conversion, returns `null` if the type does not matches.
 */
inline fun <reified T> Any?.safeCast(): T? {
  contract {
    returnsNotNull() implies (this@safeCast is T)
    returns(null) implies (this@safeCast !is T)
  }
  return this as? T
}

/**
 * When the this object type is [T], call the [action].
 *
 * @return returns `null` if the object type is not [T], otherwise returns itself.
 */
inline fun <reified T> Any?.withType(action: T.() -> Unit): T? {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  return this.safeCast<T>()?.apply(action)
}

/**
 * When the this object type meets [expected], call the [action].
 *
 * ```kotlin
 * val foo: Any = "abc"
 * val result = foo.whenType(String::class) {
 *   this.replace("c", "d")
 * }
 *
 * result -> "abd"
 * ```
 *
 * @return returns `null` if the object type is not [T], otherwise returns the object by
 * given [action] result.
 */
@Suppress("UNUSED_PARAMETER")
inline fun <reified T: Any, R> Any?.withType(expected: KClass<T>, action: T.() -> R): R? {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
  return this.safeCast<T>()?.let(action)
}