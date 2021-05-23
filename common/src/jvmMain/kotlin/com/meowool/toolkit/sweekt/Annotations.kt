package com.meowool.toolkit.sweekt

/**
 * Used to mark APIs that are not available externally.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Retention(value = AnnotationRetention.SOURCE)
@RequiresOptIn(
  level = RequiresOptIn.Level.ERROR,
  message = "This is an internal com.meowool.toolkit.sweekt API that should not be used from outside."
)
annotation class InternalSweektApi