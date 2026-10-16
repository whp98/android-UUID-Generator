import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

val versionFile = file("../version.properties")
val version = Properties().apply { load(versionFile.reader()) }
val currentVersionCode = version.getProperty("versionCode")?.trim()?.toIntOrNull() ?: 1792146368
val currentVersionName = version.getProperty("versionName")?.trim() ?: "v2.0.0"

val isReleasePackageTask = gradle.startParameter.taskNames.any { taskName ->
    taskName.contains("Release", ignoreCase = true) &&
        (taskName.contains("assemble", ignoreCase = true) ||
            taskName.contains("bundle", ignoreCase = true) ||
            taskName.contains("package", ignoreCase = true) ||
            taskName.contains("publish", ignoreCase = true))
}

val resolvedVersionCode = if (isReleasePackageTask) {
    val nextVersionCode = currentVersionCode + 1
    versionFile.writeText("versionCode=$nextVersionCode\nversionName=$currentVersionName\n")
    println("Incremented versionCode: $currentVersionCode -> $nextVersionCode")
    nextVersionCode
} else {
    currentVersionCode
}

val signFolder = "../androidSign/androidSign/"
val props =
    Properties().apply { load(file(signFolder + "signing.properties").reader()) }
val keyStoreFile = file(signFolder + props.getProperty("storeFile"))
android {
    namespace = "work.jsfr.uuidgenerator"
    compileSdk = 36

    defaultConfig {
        applicationId = "work.jsfr.uuidgenerator"
        minSdk = 24
        targetSdk = 36
        versionCode = resolvedVersionCode
        versionName = currentVersionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        setProperty("archivesBaseName", "uuidGenerator")
        setProperty("archivesBaseName", "$applicationId-$versionName-$versionCode-sdk-$minSdk-$targetSdk")
    }
    val signing = if (file(signFolder + "signing.properties").exists()) {
        signingConfigs.create("release") {
            val props =
                Properties().apply { load(file(signFolder + "signing.properties").reader()) }
            storeFile = file(signFolder + props.getProperty("storeFile"))
            storePassword = props.getProperty("storePassword")
            keyAlias = props.getProperty("keyAlias")
            keyPassword = props.getProperty("keyPassword")
        }
    } else signingConfigs.getByName("debug")
    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signing
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    // 配置 App Bundle
    bundle {
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    tasks.withType<JavaCompile>().configureEach {
//        options.compilerArgs.add("-Xlint:-options")
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}