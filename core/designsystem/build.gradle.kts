plugins {
    id("imsh.android.library")
    id("imsh.android.hilt")
    id("imsh.android.library.compose")
}

android {
    namespace = "com.imsh.core.designsystem"

    lint {
        checkDependencies = true
    }
}

dependencies {
    lintPublish(project(":lint"))
    implementation(libs.kotlinx.coroutines.android)
}
