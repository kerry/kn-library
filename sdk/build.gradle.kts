import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.multiplatform")
    id("kotlinx-serialization")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(18)
        targetSdkVersion(29)
    }
}  

kotlin {
    val ios32 = iosArm32("ios32")
    val ios64 = iosArm64("ios64")
    val iosSim = iosX64("iosSim")

    targets {
        targetFromPreset(presets.getByName("jvm"), "android")

        ios32.apply {  }
        ios64.apply {  }
        iosSim.apply {  }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:1.3.72")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.20.0")
            }
        }   

      commonTest {
         dependencies {
              implementation("org.jetbrains.kotlin:kotlin-test-common")
              implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
              implementation("org.mockito:mockito-inline:2.23.4")
              implementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-RC1")
            }
        }

        maybeCreate("androidMain")
        getByName("androidMain") {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.1")
            }
        }


        val ios32Main by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:0.20.0")
            }
        }

        val ios32Test by getting {
            dependencies {

            }
        }

        val ios64Main by getting {
            dependsOn(ios32Main)
        }

        val ios64Test by getting {
            dependsOn(ios32Test)
        }

        val iosSimMain by getting {
            dependsOn(ios32Main)
        }

        val iosSimTest by getting {
            dependsOn(ios32Test)
        }
    }
}
