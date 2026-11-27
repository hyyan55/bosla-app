package com.bosla.template

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bosla.template.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var totalPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // أسعار المنتجات المحددة مسبقاً
        val price1 = 350
        val price2 = 280
        val price3 = 220
        val price4 = 450

        binding.btnPerfume1Add.setOnClickListener {
            totalPrice += price1
            updateCartDisplay()
            Toast.makeText(this, "تمت إضافة عطر الأخشاب الملكية للسلة", Toast.LENGTH_SHORT).show()
        }

        binding.btnPerfume2Add.setOnClickListener {
            totalPrice += price2
            updateCartDisplay()
            Toast.makeText(this, "تمت إضافة عطر الياسمين الفرنسي للسلة", Toast.LENGTH_SHORT).show()
        }

        binding.btnPerfume3Add.setOnClickListener {
            totalPrice += price3
            updateCartDisplay()
            Toast.makeText(this, "تمت إضافة عطر المسك الأبيض للسلة", Toast.LENGTH_SHORT).show()
        }

        binding.btnPerfume4Add.setOnClickListener {
            totalPrice += price4
            updateCartDisplay()
            Toast.makeText(this, "تمت إضافة عطر العنبر العتيق للسلة", Toast.LENGTH_SHORT).show()
        }

        binding.btnCheckout.setOnClickListener {
            if (totalPrice > 0) {
                Toast.makeText(this, R.string.checkout_success, Toast.LENGTH_LONG).show()
                totalPrice = 0
                updateCartDisplay()
            } else {
                Toast.makeText(this, R.string.empty_cart, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateCartDisplay() {
        val currencyText = getString(R.string.currency)
        binding.tvTotalPrice.text = "$totalPrice $currencyText"
    }
}
