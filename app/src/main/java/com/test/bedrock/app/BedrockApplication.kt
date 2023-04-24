package com.test.bedrock.app

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.test.bedrock.BuildConfig
import com.test.bedrock.appModule
import com.test.bedrock.base.baseModule
import com.test.bedrock.feature.home.featureHomeModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class BedrockApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initTimber()
        initDynamicColorScheme()
    }

    private fun initDynamicColorScheme() {
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@BedrockApplication)

            modules(appModule)
            modules(baseModule)
            modules(featureHomeModules)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
