plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    api(project(":samples:multi-module:open"))

    implementation(project(":popkorn"))
    kapt(project(":popkorn-compiler"))
}
