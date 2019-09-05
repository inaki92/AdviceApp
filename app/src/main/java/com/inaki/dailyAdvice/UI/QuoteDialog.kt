package com.inaki.dailyAdvice.UI

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class QuoteDialog(private val gameQuote: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(gameQuote)
                .setPositiveButton("DISMISS"
                ) { dialog, id ->
                  //DO SOMETHING
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}