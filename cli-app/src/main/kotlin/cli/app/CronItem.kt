package cli.app

import java.lang.IllegalStateException

private const val ANY = "*"
private const val ANY_VALUE = -1

data class CronItem(val minute: MinuteCronConfig, val hour: HourCronConfig, val binaryPath: String) {
    companion object {
        private const val INPUT_CRON_REGEX = "([0-9]{1,2}|\\*) ([0-9]{1,2}|\\*) (.+)"

        fun parse(input: String): CronItem {
            return Regex(INPUT_CRON_REGEX).find(input)?.let {
                val (minute, hour, scriptPath) = it.destructured

                CronItem(MinuteCronConfig.parse(minute), HourCronConfig.parse(hour), scriptPath)
            } ?: throw IllegalStateException("input does not match expected format $input")
        }
    }
}

data class HourCronConfig(val hour: Int) {
    val isAny = hour == ANY_VALUE

    companion object {
        fun any() = HourCronConfig(ANY_VALUE)

        fun parse(hour: String): HourCronConfig {
            return if (hour == ANY) {
                HourCronConfig(ANY_VALUE)
            } else {
                val parsedHour = hour.toIntOrNull() ?: throw IllegalStateException("hour should be an int")
                if (parsedHour !in 0..23) {
                    throw IllegalStateException("hour should be between 0 and 23")
                }
                HourCronConfig(parsedHour)
            }
        }
    }
}


data class MinuteCronConfig(val minute: Int) {
    val isAny = minute == ANY_VALUE

    companion object {
        fun any() = MinuteCronConfig(ANY_VALUE)

        fun parse(minute: String): MinuteCronConfig {
            return if (minute == ANY) {
                MinuteCronConfig(ANY_VALUE)
            } else {
                val parsedMinute = minute.toIntOrNull() ?: throw IllegalStateException("hour should be an int")
                if (parsedMinute !in 0..59) {
                    throw IllegalStateException("minute should be between 0 and 23")
                }
                MinuteCronConfig(parsedMinute)
            }
        }
    }
}

