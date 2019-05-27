package com.example.myonfrashcard

import android.app.Application
import io.realm.Realm

/**
 * TODO クラス説明
 *
 * Created by tomohiroinagaki on 2019-05-27.
 */
class MyAoocation: Application()
{
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)


    }
}