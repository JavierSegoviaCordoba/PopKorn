val appCompatVersion = "1.2.0"
val coreVersion = "1.3.2"

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
    kotlin("kapt")
}

repositories {
    mavenCentral()
    jcenter()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":popkorn"))
    implementation(project(":popkorn-compose"))
    kapt(project(":popkorn-compiler"))

    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("androidx.core:core-ktx:$coreVersion")
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.runtime)
    implementation(compose.ui)
}
