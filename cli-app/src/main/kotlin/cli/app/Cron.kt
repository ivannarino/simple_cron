package cli.app

import java.lang.IllegalStateException

private const val HOURS_IN_A_DAY = 24

class Cron(private val currentTime: Time) {

    init {
        if (currentTime.hour !in 0..23) {
            throw IllegalStateException("hour should be a int between 0 and 23")
        }

        if (currentTime.minute !in 0..59) {
            throw IllegalStateException("hour should be a int between 0 and 23")
        }
    }

    /**
     * Get next run (including the soonest occurrence) formatted as a String
     */
    fun getNextRun(cron: CronItem): String {
        val (cronMinute, cronHour) = cron

        val nextOccurrenceTime = when {
            cronMinute.isAny && cronHour.isAny -> currentTime
            !cronMinute.isAny && !cronHour.isAny -> {
                Time(cronMinute.minute, cronHour.hour)
            }
            cronMinute.isAny && !cronHour.isAny -> {
                Time(if (cronHour.hour == currentTime.hour) currentTime.minute else 0, cronHour.hour)
            }
            !cronMinute.isAny && cronHour.isAny -> {
                Time(cronMinute.minute, if (currentTime.minute <= cronMinute.minute) currentTime.hour else (currentTime.hour + 1) % HOURS_IN_A_DAY)
            }
            else -> throw IllegalStateException("Should not get here")
        }

        return "$nextOccurrenceTime ${nextOccurrenceTime.getDayLabel(currentTime)} - ${cron.binaryPath}"
    }

    private fun Time.getDayLabel(time: Time): String {
        return if (isToday(time)) "today" else "tomorrow"
    }

    /**
     * Get next runs (including the soonest occurrence) for a list of cron expressions.
     */
    fun getNextRuns(cronList: List<CronItem>): List<String> {
        return cronList.map { getNextRun(it) }
    }
}
