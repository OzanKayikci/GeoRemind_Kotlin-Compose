package com.laivinieks.georemind.core.di

import android.app.Application
import android.content.Context

import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.laivinieks.georemind.core.data.data_source.GeoRemindDatabase
import com.laivinieks.georemind.feature_geofence.data.GeofenceManager
import com.laivinieks.georemind.feature_geofence.data.GeofenceRepositoryImp
import com.laivinieks.georemind.feature_geofence.domain.repository.GeofenceRepository
import com.laivinieks.georemind.feature_geofence.domain.use_case.AddGeofenceUseCase
import com.laivinieks.georemind.feature_geofence.domain.use_case.CreateGeofenceUseCase
import com.laivinieks.georemind.feature_geofence.domain.use_case.GeofenceUseCases
import com.laivinieks.georemind.feature_geofence.domain.use_case.GetAllLocationsUseCase
import com.laivinieks.georemind.feature_geofence.domain.use_case.RemoveGeofenceUseCase
import com.laivinieks.georemind.feature_geofence.presentation.NotificationHelper

import com.laivinieks.georemind.feature_note.data.repository.NoteRepositoryImplementation
import com.laivinieks.georemind.feature_note.domain.repository.NoteRepository
import com.laivinieks.georemind.feature_note.domain.use_case.AddNote
import com.laivinieks.georemind.feature_note.domain.use_case.DeleteNote
import com.laivinieks.georemind.feature_note.domain.use_case.GetNote
import com.laivinieks.georemind.feature_note.domain.use_case.GetNotes
import com.laivinieks.georemind.feature_note.domain.use_case.NoteUseCases
import com.laivinieks.georemind.feature_reminder.data.repository.LocationRepositoryImpl
import com.laivinieks.georemind.feature_reminder.data.repository.ReminderRepositoryImplementation
import com.laivinieks.georemind.feature_reminder.data.service.DefaultLocationClient
import com.laivinieks.georemind.feature_reminder.domain.repository.LocationRepository
import com.laivinieks.georemind.feature_reminder.domain.repository.ReminderRepository
import com.laivinieks.georemind.feature_reminder.domain.service.LocationClient
import com.laivinieks.georemind.feature_reminder.domain.use_case.AddReminder
import com.laivinieks.georemind.feature_reminder.domain.use_case.DeleteReminder
import com.laivinieks.georemind.feature_reminder.domain.use_case.GetReminder
import com.laivinieks.georemind.feature_reminder.domain.use_case.GetReminders
import com.laivinieks.georemind.feature_reminder.domain.use_case.ReminderUseCases

import com.laivinieks.georemind.feature_reminder.domain.use_case.location_use_case.LocationUseCases
import com.laivinieks.georemind.feature_reminder.domain.use_case.location_use_case.StartLocationUpdatesUseCase
import com.laivinieks.georemind.feature_reminder.domain.use_case.location_use_case.StopLocationUpdatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent


import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGeoReminderDatabase(app: Application): GeoRemindDatabase {
        return Room.databaseBuilder(
            app, GeoRemindDatabase::class.java, GeoRemindDatabase.DATABASE_NAME
        ).build()

    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: GeoRemindDatabase): NoteRepository {
        return NoteRepositoryImplementation(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideReminderRepository(db: GeoRemindDatabase): ReminderRepository {
        return ReminderRepositoryImplementation(db.reminderDao)
    }

    //geofence part
    @Provides
    @Singleton
    fun providerGeofenceManager(app: Application): GeofenceManager {

        return GeofenceManager(app)
    }


    // we initialize data class of usecases
    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }

    @Provides
    @Singleton
    fun provideGeofenceRepository(geofenceManager: GeofenceManager): GeofenceRepository {
        return GeofenceRepositoryImp(geofenceManager)
    }

    @Provides
    @Singleton
    fun provideGeofenceUseCases(repository: GeofenceRepository): GeofenceUseCases {
        return GeofenceUseCases(
            addGeofenceUseCase = AddGeofenceUseCase(repository),
            createGeofenceUseCase = CreateGeofenceUseCase(repository),
            removeGeofenceUseCase = RemoveGeofenceUseCase(repository)

        )
    }

    @Provides
    @Singleton
    fun provideGetAllLocationsUseCase(repository: ReminderRepository): GetAllLocationsUseCase {
        return GetAllLocationsUseCase(repository)
    }

    //Notification
    @Provides
    @Singleton
    fun provideNotificationHelper(app: Application): NotificationHelper {
        return NotificationHelper(app)
    }

    @Provides
    @Singleton
    fun provideReminderUseCases(repository: ReminderRepository): ReminderUseCases {
        return ReminderUseCases(
            getReminders = GetReminders(repository = repository),
            deleteReminder = DeleteReminder(repository = repository),
            addReminder = AddReminder(repository = repository),
            getReminder = GetReminder(repository = repository)

        )
    }


    /** LOCATION IMPLEMENTATIONS*/


    @Provides
    @Singleton
    fun provideLocationClient(@ApplicationContext context: Context): LocationClient {
        return DefaultLocationClient(context, LocationServices.getFusedLocationProviderClient(context))
    }

    @Provides
    @Singleton
    fun provideLocationRepository(locationClient: LocationClient): LocationRepository {
        return LocationRepositoryImpl(locationClient)
    }


    @Provides
    @Singleton
    fun provideLocationUseCases(repository: LocationRepository): LocationUseCases {
        return LocationUseCases(
            startLocationUpdatesUseCase = StartLocationUpdatesUseCase(
                locationRepository = repository
            ),
            stopLocationUpdatesUseCase = StopLocationUpdatesUseCase(
                locationRepository = repository
            ),

            )
    }


}
