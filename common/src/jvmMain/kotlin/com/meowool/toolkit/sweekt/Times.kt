@file:Suppress("NOTHING_TO_INLINE")

package com.meowool.toolkit.sweekt

import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle.FULL
import java.util.*

/**
 * Returns the current instant from the system clock.
 */
inline val nowInstant: Instant get() = Instant.now()

/**
 * Returns the zoned date-time formed from current instant and the system zone.
 *
 * @see nowInstant
 */
inline val nowDateTime: ZonedDateTime get() = ZonedDateTime.now()

/**
 * Returns this year's number, such as `2021/03/01` is `2021`.
 */
inline val thisYear: Int get() = nowDateTime.year

/**
 * Returns this month's number in the year, such as `2021/03/01` is `3`.
 */
inline val thisMonth: Int get() = nowDateTime.monthValue

/**
 * Returns today's number in month, such as `2021/03/01` is `60`.
 */
inline val today: Int get() = nowDateTime.dayOfMonth

/**
 * Returns today's number in the year, such as `2021/03/01` is `1`.
 */
inline val todayOfYear: Int get() = nowDateTime.dayOfYear

/**
 * Returns today's number in the week, such as `2021/03/01` is [DayOfWeek.MONDAY].
 */
inline val todayOfWeek: DayOfWeek get() = nowDateTime.dayOfWeek

/**
 * Converts this instant to the number of milliseconds from the epoch of 1970-01-01T00:00:00Z.
 */
inline val Instant.epochMilli: Long get() = this.toEpochMilli()

/**
 * Converts this date-time to the number of milliseconds from the epoch of 1970-01-01T00:00:00Z.
 *
 * @see Instant.epochMilli
 */
inline val ZonedDateTime.epochMilli: Long get() = this.toInstant().epochMilli

/**
 * Converts the instant to zoned date-time.
 *
 * @param zone the time-zone to create zoned date-time.
 */
inline fun Instant.toDateTime(
  zone: ZoneId = ZoneId.systemDefault()
): ZonedDateTime = ZonedDateTime.ofInstant(this, zone)

/**
 * Formats this date-time using the localized formatter.
 *
 * @param locale the locale of the formatter.
 */
fun ZonedDateTime.format(locale: Locale = Locale.getDefault()): String =
  this.format(DateTimeFormatter.ofLocalizedDateTime(FULL).withLocale(locale))

/**
 * Formats the date-time using the given pattern and locale.
 *
 * @param pattern the pattern used to format date-time.
 * @param locale the locale of the formatter.
 */
fun ZonedDateTime.format(
  pattern: String,
  locale: Locale = Locale.getDefault()
): String = this.format(DateTimeFormatter.ofPattern(pattern, locale))

/**
 * Formats the date-time using the localized formatter.
 *
 * @param dateTime the date-time to be formatted.
 * @param locale the locale of the formatter.
 */
inline fun formatDateTime(
  dateTime: ZonedDateTime = nowDateTime,
  locale: Locale = Locale.getDefault()
): String = dateTime.format(locale)

/**
 * Formats the date-time using the given pattern and locale.
 *
 * @param pattern the pattern used to format date-time.
 * @param dateTime the date-time to be formatted.
 * @param locale the locale of the formatter.
 */
inline fun formatDateTime(
  pattern: String,
  dateTime: ZonedDateTime = nowDateTime,
  locale: Locale = Locale.getDefault()
): String = dateTime.format(pattern, locale)

/**
 * Formats this instant using the localized formatter.
 *
 * @param locale the locale of the formatter.
 */
inline fun Instant.format(locale: Locale = Locale.getDefault()): String =
  this.toDateTime().format(locale)

/**
 * Formats the instant using the given pattern and locale.
 *
 * @param pattern the pattern used to format date-time.
 * @param locale the locale of the formatter.
 */
inline fun Instant.format(
  pattern: String,
  locale: Locale = Locale.getDefault()
): String = this.toDateTime().format(pattern, locale)

/**
 * Formats the instant using the localized formatter.
 *
 * @param instant the instant to be formatted.
 * @param locale the locale of the formatter.
 */
inline fun formatInstant(
  instant: Instant = nowInstant,
  locale: Locale = Locale.getDefault()
): String = instant.format(locale)

/**
 * Formats the instant using the given pattern and locale.
 *
 * @param pattern the pattern used to format date-time.
 * @param instant the instant to be formatted.
 * @param locale the locale of the formatter.
 */
inline fun formatInstant(
  pattern: String,
  instant: Instant = nowInstant,
  locale: Locale = Locale.getDefault()
): String = instant.toDateTime().format(pattern, locale)

/**
 * Take this [Long] as milliseconds and returns instant.
 */
