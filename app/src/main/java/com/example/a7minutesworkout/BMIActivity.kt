package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW  = "US_UNITS_VIEW"


    }


    private var currentVisibleView:String = METRIC_UNITS_VIEW

    private var binding : ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()
        binding?.rgUnits?.setOnCheckedChangeListener{
            _, checkedId:Int->
            if(checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }
            else{
                makeVisibleUsUnitsView()
            }
        }
        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }


    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.llMetricUnitsView?.visibility= View.VISIBLE
        binding?.llUsUnitsView?.visibility=View.GONE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility=View.INVISIBLE


    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        binding?.llUsUnitsView?.visibility= View.VISIBLE
        binding?.llMetricUnitsView?.visibility=View.GONE

        binding?.etUsUnitHeightFeet?.text!!.clear()
        binding?.etUsUnitHeightInch?.text!!.clear()
        binding?.etUsUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility=View.INVISIBLE


    }

    private fun displayBMIResults(bmi :Float){
        val bmiLabel:String
        val bmiDescription:String

        if(bmi.compareTo(15f)<=0){
            bmiLabel= "Very Severely Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }
        else if(bmi.compareTo(15f)>0 && bmi.compareTo(16f)<=0){
            bmiLabel= "Severely Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }
        else if( bmi.compareTo(16f)>0 && bmi.compareTo(18.5f)<=0){
            bmiLabel= "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }
        else if(bmi.compareTo(18.5)>0 && bmi.compareTo(25)<=0){
            bmiLabel= "Normal"
            bmiDescription = "Comgratulations! Yor're in Good Shape"
        }
        else if(bmi.compareTo(25f)>0 && bmi.compareTo(30f)<=0){
            bmiLabel= "Overweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout More!"
        }
        else if(bmi.compareTo(30f)>0 && bmi.compareTo(35f)<=0){
            bmiLabel= "Obese Class | (Moderately Obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout More!"
        }
        else if(bmi.compareTo(35f)>0 && bmi.compareTo(40f)<=0){
            bmiLabel= "Obese Class | (Severely Obese)"
            bmiDescription = "You're in very dangerous condition! Act Now!"
        }
        else{
            bmiLabel= "Obese Class | (Very Severely Obese)"
            bmiDescription = "You're in very dangerous condition! Act Now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility= View.VISIBLE

        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }

    private fun calculateUnits(){
        if(currentVisibleView== METRIC_UNITS_VIEW){
            if(validateMetricUnits()){
                val heightValue : Float = binding?.etMetricUnitHeight?.text.toString().toFloat()/100
                val weightValue :Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue/(heightValue*heightValue)
                displayBMIResults(bmi)

            }
            else{
                Toast.makeText(this@BMIActivity, "Please Enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            if(validateUsUnits()){
                val usUniteHeightValueFeet:String =
                    binding?.etUsUnitHeightFeet?.text.toString()
                val usUniteHeightValueInch:String =
                    binding?.etUsUnitHeightInch?.text.toString()
                val usUnitWeightValue:Float =
                    binding?.etUsUnitWeight?.text.toString().toFloat()

                val heightValue =usUniteHeightValueInch.toFloat() + usUniteHeightValueFeet.toFloat()*12

                val bmi = 703 *(usUnitWeightValue/(heightValue*heightValue))
                displayBMIResults(bmi)
            }
            else{
                Toast.makeText(this@BMIActivity, "Please Enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateMetricUnits():Boolean{
        var isValid = true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid= false
        }
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid =false
        }
        return isValid

    }

    private fun validateUsUnits():Boolean {
        var isValid = true

        when{
            binding?.etUsUnitWeight?.text.toString().isEmpty()->{
                isValid=false
            }
            binding?.etUsUnitHeightFeet?.text.toString().isEmpty()->{
                isValid=false
            }
            binding?.etUsUnitHeightInch?.text.toString().isEmpty()->{
                isValid=false
            }
        }
        return isValid
    }
}