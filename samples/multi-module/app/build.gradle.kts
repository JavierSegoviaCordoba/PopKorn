plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":samples:multi-module:presentation"))
    implementation(project(":samples:multi-module:internal"))

    implementation(project(":popkorn"))
}
