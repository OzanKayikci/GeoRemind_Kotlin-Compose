package com.laivinieks.georemind.core.domain.util

sealed class OrderType {
    data object Ascending : OrderType()
    data object Descending : OrderType()
}
