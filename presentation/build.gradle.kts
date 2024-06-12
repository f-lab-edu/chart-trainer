plugins {
    id(Plugin.ANDROID_LIBRARY)
    id(Plugin.KOTLIN_ANDROID)
    id(Plugin.KTLINT)
    id(Plugin.HILT)
    kotlin(Plugin.KAPT)
}

android {
    namespace = "com.yessorae.presentation"
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Config.COMPOSE_COMPILER_VERSION
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

    implementation(Dependency.Common.HILT)
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    kapt(Dependency.Common.HILT_COMPILER)

    implementation(Dependency.Presentation.LIFECYCLE_RUNTIME)
    implementation(Dependency.Presentation.ACTIVITY_COMPOSE) // 1.9.0
    implementation(Dependency.Presentation.VIEWMODEL_COMPOSE)
    implementation(platform(Dependency.Presentation.BOM))
    implementation(Dependency.Presentation.UI)
    implementation(Dependency.Presentation.UI_GRAPHICS)
    implementation(Dependency.Presentation.UI_TOOLING_PREVIEW)
    implementation(Dependency.Presentation.MATERIAL_3)
    implementation(Dependency.Presentation.NAVIGATION_COMPOSE)
    implementation(Dependency.Presentation.HILT_NAVIGATION_COMPOSE)
    androidTestImplementation(platform(Dependency.Presentation.BOM))
    androidTestImplementation(Dependency.Presentation.JUNIT)
    debugImplementation(Dependency.Presentation.UI_TOOLING)
    debugImplementation(Dependency.Presentation.UI_TEST_MANIFEST)

    implementation(Dependency.Presentation.VICO_CORE)
    implementation(Dependency.Presentation.VICO_COMPOSE)
    implementation(Dependency.Presentation.VICO_COMPOSE_M3)

    implementation(Dependency.Common.ANDROIDX_CORE)
    testImplementation(Dependency.Common.JUNIT)
    androidTestImplementation(Dependency.Common.JUNIT_EXT)
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    additionalEditorconfig.set(
        mapOf(
            "ktlint_function_naming_ignore_when_annotated_with" to "Composable"
        )
    )
}
