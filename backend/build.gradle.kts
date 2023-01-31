import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("co.uzzu.dotenv.gradle") version "2.0.0"
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("com.diffplug.spotless") version "6.12.0"
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.spring") version "1.7.21"
    application
}

group = "dev.marcel"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.session:spring-session-data-redis")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.withType<JavaExec> {
    env.allVariables.map {
        systemProperty(it.key, it.value)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test framework
            useJUnitJupiter()
        }
    }
}

application {
    mainClass.set("dev.marcel.sessionredis.ServerKt")
}

spotless {
    kotlin {
        ktlint()
    }
    kotlinGradle {
        target("*.gradle.kts", "gradle/*.gradle.kts")
        ktlint()
    }
    format("misc") {
        target(
            "**/*.gradle",
            "**/*.gitignore",
            "README.md",
            "config/**/*.xml",
            "src/**/*.xml",
            ".env"
        )
        trimTrailingWhitespace()
        endWithNewline()
    }
    encoding("UTF-8")
}
