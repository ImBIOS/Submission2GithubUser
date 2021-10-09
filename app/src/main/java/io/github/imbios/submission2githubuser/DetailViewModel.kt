package io.github.imbios.submission2githubuser

import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    var dataUser: UserData = UserData()
    var listUser: ArrayList<UserData> = ArrayList()
    var followersFilterList = ArrayList<UserData>()
    var followingFilterList = ArrayList<UserData>()
}