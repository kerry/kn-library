
package com.example.sdk.infoprovider


/**
 * Created by Chethan on 2020-04-16.
 */

actual class DeviceInfoProvider {
    actual companion object {

        actual fun getIP(): String? {
            return null //To change body of created functions use File | Settings | File Templates.
        }

        actual fun getIPV6(): String? {
            return null //To change body of created functions use File | Settings | File Templates.
        }

        actual fun getOS(): String? {
            return "iOS"
        }

        actual fun getOSVersion(): String? {
            return null
        }

        actual fun getModel(): String? {
            return null
        }

        actual fun getMake(): String? {
            return "Apple"
        }

    }
}