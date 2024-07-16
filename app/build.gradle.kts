import com.android.tools.r8.internal.kt

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.cryptoapp.cryptocompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cryptoapp.cryptocompose"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Özel BuildConfig alanlarını tanımlıyoruz
        buildConfigField("String", "BASE_URL", "\"https:/api.coingecko.com/api/v3/\"")
        buildConfigField("String", "API_KEY", "\"CG-tfsrZuKZswpMo1duMiHM2dG8\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Dependencies
    // Retrofit for API requests
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    //OK-http
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation ("io.coil-kt:coil-compose:2.0.0-rc01")


}