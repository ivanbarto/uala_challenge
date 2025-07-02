plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("com.google.devtools.ksp")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)

    implementation(libs.androidx.room.runtime.jvm)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.sqlite.bundled.jvm)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.koin.test)
    testImplementation(libs.junit.junit)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.koin.test.junit4)
}