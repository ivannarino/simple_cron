package cli.app

import kotlin.test.Test
import kotlin.test.assertNotNull

class AppTest {
    @Test fun testAppHasAGreeting() {
        assertNotNull(App.appName, "Cron CLI App")
        assertNotNull(App.version, "0.0.1")
    }
}
