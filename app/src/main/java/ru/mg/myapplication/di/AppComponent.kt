package ru.mg.myapplication.di

import dagger.Component
import ru.mg.myapplication.auth.di.AuthComponent
import ru.mg.myapplication.di.api.ApiModule
import ru.mg.myapplication.di.api.AuthApiModule
import ru.mg.myapplication.push.di.PushComponent
import ru.mg.myapplication.ui.address.di.AddressComponent
import ru.mg.myapplication.ui.cart.di.CartComponent
import ru.mg.myapplication.ui.checkout.di.CheckoutComponent
import ru.mg.myapplication.ui.main.MainActivity
import ru.mg.myapplication.ui.maincatalog.di.MainCatalogComponent
import ru.mg.myapplication.ui.mainflow.di.MainFlowComponent
import ru.mg.myapplication.ui.orderhistory.di.OrderHistoryComponent
import ru.mg.myapplication.ui.profile.di.ProfileComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, AppBindsModule::class, ApiModule::class, AuthApiModule::class,
        FlowCheckModule::class, AppDBModule::class]
)
interface AppComponent {

    fun authComponent(): AuthComponent
    fun addressComponent(): AddressComponent

    fun mainCatalogComponent(): MainCatalogComponent

    fun mainFlowComponent(): MainFlowComponent

    fun cartComponent(): CartComponent

    fun profileComponent(): ProfileComponent

    fun orderHistoryComponent(): OrderHistoryComponent

    fun checkoutComponent(): CheckoutComponent

    fun pushComponent(): PushComponent

    fun inject(activity: MainActivity)

}