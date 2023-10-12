import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    `java-library`
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.deps.management)
    pmd
}

group = "ru.vasiand"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation(libs.log4jdbc)
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.springframework.boot:spring-boot-autoconfigure")

    testImplementation("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withSourcesJar()
}

tasks.compileJava {
    options.compilerArgs.addAll(
        listOf(
            "-Xlint:all,-processing", // Enables all recommended warnings.
            "-Werror" // Terminates compilation when warnings occur.
        )
    )
    options.encoding = "UTF-8"
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version
            )
        )
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events = setOf(TestLogEvent.FAILED)
        exceptionFormat = TestExceptionFormat.FULL
    }
}

pmd {
    toolVersion = "6.23.0"
    ruleSetFiles = files("pmd.xml")
}