inline fun Long.asMilliInstant(): Instant =
  Instant.ofEpochMilli(this)

/**
 * Take this [Long] as seconds and returns instant.
 */
inline fun Long.asSecondInstant(nanoAdjustment: Long): Instant =
  Instant.ofEpochSecond(this, nanoAdjustment)

/**
 * Returns `true` if this zoned date-time belongs to this year.
 */
inline fun ZonedDateTime.isThisYear(): Boolean =
  this.year == thisYear

/**
 * Returns `true` if this instant belongs to this year.
 */
inline fun Instant.isThisYear(zone: ZoneId = ZoneId.systemDefault()): Boolean =
  this.toDateTime(zone).isThisYear()

/**
 * Returns `true` if this zoned date-time belongs to this month.
 */
fun ZonedDateTime.isThisMonth(): Boolean =
  this.year == thisYear && this.monthValue == thisMonth

/**
 * Returns `true` if this instant belongs to this month.
 */
inline fun Instant.isThisMonth(zone: ZoneId = ZoneId.systemDefault()): Boolean =
  this.toDateTime(zone).isThisMonth()

/**
 * Returns `true` if this zoned date-time belongs to today.
 */
fun ZonedDateTime.isToday(): Boolean =
  this.year == thisYear && this.dayOfYear == dayOfYear

/**
 * Returns `true` if this instant belongs to today.
 */
inline fun Instant.isToday(zone: ZoneId = ZoneId.systemDefault()): Boolean =
  this.toDateTime(zone).isToday()

/**
 * Resolves this [CharSequence] to [ZonedDateTime] using the given pattern and locale.
 *
 * @param pattern the pattern used to resolve time string.
 * @param locale the locale of the formatter.
 */
fun CharSequence.resolveToTime(
  pattern: String,
  locale: Locale = Locale.getDefault()
): ZonedDateTime = ZonedDateTime.parse(this, DateTimeFormatter.ofPattern(pattern, locale))

/**
 * Resolves this [CharSequence] to [ZonedDateTime] using the given [formatter].
 *
 * @param formatter the formatter to resolve time string.
 */
inline fun CharSequence.resolveToTime(
  formatter: DateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
): ZonedDateTime = ZonedDateTime.parse(this, formatter)

/**
 * Resolves this [CharSequence] to [Instant] using the given pattern and locale.
 *
 * @param pattern the pattern used to resolve time string.
 * @param locale the locale of the formatter.
 */
inline fun CharSequence.resolveToInstant(
  pattern: String,
  locale: Locale = Locale.getDefault()
): Instant = resolveToTime(pattern, locale).toInstant()

/**
 * Resolves this [CharSequence] to [Instant] using the given [formatter].
 *
 * @param formatter the formatter to resolve time string.
 */
inline fun CharSequence.resolveToInstant(
  formatter: DateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
): Instant = resolveToTime(formatter).toInstant()

/**
 * Resolves this [CharSequence] to epoch milliseconds using the given pattern and locale.
 *
 * @param pattern the pattern used to resolve time string.
 * @param locale the locale of the formatter.
 */
inline fun CharSequence.resolveToEpochMilli(
  pattern: String,
  locale: Locale = Locale.getDefault()
): Long = resolveToInstant(pattern, locale).epochMilli

/**
 * Resolves this [CharSequence] to epoch milliseconds using the given [formatter].
 *
 * @param formatter the formatter to resolve time string.
 */
inline fun CharSequence.resolveToEpochMilli(
  formatter: DateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
): Long = resolveToInstant(formatter).epochMilli

/**
 * Tests whether the [time] is between the given range.
 *
 * @param start the range start.
 * @param stop the range stop.
 * @param time the time to test.
 */
fun isTimeInRange(
  start: Instant,
  stop: Instant,
  time: Instant = nowInstant
): Boolean = time.isBefore(start).not() && time.isBefore(stop)

/**
 * Tests whether the [time] is between the given range.
 *
 * @param start the range start.
 * @param stop the range stop.
 * @param time the time to test.
 */
fun isTimeInRange(
  start: ZonedDateTime,
  stop: ZonedDateTime,
  time: ZonedDateTime = nowDateTime
): Boolean = time.isBefore(start).not() && time.isBefore(stop)

/**
 * Tests whether the [time] is between the given range.
 *
 * @param startTime the start time string of range.
 * @param stopTime the stop time string of range.
 * @param time the time to test.
 */
inline fun isTimeInRange(
  startTime: String,
  stopTime: String,
  time: ZonedDateTime = nowDateTime
): Boolean = isTimeInRange(startTime.resolveToTime(), stopTime.resolveToTime(), time)