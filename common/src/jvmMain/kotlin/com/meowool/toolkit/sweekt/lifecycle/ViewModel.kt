@file:Suppress("DeferredIsResult")

package com.meowool.toolkit.sweekt.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meowool.toolkit.sweekt.asyncDefault
import com.meowool.toolkit.sweekt.asyncIO
import com.meowool.toolkit.sweekt.asyncUI
import com.meowool.toolkit.sweekt.launchDefault
import com.meowool.toolkit.sweekt.launchIO
import com.meowool.toolkit.sweekt.launchUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Launch a coroutine in the [ViewModel.viewModelScope].
 *
 * For more details [CoroutineScope.launch].
 */
fun ViewModel.launch(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  action: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(context, start, action)

/**
 * Launch a coroutine in the [ViewModel.viewModelScope].
 *
 * For more details [CoroutineScope.launchUI].
 */
fun ViewModel.launchUI(
  start: CoroutineStart = CoroutineStart.DEFAULT,
  action: suspend CoroutineScope.() -> Unit
) = viewModelScope.launchUI(start, action)

/**
 * Launch a coroutine in the [ViewModel.viewModelScope].
 *
 * For more details [CoroutineScope.launchDefault].
 */
fun ViewModel.launchDefault(
  start: CoroutineStart = CoroutineStart.DEFAULT,
  action: suspend CoroutineScope.() -> Unit
) = viewModelScope.launchDefault(start, action)

/**
 * Launch a coroutine in the [ViewModel.viewModelScope].
 *
 * For more details [CoroutineScope.launchIO].
 */
fun ViewModel.launchIO(
  start: CoroutineStart = CoroutineStart.DEFAULT,
  action: suspend CoroutineScope.() -> Unit
) = viewModelScope.launchIO(start, action)


/**
 * Launch a coroutine in the [ViewModel.viewModelScope].
 *
 * For more details [CoroutineScope.asyncUI].
 */
fun <T> ViewModel.asyncUI(
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = viewModelScope.asyncUI(start, block)

/**
 * Launch a coroutine in the [ViewModel.viewModelScope].
 *
 * For more details [CoroutineScope.asyncDefault].
 */
fun <T> ViewModel.asyncDefault(
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = viewModelScope.asyncDefault(start, block)

/**
 * Launch a coroutine in the [ViewModel.viewModelScope].
 *
 * For more details [CoroutineScope.asyncIO].
 */
fun <T> ViewModel.asyncIO(
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> T
): Deferred<T> = viewModelScope.asyncIO(start, block)