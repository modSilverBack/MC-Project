plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.dagger.hilt) apply false // ✅ Add this line
    id("com.google.devtools.ksp") version "2.0.0-1.0.22" apply false
}