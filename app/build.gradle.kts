import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}
val version = Properties().apply { load(file("../version.properties").reader()) }
val signFolder = "../androidSign/androidSign/"
val props =
    Properties().apply { load(file(signFolder + "signing.properties").reader()) }
val keyStoreFile = file(signFolder + props.getProperty("storeFile"))

android {
    namespace = "work.jsfr.uuidgenerator"
    compileSdk = 34

    defaultConfig {
        applicationId = "work.jsfr.uuidgenerator"
        minSdk = 24
        targetSdk = 34
        versionCode = version.getProperty("versionCode").toInt()
        versionName = version.getProperty("versionName")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        language {
            enableSplit = false
        }
        density {
            enableSplit = false
        }
        abi {
            enableSplit = true
        }
    }
    applicationVariants.all {
        outputs.all {
            (this as? com.android.build.gradle.internal.api.ApkVariantOutputImpl)
                ?.outputFileName = "uuidGenerator.apk"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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