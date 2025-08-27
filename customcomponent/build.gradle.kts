plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    `maven-publish`
}

android {
    namespace = "com.example.customcomponent"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.register("sourcesJar", Jar::class.java) {
    description = "Creates a jar file containing the sources."
    group = "build"
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

publishing {
    repositories {
        maven {
            name = "ui-library"
            url = uri("https://maven.pkg.github.com/russvkm/custom-compose")
        }
    }
    publications {
        create<MavenPublication>("custom-compose") {
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")

            // publishes the source code
            artifact(tasks["sourcesJar"])

            groupId = "com.russvkm"
            artifactId = "custom-compose"
            version = version

            pom.withXml {
                val dependenciesNode = asNode().appendNode("dependencies")
                configurations["api"].allDependencies.forEach {
                    val dependencyNode = dependenciesNode.appendNode("dependency")
                    dependencyNode.appendNode("groupId", it.group)
                    dependencyNode.appendNode("artifactId", it.name)
                    dependencyNode.appendNode("version", it.version)
                    dependencyNode.appendNode(
                        "scope",
                        "compile"
                    ) // "compile" for API, "runtime" for implementation
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}