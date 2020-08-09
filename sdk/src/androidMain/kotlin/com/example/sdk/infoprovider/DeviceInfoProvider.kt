package com.example.sdk.infoprovider

import android.os.Build
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*

/**
 * Created by Chethan on 2020-04-16.
 */

actual class DeviceInfoProvider {
    actual companion object {

        actual fun getMake(): String? {
            return Build.MANUFACTURER
        }

        actual fun getIP(): String? {
            return getIPAddress(true)
        }

        actual fun getIPV6(): String? {
            return getIPAddress(false)
        }

        actual fun getOS(): String? {
            return "android"
        }

        actual fun getOSVersion(): String? {
            return Build.VERSION.SDK_INT.toString()
        }

        actual fun getModel(): String? {
            return Build.MODEL
        }

        fun getIPAddress(useIPv4: Boolean): String {
            try {
                val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
                val iterator = interfaces.iterator()

                while (iterator.hasNext()) {
                    val networkInterface = iterator.next() as NetworkInterface
                    val addresses = Collections.list(networkInterface.inetAddresses)
                    val addressIterator = addresses.iterator()

                    while (addressIterator.hasNext()) {
                        val addr = addressIterator.next() as InetAddress
                        if (!addr.isLoopbackAddress) {
                            val sAddr = addr.hostAddress
                            val isIPv4 = sAddr.indexOf(":") < 0
                            if (useIPv4) {
                                if (isIPv4) {
                                    return sAddr
                                }
                            } else if (!isIPv4) {
                                val delim = sAddr.indexOf("%")
                                return if (delim < 0) sAddr.toUpperCase() else sAddr.substring(
                                    0,
                                    delim
                                ).toUpperCase()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
            }

            return ""
        }
    }
}