package com.studiomk.footballhighlights.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studiomk.footballhighlights.R
import com.studiomk.footballhighlights.domain.model.HighLight
import com.studiomk.footballhighlights.domain.model.HighLightVideo
import com.studiomk.footballhighlights.presentation.HighLightContract
import com.studiomk.footballhighlights.presentation.adapter.HighLightsAdapter
import com.studiomk.footballhighlights.presentation.adapter.HighLightsVideoAdapter
import kotlinx.android.synthetic.main.activity_high_light.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class HighLightActivity : AppCompatActivity(), HighLightContract.View {

    companion object {
        const val HIGH_LIGHT_PARAM = "HIGH_LIGHT_PARAM"
        fun createIntent(context: Context, highLight: HighLight): Intent {
            return Intent(context, HighLightActivity::class.java)
                .putExtra(HIGH_LIGHT_PARAM, highLight)
        }
    }

    private val presenter: HighLightContract.Presenter by inject { parametersOf(this) }
    private val highLight by lazy { intent?.getSerializableExtra(HIGH_LIGHT_PARAM) as HighLight}
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_light)

        viewManager = LinearLayoutManager(this)
        viewAdapter = HighLightsVideoAdapter(this, highLight.video, highLight.title, highLight.competition.name)

        highlight_recycler_view?.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onItemClick(highLightVideo: HighLightVideo) {
        presenter.prepareWebView(highLightVideo)
    }

    override fun openWebViewActivity(highLightVideo: HighLightVideo) {
        startActivity(WebViewVideoActivity.createIntent(this, highLightVideo.videoUrl))
    }
}
