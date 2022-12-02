package com.galuhsaputri.core.domain.repository

import com.galuhsaputri.core.domain.model.*
import com.galuhsaputri.core.utils.state.ResultState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    /**
     * Remote
     */
    suspend fun getUserFromApi(username: String) : Flow<ResultState<List<UserSearchItem>>>

    suspend fun getDetailUserFromApi(username: String) : Flow<ResultState<UserDetail>>

    suspend fun getUserFollowers(username: String) : Flow<ResultState<List<UserFollower>>>

    suspend fun getUserFollowing(username: String) : Flow<ResultState<List<UserFollowing>>>

    /**
     * Local
     */
    fun fetchAllUserFavorite() : Flow<List<UserFavorite>>

    fun getFavoriteUserByUsername(username: String) : Flow<List<UserFavorite>>

    suspend fun addUserToFavDB(userFavoriteEntity: UserFavorite)

    suspend fun deleteUserFromFavDB(userFavoriteEntity: UserFavorite)



}