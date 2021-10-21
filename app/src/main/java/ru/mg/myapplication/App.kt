package ru.mg.myapplication

import android.content.Context
import com.google.crypto.tink.aead.AeadConfig
import com.yandex.mapkit.MapKitFactory
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import net.danlew.android.joda.JodaTimeAndroid
import ru.mg.myapplication.di.AppComponent
import ru.mg.myapplication.di.AppModule
import ru.mg.myapplication.di.DaggerAppComponent

class App : ClientCoreApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        initTink()
        initGraph()
        initMap()
    }

    override fun initAppMetrica() {
        val config = YandexMetricaConfig
            .newConfigBuilder(BuildConfig.APPMETRICA_API_KEY)
            .withCrashReporting(true)
            .withNativeCrashReporting(true)
            .build()
        YandexMetrica.activate(this, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }


    private fun initTink() {
        AeadConfig.register()
    }

    private fun initMap() {
        MapKitFactory.setApiKey(BuildConfig.MAP_KEY)
    }

    private fun initGraph() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        fun get(context: Context): App =
            context.applicationContext as App
    }
}