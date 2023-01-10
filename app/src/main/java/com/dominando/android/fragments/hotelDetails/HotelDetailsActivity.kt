package com.dominando.android.fragments.hotelDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dominando.android.fragments.R
import com.dominando.android.fragments.databinding.ActivityHotelDetailsBinding

class HotelDetailsActivity : AppCompatActivity() {

    private val hotelId: Long by lazy { intent.getLongExtra(EXTRA_HOTEL_ID, -1) }

    private lateinit var binding: ActivityHotelDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            showHotelDetailsFragment()
        }
    }

    private fun showHotelDetailsFragment() {
        val fragment = HotelDetailsFragment.newInstance(hotelId)
        supportFragmentManager.beginTransaction().replace(
            R.id.details,
            fragment,
            HotelDetailsFragment.TAG_DETAILS
        ).commit()
    }

    companion object {
        private const val EXTRA_HOTEL_ID = "hotel_id"
        fun open(context: Context, hotelId: Long) {
            context.startActivity(
                Intent(context, HotelDetailsActivity::class.java).apply {
                    putExtra(EXTRA_HOTEL_ID, hotelId)
                })
        }
    }
}
