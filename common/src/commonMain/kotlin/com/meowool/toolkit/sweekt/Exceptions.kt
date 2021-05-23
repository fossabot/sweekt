package com.meowool.toolkit.sweekt

/**
 * Return the value safely, if the process calling [trying] throws an exception, call [catching]
 * and return its result.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
inline fun <R> safetyValue(trying: () -> R, catching: () -> R): R = try {
  trying()
} catch (e: Throwable) {
  catching()
}

/**
 * Return the value safely, if the process calling [trying] throws an exception, return the `null`.
 */
inline fun <R> safetyValue(trying: () -> R): R? = try {
  trying()
} catch (e: Throwable) {
  null
}
