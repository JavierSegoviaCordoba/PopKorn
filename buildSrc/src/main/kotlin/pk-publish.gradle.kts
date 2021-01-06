plugins {
    `maven-publish`
    signing
}

if (rootProject.file("local.properties").exists()) {
    val properties = java.util.Properties()
    properties.load(rootProject.file("local.properties").inputStream())

    val signingKeyId = properties.getProperty("signing.keyId")
    val signingSecretKeyRingFile = properties.getProperty("signing.secretKeyRingFile")
    val signingPassword = properties.getProperty("signing.password")

    val nexusPro = properties.getProperty("nexus.pro_url")
    val nexusSnapshot = properties.getProperty("nexus.snap_url")

    val nexusUsername = properties.getProperty("nexus.username")
    val nexusPassword = properties.getProperty("nexus.password")

    if (
        listOf(
            signingKeyId,
            signingSecretKeyRingFile,
            signingPassword,
            nexusPro,
            nexusSnapshot,
            nexusUsername,
            nexusPassword
        ).all { property -> property != null }
    ) {
        val isReleaseVersion = !version.toString().endsWith("SNAPSHOT")

        project.extra.set("signing.keyId", signingKeyId)
        project.extra.set("signing.secretKeyRingFile", signingSecretKeyRingFile)
        project.extra.set("signing.password", signingPassword)

        publishing {

            publications.configureEach {
                if (this is MavenPublication) {
                    val publicName = "${rootProject.name} ${name.capitalize()}"
                    pom {
                        name.set(publicName)
                        description.set("Powerful Dependency Injector for Kotlin")
                        url.set("https://github.com/corbella83/PopKorn")
                        licenses {
                            license {
                                name.set("The Apache Software License, Version 2.0")
                                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                                distribution.set("repo")
                            }
                        }
                        developers {
                            developer {
                                id.set("corbella83")
                                name.set("Pau Corbella")
                            }
                        }
                        scm {
                            connection.set("scm:git:git://github.com/corbella83/PopKorn.git")
                            developerConnection.set("scm:git:ssh://git@github.com/corbella83/PopKorn.git")
                            url.set("https://github.com/corbella83/PopKorn")
                        }
                    }
                }
            }

            repositories {
                maven {
                    val releasesRepoUrl = uri(nexusPro)
                    val snapshotsRepoUrl = uri(nexusSnapshot)
                    url = if (isReleaseVersion) releasesRepoUrl else snapshotsRepoUrl
                    credentials {
                        username = nexusUsername
                        password = nexusPassword
                    }
                }
            }
        }

        tasks.withType<Sign>().configureEach {
            onlyIf { isReleaseVersion }
        }

        signing {
            sign(publishing.publications)
        }
    }
}
