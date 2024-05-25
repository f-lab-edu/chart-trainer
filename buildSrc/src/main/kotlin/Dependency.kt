object Dependency {
    object Common {
        const val HILT = "com.google.dagger:hilt-android:${Version.HILT}"
        const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Version.HILT}"

        const val ANDROIDX_CORE = "androidx.core:core-ktx:${Version.ANDROIDX_CORE}" // 1.13.1

        const val JUNIT = "junit:junit:${Version.JUNIT}"
        const val JUNIT_EXT = "androidx.test.ext:junit:${Version.JUNIT_EXT}"

        object Version {
            const val HILT = "2.44"
            const val ANDROIDX_CORE = "1.9.0"
            const val JUNIT = "4.13.2"
            const val JUNIT_EXT = "1.1.5"
        }
    }

    object PlatformIndependent {
        const val JAVAX_INJECT = "javax.inject:javax.inject:${Version.JAVAX_INJECT}"
        const val KOTLINX_COROUTINSE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.KOTLINX_COROUTINSE}"

        object Version {
            const val JAVAX_INJECT = "1"
            const val KOTLINX_COROUTINSE = "1.8.1"
        }
    }

    object Data {
        const val DATA_STORE = "androidx.datastore:datastore-preferences:${Version.DATA_STORE}"
        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Version.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Version.ROOM}"
        const val ROOM_KAPT = "androidx.room:room-compiler:${Version.ROOM}"
        const val ROOM_COROUTINE = "androidx.room:room-ktx:${Version.ROOM}"
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Version.RETROFIT}"
        const val RETROFIT_GSON_CONVERTER =
            "com.squareup.retrofit2:converter-gson:${Version.RETROFIT}"
        const val OK_HTTP_3 = "com.squareup.okhttp3:logging-interceptor:${Version.OK_HTTP_3}"

        object Version {
            const val DATA_STORE = "1.1.1"
            const val ROOM = "2.6.1"
            const val RETROFIT = "2.11.0"
            const val OK_HTTP_3 = "4.12.0"
        }
    }

    object Presentation {
        const val BOM = "androidx.compose:compose-bom:${Version.BOM}"
        const val UI = "androidx.compose.ui:ui"
        const val UI_GRAPHICS = "androidx.compose.ui:ui-graphics"
        const val MATERIAL_3 = "androidx.compose.material3:material3"
        const val LIFECYCLE_RUNTIME =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Version.LIFECYCLE_RUNTIME}"
        const val ACTIVITY_COMPOSE =
            "androidx.activity:activity-compose:${Version.ACTIVITY_COMPOSE}"

        const val VICO_COMPOSE = "com.patrykandpatrick.vico:compose:${Version.VICO}"
        const val VICO_COMPOSE_M3 = "com.patrykandpatrick.vico:compose-m3:${Version.VICO}"
        const val VICO_CORE = "com.patrykandpatrick.vico:core:${Version.VICO}"
        const val UI_TOOLING = "androidx.compose.ui:ui-tooling"
        const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
        const val JUNIT = "androidx.compose.ui:ui-test-junit4"
        const val UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest"

        object Version {
            const val BOM = "2023.03.00"
            const val LIFECYCLE_RUNTIME = "2.7.0"
            const val ACTIVITY_COMPOSE = "1.8.2"
            const val VICO = "2.0.0-alpha.19"// "1.14.0"
        }
    }
}

