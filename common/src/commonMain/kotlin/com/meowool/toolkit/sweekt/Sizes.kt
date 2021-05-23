@file:Suppress("SpellCheckingInspection", "NAME_SHADOWING")

package com.meowool.toolkit.sweekt

import java.util.*
import kotlin.math.abs

/**
 * For the definition of data size units.
 *
 * The terms and units used in this class are based on [Binary Prefix](https://en.wikipedia.org/wiki/Binary_prefix)
 *
 * @author 凛 (https://github.com/RinOrz)
 */
interface DataSizeUnits {
  /**
   * Whether to use the International System of Units to calculate units.
   *
   * [For SI details](https://en.wikipedia.org/wiki/Binary_prefix#cite_note-d.SIp-5)
   * [For Binary details](https://en.wikipedia.org/wiki/Binary_prefix#cite_note-d.binpref-11)
   */
  var useSI: Boolean

  /**
   * A unit with the same meaning as `kilo`.
   *
   * [Reference](https://en.wikipedia.org/wiki/Kibibit)
   */
  val kibi: String

  /**
   * A unit with the same meaning as `mega`.
   *
   * [Reference](https://en.wikipedia.org/wiki/Mebibit)
   */
  val mebi: String

  /**
   * A unit with the same meaning as `giga`.
   *
   * [Reference](https://en.wikipedia.org/wiki/Gibibit)
   */
  val gibi: String

  /**
   * A unit with the same meaning as `terabit`.
   *
   * [Reference](https://en.wikipedia.org/wiki/Tebibit)
   */
  val tebi: String

  /**
   * A unit with the same meaning as `petabit`.
   *
   * [Reference](https://en.wikipedia.org/wiki/Pebibit)
   */
  val pebi: String

  /**
   * A unit with the same meaning as `exabit`.
   *
   * [Reference](https://en.wikipedia.org/wiki/Exbibit)
   */
  val exbi: String
}

object DefaultDataSizeUnits : DataSizeUnits {
  override var useSI: Boolean = true
  override val kibi: String
    get() = if (useSI) "K" else "Ki"
  override val mebi: String
    get() = if (useSI) "M" else "Mi"
  override val gibi: String
    get() = if (useSI) "G" else "Gi"
  override val tebi: String
    get() = if (useSI) "T" else "Ti"
  override val pebi: String
    get() = if (useSI) "P" else "Pi"
  override val exbi: String
    get() = if (useSI) "E" else "Ei"
}

/**
 * TODO Add to Unit testing.
 * Converts the long size to human-readable string.
 *
 * For precision 1 example:
 * ```
 *                              SI     BINARY
 *
 *                   0:        0 B        0 B
 *                  27:       27 B       27 B
 *                 999:      999 B      999 B
 *                1000:     1.0 kB     1000 B
 *                1023:     1.0 kB     1023 B
 *                1024:     1.0 kB    1.0 KiB
 *                1728:     1.7 kB    1.7 KiB
 *              110592:   110.6 kB  108.0 KiB
 *             7077888:     7.1 MB    6.8 MiB
 *           452984832:   453.0 MB  432.0 MiB
 *         28991029248:    29.0 GB   27.0 GiB
 *       1855425871872:     1.9 TB    1.7 TiB
 * 9223372036854775807:     9.2 EB    8.0 EiB   (Long.MAX_VALUE)
 * ```
 *
 * [Original source](https://stackoverflow.com/a/3758880)
 * [For more details](https://en.wikipedia.org/wiki/Binary_prefix)
 *
 * @param precision the precision of result size.
 * @param prefix the string added before to the result size string.
 * @param suffix the string added after to the result size string.
 * @param separator the string added after size and before unit.
 * @param units determine the name of unit used to result size string.
 * @param useSI to see [DataSizeUnits.useSI].
 *
 * @see DataSizeUnits
 */
fun Long.toReadableSize(
  precision: Int = 2,
  prefix: String? = null,
  suffix: String? = "B",
  separator: String? = " ",
  units: DataSizeUnits = DefaultDataSizeUnits,
  useSI: Boolean = units.useSI,
): String {
  units.useSI = useSI

  val units = arrayOf(
    units.kibi,
    units.mebi,
    units.gibi,
    units.tebi,
    units.pebi,
    units.exbi
  )

  val result = if (useSI) {
    this.toHumanReadableSI(units, separator, precision)
  } else {
    this.toHumanReadableBin(units, separator, precision)
  }

  return buildString {
    prefix?.apply(::append)
    append(result)
    suffix?.apply(::append)
  }
}

private fun Long.toHumanReadableSI(units: Array<String>, separator: String?, precision: Int): String {
  var bytes = this
  if (-1000 < bytes && bytes < 1000) {
    return buildString {
      append(bytes)
      separator?.apply(::append)
    }
  }

  var index = 0
  while (bytes <= -999950 || bytes >= 999950) {
    bytes /= 1000
    index++
  }

  return buildString {
    append("%.${precision}f".format(Locale.getDefault(), bytes / 1000.0))
    separator?.apply(::append)
    append(units[index])
  }
}

private fun Long.toHumanReadableBin(units: Array<String>, separator: String?, precision: Int): String {
  val bytes = this

  val absB = if (bytes == Long.MIN_VALUE) Long.MAX_VALUE else abs(bytes)
  if (absB < 1024) {
    return buildString {
      append(bytes)
      separator?.apply(::append)
    }
  }
  var value = absB
  var i = 40

  var index = 0
  while (i >= 0 && absB > 0xfffccccccccccccL shr i) {
    value = value shr 10
    i -= 10
    index++
  }
  value *= java.lang.Long.signum(bytes).toLong()

  return buildString {
    append("%.${precision}f".format(Locale.getDefault(), value / 1024.0))
    separator?.apply(::append)
    append(units[index])
  }
}