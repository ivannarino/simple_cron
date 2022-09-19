package cli.app

data class Time(val minute: Int, val hour: Int) : Comparable<Time> {

    fun isToday(currentTime: Time): Boolean {
        return this >= currentTime
    }

    override fun compareTo(other: Time): Int {
        return when {
            hour < other.hour -> -1
            hour > other.hour -> 1
            else -> minute.compareTo(other.minute)
        }
    }

    override fun toString(): String {
        return "$hour:${String.format("%02d", minute)}"
    }
}