val kotlinPoetVersion = "1.7.2"
val kotlinxMetadataVersion = "0.2.0"
val apacheVersion = "2.6"
val compileTestVersion = "0.19"

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
        named("jvmMain") {
            dependencies {
                implementation(project(":popkorn"))
                implementation(kotlin("stdlib"))
                implementation(kotlin("reflect"))
                implementation("com.squareup:kotlinpoet:$kotlinPoetVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:$kotlinxMetadataVersion")
            }
        }

        named("jvmTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("com.google.testing.compile:compile-testing:$compileTestVersion")
                implementation("commons-io:commons-io:$apacheVersion")
            }
        }
    }
}
