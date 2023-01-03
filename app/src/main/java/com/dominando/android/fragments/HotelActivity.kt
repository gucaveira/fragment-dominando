package com.dominando.android.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dominando.android.fragments.hotelDetails.HotelDetailsActivity
import com.dominando.android.fragments.hotelList.HotelListFragment
import com.dominando.android.fragments.model.Hotel

class HotelActivity : AppCompatActivity(), HotelListFragment.OnHotelClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onHotelClick(hotel: Hotel) {
        showDetailsActivity(hotel.id)
    } private fun showDetailsActivity(hotelId: Long) { HotelDetailsActivity.open(this, hotelId) } }

