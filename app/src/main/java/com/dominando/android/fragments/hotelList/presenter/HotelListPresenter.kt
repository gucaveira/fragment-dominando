package com.dominando.android.fragments.hotelList.presenter

import com.dominando.android.fragments.model.Hotel
import com.dominando.android.fragments.repository.HotelRepository

class HotelListPresenter(
    private val view: HotelListView,
    private val repository: HotelRepository
) {

    private var lastTerm = ""
    private var inDeleteMode = false
    private val selectedItems = mutableListOf<Hotel>()

    fun searchHotels(term: String) {
        lastTerm = term
        repository.search(term) { hotels -> view.showHotels(hotels) }
    }

    fun selectHotel(hotel: Hotel) {
        if (inDeleteMode) {
            toggleHotelSelected(hotel)
            if (selectedItems.size == 0) {
                view.hideDeleteMode()
            } else {
                view.updateSelectionCountText(selectedItems.size)
                view.showSelectedHotels(selectedItems)
            }
        } else {
            view.showHotelDetails(hotel)
        }
    }

    private fun toggleHotelSelected(hotel: Hotel) {
        val existing = selectedItems.find { it.id == hotel.id }

        existing?.let {
            selectedItems.add(it)
        } ?: run {
            selectedItems.removeAll { it.id == hotel.id }
        }
    }

    fun showDeleteMode() {
        inDeleteMode = true
        view.showDeleteMode()
    }

    fun hideDeleteMode() {
        inDeleteMode = false
        selectedItems.clear()
        view.hideDeleteMode()
    }

    private fun refresh() {
        searchHotels(lastTerm)
    }

    fun deleteSelected(action: (List<Hotel>) -> Unit) {
        //: É possível passar um array para um método que recebe um varargs simplesmente
        // adicionando um asterisco (*) à frente.
        repository.remove(*selectedItems.toTypedArray())
        refresh()
        action(selectedItems)
        hideDeleteMode()
    }
}