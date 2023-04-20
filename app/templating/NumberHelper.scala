package lila.app
package templating

import java.text.NumberFormat
import java.util.concurrent.ConcurrentHashMap
import play.api.i18n.Lang

trait NumberHelper { self: I18nHelper =>

  private val formatters = new ConcurrentHashMap[String, NumberFormat]

  private def formatter(using lang: Lang): NumberFormat =
    formatters.computeIfAbsent(
      lang.language,
      _ => NumberFormat.getInstance(lang.toLocale)
    )

  def showMillis(millis: Int)(using Lang) = formatter.format((millis / 100).toDouble / 10)

  implicit final class RichInt(number: Int):
    def localize(using Lang): String = formatter format number

  implicit final class RichLong(number: Long):
    def localize(using Lang): String = formatter format number
}
