package com.zigcon.common.artifacts.extensions

import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.Calendar
import java.util.Date

/**
 * @Authoer Dharmesh
 * @Date 24-10-2022
 *
 * Information
 **/
fun EditText.setDateOfBirthPicker(date: Date, onDateSelected: (Calendar, EditText) -> Unit) {
    val myCalendar = Calendar.getInstance()
    myCalendar.time = date
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            onDateSelected(myCalendar, this)
        }
    this.setOnClickListener {
        val datePickerDialog = DatePickerDialog(
            this.context,
            datePickerOnDataSetListener,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR,-18)
        datePickerDialog.datePicker.maxDate = calendar.time.time
        datePickerDialog.show()
    }
}

fun EditText.setSocialSecurityNumber() {
    val editText = this
    this.addTextChangedListener(object : TextWatcher {
        private var spaceDeleted = false
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            val charDeleted = s.subSequence(start, start + count)
            spaceDeleted = "-" == charDeleted.toString()
        }

        override fun afterTextChanged(editable: Editable) {
            editText.removeTextChangedListener(this)
            val cursorPosition: Int = editText.getSelectionStart()
            val withSpaces = formatText(editable)
            editText.setText(withSpaces)
            editText.setSelection(cursorPosition + (withSpaces.length - editable.length))
            if (spaceDeleted) {
                //  userNameET.setSelection(userNameET.getSelectionStart() - 1);
                spaceDeleted = false
            }
            editText.addTextChangedListener(this)
        }

        private fun formatText(text: CharSequence): String {
            val formatted = StringBuilder()
            if (text.length == 3 || text.length == 6) {
                if (!spaceDeleted) formatted.append("$text-") else formatted.append(text)
            } else formatted.append(text)
            return formatted.toString()
        }
    })
}

fun EditText.getString() : String{
    return this.text.toString().trim()
}
