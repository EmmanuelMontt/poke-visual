pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()

        maven {
            url = uri("http://localhost:8082/artifactory/poke-montty-libs-release-local")
            credentials {
                username = "ash"
                password = "cmVmdGtuOjAxOjE3NDE3MzkzMDA6dkJLMGN1ZFVld1k3V1RVdjIzZ1JDdUtSWEpW"

            }
            isAllowInsecureProtocol = true
        }
    }
}

rootProject.name = "Visual App"
include(":app")
include(":visual")
