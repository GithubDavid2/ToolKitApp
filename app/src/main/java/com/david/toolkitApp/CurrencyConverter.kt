package com.david.toolkitApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.david.toolkitApp.R
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import com.david.toolkitApp.databinding.ActivityCurrencyConverterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat

class CurrencyConverter : AppCompatActivity() {

    private lateinit var binding: ActivityCurrencyConverterBinding

    var baseCurrency = "USD"
    var convertedToCurrency = "COP"
    private var conversionRate = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter)
        binding = ActivityCurrencyConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etSecondConversion.isEnabled = false

        spinnerSetup()
        setupConvertButton()
    }

    private fun setupConvertButton() {
        binding.btnConvert.setOnClickListener {
            try {
                getApiResult(binding.etFirstConversion, binding.etSecondConversion, baseCurrency, convertedToCurrency)
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Escriba un valor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getApiResult(input: EditText, output: EditText, base: String, target: String) {
        if ( input.text.isNotEmpty() && input.text.isNotBlank()) {

            val aPI = "https://api.fastforex.io/fetch-one?from=$base&to=$target&api_key=dd7fdba0b4-6e10d0e63c-rvry74"

            if (base == target) {
                Toast.makeText(
                    applicationContext,
                    "Por favor seleccione una divisa para convertir",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                GlobalScope.launch(Dispatchers.IO) {

                    try {

                        val apiResult = URL(aPI).readText()
                        val jsonObject = JSONObject(apiResult)
                        conversionRate = jsonObject.getJSONObject("result").getString(target).toFloat()

                        withContext(Dispatchers.Main) {
                            val decimalFormat = DecimalFormat("#.#######") // formato para 8 decimales
                            val text = decimalFormat.format((input.text.toString().toFloat()) * conversionRate)
                            output.setText(text)
                        }

                    } catch (e: Exception) {
                        Log.e("Main", "$e")
                    }
                }
            }
        }
    }


    private fun spinnerSetup() {
        val spinner: Spinner = findViewById(R.id.spinner_firstConversion)
        val spinner2: Spinner = findViewById(R.id.spinner_secondConversion)

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter

        }

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies2,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner2.adapter = adapter

        }

        spinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseCurrency = parent?.getItemAtPosition(position).toString()
            }

        })

        spinner2.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertedToCurrency = parent?.getItemAtPosition(position).toString()
            }

        })
    }


}