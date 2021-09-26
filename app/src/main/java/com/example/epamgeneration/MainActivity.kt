package com.example.epamgeneration

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    var day = 0
    var month = 0
    var year = 0

    var saveDay = 0
    var saveMonth = 0
    var saveYear = 0

    lateinit var prefs: SharedPreferences
    lateinit var generationText: TextView
    lateinit var dateText: TextView
    val PREFERNCES_DATE = "BIRTH_DATE"
    val PREFERENCES_GENERATION = "GENERATION_INFO"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("info", Context.MODE_PRIVATE)

        dateText = findViewById(R.id.birth_date)
        generationText = findViewById(com.example.epamgeneration.R.id.your_generation)

        if (prefs.contains(PREFERNCES_DATE)) {
            dateText.text = prefs.getString(PREFERNCES_DATE, "")
        }
        if (prefs.contains(PREFERENCES_GENERATION)) {
            generationText.text = prefs.getString(PREFERENCES_GENERATION, "")
        }

        val pickButton = findViewById<Button>(R.id.btn_pickDate)
        pickButton.setOnClickListener() {
            pickDate()
        }
    }


    private fun getDateCalendar() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)

    }

    private fun pickDate() {
        getDateCalendar()
        DatePickerDialog(this, this, year, month, day).show()
    }

    private fun chooseGeneration(year: Int) {

        when (year) {
            in 0..1945 -> generationText.text = "Вы слишком стары, я не могу найти ваше поколение"
            in 1946..1964 -> generationText.text = "Поздравляю, Вы - Бэби-бумер"
            in 1965..1980 -> generationText.text = "Поздравляю, Вы относитесь к поколению X"
            in 1981..1996 -> generationText.text = "Поздравляю, Вы Миллениал"
            in 1997..2012 -> generationText.text = "Поздравляю, Вы - Зумер"
            in 2013..2022 -> generationText.text =
                "Ты же еще совсем малыш, зачем тебе этим забивать себе голову"
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        saveDay = dayOfMonth
        saveMonth = month + 1
        saveYear = year
        dateText.text = "Ваша дата рождения:\n $saveDay - $saveMonth - $saveYear"
        chooseGeneration(saveYear)
    }

    override fun onResume() {
        super.onResume()
        if (prefs.contains(PREFERNCES_DATE)) {
            dateText.text = prefs.getString(PREFERNCES_DATE, "")
        }
        if (prefs.contains(PREFERENCES_GENERATION)) {
            generationText.text = prefs.getString(PREFERENCES_GENERATION, "")
        }
    }
    override fun onStop() {
        super.onStop()
        val editor = prefs.edit()
        editor
            .putString(PREFERNCES_DATE, findViewById<TextView>(R.id.birth_date).text as String?)
            .apply()
        editor
            .putString(
                PREFERENCES_GENERATION,
                findViewById<TextView>(R.id.your_generation).text as String?
            )
            .apply()

    }
}