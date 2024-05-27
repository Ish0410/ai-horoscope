plugins {
    id("imsh.android.library")
    id("imsh.android.hilt")
}

android {
    namespace = "com.imsh.core.data"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
}
