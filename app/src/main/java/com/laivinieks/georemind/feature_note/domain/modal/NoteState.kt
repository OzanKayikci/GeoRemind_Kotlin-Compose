package com.laivinieks.georemind.feature_note.domain.modal

import com.laivinieks.georemind.feature_note.domain.util.NoteOrder
import com.laivinieks.georemind.core.domain.util.OrderType

data class NoteState(
    val notes:List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    var isOrderSectionVisible:Boolean = false
)
