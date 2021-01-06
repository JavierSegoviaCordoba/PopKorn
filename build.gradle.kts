buildscript {
    val kotlinVersion by System.getProperties()
    val dokkaVersion = "0.10.1"
    val composeVersion = "0.3.0-build139"

    repositories {
        mavenCentral()
        jcenter()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:$dokkaVersion")
        classpath("org.jetbrains.compose:compose-gradle-plugin:$composeVersion")
    }
}

allprojects {
    val libraryGroup: String by project
    val libraryVersion: String by project

    group = libraryGroup
    version = libraryVersion
}
