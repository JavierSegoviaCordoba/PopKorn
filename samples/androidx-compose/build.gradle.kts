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
    defaultConfig {
        applicationId = "cc.popkorn.samples.androidx.compose"
    }

    androidConfig(isMultiplatform = false)
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
