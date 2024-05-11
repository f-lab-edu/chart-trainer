object Dependency {
    const val HILT = "com.google.dagger:hilt-android:2.44"
    const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:2.44"
    const val JAVAX_INJECT = "javax.inject:javax.inject:1"

    const val ANDROIDX_CORE = "androidx.core:core-ktx:1.9.0" // 1.13.1

    object Compose {
        const val BOM = "androidx.compose:compose-bom:2023.03.00"
        const val UI = "androidx.compose.ui:ui"
        const val UI_GRAPHICS = "androidx.compose.ui:ui-graphics"
        const val MATERIAL_3 = "androidx.compose.material3:material3"
        const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:2.7.0"
        const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:1.8.2"

        const val UI_TOOLING = "androidx.compose.ui:ui-tooling"
        const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
        const val JUNIT = "androidx.compose.ui:ui-test-junit4"
        const val UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest"
    }

    object Test {
        const val JUNIT = "junit:junit:4.13.2"
        const val JUNIT_EXT = "androidx.test.ext:junit:1.1.5"
    }
}

