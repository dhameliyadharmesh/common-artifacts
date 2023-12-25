package com.zigcon.common.artifacts.base

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.common.managers.NetworkManager
import com.zigcon.common.artifacts.R
import com.zigcon.common.artifacts.extensions.shortToast

/**
 * @Author Dharmesh
 * @Date 30-08-2022
 *
 * Information
 **/
abstract class BaseViewBindingActivity<VB : ViewBinding> : AppCompatActivity() {

    abstract fun layout(layoutInflater: LayoutInflater): VB

    abstract fun onNetworkChanged(connected: Boolean)

    abstract fun onActivityCreate(bundle: Bundle?)

    private var baseViewModel: BaseViewModel? = null
    private lateinit var progressDialog: Dialog
    private var binding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = layout(LayoutInflater.from(this))
        binding?.root?.keepScreenOn = true
        setContentView(binding!!.root)
        onActivityCreate(savedInstanceState)
        initObservers()
        initNetworkObserver()
    }

    private fun initObservers() {
        baseViewModel?.getToastData()?.observe(this) { strResId ->
            strResId?.let {
                if (it.getContentIfNotHandled() == null) return@observe
                this.shortToast(it.peekContent())
            }
        }
        baseViewModel?.getToastStrData()?.observe(this) { str ->
            str?.let {
                if (it.getContentIfNotHandled() == null) return@observe
                this.shortToast(it.peekContent())
            }
        }
    }

    private fun initNetworkObserver() {
        val app: BaseApplication = application as BaseApplication
        val networkManager: NetworkManager = app.networkManager
        networkManager.networkData.observe(this, ::onNetworkChanged)
    }

    fun isNetConnected(): Boolean {
        val app: BaseApplication = application as BaseApplication
        val networkManager: NetworkManager = app.networkManager
        return networkManager.isConnected(this)
    }

    fun isInternetConnected(): Boolean {
        val app: BaseApplication = application as BaseApplication
        val networkManager: NetworkManager = app.networkManager
        val connected = networkManager.networkData.value ?: run {
            false
        }
        return connected
    }

    /**
     *@desc create progress dialog for doing api call or heavy task on main thread
     *@return Dialog
     */
    fun initializeProgressDialog(isCancelable: Boolean = false, layoutRes : Int  = R.layout.dialog_progress): Dialog {
        progressDialog = Dialog(this)
        progressDialog.setContentView(/* layoutResID = */ layoutRes)
        progressDialog.setCancelable(isCancelable)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(0))
        return progressDialog
    }

    /**
     *@desc show progress dialog when doing api call or heavy task on main thread
     */
    open fun showProgressDialog() {
        if (!isFinishing) {
            if (::progressDialog.isInitialized && !progressDialog.isShowing) progressDialog.show()
        }
    }

    /**
     * Hide progress bar when doing api call or heavy task on main thread
     */
    fun dismissProgressDialog() {
        if (::progressDialog.isInitialized && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}