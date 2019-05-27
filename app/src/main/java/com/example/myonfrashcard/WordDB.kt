package com.example.myonfrashcard

import io.realm.Realm
import io.realm.RealmObject

/**
 * モデルクラスの作成
 *
 * Created by tomohiroinagaki on 2019-05-27.
 */

open class WordDB : RealmObject() {
    //問題
    var strQuestion: String = ""
    // 答え
    var strAnswer: String = ""

}