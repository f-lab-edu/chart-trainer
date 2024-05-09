import Dependency

plugins {
    id("java-library")
    id(Plugin.KOTLIN_JVM)
    id(Plugin.KTLINT)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(Dependency.JAVAX_INJECT)
}