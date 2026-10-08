package org.example.wordle

import android.app.Application
import org.example.wordle.di.platformModule
import org.example.wordle.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WordleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WordleApp)
            modules(sharedModule, platformModule)
        }
    }
}