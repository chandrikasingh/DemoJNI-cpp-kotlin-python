plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	id("com.chaquo.python")
}

android {
	namespace = "com.jsrvc.ui.rndnative"
	compileSdk = 36
	
	defaultConfig {
		applicationId = "com.jsrvc.ui.rndnative"
		minSdk = 24
		targetSdk = 36
		versionCode = 1
		versionName = "1.0"
		
		flavorDimensions += "pyVersion"
		productFlavors {
			create("py310") { dimension = "pyVersion" }
			create("py311") { dimension = "pyVersion" }
		}
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		externalNativeBuild {
			cmake {
				cppFlags += "-std=c++17"
			}
		}
		ndk {
			// On Apple silicon, you can omit x86_64.
			abiFilters += listOf("arm64-v8a", "x86_64")
		}
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
	externalNativeBuild {
		cmake {
			path = file("src/main/cpp/CMakeLists.txt")
			version = "3.22.1"
		}
	}
	buildFeatures {
		viewBinding = true
	}
}

//chaquopy {
//	productFlavors {
//		getByName("py310") { version = "3.10" }
//		getByName("py311") { version = "3.11" }
//	}
//}

dependencies {
	
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.constraintlayout)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
}