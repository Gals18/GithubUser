package com.galuhsaputri.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.galuhsaputri.core.domain.usecase.UserUseCase

class FavoriteUserViewModel(
    userUseCase: UserUseCase
) : ViewModel() {
    val fetchAllUserFavorite = userUseCase.fetchAllUserFavorite().asLiveData()
}