package com.laivinieks.georemind.feature_note.domain.repository

import com.laivinieks.georemind.core.data.repository.GeoRemindRepository
import com.laivinieks.georemind.feature_note.domain.modal.Note

interface NoteRepository : GeoRemindRepository<Note> {

}