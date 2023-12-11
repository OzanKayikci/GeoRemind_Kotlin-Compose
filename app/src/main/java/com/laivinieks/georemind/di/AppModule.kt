package com.laivinieks.georemind.di

import android.app.Application
import androidx.room.Room
import com.laivinieks.georemind.feature_note.data.data_source.NoteDatabase
import com.laivinieks.georemind.feature_note.data.repository.NoteRepositoryImplementation
import com.laivinieks.georemind.feature_note.domain.repository.NoteRepository
import com.laivinieks.georemind.feature_note.domain.usecase.AddNote
import com.laivinieks.georemind.feature_note.domain.usecase.DeleteNote
import com.laivinieks.georemind.feature_note.domain.usecase.GetNote
import com.laivinieks.georemind.feature_note.domain.usecase.GetNotes
import com.laivinieks.georemind.feature_note.domain.usecase.NoteUseCases
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
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app, NoteDatabase::class.java, NoteDatabase.DATABASE_NAME
        ).build()

    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImplementation(db.noteDao)
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
}
