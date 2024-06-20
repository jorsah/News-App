// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}

//gradle configurations for modules

subprojects {
    project.plugins.configure(project = project)
}

fun PluginContainer.configure(project: Project) {
    whenPluginAdded {
        when (this) {
            is com.android.build.gradle.LibraryPlugin -> {
                project.extensions
                    .getByType<com.android.build.gradle.LibraryExtension>()
                    .apply {
                        applyCommons()
                    }
            }
        }
    }
}

fun com.android.build.gradle.LibraryExtension.applyCommons() {
    compileSdk = Sdk.COMPILE

    defaultConfig.apply {
        minSdk = Sdk.MIN
        targetSdk = Sdk.TARGET
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            consumerProguardFiles(
                "${rootProject.rootDir.absolutePath}/app/proguard-rules.pro"
            )
        }
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = Constants.JVM
        }
    }
}