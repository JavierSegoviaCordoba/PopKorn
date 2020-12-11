val lifecycleVersion = "2.2.0"

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    id("pk-publish")
}


tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    classifier = "javadoc"
    from(tasks.dokka)
}

publishing {
    publications {
        publications.configureEach {
            if (this is MavenPublication) {
                artifact(dokkaJar)
            }
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
    google()
}

kotlin {
    jvm()

    android()

    js {
        browser {}
        nodejs {}
    }

    ios() // Creates iosX64("ios") and iosArm64("ios")

    linuxX64()
    linuxArm64()
    macosX64()

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting

        val androidMain by getting {
            dependencies {
                dependsOn(jvmMain)
                implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
            }
        }

        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val iosMain by getting
        val iosTest by getting


        val linuxX64Main by getting
        val linuxArm64Main by getting
        val macosX64Main by getting
        val nativeMain by creating {
            dependsOn(commonMain)
            linuxX64Main.dependsOn(this)
            linuxArm64Main.dependsOn(this)
            macosX64Main.dependsOn(this)
        }
        val nativeTest by creating {
            dependsOn(commonTest)
        }
    }
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

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
    }
}
