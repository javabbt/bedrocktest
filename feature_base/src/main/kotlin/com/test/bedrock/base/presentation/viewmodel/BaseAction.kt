package com.test.bedrock.base.presentation.viewmodel

interface BaseAction<State> {
    fun reduce(state: State): State
}
