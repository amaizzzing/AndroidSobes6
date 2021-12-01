package com.amaizzzing.sobes6

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaizzzing.sobes6.databinding.ActivityMainBinding
import com.amaizzzing.sobes6.di.ViewModelFactory
import com.amaizzzing.sobes6.services.image.IImageLoader
import com.amaizzzing.sobes6.ui.AddHealthDialogFragment
import com.amaizzzing.sobes6.ui.BaseState
import com.amaizzzing.sobes6.ui.adapters.HealthAdapter
import com.amaizzzing.sobes6.ui.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

const val COMMENT_DIALOG_TAG = "AddHealthDialogFragment"

class MainActivity : AppCompatActivity(), AddHealthDialogFragment.OnCloseClick, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    private var binding: ActivityMainBinding? = null

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageBackgroundLoader: IImageLoader<ViewGroup>

    private var healthAdapter: HealthAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        HealthApp.appComponent.inject(this)

        mainViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[MainViewModel::class.java]

        observeData()

        initRecyclerView()

        AddHealthDialogFragment.onCloseImpl = this

        binding?.fabMainFragment?.setOnClickListener {
            showAddHealthDialog()
        }
    }

    private fun initRecyclerView() {
        binding?.let {
            it.rvHealth.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            healthAdapter = HealthAdapter(imageBackgroundLoader, listOf())
            it.rvHealth.adapter = healthAdapter
        }
    }

    private fun showAddHealthDialog() {
        AddHealthDialogFragment
            .newInstance()
            .show(supportFragmentManager, COMMENT_DIALOG_TAG)
    }

    private fun observeData() {
        mainViewModel.data.observe(this) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }

        mainViewModel.getAllRecords()
    }

    private fun renderData(data: BaseState) {
        when(data) {
            is BaseState.Success<*> -> {
                isProgressVisible(false)
                (data.resultData as List<Any>).also { result ->
                    healthAdapter?.let {
                        it.healthList = result
                        it.notifyDataSetChanged()
                    }
                }
            }
            is BaseState.Error -> {
                isProgressVisible(false)
                Toast.makeText(this, "ERROR!", Toast.LENGTH_LONG).show()
            }
            BaseState.Loading -> {
                isProgressVisible(true)
            }
        }
    }

    private fun isProgressVisible(isVisible: Boolean) {
        binding?.let {
            if (isVisible) {
                it.rvHealth.visibility = View.GONE
                it.pbMain.visibility = View.VISIBLE
            } else {
                it.rvHealth.visibility = View.VISIBLE
                it.pbMain.visibility = View.GONE
            }
        }
    }

    override fun onCloseAddCHealthDialog() {
        mainViewModel.getAllRecords()
    }
}