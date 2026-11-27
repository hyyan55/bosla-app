package com.bosla.template

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bosla.template.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonShowMessage.setOnClickListener {
            Toast.makeText(
                this,
                getString(R.string.toast_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
