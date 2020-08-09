buildscript {
    extra["kotlin"] = "1.3.72"
    extra["kotlinNativeGradlePlugin"] = "1.3.41"
    extra["kotlinxSerialization"] = "0.20.0"

    repositories {
        jcenter()
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://maven.fabric.io/public")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.5.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin"]}")
        classpath("org.jetbrains.kotlin:kotlin-native-gradle-plugin:${project.extra["kotlinNativeGradlePlugin"]}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${project.extra["kotlin"]}")
    }
}

allprojects {
    repositories {
        jcenter()
        google()
        mavenCentral()
        maven("https://kotlin.bintray.com/kotlinx")
    }
}
