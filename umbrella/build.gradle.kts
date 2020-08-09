import org.jetbrains.kotlin.backend.common.push
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    id("kotlin-multiplatform")
}

val targetList: MutableList<KotlinNativeTarget> = mutableListOf()
populateIOSTargetList()

val frameworkName = "KNUmbrellaLib"

kotlin {
    val nativeBuildTypes = getNativeBuildTypes()
    targets {
        for (target in targetList) {
            target.apply {
                binaries {
                    framework(frameworkName, nativeBuildTypes) {
                        export(project(":sdk"))
                    }
                }
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":sdk"))
            }
        }
    }
}

tasks.register<Delete>("cleanFrameworksFolder") {
    val frameworkDir = File(buildDir, "../products/xcode-frameworks")
    val binDir = File(buildDir, "bin")
    delete(frameworkDir, binDir)
}

tasks.create("fat", FatFrameworkTask::class) {
    group = "Universal framework"
    description = "Builds a universal (fat) framework"
    baseName = frameworkName

    val mode = getBuildConfiguration()

    from(frameworks = targetList.map { it.binaries.getFramework(frameworkName, mode) })
    destinationDir = buildDir.resolve("../products/xcode-frameworks")
}

fun populateIOSTargetList() {
    val presetName: String = getPresetName()

    when (presetName) {
        "ios32" -> targetList.push(project.kotlin.iosArm32("ios32"))
        "ios64" -> targetList.push(project.kotlin.iosArm64("ios64"))
        "iosSim" -> targetList.push(project.kotlin.iosX64("iosSim"))
        "fat" -> targetList.addAll(listOf(project.kotlin.iosX64("iosSim"), project.kotlin.iosArm32("ios32"), project.kotlin.iosArm64("ios64")))
        else -> targetList.push(project.kotlin.iosX64("iosSim"))
    }
}

fun getPresetName(): String {
    return if (project.hasProperty("type")) project.properties["type"] as String else "iosSim"
}

fun getNativeBuildTypes(): List<NativeBuildType> {
    return when (getBuildConfiguration()) {
        "DEBUG" -> listOf(NativeBuildType.DEBUG)
        "RELEASE" -> listOf(NativeBuildType.RELEASE)
        else -> listOf(NativeBuildType.DEBUG, NativeBuildType.RELEASE)
    }
}

fun getBuildType(): String {
    return if (project.hasProperty("configuration")) project.properties["configuration"] as String else "DEBUG"
}

fun getBuildConfiguration(): String {
    val buildType: String = getBuildType()
    if (buildType.contains("Debug", ignoreCase = true)) {
        return "DEBUG"
    } else if (buildType.contains("Release", ignoreCase = true)) {
        return "RELEASE"
    } else {
        return "DEBUG"
    }
}

fun getBuildEnvironment(): String {
    return getBuildType().toUpperCase()
            .replace("DEBUG-", "")
            .replace("RELEASE-", "")
            .replace("-", "_")
}

tasks.getByName("build").finalizedBy(tasks.getByName("fat"))
tasks.getByName("assemble").dependsOn(tasks.getByName("cleanFrameworksFolder"))