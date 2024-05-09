// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugin.ANDROID_APPLICATION) version Plugin.Version.ANDROID_APPLICATION apply false
    id(Plugin.KOTLIN_ANDROID) version Plugin.Version.KOTLIN_ANDROID apply false
    id(Plugin.KOTLIN_JVM) version Plugin.Version.KOTLIN_JVM apply false
    id(Plugin.ANDROID_LIBRARY) version Plugin.Version.ANDROID_LIBRARY apply false
    id(Plugin.KTLINT) version Plugin.Version.KTLINT
    id(Plugin.HILT) version Plugin.Version.HILT apply false
}
