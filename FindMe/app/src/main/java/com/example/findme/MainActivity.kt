package com.example.findme

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dialog = createDialog()
        dialog.show()
    }

    private fun createDialog(): Dialog {
        val dialog = AlertDialog.Builder(this)
            .setIcon(R.drawable.android)
            .setTitle(R.string.instructions_title)
            .setMessage(R.string.instructions)
        return dialog.create()
    }
}