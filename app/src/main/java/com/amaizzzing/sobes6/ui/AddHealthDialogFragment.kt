package com.amaizzzing.sobes6.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.amaizzzing.sobes6.HealthApp
import com.amaizzzing.sobes6.R
import com.amaizzzing.sobes6.entities.HealthEntity
import com.amaizzzing.sobes6.services.database.repositories.IHealthRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AddHealthDialogFragment: DialogFragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext =
        Dispatchers.Main + SupervisorJob()

    interface OnCloseClick {
        fun onCloseAddCHealthDialog()
    }

    @Inject
    lateinit var healthRepository: IHealthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HealthApp.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_health, container, false)

        val topDavl = view.findViewById<EditText>(R.id.top_davl)
        val bottomDavl = view.findViewById<EditText>(R.id.bottom_davl)
        val heartbeat = view.findViewById<EditText>(R.id.heartbeat)

        val cancelButton = view.findViewById<Button>(R.id.button_cancel)
        cancelButton.setOnClickListener{ closeDialog() }

        val okButton = view.findViewById<Button>(R.id.button_ok)
        okButton.setOnClickListener {
            if (topDavl.text.isBlank() || bottomDavl.text.isBlank() || heartbeat.text.isBlank()) {
                Toast.makeText(requireContext(), R.string.emtpy_dialog_text, Toast.LENGTH_LONG).show()
            } else {
                launch(Dispatchers.IO) {
                    healthRepository.insertHealthEntity(
                        HealthEntity(
                            top = topDavl.text.toString().toInt(),
                            bottom = bottomDavl.text.toString().toInt(),
                            heartbeat = heartbeat.text.toString().toInt(),
                            time = System.currentTimeMillis()
                        )
                    )
                    onCloseImpl?.onCloseAddCHealthDialog()
                    closeDialog()
                }
            }
        }

        return view
    }
    
    private fun closeDialog() {
        parentFragmentManager
            .beginTransaction()
            .remove(this@AddHealthDialogFragment)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.8).toInt()
        dialog?.window?.apply {
            setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawableResource(R.drawable.round_corners)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        coroutineContext.cancel()
    }

    companion object {
        fun newInstance(): AddHealthDialogFragment = AddHealthDialogFragment()

        var onCloseImpl: OnCloseClick? = null
    }
}