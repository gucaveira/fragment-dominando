package com.dominando.android.fragments

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.dominando.android.fragments.hotelDetails.HotelDetailsActivity
import com.dominando.android.fragments.hotelDetails.HotelDetailsFragment
import com.dominando.android.fragments.hotelList.HotelListFragment
import com.dominando.android.fragments.model.Hotel

class HotelActivity : AppCompatActivity(), HotelListFragment.OnHotelClickListener,
    SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private var lastSearchTerm: String = ""
    private var searchView: SearchView? = null


    private val listFragment: HotelListFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentList) as HotelListFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_SEARCH_TERM, lastSearchTerm)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastSearchTerm = savedInstanceState.getString(EXTRA_SEARCH_TERM) ?: ""
    }

    override fun onHotelClick(hotel: Hotel) {
        if (isTablet()) {
            showDetailsFragment(hotel.id)
        } else if (isSmartphone()) {
            showDetailsActivity(hotel.id)
        }
    }

    //private fun isTablet() = findViewById<View>(R.id.details) != null
    private fun isTablet() = resources.getBoolean(R.bool.tablet)
    private fun isSmartphone() = resources.getBoolean(R.bool.smartphone)

    private fun showDetailsFragment(hotelId: Long) {
        val fragment = HotelDetailsFragment.newInstance(hotelId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.details, fragment, HotelDetailsFragment.TAG_DETAILS).commit()
    }

    private fun showDetailsActivity(hotelId: Long) {
        /* o menu será recriado quando o fragment de detalhe for exibido,
         então o listener deve ser removido para não ser notificado com o texto vazio */
        searchView?.setOnQueryTextListener(null)

        HotelDetailsActivity.open(this, hotelId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hotel, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(this)

        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = getString(R.string.hint_search)
        searchView?.setOnQueryTextListener(this)
        if (lastSearchTerm.isNotEmpty()) {
            Handler().post {
                val query = lastSearchTerm
                searchItem.expandActionView()
                searchView?.setQuery(query, true)
                searchView?.clearFocus()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        lastSearchTerm = newText ?: ""
        listFragment.search(lastSearchTerm)
        return true
    }

    override fun onQueryTextSubmit(query: String?) = true

    override fun onMenuItemActionExpand(item: MenuItem?) =
        true // para expandir a view

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        lastSearchTerm = ""
        listFragment.clearSearch() // para voltar ao normal
        return true
    }

    companion object {
        const val EXTRA_SEARCH_TERM = "lastSearch"
    }
}

