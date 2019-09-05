package com.inaki.dailyAdvice.DI

import com.inaki.dailyAdvice.Network.AdviceService
import com.inaki.dailyAdvice.UI.GameAdviceFragment
import com.inaki.dailyAdvice.UI.RandomAdviceFragment
import com.inaki.dailyAdvice.UI.SearchAdviceFragment
import com.inaki.dailyAdvice.ViewModel.AdviceViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.create

private val adviseClient = retrofitClient().create<AdviceService>()

val networkModule = module {
    single { adviseClient }
}

val viewModelModule = module {
    viewModel { AdviceViewModel(get()) }
}

val fragmentModule = module {
    single { SearchAdviceFragment() }
    single { RandomAdviceFragment() }
    single { GameAdviceFragment() }
}