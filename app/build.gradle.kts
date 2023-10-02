plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.widgets.widgey"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.widgets.widgey"
        minSdk = 26
        targetSdk = 33
        versionCode = 3
        versionName = "1.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.google.android.play:core:1.10.3")

    implementation("com.revenuecat.purchases:purchases:6.0.0") //Revenuecat
    implementation("com.onesignal:OneSignal:4.8.0") // Onesignal
    implementation(platform("com.google.firebase:firebase-bom:32.2.2")) // Firebase master
    implementation("com.google.firebase:firebase-analytics-ktx") // Google Analytics
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.flurry.android:analytics:13.3.0@aar")
    implementation("com.microsoft.appcenter:appcenter-analytics:4.1.0")
    implementation("com.microsoft.appcenter:appcenter-crashes:4.1.0")

    implementation("com.google.code.gson:gson:2.8.9")
    implementation("net.danlew:android.joda:2.10.9")
    implementation("org.apache.commons:commons-text:1.7")
    implementation("com.github.KwabenBerko:OpenWeatherMap-Android-Library:2.1.0")

    implementation("com.github.bumptech.glide:glide:4.9.0")
    implementation("com.codemybrainsout.rating:ratingdialog:1.0.8")// Legacy Rating Dialog
    implementation("com.github.daniel-stoneuk:material-about-library:3.1.2")// About Page
    implementation("com.mikepenz:iconics-core:3.2.5") //Icons
    implementation("com.mikepenz:community-material-typeface:2.0.46.1@aar")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}