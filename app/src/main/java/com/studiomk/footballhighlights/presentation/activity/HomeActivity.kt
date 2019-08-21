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

class HomeActivity : AppCompatActivity(), HomeContract.View, KoinComponent {

    private val presenter: HomeContract.Presenter by inject { parametersOf(this) }
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        home_loading_lootie?.imageAssetsFolder = "images/"
        presenter.initPresenter()
    }

    override fun initListeners() {
        home_floating_button?.setOnClickListener {
            presenter.initPresenter()
        }
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
        home_loading?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        home_loading?.visibility = View.GONE
    }

    override fun showErrorMessage() {
        home_error?.visibility = View.VISIBLE
        home_recycler_view?.visibility = View.GONE
    }

    override fun hideErrorMessage() {
        home_error?.visibility = View.GONE
        home_recycler_view?.visibility = View.VISIBLE
    }

    override fun openHighLightActivity(highLight: HighLight) {
        startActivity(HighLightActivity.createIntent(this, highLight))
    }
}
