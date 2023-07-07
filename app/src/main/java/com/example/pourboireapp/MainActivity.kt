package com.example.pourboireapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.pourboireapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //adapter notre spinner
        spinnerAdapter()
        binding.btnCalculer.setOnClickListener {
                    calculateTip()
        }

    }
    private fun spinnerAdapter(){
        ArrayAdapter.createFromResource(
            this,
            R.array.categorie_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spCategorie.adapter = adapter
        }
    }
    private fun calculateTip() {
        if(binding.edtPrix.text.isEmpty()) {
            binding.edtPrix.error = "Entrez le prix"
            return
        }
        val prix= binding.edtPrix.text.toString().toDouble()
        var augmentation=0.0
        when(binding.radioReduction.checkedRadioButtonId){
            R.id.rb_excellent -> augmentation = 0.20
            R.id.rb_bon -> augmentation = 0.18
            R.id.rb_ok -> augmentation = 0.15
        }
        val categorie = binding.spCategorie.selectedItem.toString()
        if (categorie=="VIP")
            augmentation*=2
        val pourboire = prix * augmentation
        var total = prix + pourboire
        //arrondir
        if(binding.swcArrondir.isChecked)
            total= Math.round(total).toDouble()
        binding.txtTotal.text=total.toString()

    }
}