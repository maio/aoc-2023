import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.21"
}

group = "cz.maio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("com.approvaltests:approvaltests:22.2.1")
    testImplementation("net.jqwik:jqwik:1.8.1")
}

tasks {
    test {
        useJUnitPlatform {
            includeEngines("jqwik", "junit-jupiter")
        }

        testLogging.exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        testLogging.showCauses = true
    }
}

private val javaLanguageVersion = JavaLanguageVersion.of("21")

java {
    toolchain {
        languageVersion.set(javaLanguageVersion)
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(javaLanguageVersion)
    }

    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = javaLanguageVersion.toString()
    targetCompatibility = javaLanguageVersion.toString()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = javaLanguageVersion.toString()
    }
}
