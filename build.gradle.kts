plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation("org.junit.jupiter:junit-jupiter-params:5.8.2")

}

tasks.test {
    useJUnitPlatform()
}