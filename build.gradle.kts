buildscript {
	dependencies {
		classpath("org.flywaydb:flyway-database-postgresql:10.10.0")
	}
}

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.jooq.jooq-codegen-gradle") version  "3.19.11"
	id("org.flywaydb.flyway") version "10.10.0"

}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.jooq:jooq-meta")
	implementation("org.jooq:jooq-codegen")
	implementation("org.jooq:jooq-postgres-extensions:3.19.11")
	jooqCodegen("org.postgresql:postgresql:42.7.3")
	testImplementation("org.mockito:mockito-core:4.5.1")
	testImplementation("org.mockito:mockito-inline:4.5.1")
	testImplementation("com.h2database:h2")
	testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs?.plusAssign("-javaagent:${configurations.testRuntimeClasspath.get().find { it.name.contains("mockito-inline") }!!.absolutePath}")
}

jooq {
	configuration {

		jdbc {
			driver = "org.postgresql.Driver"
			url = "jdbc:postgresql://localhost:5432/postgres"
			user = "myuser"
			password = "secret"
		}
		generator {
			database {
				name = "org.jooq.meta.postgres.PostgresDatabase"
				includes = ".*"
				inputSchema = "public"
				excludes = "flyway_schema_history"
			}

			target {
				packageName = "com.example.db"
				directory = "src/main/kotlin"
			}
		}
	}
}

sourceSets.main {
	java.srcDirs("build/generated-sources/jooq")
}

tasks.named("compileKotlin") {
	dependsOn(tasks.named("jooqCodegen"))
}

flyway {
	driver = "org.postgresql.Driver"
	url = "jdbc:postgresql://localhost:5432/postgres"
	user = "myuser"
	password = "secret"
	schemas = arrayOf("public")
	cleanDisabled = false
}

tasks.named("jooqCodegen") {
	dependsOn(tasks.named("flywayMigrate"))
	inputs.files(fileTree("src/main/resources/db/migration"))
}