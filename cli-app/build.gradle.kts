plugins {
    id("org.jetbrains.kotlin.jvm").version("1.7.10")
    application
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx", "kotlinx-cli", "0.3.5")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    // Define the main class for the application
    mainClass.set("cli.app.AppKt")
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "cli.app.AppKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.register<Copy>("packageDistribution") {
    dependsOn("jar")
    from("${project.rootDir}/scripts/cron-cli")
    from("${project.projectDir}/build/libs/${project.name}.jar") {
        into("lib")
    }
    into("${project.rootDir}/dist")
}