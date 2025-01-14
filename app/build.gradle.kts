plugins {
    id(Plugin.ANDROID_APPLICATION)
    id(Plugin.KOTLIN_ANDROID)
    id(Plugin.KTLINT)
    id(Plugin.HILT)
    kotlin(Plugin.KAPT)
}

android {
    namespace = "com.yessorae.chartrainer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yessorae.chartrainer"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(path = ":data"))
    implementation(project(path = ":domain"))
    implementation(project(path = ":presentation"))

    implementation(Dependency.Common.HILT)
    kapt(Dependency.Common.HILT_COMPILER)

    implementation(Dependency.Common.ANDROIDX_CORE)
    testImplementation(Dependency.Common.JUNIT)
    androidTestImplementation(Dependency.Common.JUNIT_EXT)
    testImplementation(Dependency.Common.TURBINE)
    testImplementation(Dependency.Common.MOCKK)
    testImplementation(Dependency.Common.PAGING_COMMON)
    testImplementation(Dependency.Common.COROUTINE_TEST)
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    additionalEditorconfig.set(
        mapOf(
            "ktlint_function_naming_ignore_when_annotated_with" to "Composable"
        )
    )
}
