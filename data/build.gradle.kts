import java.util.Properties

plugins {
    id(Plugin.ANDROID_LIBRARY)
    id(Plugin.KOTLIN_ANDROID)
    id(Plugin.KTLINT)
    id(Plugin.HILT)
    kotlin(Plugin.KAPT)
}

val properties = Properties()
val localPropertiesFile = rootProject.file("local.properties").inputStream()
properties.load(localPropertiesFile)
localPropertiesFile.close()

android {
    namespace = "com.yessorae.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        defaultConfig {
            buildConfigField(
                "String",
                "POLYGON_API_KEY",
                "${properties["POLYGON_API_KEY"]}"
            )
        }

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
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(path = ":domain"))

    implementation(Dependency.Data.DATA_STORE)

    implementation(Dependency.Data.ROOM_RUNTIME)
    annotationProcessor(Dependency.Data.ROOM_COMPILER)
    kapt(Dependency.Data.ROOM_KAPT)
    implementation(Dependency.Data.ROOM_COROUTINE)

    implementation(Dependency.Data.RETROFIT)
    implementation(Dependency.Data.RETROFIT_GSON_CONVERTER)
    implementation(Dependency.Data.OK_HTTP_3)

    implementation(Dependency.Common.HILT)
    kapt(Dependency.Common.HILT_COMPILER)

    testImplementation(Dependency.Common.JUNIT)
    androidTestImplementation(Dependency.Common.JUNIT_EXT)
}
