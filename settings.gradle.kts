pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MultiTimer"
include(":app")
include(":feature")
include(":feature:timer")
include(":data")
include(":domain")
include(":utils")
include(":feature:player")
include(":feature:ringtone")
include(":feature:stopwatch")
include(":feature:world_time")
include(":core_presentation")
include(":feature:alarm")
