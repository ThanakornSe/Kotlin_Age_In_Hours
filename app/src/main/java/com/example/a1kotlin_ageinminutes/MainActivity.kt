package com.example.a1kotlin_ageinminutes

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate: TextView? = null
    private var tvSelectedDateInMinutes: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener { clickDatePicker() }
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSelectedDateInMinutes = findViewById(R.id.tvSelectedDateInMinutes)
    }

    private fun clickDatePicker() {
        /**
         * This Gets a calendar using the default time zone and locale.
         * The calender returned is based on the current time
         * in the default time zone with the default.
         */
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { views, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this, "Year was $selectedYear and month was ${selectedMonth+1}, day $selectedDayOfMonth", Toast.LENGTH_SHORT).show()
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate?.text = selectedDate
                val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
                // The formatter will parse the selected date in to Date object
                // so we can simply get date in to milliseconds.
                val theDate = sdf.parse(selectedDate)
                //use the safe call operator with .let to avoid app crashing it theDate is null
                theDate?.let {
                    /** Here we have get the time in milliSeconds from Date object
                     * And as we know the formula as milliseconds can be converted to second by dividing it by 1000.
                     * And the seconds can be converted to minutes by dividing it by 60.
                     * And the minutes can be converted to hours by dividing it by 60.
                     * So now in the selected date into Hours.
                     */
                    val selectedDateInHours = theDate.time/(60000*60)
                    // Here we have parsed the current date with the Date Formatter which is used above.
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    //use the safe call operator with .let to avoid app crashing it currentDate is null
                    currentDate?.let {
                        // Current date in to minutes.
                        val currentDateInHours = currentDate.time/(60000*60)
                        /**
                         * Now to get the difference into minutes.
                         * We will subtract the selectedDateToMinutes from currentDateToMinutes.
                         * Which will provide the difference in minutes.
                         */
                        val differentInHours = currentDateInHours - selectedDateInHours
                        tvSelectedDateInMinutes?.text = differentInHours.toString()
                    }
                }
            },
            year,
            month,
            day
        )
        /**
         * Sets the maximal date supported by this in
         * milliseconds since January 1, 1970 00:00:00 in time zone.
         *
         * @param maxDate The maximal supported date.
         */
        // 86400000 is milliseconds of 24 Hours. Which is used to restrict the user from selecting today and future day.
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }

}
