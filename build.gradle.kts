plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform {
            excludeTags("slow")

        }
    }
    wrapper {
        gradleVersion = "7.3"
    }
}
