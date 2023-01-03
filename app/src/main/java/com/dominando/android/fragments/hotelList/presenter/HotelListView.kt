package com.dominando.android.fragments.hotelList.presenter

import com.dominando.android.fragments.model.Hotel

interface HotelListView {
    fun showHotels(hotels: List<Hotel>)
    fun showHotelDetails(hotel: Hotel) }