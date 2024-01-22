package com.rt.inspector.startup

import com.rt.base.proxy.OnAppBaseProxyLinsener
import com.rt.inspector.BuildConfig

class OnAppBaseProxyManager : OnAppBaseProxyLinsener {
    override fun onIsProxy(): Boolean {
        return BuildConfig.is_proxy
    }

    override fun onIsDebug(): Boolean {
        return BuildConfig.is_debug
    }

}