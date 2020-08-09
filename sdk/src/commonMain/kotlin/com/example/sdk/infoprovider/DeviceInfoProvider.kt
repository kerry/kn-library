package com.example.sdk.infoprovider

expect class DeviceInfoProvider {
    companion object {
        fun getIP(): String?
        fun getIPV6(): String?
        fun getOS(): String?
        fun getOSVersion(): String?
        fun getModel(): String?
        fun getMake(): String?
    }
}
