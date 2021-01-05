plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("kapt")
}

repositories {
    mavenCentral()
    jcenter()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm()

    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(project(":popkorn"))
                implementation(project(":popkorn-compose"))
                configurations["kapt"].dependencies.add(project(":popkorn-compiler"))

                implementation(compose.desktop.currentOs)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                implementation(compose.ui)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "cc.popkorn.samples.desktop.compose.MainKt"
    }
}
