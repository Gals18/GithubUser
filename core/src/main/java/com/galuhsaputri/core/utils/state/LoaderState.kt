package com.galuhsaputri.core.utils.state

sealed class LoaderState {
    object ShowLoading: LoaderState()
    object HideLoading: LoaderState()
}