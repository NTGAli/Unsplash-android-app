package com.example.pic.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.pic.R
import com.google.android.material.snackbar.Snackbar

@SuppressLint("InflateParams")
fun View.showSnackBar(errorTitle: String, errorMassage: String) {
    val snackBar = Snackbar.make(this, "", Snackbar.LENGTH_LONG)

    val layout = snackBar.view as Snackbar.SnackbarLayout
    val snackView: View =
        LayoutInflater.from(this.context).inflate(R.layout.my_snackbar, null)
    val errorTitleTV: TextView = snackView.findViewById(R.id.txt_error_title)
    val errorMassageTV: TextView = snackView.findViewById(R.id.txt_error_massage)

    errorTitleTV.text = errorTitle
    errorMassageTV.text = errorMassage

    layout.addView(snackView, 0)

    snackView.setPadding(0, 0, 0, 0)
    snackBar.show()
}
