package com.dominando.android.fragments.hotelDetails.presenter

import com.dominando.android.fragments.model.Hotel

interface HotelDetailsView {
    fun showHotelDetails(hotel: Hotel)
    fun errorHotelNotFound()
}
