package com.laivinieks.georemind.core.di

import android.app.Application

import androidx.room.Room
import com.laivinieks.georemind.core.data.data_source.GeoRemindDatabase

import com.laivinieks.georemind.feature_note.data.repository.NoteRepositoryImplementation
import com.laivinieks.georemind.feature_note.data.repository.ReminderRepositoryImplementation
import com.laivinieks.georemind.feature_note.domain.repository.NoteRepository
import com.laivinieks.georemind.feature_note.domain.use_case.AddNote
import com.laivinieks.georemind.feature_note.domain.use_case.DeleteNote
import com.laivinieks.georemind.feature_note.domain.use_case.GetNote
import com.laivinieks.georemind.feature_note.domain.use_case.GetNotes
import com.laivinieks.georemind.feature_note.domain.use_case.NoteUseCases
import com.laivinieks.georemind.feature_reminder.domain.repository.ReminderRepository
import com.laivinieks.georemind.feature_reminder.domain.use_case.AddReminder
import com.laivinieks.georemind.feature_reminder.domain.use_case.DeleteReminder
import com.laivinieks.georemind.feature_reminder.domain.use_case.GetReminder
import com.laivinieks.georemind.feature_reminder.domain.use_case.GetReminders
import com.laivinieks.georemind.feature_reminder.domain.use_case.ReminderUserCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

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
    fun provideReminderUseCases(repository: ReminderRepository): ReminderUserCases {
        return ReminderUserCases(
            getReminders = GetReminders(repository = repository),
            deleteReminder = DeleteReminder(repository = repository),
            addReminder = AddReminder(repository = repository),
            getReminder = GetReminder(repository = repository)

        )
    }


}
