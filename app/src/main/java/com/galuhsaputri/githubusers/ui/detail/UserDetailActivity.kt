package com.galuhsaputri.githubusers.ui.detail

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.galuhsaputri.githubusers.R
import com.galuhsaputri.githubusers.ui.pager.ViewPagerAdapter
import com.galuhsaputri.githubusers.ui.settings.SettingsActivity
import com.galuhsaputri.core.domain.model.UserDetail
import com.galuhsaputri.core.domain.model.UserFavorite
import com.galuhsaputri.core.utils.DataMapper
import com.galuhsaputri.core.utils.state.LoaderState
import com.galuhsaputri.core.utils.viewUtils.load
import com.galuhsaputri.core.utils.viewUtils.setGone
import com.galuhsaputri.core.utils.viewUtils.setVisible
import com.galuhsaputri.core.utils.viewUtils.toast
import com.galuhsaputri.githubusers.databinding.ActivityUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint
//berisikan fungsi didalam paket bundle. dan nantinya fungsi tersebut akan digunakan sesuai kegunaannya.
@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private val binding : ActivityUserDetailBinding by lazy{
        ActivityUserDetailBinding.inflate(layoutInflater)
    }
//membuat binding View binding adalah fitur yang memudahkan Anda menulis
// kode yang berinteraksi dengan tampilan. Setelah diaktifkan dalam sebuah modul, view binding akan
// menghasilkan class binding untuk setiap file tata letak XML yang ada dalam modul tersebut.

    private val userDetailViewModel: UserDetailViewModel by viewModels()
    //Alternatif untuk ViewModel adalah class biasa yang menyimpan data yang ditampilkan di UI.
    // Hal ini dapat menjadi masalah saat bernavigasi di antara aktivitas atau tujuan Navigation.
    // Tindakan ini akan menghapus data tersebut jika Anda tidak menyimpannya menggunakan mekanisme
    // status instance penyimpanan. ViewModel menyediakan API yang nyaman untuk persistensi data yang
    // menyelesaikan masalah ini.

    private var userDetail: UserDetail? = null

    private var userFavorite: UserFavorite? = null

    private var favoriteActive = false

    private var username: String? = null
//kode diatas merupakan deklarasi variable.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleIntent()
        initObserver()
        fetchData()
        initToolbar()
        initPageAdapter()

    }
//Dipanggil saat aktivitas dimulai. Di sinilah sebagian besar inisialisasi harus pergi:
// memanggiluntuk mengembang UI aktivitas, menggunakanuntuk berinteraksi secara terprogram
// dengan widget di UI,
// callinguntuk mengambil kursor untuk data yang ditampilkan,
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }
//
    fun getUsername(): String? {
        return username
    }
//merupakan fungsi untuk mengambil username.
    private fun initToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            elevation = 0f
            title = "$username\'s Profile"
        }

        binding.favButton.setOnClickListener {
            setFavoriteUser()
        }
    }
//Dalam aplikasi Android, Toolbar adalah sejenis ViewGroup
// yang dapat ditempatkan dalam tata letak XML suatu aktivitas.
// Itu diperkenalkan oleh tim Google Android selama rilis
// Android Lollipop (API 21). Toolbar pada dasarnya adalah penerus lanjutan dari ActionBar.
    private fun initPageAdapter() {
        val sectionPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }
//Fungsi init saat ingin menjalankan suatu fungsi secara automatis saat memanggil kelas,
// kita bisa menggunakan fungsi init di bahasa program kotlin
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.menu_favorite -> startActivity(
                Intent(
                    this,
                    Class.forName("com.galuhsaputri.favorite.ui.FavoriteUserActivity")
                )
            )
            R.id.menu_language -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun fetchData() {
        username?.let {
            userDetailViewModel.getUserDetailFromApi(it)
            userDetailViewModel.getFavUserByUsername(it)
        }
    }

    private fun handleIntent() {
        username = intent.getStringExtra(USERNAME_KEY) as String
    }

    private fun initObserver() {
        with(userDetailViewModel) {
            state.observe(this@UserDetailActivity) {
                handleStateLoading(it)
            }
            resultUserDetail.observe(this@UserDetailActivity) {
                handleResultUserDetail(it)
            }
            username?.let {
                getFavUserByUsername(it).observe(this@UserDetailActivity) {
                    handleUserDetailFromDb(it)
                }
            }
            resultInsertUserDb.observe(this@UserDetailActivity) { it ->
                if (it) {
                    username?.let {
                        userDetailViewModel.getFavUserByUsername(it)
                    }
                    toast(getString(R.string.user_success))
                }
            }
            resultDeleteFromDb.observe(this@UserDetailActivity) { it ->
                if (it) {
                    username?.let {
                        userDetailViewModel.getFavUserByUsername(it)
                    }
                    toast(getString(R.string.user_deleted))
                }
            }
        }

    }

    private fun setFavoriteUser() {
        if (favoriteActive) {
            userFavorite?.let {
                userDetailViewModel.deleteUserFromDb(it)
            }
        } else {
            val userFavorite = userDetail?.let { DataMapper.mapUserDetailToUserFavorite(it) }
            userFavorite?.let {
                userDetailViewModel.addUserToFavDB(it)
            }
        }
    }

    private fun handleUserDetailFromDb(userFavorite: List<UserFavorite>) {
        if (userFavorite.isEmpty()) {
            favoriteActive = false
            val icon = R.drawable.ic_baseline_favorite_border_24
            binding.favButton.setImageResource(icon)
        } else {
            this.userFavorite = userFavorite.first()
            favoriteActive = true
            val icon = R.drawable.ic_baseline_favorite_24
            binding.favButton.setImageResource(icon)
        }
    }

    private fun handleStateLoading(loading: LoaderState) {
        if (loading is LoaderState.ShowLoading) {
            binding.favButton.setGone()
        } else {
            binding.favButton.setVisible()
        }
    }

    private fun handleResultUserDetail(data: UserDetail) {
        userDetail = data
        binding.apply {
            txtUsername.text = data.name
            txtBio.text = data.bio ?: getString(R.string.no_bio)
            txtFollower.text = data.followers.toString()
            txtFollowing.text = data.following.toString()
            txtRepo.text = data.publicRepos.toString()
            ivUser.load(data.avatarUrl)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    companion object {
        const val USERNAME_KEY = "username_key"
    }
}