

plugins {
    id("java-library")
    id(Plugin.KOTLIN_JVM)
    id(Plugin.KTLINT)
    kotlin(Plugin.KOTLIN_SERIALIZATION)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(Dependency.PlatformIndependent.JAVAX_INJECT)
    implementation(Dependency.PlatformIndependent.KOTLINX_COROUTINSE)
    implementation(Dependency.Common.KOTOIN_SERIALIZATION_JSON)
    implementation(Dependency.Common.PAGING_COMMON)
    testImplementation(Dependency.Common.JUNIT)
}
