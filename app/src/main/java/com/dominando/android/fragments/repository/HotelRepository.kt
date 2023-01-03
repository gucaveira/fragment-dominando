package com.dominando.android.fragments.repository

import com.dominando.android.fragments.model.Hotel

interface HotelRepository {
    fun save(hotel: Hotel)
    fun remove(vararg hotels: Hotel)
    fun hotelById(id: Long, action: (Hotel?) -> Unit)
    fun search(term: String, action: (List<Hotel>) -> Unit)
}