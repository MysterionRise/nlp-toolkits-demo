plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

    implementation("org.apache.opennlp:opennlp-tools:1.9.4")
}

application {
    mainClass.set("org.mystic.opennlp.demo.Main")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}



