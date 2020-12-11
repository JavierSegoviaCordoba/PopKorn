plugins {
    kotlin("multiplatform")
    kotlin("kapt")
}


repositories {
    mavenCentral()
}

kotlin {
    jvm {
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":popkorn"))
                configurations["kapt"].dependencies.add(project(":popkorn-compiler"))
            }
        }
    }
}