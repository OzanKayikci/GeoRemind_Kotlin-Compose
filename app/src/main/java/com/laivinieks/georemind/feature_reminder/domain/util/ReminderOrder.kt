package com.laivinieks.georemind.feature_reminder.domain.util

import com.laivinieks.georemind.core.domain.util.OrderType

sealed class ReminderOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : ReminderOrder(orderType)
    class Date(orderType: OrderType) : ReminderOrder(orderType)
    class Color(orderType: OrderType) : ReminderOrder(orderType)
    class Location(orderType: OrderType) : ReminderOrder(orderType)
    class ReminderTime(orderType: OrderType) : ReminderOrder(orderType)

    fun copy(orderType: OrderType): ReminderOrder {
        return when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
            is Location -> Location(orderType)
            is ReminderTime -> ReminderTime(orderType)
        }
    }

}
