package com.zigcon.common.artifacts.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.DialogFragment
import com.zigcon.common.artifacts.R
import com.zigcon.common.artifacts.databinding.DialogFullscreenBinding


/**
 * @Author Dharmesh
 * @Date 23-10-2022
 *
 * Information
 **/
class FullScreenDialog : DialogFragment() {

    private lateinit var binding : DialogFullscreenBinding
    private lateinit var progressDialog: Dialog
    private  var url : String = "www.google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NO_TITLE, R.style.DialogFullScreen)
        arguments?.let {
            url = it.getString("url") ?: "www.google.com"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFullscreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            imgClose.setOnClickListener { dismiss() }
        }
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.run {

            // Get the web view settings instance
            val settings = webView.settings

            // Enable java script in web view
            settings.javaScriptEnabled = true

            // Enable and setup web view cache
            settings.cacheMode = WebSettings.LOAD_DEFAULT

            // Enable zooming in web view
            settings.setSupportZoom(false)
            settings.builtInZoomControls = false
            settings.displayZoomControls = false

            // Zoom web view text
            settings.textZoom = 100

            // Enable disable images in web view
            settings.blockNetworkImage = true
            // Whether the WebView should load image resources
            settings.loadsImagesAutomatically = true


            // More web view settings
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = false
            }   // api 26
            //settings.pluginState = WebSettings.PluginState.ON
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.mediaPlaybackRequiresUserGesture = false


            // More optional settings, you can enable it by yourself
            settings.domStorageEnabled = true
            settings.setSupportMultipleWindows(true)
            settings.loadWithOverviewMode = true
            settings.allowContentAccess = true
            settings.setGeolocationEnabled(true)
            settings.allowFileAccess = true

            // WebView settings
            webView.fitsSystemWindows = true


            /*
                    if SDK version is greater of 19 then activate hardware acceleration
                    otherwise activate software acceleration
                */
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)


            // Set web view client
            webView.webViewClient = object : WebViewClient() {
                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    url?.let {
                        view?.loadUrl(url)
                    }
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    dismissProgressDialog()
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    dismissProgressDialog()
                    error?.let {

                    }
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    errorResponse?.let {
                    }
                }
            }
            showProgressDialog()
            webView.loadUrl(url);
        }
    }

    /**
     *@desc create progress dialog for doing api call or heavy task on main thread
     *@return Dialog
     */
    private fun initializeProgressDialog(isCancelable: Boolean = false): Dialog {
        progressDialog = Dialog(requireActivity())
        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.setCancelable(isCancelable)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(0))
        return progressDialog
    }

    /**
     *@desc show progress dialog when doing api call or heavy task on main thread
     */
    private fun showProgressDialog() {
        if (!(requireContext() as Activity).isFinishing) {
            if (::progressDialog.isInitialized && !progressDialog.isShowing)
                progressDialog.show()
        }
    }

    /**
     * Hide progress bar when doing api call or heavy task on main thread
     */
    fun dismissProgressDialog() {
        if (::progressDialog.isInitialized &&  progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}