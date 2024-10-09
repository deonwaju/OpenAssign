plugins {
    `core-module-config`
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "com.deontch.core.securestore"
}

dependencies {
    implementation(project(":core:base"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}
