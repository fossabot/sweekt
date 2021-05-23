@file:Suppress("UNCHECKED_CAST", "PropertyName", "OVERRIDE_BY_INLINE", "NOTHING_TO_INLINE")

package com.meowool.toolkit.sweekt.hosting

import kotlin.reflect.KProperty

internal class HostingImpl<T> : Hosting<T> {
  @Volatile internal var _value: Any? = InvalidHosting

  override var value: T
    get() = when (isHosting()) {
      true -> _value as T
      else -> error("You have not hosted any value!")
    }
    set(value) {
      _value = value
    }

  override inline fun getOrHost(defaultValue: () -> T): T {
    if (isHosting().not()) {
      _value = defaultValue()
    }
    return value
  }

  override fun isHosting(): Boolean = _value !== InvalidHosting

  override fun invalidate() {
    _value = InvalidHosting
  }

  override inline fun setValue(thisRef: Any?, property: KProperty<*>, value: InvalidHosting) {
    invalidate()
  }

  override inline fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    _value = value
  }

  override inline fun getValue(thisRef: Any?, property: KProperty<*>): T = value

  override fun toString(): String = value.toString()

  override fun getOrNull(): T? = if (isHosting()) value else null
}

internal class LazyHostingImpl<T>(
  lock: Any? = null,
  initializer: () -> T,
  private val base: HostingImpl<T> = HostingImpl()
) : Hosting<T> by base {
  private var initializer: (() -> T)? = initializer

  // final field is required to enable safe publication of constructed instance
  private val lock = lock ?: this

  override var value: T
    set(value) {
      base._value = value
    }
    get() = when (isHosting()) {
      true -> base._value as T
      else -> synchronized(lock) {
        when (isHosting()) {
          true -> base._value as T
          else -> {
            val typedValue = initializer!!()
            base._value = typedValue
            initializer = null
            typedValue
          }
        }
      }
    }

  override fun toString(): String = value.toString()
}