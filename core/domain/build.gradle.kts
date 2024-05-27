plugins {
    id("imsh.android.library")
    id("imsh.android.hilt")
}

android {
    namespace = "com.imsh.core.domain"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
}
