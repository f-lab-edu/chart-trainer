plugins {
    id(Plugin.ANDROID_LIBRARY)
    id(Plugin.KOTLIN_ANDROID)
    id(Plugin.KTLINT)
    id(Plugin.HILT)
    kotlin(Plugin.KAPT)
}

android {
    namespace = "com.yessorae.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(path = ":domain"))

    implementation(Dependency.HILT)
    kapt(Dependency.HILT_COMPILER)

    testImplementation(Dependency.Test.JUNIT)
    androidTestImplementation(Dependency.Test.JUNIT_EXT)
}
