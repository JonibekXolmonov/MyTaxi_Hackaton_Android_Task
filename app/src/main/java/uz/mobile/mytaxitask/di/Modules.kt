package uz.mobile.mytaxitask.di

import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import uz.mobile.mytaxitask.common.service.DefaultLocationClient
import uz.mobile.mytaxitask.common.service.LocationClient
import uz.mobile.mytaxitask.data.db.AppDatabase
import uz.mobile.mytaxitask.data.repository.MainRepositoryImpl
import uz.mobile.mytaxitask.data.source.LocalDataSourceImpl
import uz.mobile.mytaxitask.domain.datasource.LocalDataSource
import uz.mobile.mytaxitask.domain.repository.MainRepository
import uz.mobile.mytaxitask.presentation.ui.screens.home.HomeViewModel
import uz.mobile.mytaxitask.common.APP_DATABASE_NAME
import uz.mobile.mytaxitask.data.db.UserLocationDao
import uz.mobile.mytaxitask.domain.use_cases.GetUserLatestLocation
import uz.mobile.mytaxitask.domain.use_cases.InsertUserLocationUseCase

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        coreModule,
        useCaseModule,
        viewModelModule,
        scopes
    )
}

private val coreModule = module {
    //repo
    single<MainRepository> { MainRepositoryImpl(get()) }


    //ds
    single<LocalDataSource> { LocalDataSourceImpl(get()) }

    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(
            androidContext()
        )
    }

    single<LocationClient> { DefaultLocationClient(androidContext(), get()) }

    //db
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            APP_DATABASE_NAME
        ).build()
    }

    single<UserLocationDao> {
        val database = get<AppDatabase>()
        database.userLocationDao()
    }

}


private val scopes = module {
    single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
}

private val useCaseModule = module {
    single { InsertUserLocationUseCase(get()) }
    single { GetUserLatestLocation(get()) }
}

private val viewModelModule = module {
    single { HomeViewModel(get()) }
}
