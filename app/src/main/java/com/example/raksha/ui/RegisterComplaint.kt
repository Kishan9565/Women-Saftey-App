package com.example.raksha.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.raksha.databinding.ActivityRegisterComplaintBinding

class RegisterComplaint : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterComplaintBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        binding = ActivityRegisterComplaintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("url") ?: ""

        makeFullScreen()
        setupWebView(url)

        // Refresh listener
        binding.swipeRefresh.setOnRefreshListener {
            if (isNetworkAvailable()) {
                binding.tvNoInternet.visibility = View.GONE
                binding.webview.reload()
            } else {
                binding.swipeRefresh.isRefreshing = false
                binding.tvNoInternet.visibility = View.VISIBLE
            }
        }

        // Prevent swipe refresh on scroll
        binding.webview.viewTreeObserver.addOnScrollChangedListener {
            binding.swipeRefresh.isEnabled = binding.webview.scrollY == 0
        }
    }

    private fun setupWebView(url: String) {
        val webSettings: WebSettings = binding.webview.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: android.webkit.WebView?, progress: Int) {
                binding.progressBar.visibility = if (progress < 100) View.VISIBLE else View.GONE
                if (progress == 100) binding.swipeRefresh.isRefreshing = false
            }
        }

        binding.webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: android.webkit.WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBar.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
                binding.tvNoInternet.visibility = View.GONE
                binding.webview.visibility = View.VISIBLE
            }

            override fun onReceivedError(
                view: android.webkit.WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                binding.progressBar.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
                binding.tvNoInternet.visibility = View.VISIBLE
                binding.webview.visibility = View.GONE
            }
        }

        if (isNetworkAvailable()) {
            binding.tvNoInternet.visibility = View.GONE
            binding.webview.visibility = View.VISIBLE
            binding.webview.loadUrl(url)
        } else {
            binding.tvNoInternet.visibility = View.VISIBLE
            binding.webview.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    private fun makeFullScreen() {
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        val flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        window.decorView.systemUiVisibility = flags
    }

    override fun onBackPressed() {
        if (binding.webview.canGoBack()) {
            binding.webview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
