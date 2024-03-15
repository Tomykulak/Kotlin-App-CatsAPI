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
        google()
        mavenCentral()
    }
    repositories {
        // Depending on AndroidX Snapshot Builds to get the latest CameraX libs.
        maven { setUrl ("https://androidx.dev/snapshots/builds/6787662/artifacts/repository/")}
    }
}

rootProject.name = "FinalProject"
include(":app")
