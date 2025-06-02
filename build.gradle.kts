import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    java
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

java {

}

kotlin {

}

dependencies {
    // https://mvnrepository.com/artifact/com.sun.mail/jakarta.mail
    implementation("com.sun.mail:jakarta.mail:2.0.1")

    // The BOM will manage the module versions and transitive dependencies
    implementation(platform("com.google.auth:google-auth-library-bom:1.30.1"))
    // Replace with the module(s) that are needed
    implementation("com.google.auth:google-auth-library-oauth2-http")

    // https://mvnrepository.com/artifact/com.google.oauth-client/google-oauth-client
    implementation("com.google.oauth-client:google-oauth-client:1.39.0")

    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20250107")

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1.1-jre")

    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "demo"
            packageVersion = "1.0.0"
        }
    }
}
