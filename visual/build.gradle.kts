import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("com.jfrog.artifactory")
}

val artifactoryUrl: String by project
val artifactoryUser: String by project
val artifactoryPassword: String by project
val artifactoryRepoRelease: String by project
val artifactoryRepoSnapshot: String by project
val isRelease: String by project
val buildNumber: String by project

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

tasks.register("generateSourcesJar", Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

fun getVersionNumber(): String {
    val props = Properties()
    file("version.properties").inputStream().use { stream ->
        props.load(stream)
    }
    return props.getProperty("version").toString()
}

publishing {
    println(components)
    publications {
        create<MavenPublication>("release") {
            groupId = "poke-montty-visual"
            artifactId = "visual"

            val baseVersionNumber = getVersionNumber() + "." + buildNumber
            val versionName = if (isRelease.toBoolean()) {
                baseVersionNumber
            } else {
                "$baseVersionNumber-SNAPSHOT"
            }
            version = (versionName)

            artifact("$buildDir/outputs/aar/${project.name}-release.aar")

            afterEvaluate {
                from(components["release"])
            }
        }
    }


}

tasks.named("artifactoryPublish").configure {
    dependsOn("build")
}

artifactory {
    clientConfig.isIncludeEnvVars = true
    setContextUrl(artifactoryUrl)
    publish {
        repository {
            repoKey = if (isRelease.toBoolean()) artifactoryRepoRelease else artifactoryRepoSnapshot
            username = artifactoryUser
            password = artifactoryPassword
        }
        defaults {
            setPublishArtifacts(true)
            setPublishPom(true)
            publications(true)
            publications("release")
        }
    }
}

android {
    namespace = "com.poke.montty.visual"
    compileSdk = 34

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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")

    implementation("com.google.android.material:material:1.11.0")

    val composeBom = platform("androidx.compose:compose-bom:2024.02.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")

    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}