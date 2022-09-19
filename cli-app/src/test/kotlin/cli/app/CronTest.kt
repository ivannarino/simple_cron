package cli.app

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CronTest(
    private val cronItem: CronItem,
    private val currentTime: Time,
    private val result: String
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                // exercise tests
                arrayOf(
                    CronItem(MinuteCronConfig(30), HourCronConfig(1), "/bin/run_me_daily"),
                    Time(10, 16),
                    "1:30 tomorrow - /bin/run_me_daily"
                ),
                arrayOf(
                    CronItem(MinuteCronConfig(45), HourCronConfig.any(), "/bin/run_me_hourly"),
                    Time(10, 16),
                    "16:45 today - /bin/run_me_hourly"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig.any(),
                        HourCronConfig.any(),
                        "/bin/run_me_every_minute"
                    ), Time(10, 16), "16:10 today - /bin/run_me_every_minute"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig.any(),
                        HourCronConfig(19),
                        "/bin/run_me_sixty_times"
                    ), Time(10, 16), "19:00 today - /bin/run_me_sixty_times"
                ),

                // new cases and edge cases
                arrayOf(
                    CronItem(
                        MinuteCronConfig(45),
                        HourCronConfig.any(),
                        "/bin/run_me_sixty_times"
                    ), Time(47, 23), "0:45 tomorrow - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig(45),
                        HourCronConfig.any(),
                        "/bin/run_me_sixty_times"
                    ), Time(45, 23), "23:45 today - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig(45),
                        HourCronConfig.any(),
                        "/bin/run_me_sixty_times"
                    ), Time(43, 23), "23:45 today - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig.any(),
                        HourCronConfig(23),
                        "/bin/run_me_sixty_times"
                    ), Time(47, 12), "23:00 today - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig.any(),
                        HourCronConfig(1),
                        "/bin/run_me_sixty_times"
                    ), Time(47, 12), "1:00 tomorrow - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig.any(),
                        HourCronConfig(10),
                        "/bin/run_me_sixty_times"
                    ), Time(47, 12), "10:00 tomorrow - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig.any(),
                        HourCronConfig(3),
                        "/bin/run_me_sixty_times"
                    ), Time(30, 0), "3:00 today - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig(30),
                        HourCronConfig(3),
                        "/bin/run_me_sixty_times"
                    ), Time(45, 1), "3:30 today - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig(45),
                        HourCronConfig.any(),
                        "/bin/run_me_sixty_times"
                    ), Time(45, 0), "0:45 today - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig(12),
                        HourCronConfig.any(),
                        "/bin/run_me_sixty_times"
                    ), Time(5, 15), "15:12 today - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig(12),
                        HourCronConfig.any(),
                        "/bin/run_me_sixty_times"
                    ), Time(12, 15), "15:12 today - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig(12),
                        HourCronConfig.any(),
                        "/bin/run_me_sixty_times"
                    ), Time(59, 15), "16:12 today - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig(0),
                        HourCronConfig.any(),
                        "/bin/run_me_sixty_times"
                    ), Time(59, 15), "16:00 today - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig.any(),
                        HourCronConfig(11),
                        "/bin/run_me_sixty_times"
                    ), Time(59, 10), "11:00 today - /bin/run_me_sixty_times"
                ),
                arrayOf(
                    CronItem(
                        MinuteCronConfig.any(),
                        HourCronConfig(11),
                        "/bin/run_me_sixty_times"
                    ), Time(30, 11), "11:30 today - /bin/run_me_sixty_times"
                ),
            )
        }
    }

    @Test
    fun shouldReturnExpected() {
        assertEquals(result, Cron(currentTime).getNextRun(cronItem))
    }
}