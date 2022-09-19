package cli.app

import cli.app.App.defaultInput
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.optional

private const val TIME_REGEX = "([0-9]{2}):([0-9]{2})"

fun main(args: Array<String>) {
    val input = getStdIn()
    val parser = ArgParser("${App.appName}:: ${App.version}")
    val currentTime by parser.argument(ArgType.String, description = "Current time (HH:MM)").optional().default("16:10")

    parser.parse(args)

    Regex(TIME_REGEX).find(currentTime)?.let {
        val (hour, minute) = it.destructured

        if (minute.toIntOrNull() == null || hour.toIntOrNull() == null) {
            throw IllegalStateException("time should have valid integers")
        }

        val cron = Cron(Time(minute.toInt(), hour.toInt()))
        cron.getNextRuns(input.ifEmpty { defaultInput.lines() }.map { CronItem.parse(it) }).onEach {
            println(it)
        }
    } ?: throw IllegalStateException("time does not match HH:MM")
}

private fun getStdIn(): List<String> {
    val input = generateSequence(::readLine)
    return input.toList()
}

object App {
    const val appName = "Cron CLI App"
    const val version = "0.0.1"

    val defaultInput = """
30 1 /bin/run_me_daily
45 * /bin/run_me_hourly
* * /bin/run_me_every_minute
* 19 /bin/run_me_sixty_times
""".trimIndent()
}