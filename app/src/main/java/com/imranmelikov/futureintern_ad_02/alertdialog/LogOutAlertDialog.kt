package com.imranmelikov.futureintern_ad_02.alertdialog

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.imranmelikov.futureintern_ad_02.R
import com.imranmelikov.futureintern_ad_02.RegisterActivity

class LogOutAlertDialog {
    fun alertDialog(context: Context, layout:Int,editor: Editor,intent:Intent,activity: FragmentActivity?){
        val builder= AlertDialog.Builder(context, R.style.RoundedAlertDialog)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView: View = inflater.inflate(layout, null)


        val alertDialog = builder.setView(dialogView).create()
        val window: Window = alertDialog.window!!

        //Adjust the position of the window and set the bottom margin
        val params = window.attributes
        params.gravity = Gravity.BOTTOM
        params.y = context.resources.getDimensionPixelSize(R.dimen._40dp)


        //Make the window full screen
        val displayMetrics = context.resources.displayMetrics
        val width = displayMetrics.widthPixels
        params.width = width
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT

        // Update window
        window.attributes = params

        logOut(dialogView,alertDialog,editor,intent,activity)
        alertDialog.show()
    }

    private fun logOut(dialogView: View, alertDialog: AlertDialog,editor: Editor,intent:Intent,activity: FragmentActivity?){
        val logOutBtn = dialogView.findViewById<Button>(R.id.logout_btn)
        val cancelBtn=dialogView.findViewById<Button>(R.id.cancel_btn)

        cancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }
        logOutBtn.setOnClickListener {
            editor.putBoolean("register",false)
            editor.putString("userName","")
            editor.apply()
            activity?.startActivity(intent)
            activity?.finishAffinity()
            alertDialog.dismiss()
        }
    }
}