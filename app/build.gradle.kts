plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.wendehor.s_crafter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.wendehor.s_crafter"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.recyclerview)
    implementation(libs.constraintlayout)
    implementation(libs.roomRuntime)
    implementation(libs.lifecycleViewmodelKtx)
    implementation(libs.lifecycleLivedataKtx)
    annotationProcessor(libs.roomCompiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}