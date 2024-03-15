plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // For Hilt
    kotlin("kapt")
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    // Navigation using: https://github.com/raamcosta/compose-destinations
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
}

android {
    namespace = "xokruhli.finalproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "xokruhli.finalproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        packaging {
            resources.excludes.add("META-INF/*")
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation("androidx.benchmark:benchmark-common:1.2.0")
    implementation("androidx.room:room-common:2.6.0")
    implementation("com.google.firebase:firebase-inappmessaging-ktx:20.4.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.mlkit:vision-common:17.3.0")
    implementation("com.google.android.gms:play-services-mlkit-text-recognition-common:19.0.0")
    implementation("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")
    implementation("androidx.test:rules:1.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.compose.material:material-icons-extended:1.4.3")
    implementation("androidx.compose.material3:material3:1.1.0-alpha04")
    implementation ("androidx.compose.material:material:1.5.3")



    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")


    // Moshi for json converter
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")


    // Navigation
    implementation("io.github.raamcosta.compose-destinations:animations-core:1.8.42-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.8.42-beta")

    // DataStore
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Gson
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    implementation("io.coil-kt:coil:2.4.0")
    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation ("com.google.android.gms:play-services-maps:17.0.0")



    // Map
    implementation("com.google.maps.android:maps-compose:3.1.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("androidx.compose.foundation:foundation:1.6.0-alpha07")
    implementation("com.google.maps.android:android-maps-utils:3.5.3")
    implementation("com.google.maps.android:maps-compose-widgets:3.1.1")
    implementation("com.google.maps.android:maps-compose-utils:3.1.1")

    //room

    implementation ("androidx.room:room-runtime:2.6.0")
    kapt ("androidx.room:room-compiler:2.6.0")
    implementation ("androidx.room:room-ktx:2.6.0")


    // Koin
    implementation ("io.insert-koin:koin-core:3.1.5")
    implementation ("io.insert-koin:koin-android:3.1.5")
    implementation ("io.insert-koin:koin-androidx-compose:3.1.5")


    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")


    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

    // ML Kit
    // Layouts
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.annotation:annotation:1.2.0")
    // For graphic overlay
    implementation("com.google.guava:guava:27.1-android")
    // text Recognition
    implementation ("com.google.mlkit:text-recognition:16.0.0")

    implementation ("com.google.accompanist:accompanist-permissions:0.31.1-alpha")

    implementation ("androidx.camera:camera-camera2:1.0.0") // Update with the latest version
    implementation ("androidx.camera:camera-lifecycle:1.0.0") // Update with the latest version
    implementation ("androidx.camera:camera-view:1.0.0-alpha20") // Use the latest version available

    // Hilt
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    //Places
    implementation("com.google.android.libraries.places:places:3.2.0")

    //Accompanist (Permission)
    implementation("com.google.accompanist:accompanist-permissions:0.31.3-beta")

    // Hilt testing
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")

    //Mockk for testing
    testImplementation("io.mockk:mockk:1.13.8")
    androidTestImplementation("io.mockk:mockk-android:1.13.8")

    //MLkit translation
    implementation("com.google.mlkit:translate:17.0.2")


    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:3.11.2") // Use the latest version available
    testImplementation ("androidx.arch.core:core-testing:2.1.0")


    testImplementation ("org.mockito:mockito-inline:3.11.2")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

    testImplementation ("org.robolectric:robolectric:4.6.1")

    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")

    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("io.coil-kt:coil-gif:2.2.2")


    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.4")
    androidTestImplementation ("androidx.compose.ui:ui-test-manifest:1.5.4")

}