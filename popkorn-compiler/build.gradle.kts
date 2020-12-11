val kotlinPoetVersion = "1.6.0"
val kotlinxMetadataVersion = "0.1.0"
val apacheVersion = "2.7"
val compileTestVersion = "0.18"

plugins {
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
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":popkorn"))
                implementation(kotlin("stdlib"))
                implementation(kotlin("reflect"))
                implementation("com.squareup:kotlinpoet:$kotlinPoetVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:$kotlinxMetadataVersion")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("com.google.testing.compile:compile-testing:$compileTestVersion")
                implementation("commons-io:commons-io:$apacheVersion")
            }
        }
    }
}
