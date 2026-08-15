import org.jetbrains.kotlin.konan.properties.Properties
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

val defaultVersionCode = ChronoUnit.SECONDS.between(
    Instant.EPOCH, // 1970-01-01T00:00:00Z
    Instant.now()
).toInt()
val version = Properties().apply { load(file("../version.properties").reader()) }
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
        versionCode = defaultVersionCode
        versionName = version.getProperty("versionName")
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