package com.studiomk.footballhighlights.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studiomk.footballhighlights.R
import com.studiomk.footballhighlights.domain.model.HighLight
import com.studiomk.footballhighlights.presentation.HomeContract
import com.studiomk.footballhighlights.presentation.adapter.HighLightsAdapter
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.standalone.KoinComponent
import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.widget.SearchView
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager


class HomeActivity : AppCompatActivity(), HomeContract.View, KoinComponent {

    private val presenter: HomeContract.Presenter by inject { parametersOf(this) }
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setToolbar()
        home_loading_lootie?.imageAssetsFolder = "images/"
        presenter.initPresenter()
    }

    override fun initListeners() {
        home_floating_button?.setOnClickListener {
            presenter.initPresenter()
            cleanSearchView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )
        searchView.queryHint = getString(R.string.home_search_hint)
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideEmptySearchResultText()
                (viewAdapter as HighLightsAdapter).filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                hideEmptySearchResultText()
                (viewAdapter as HighLightsAdapter).filter.filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun buildHighLightList(list: List<HighLight>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = HighLightsAdapter(this, list)

        home_recycler_view?.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun showLoading() {
        hideKeyboard()
        home_loading?.visibility = View.VISIBLE
        home_toolbar_layout?.visibility = View.GONE
    }

    override fun hideLoading() {
        home_loading?.visibility = View.GONE
        home_toolbar_layout?.visibility = View.VISIBLE
    }

    override fun showErrorMessage() {
        home_error?.visibility = View.VISIBLE
        home_recycler_view?.visibility = View.GONE
        home_highlight_not_found_text?.visibility = View.GONE
    }

    override fun hideErrorMessage() {
        home_error?.visibility = View.GONE
        home_recycler_view?.visibility = View.VISIBLE
        home_highlight_not_found_text?.visibility = View.GONE
    }

    override fun openHighLightActivity(highLight: HighLight) {
        startActivity(HighLightActivity.createIntent(this, highLight))
    }

    override fun cleanSearchView() {
        searchView.setQuery("", false)
        searchView.clearFocus()
    }

    private fun setToolbar() {
        setSupportActionBar(home_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.home_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    fun showEmptySearchResultText() {
        home_highlight_not_found_text?.visibility = View.VISIBLE
    }

    fun hideEmptySearchResultText() {
        home_highlight_not_found_text?.visibility = View.GONE
    }

    private fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
