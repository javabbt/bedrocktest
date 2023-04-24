package com.test.bedrock.base

import com.test.bedrock.base.presentation.nav.NavManager
import org.koin.dsl.module

val baseModule = module {

    single { NavManager() }
}
