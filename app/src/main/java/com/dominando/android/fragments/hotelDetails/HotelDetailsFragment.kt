package com.dominando.android.fragments.hotelDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dominando.android.fragments.R
import com.dominando.android.fragments.databinding.FragmentHotelDetailsBinding
import com.dominando.android.fragments.hotelDetails.presenter.HotelDetailsPresenter
import com.dominando.android.fragments.hotelDetails.presenter.HotelDetailsView
import com.dominando.android.fragments.model.Hotel
import com.dominando.android.fragments.repository.MemoryRepository

class HotelDetailsFragment : Fragment(), HotelDetailsView {
    private val presenter = HotelDetailsPresenter(this, MemoryRepository)
    private var hotel: Hotel? = null

    private var _binding: FragmentHotelDetailsBinding? = null
    private val binding: FragmentHotelDetailsBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHotelDetailsBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadHotelDetails(arguments?.getLong(EXTRA_HOTEL_ID, -1) ?: -1)
    }

    override fun showHotelDetails(hotel: Hotel) {
        this.hotel = hotel
        binding.txtName.text = hotel.name
        binding.txtAddress.text = hotel.address
        binding.rtbRating.rating = hotel.rating
    }

    override fun errorHotelNotFound() {
        binding.txtName.text = getString(R.string.error_hotel_not_found)
        binding.txtAddress.visibility = View.GONE
        binding.rtbRating.visibility = View.GONE
    }

    companion object {
        const val TAG_DETAILS = "tagDetalhe"
        private const val EXTRA_HOTEL_ID = "hotelId"
        fun newInstance(id: Long) = HotelDetailsFragment().apply {
            arguments = Bundle().apply { putLong(EXTRA_HOTEL_ID, id) }
        }
    }
}


