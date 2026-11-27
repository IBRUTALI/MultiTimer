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

plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.1.32"
}

gitHooks {
    preCommit {
        tasks("test")
    }
    createHooks()
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
include(":data")
include(":domain")
include(":utils")
include(":feature:player")
include(":feature:ringtone")
include(":feature:world_time")
include(":feature:stopwatch")
include(":feature:timer")
include(":core-presentation")
include(":core-android-service")
include(":feature:alarm")
include(":core-android-service")
