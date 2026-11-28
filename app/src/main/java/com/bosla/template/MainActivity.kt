package com.bosla.template

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bosla.template.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        // Set default fragment
        if (savedInstanceState == null) {
            loadFragment(StoreFragment())
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.menu.apply {
            add(0, R.id.menu_store, 0, getString(R.string.tab_store)).setIcon(R.drawable.ic_store_nav)
            add(0, R.id.menu_fav, 1, getString(R.string.tab_favorites)).setIcon(R.drawable.ic_fav_nav)
            add(0, R.id.menu_cart, 2, getString(R.string.tab_cart)).setIcon(R.drawable.ic_cart_nav)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_store -> {
                    loadFragment(StoreFragment())
                    true
                }
                R.id.menu_fav -> {
                    loadFragment(FavoritesFragment())
                    true
                }
                R.id.menu_cart -> {
                    loadFragment(CartFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
