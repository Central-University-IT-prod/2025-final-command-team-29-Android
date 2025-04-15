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

rootProject.name = "T-Loyalty"
include(":app")
include(":feature-auth-customer")
include(":feature-auth-partner")
include(":feature-main-customer")
include(":feature-main-partner")
include(":feature-stats-partner")
include(":feature-scan-qr-partner")
include(":common")
