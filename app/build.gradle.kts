plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.achsanit.simpleweather"
    compileSdk = 34

    defaultConfig {
        val apiKeyOpenWeather: String by project

        applicationId = "com.achsanit.simpleweather"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String","API_KEY_OPEN_WEATHER", apiKeyOpenWeather)
    }

    buildTypes {
        val baseApiWeather: String by project
        val baseApiForecast: String by project
        val baseImageUrl: String by project

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String","BASE_API_WEATHER", baseApiWeather)
            buildConfigField("String","BASE_API_FORECAST", baseApiForecast)
            buildConfigField("String","BASE_IMAGE_URL", baseImageUrl)
        }
        debug {
            buildConfigField("String","BASE_API_WEATHER", baseApiWeather)
            buildConfigField("String","BASE_API_FORECAST", baseApiForecast)
            buildConfigField("String","BASE_IMAGE_URL", baseImageUrl)
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
        buildConfig = true
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.vmadalin:easypermissions-ktx:1.0.0")

    val lifecycle_version = "2.6.2"
    // ViewModel LiveData (lifecycle)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    val retrofit_version = "2.9.0"
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    val chucker_version = "3.5.2"
    // chucker for dev logging
    implementation("com.github.chuckerteam.chucker:library:$chucker_version")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:$chucker_version")

    val koin_version = "3.5.0"
    // koin for DI
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-android:$koin_version")

    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

    val room_version = "2.6.0"
    // room
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-rxjava2:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //coil for load image
    implementation("io.coil-kt:coil:1.3.2")

}