plugins {
    kotlin("multiplatform")
    kotlin("kapt")
}


repositories {
    mavenCentral()
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":popkorn"))
                implementation(project(":popkorn-examples:java"))
                configurations["kapt"].dependencies.add(project(":popkorn-compiler"))
            }
        }
    }
}