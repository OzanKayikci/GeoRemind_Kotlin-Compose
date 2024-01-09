package com.laivinieks.georemind.feature_alarm.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laivinieks.georemind.core.domain.util.TimeConverters.convertLocalTimeToUtc
import com.laivinieks.georemind.feature_alarm.domain.AlarmItem
import com.laivinieks.georemind.feature_alarm.domain.AlarmScheduler
import com.laivinieks.georemind.feature_alarm.domain.use_case.GetAllAlarmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmScheduler:
    AlarmScheduler,
    private val getAllAlarmUseCase: GetAllAlarmsUseCase

) : ViewModel() {
    private val _alarms = mutableStateOf<List<AlarmItem>>(emptyList())
    val alarms: MutableState<List<AlarmItem>> = _alarms

    fun createAlarm() {
        removeAlarm()
        alarms.value.forEach {
            alarmScheduler.schedule(it)
        }
    }

    private fun removeAlarm() {
        alarms.value.forEach {
            alarmScheduler.cancel(it)

        }
    }

    private var getAlarmsJob: Job? = null

    init {
        getAlarms()
    }

    private fun getAlarms() {
        getAlarmsJob?.cancel()
        getAlarmsJob = getAllAlarmUseCase().onEach { savedAlarms ->
            var reminderAlarms: List<AlarmItem> = emptyList()
            savedAlarms.forEach { reminder ->

                if (reminder.remindTime == null || System.currentTimeMillis() > convertLocalTimeToUtc(reminder.remindTime)) {

                    return@forEach
                }

                reminderAlarms = reminderAlarms.plus(
                    AlarmItem(
                        id = reminder.id!!,
                        alarmTime = reminder.remindTime,
                        message = reminder.content.take(50),
                        title = reminder.title
                    )
                )
            }

            _alarms.value = reminderAlarms

        }.launchIn(viewModelScope)
    }
}