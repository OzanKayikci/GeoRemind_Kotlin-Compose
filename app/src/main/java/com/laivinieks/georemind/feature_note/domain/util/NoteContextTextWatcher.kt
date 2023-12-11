package com.laivinieks.georemind.feature_note.domain.util

import android.text.Editable
import android.text.TextWatcher
import android.util.Log

class NoteContentTextWatcher(private val text: (content: String) -> Unit) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Handle text change before it occurs
        //

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        text(s.toString())

    }

    override fun afterTextChanged(s: Editable?) {


    }
}