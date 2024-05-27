import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm")
    id("com.android.lint")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    //린트 버전은 Android Gradle Plugin (AGP) Version에 +23 을 해줍니다.
    // AGP Version을 따로 설정해 주지 않으신 분은 아래와 같이 Project Structure에서 확인할 수 있습니다.
    // 만약 AGP 버전이 7.2.2이라면 린트 버전은 30.2.2 가 됩니다

    compileOnly(libs.kotlin.stdlib)
    compileOnly(libs.lint.api)
    compileOnly(libs.lint.checks)
}
