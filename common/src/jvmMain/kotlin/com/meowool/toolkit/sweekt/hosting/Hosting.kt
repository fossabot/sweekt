@file:Suppress("OVERRIDE_BY_INLINE", "NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.meowool.toolkit.sweekt.hosting

import kotlin.reflect.KProperty

/**
 * Represents a [value] hosting class.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
interface Hosting<T> {

  /**
   * Get or host the value.
   *
   * Note that if not any value is hosted and get it, should throw an error.
   */
  var value: T

  /**
   * Returns the hosted value. If not any value is hosted in this hosting, calls the [defaultValue]
   * function, puts its result into the hosting and returns it.
   *
   * @see value
   */
  fun getOrHost(defaultValue: () -> T): T

  /**
   * Returns the hosted value safely. If not any value is hosted in this hosting, returns
   * the `null`, avoid calling [value] directly to cause an exception.
   *
   * @see value
   */
  fun getOrNull(): T?

  /**
   * Returns `true` if any value is hosted.
   */
  fun isHosting(): Boolean

  /**
   * Invalidate this hosting, this will clear the value currently being hosted.
   *
   * @see InvalidHosting
   */
  fun invalidate()

  /**
   * An extension to enter an [InvalidHosting] value to cancel the hosted value.
   *
   * E.g:
   * ```
   * var value: String by hosting { initializer }
   *
   * // reset
   * value = InvalidHosting
   * ```
   *
   * @see invalidate
   */
  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: InvalidHosting)

  /**
   * An extension allows to use instances of [Hosting] for mutable property delegation.
   *
   * E.g. `var value: String by hosting { initializer }`
   */
  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)

  /**
   * An extension allows to use instances of [Hosting] for immutable property delegation.
   *
   * E.g. `val value: String by hosting { initializer }`
   */
  operator fun getValue(thisRef: Any?, property: KProperty<*>): T
}

/**
 * A special class that allows instance of [Hosting] to cancel the value being hosted.
 *
 * @see Hosting.setValue
 * @see Hosting.invalidate
 */
object InvalidHosting

/**
 * Creates an empty [Hosting] to host the value at some point in the future.
 */
fun <T> hosting(): Hosting<T> = HostingImpl()

/**
 * Create a lazy [Hosting] instance.
 *
 * Only when the hosted value is called and it does not exist, will create a new value uses the
 * specified [initializer] and hosted it.
 *
 * This allows hosting to have the same behavior as [Lazy], please see [kotlin.lazy] for [lock]
 * and more details.
 */
fun <T> hosting(
  lock: Any? = null,
  initializer: () -> T
): Hosting<T> = LazyHostingImpl(lock, initializer)