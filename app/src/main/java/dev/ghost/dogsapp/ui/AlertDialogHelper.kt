package dev.ghost.dogsapp.ui

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import dev.ghost.dogsapp.R
import kotlinx.android.synthetic.main.alert_server_error.view.*

class AlertDialogHelper {
    fun showConnectionErrorDialog(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_server_error, null)

        val errorDialog = AlertDialog.Builder(context, R.style.MyThemeOverlayAlertDialog).setView(dialogView)
            .show()
        errorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        dialogView.textViewAlertErrorOk.setOnClickListener {
            errorDialog.dismiss()
        }
    }
}