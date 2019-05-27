package com.example.myonfrashcard

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_word_list.*

class WordListActivity : AppCompatActivity(), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    lateinit var realm: Realm
    lateinit var results: RealmResults<WordDB>
    lateinit var word_list : ArrayList<String>

    lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        // 画面開いたとき

        // 単語タップ

        // 単語長押し

        // 前の背景色の引き継ぎ
        constraintLayoutEdit.setBackgroundResource(intBackGroundColor)


        //新しい単語の追加ボタン
        buttonAddNewWord.setOnClickListener {

            val intent = Intent(this@WordListActivity, EditActivity::class.java)
            intent.putExtra(getString(R.string.intent_key_status), getString(R.string.status_add))
            startActivity(intent)
        }
        // 戻るボタン押したとき

        buttonBack.setOnClickListener {
            finish()
        }
        //
        listView.setOnItemClickListener(this)
        listView.setOnItemLongClickListener(this)


    }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
        // データ取得
        results =
            realm.where(WordDB::class.java).findAll().sort(getString(R.string.db_field_question))
        val length = results.size
        word_list = ArrayList<String>()
        // for 文でリストの表示形式をカエル
//        for (i in 0..length-1 ) {
//            word_list.add(results[i].strAnswer + ":" + results[i].strQuestion)
//
//        }
        results.forEach { word_list.add(it.strAnswer + ":" + it.strQuestion) }


        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, word_list)
        // listviewの表示
        listView.adapter = adapter


    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //DBから取得
        val selectedDB = results[position]
        val seledtecQuestion = selectedDB.strQuestion
        val selectedAnswer = selectedDB.strAnswer
        val intent = Intent(this@WordListActivity, EditActivity::class.java).apply {
            putExtra(getString(R.string.intent_key_question), seledtecQuestion)
            putExtra(getString(R.string.intent_key_answer), selectedAnswer)
            putExtra(getString(R.string.intent_key_position), position)
            putExtra(getString(R.string.intent_key_status), getString(R.string.status_change))


        }

//        intent.putExtra(getString(R.string.intent_key_question), seledtecQuestion)
//        intent.putExtra(getString(R.string.intent_key_answer), selectedAnswer)
//        intent.putExtra(getString(R.string.intent_key_position), position)
//        intent.putExtra(getString(R.string.intent_key_status), getString(R.string.status_change))


        startActivity(intent)

    }

    // 長押し 削除
    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        val selectedDB = results[position]
        val seledtecQuestion = selectedDB.strQuestion
        val selectedAnswer = selectedDB.strAnswer
                realm.beginTransaction()
        selectedDB.deleteFromRealm()
        realm.commitTransaction()
        // 画面の削除
        word_list.removeAt(position)
        // アダプターの方針
        listView.adapter = adapter
        return true

    }


}
