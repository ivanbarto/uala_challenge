package com.ivanbarto.challenge.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.atomic.AtomicInteger

open class BaseViewModel : ViewModel() {
    private val uiStateLoadingCounter = AtomicInteger(0)
    private val _uiState = MutableStateFlow(UiState.IDLE)
    val uiState = _uiState.asStateFlow()

    protected fun setUiState(state: UiState) {
        when (state) {
            UiState.LOADING -> uiStateLoadingCounter.incrementAndGet()
            else -> {
                val value = uiStateLoadingCounter.decrementAndGet()

                if (value < 0) {
                    uiStateLoadingCounter.set(0)
                }
            }
        }

        _uiState.value = if (uiStateLoadingCounter.get() > 0) UiState.LOADING else state
    }
}