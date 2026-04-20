package com.v2ray.ang.service

import android.annotation.SuppressLint
import android.content.Context
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoSessionSettings
import com.v2ray.ang.handler.SettingsManager

class DialerWebviewService {
    private var geckoRuntime: GeckoRuntime? = null
    private var geckoSession: GeckoSession? = null

    /**
     * Starts the WebView(GeckoView).
     * @param context Service context
     */
    fun start(context: Context) {
        val dialerAddr = SettingsManager.getBrowserDialerAddr()
        if (dialerAddr.isEmpty()) return
        
        geckoSession?.close()

        if (geckoRuntime == null) geckoRuntime = GeckoRuntime.create(context.applicationContext)

        val settings = GeckoSessionSettings.Builder()
            .usePrivateMode(true)
            .suspendMediaWhenInactive(false)
            .build()
        if (geckoSession == null) geckoSession = GeckoSession(settings)
        geckoSession?.open(geckoRuntime!!)

        geckoSession?.setActive(true)

        geckoSession?.loadUri(dialerAddr)

    }

    fun stop() {
        geckoSession?.close()
        geckoSession = null
        // GeckoRuntime manages its own lifecycle, but shutting down the session stops the JS.
        //geckoRuntime?.shutdown()
    }
    
}
