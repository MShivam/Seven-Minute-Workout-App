package com.example.sevenminuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import kotlinx.android.synthetic.main.activity_finish.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
    val STANDARD_UNITS_VIEW = "STANDARD_UNITS_VIEW"

    var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        setSupportActionBar(toolbar_bmiActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Calculate BMI"
        }
        toolbar_bmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        button_calculateUnits.setOnClickListener {
            if (currentVisibleView == METRIC_UNITS_VIEW) {
                if (validateMetricUnits()) {
                    val heightValue: Float = editText_metricUnitHeight.text.toString().toFloat() / 100
                    val weightValue: Float = editText_metricUnitWeight.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)

                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (validateStandardUnits()) {
                    val heightFeet: String = editText_standardUnitHeightFeet.text.toString()
                    val heightInches: String = editText_standardUnitHeightInches.text.toString()
                    val weightValue: Float = editText_standardUnitWeight.text.toString().toFloat()

                    val heightValue: Float = (heightFeet.toFloat() * 12) + heightInches.toFloat()


                    val bmi = 703 * (weightValue / (heightValue * heightValue))

                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            }

        }

        makeMetricUnitsViewVisible()

        radioGroup_units.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioButton_metric) {
                makeMetricUnitsViewVisible()
            } else {
                makeStandardUnitsViewVisible()
            }
        }
    }

    private fun makeMetricUnitsViewVisible(){
        currentVisibleView = METRIC_UNITS_VIEW
        textInputLayout_metricUnitWeight.visibility = View.VISIBLE
        textInputLayout_metricUnitHeight.visibility = View.VISIBLE

        editText_metricUnitHeight.text!!.clear()
        editText_metricUnitWeight.text!!.clear()

        textInputLayout_standardUnitWeight.visibility = View.GONE
        linearLayout_standardUnitHeight.visibility = View.GONE

        linearLayout_displayBMIResult.visibility = View.GONE
    }

    private fun makeStandardUnitsViewVisible(){
        currentVisibleView = STANDARD_UNITS_VIEW
        textInputLayout_metricUnitWeight.visibility = View.GONE
        textInputLayout_metricUnitHeight.visibility = View.GONE

        editText_standardUnitWeight.text!!.clear()
        editText_standardUnitHeightFeet.text!!.clear()
        editText_standardUnitHeightInches.text!!.clear()

        textInputLayout_standardUnitWeight.visibility = View.VISIBLE
        linearLayout_standardUnitHeight.visibility = View.VISIBLE

        linearLayout_displayBMIResult.visibility = View.GONE
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "You really need to take care of yourself! Act now!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "You really need to take care of yourself better! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "You need to take care of yourself better! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "You need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "You really need to take care of your yourself! Workout!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "You are in a dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "You are in a very dangerous condition! Act now!"
        }

        linearLayout_displayBMIResult.visibility = View.VISIBLE

        // This is used to round of the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(1, RoundingMode.HALF_EVEN).toString()

        textView_BMIValue.text = bmiValue
        textView_BMIType.text = bmiLabel
        textView_BMIDescription.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (editText_metricUnitHeight.text.toString().isEmpty())
            isValid = false
        else if (editText_metricUnitWeight.text.toString().isEmpty())
            isValid = false

        return isValid
    }

    private fun validateStandardUnits(): Boolean {
        var isValid = true

        if (editText_standardUnitWeight.text.toString().isEmpty())
            isValid = false
        else if (editText_standardUnitHeightFeet.text.toString().isEmpty())
            isValid = false
        else if (editText_standardUnitHeightInches.text.toString().isEmpty())
            isValid = false

        return isValid
    }
}