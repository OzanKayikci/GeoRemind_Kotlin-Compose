package com.laivinieks.georemind.core.data.data_source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.laivinieks.georemind.core.domain.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.DATA_STORE_NAME)

    }

    suspend fun storePermissionIsDenied(isDenied: Boolean, permissionType: Preferences.Key<Boolean>) {
        context.dataStore.edit { preferences ->
            preferences[permissionType] = isDenied
        }
    }

    val getLocationPermissionIsDenied: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[Constants.KEY_PERMISSION_LOCATION] ?: false
    }


    val getNotificationPermissionIsDenied: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[Constants.KEY_PERMISSION_NOTIFICATION] ?: false
    }


}