package com.zynksoftware.base.ui.common

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.zynksoftware.base.receivers.sms.SmsBroadcastReceiver
import com.zynksoftware.base.utils.sms.SmsUtils
import com.zynksoftware.base.utils.network.NetworkCallback
import com.zynksoftware.base.utils.network.NetworkConnection
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

abstract class BaseActivity<B : ViewBinding> (private val viewBinder: (LayoutInflater) -> B):
    AppCompatActivity(), KoinComponent {

    companion object {
        private val TAG = BaseActivity::class.simpleName

        var isRefreshTokenUserActiveActivityRunning = false
    }

    /**
     * This method is used to find the NavController
     * Should return the view id of a [NavHost] or a view within a [NavHost]
     */
    protected abstract fun getViewIdToFindNavController(): Int

    private val networkConnection = NetworkConnection

    private val networkCallback = object: NetworkCallback(networkConnection) {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            runOnUiThread {
                sharedViewModel.onNetworkChangedLiveData.setValue(true)
                //TODO
//                findViewById<SnackbarNoInternetComponent>(R.id.snackbarNoInternetComponent)?.hide()
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            runOnUiThread {
                sharedViewModel.onNetworkChangedLiveData.setValue(false)
                //TODO
//                findViewById<SnackbarNoInternetComponent>(R.id.snackbarNoInternetComponent)?.show()
            }
        }
    }

    protected lateinit var binding: B

    protected val sharedViewModel: SharedViewModel by viewModel()

    val activityLauncher: BetterActivityResult<Intent, ActivityResult> =
        BetterActivityResult.registerActivityForResult(this)

    override fun onCreate(savedInstanceState: Bundle?) {
//        AppConfig.initActivity(window)
        super.onCreate(savedInstanceState)
        binding = viewBinder.invoke(layoutInflater)
        setContentView(binding.root)
        registerNetworkCallback()

    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            if(!isDestroyed && !isFinishing) {
                //TODO
                if (networkConnection.isConnected) {
//                    findViewById<SnackbarNoInternetComponent>(R.id.snackbarNoInternetComponent)?.hide()
                } else {
//                    findViewById<SnackbarNoInternetComponent>(R.id.snackbarNoInternetComponent)?.show()
                }
            }
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterNetworkCallback()
    }

    protected fun startActivitySmsConsent(intent: Intent) {
        activityLauncher.launch(intent, onActivityResult = { result ->
            if (result.resultCode == RESULT_OK) {
                val message = result.data!!.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                val code = SmsUtils.parseOneTimeCode(message)

            }
        })
    }

    protected fun initSmsReceiver(smsVerificationReceiver: SmsBroadcastReceiver) {
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsVerificationReceiver, intentFilter)
        SmsRetriever.getClient(this).startSmsUserConsent(null)
    }

    private fun registerNetworkCallback() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
    }

    private fun unregisterNetworkCallback() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private var downX: Int = 0
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            downX = event.rawX.toInt()
        }
        if (event.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            if (v is EditText) {
                val x = event.rawX.toInt()
                val y = event.rawY.toInt()
                //Was it a scroll - If skip all
                if (Math.abs(downX - x) > 5) {
                    return super.dispatchTouchEvent(event)
                }
                val reducePx = 25
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                //Bounding box is to big, reduce it just a little bit
                outRect.inset(reducePx, reducePx)
                if (!outRect.contains(x, y)) {
                    v.clearFocus()
                    var touchTargetIsEditText = false
                    //Check if another editText has been touched
                    for (vi in v.getRootView().touchables) {
                        if (vi is EditText) {
                            val clickedViewRect = Rect()
                            vi.getGlobalVisibleRect(clickedViewRect)
                            //Bounding box is to big, reduce it just a little bit
                            clickedViewRect.inset(reducePx, reducePx)
                            if (clickedViewRect.contains(x, y)) {
                                touchTargetIsEditText = true
                                break
                            }
                        }
                    }
                    if (!touchTargetIsEditText) {
                        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    protected fun navigate(@IdRes actionId: Int) {
        navigate(actionId, null)
    }

    protected fun navigate(@IdRes actionId: Int, args: Bundle?) {
        if (actionId == -1) {
            Toast.makeText(
                this,
                "Navigation destination not set yet!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            findNavController(getViewIdToFindNavController()).navigate(actionId, args)
        }
    }

    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun showToastLong(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}