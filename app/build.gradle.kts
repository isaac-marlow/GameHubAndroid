import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.ilhomsoliev.gamehubandroid"
  compileSdk {
    version = release(36)
  }

  defaultConfig {
    applicationId = "com.ilhomsoliev.gamehubandroid"
    minSdk = 26
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures {
    compose = true
  }

  kotlin {
    compilerOptions {
      jvmTarget = JvmTarget.JVM_11
    }
  }
}

dependencies {
  // AndroidX Core
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)

  // Compose
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.material.icons.extended)

  // Navigation Compose
  implementation(libs.androidx.navigation.compose)

  // Koin - Dependency Injection
  implementation(libs.koin.android)
  implementation(libs.koin.androidx.compose)

  // Ktor Client - Networking
  implementation(libs.ktor.client.android)
  implementation(libs.ktor.client.content.negotiation)
  implementation(libs.ktor.client.logging)
  implementation(libs.ktor.serialization.kotlinx.json)

  // Kotlinx Serialization
  implementation(libs.kotlinx.serialization.json)

  // Room - Local Database
  implementation(libs.room.runtime)
  implementation(libs.room.ktx)
  ksp(libs.room.compiler)

  // Lifecycle ViewModel
  implementation(libs.androidx.lifecycle.viewmodel.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.compose)

  // DataStore - Key-Value Storage
  implementation(libs.androidx.datastore.preferences)

  // Glide - Image Loading
  implementation(libs.glide)
  ksp(libs.glide.compiler)
  implementation(libs.landscapist.glide)

  // Kotlinx DateTime
  implementation(libs.kotlinx.datetime)

  // Flipper - Debugging
  debugImplementation(libs.flipper)
  debugImplementation(libs.flipper.network.plugin)
  debugImplementation(libs.soloader)
  releaseImplementation(libs.flipper.noop)

  // Testing
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
}
