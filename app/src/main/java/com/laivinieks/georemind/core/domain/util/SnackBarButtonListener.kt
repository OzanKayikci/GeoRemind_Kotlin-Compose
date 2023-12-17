package com.laivinieks.georemind.core.domain.util

import android.view.View
import com.laivinieks.georemind.core.domain.util.Constants

class SnackBarButtonListener(private val function: (string: String) -> Unit) : View.OnClickListener {

    override fun onClick(v: View) {
        function(Constants.UNDO_SNACKBAR)
    }
}