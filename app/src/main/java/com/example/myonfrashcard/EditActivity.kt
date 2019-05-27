package com.example.myonfrashcard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    lateinit var realm: Realm
    var strQestion: String =""
    var strAnswer: String =""
    var intPosition: Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        // インテント作成
        val bundle = intent.extras
        val string_status = bundle.getString(getString(R.string.intent_key_status))
        textViewStatus.text = string_status
        // 背景色設定
        constraintLayoutEdit.setBackgroundResource(intBackGroundColor)

        if (string_status == getString(R.string.status_change)) {
            //画面表示の変更
            //strQuestion = bundle.getString(getString(R.string.intent_key_status))

            strQestion=bundle.getString(getString(R.string.intent_key_question)) //mondai
            strAnswer=bundle.getString(getString(R.string.intent_key_answer)) //

            editTextAnwer.setText(strAnswer)
            editTextQuestion2.setText(strQestion)
            intPosition=bundle.getInt(getString(R.string.intent_key_position))


        }


        // 登録ボタン押したとき
        buttonRegister.setOnClickListener {
            //            新しい単語の追加
            //    既存単語の修正
            if (string_status == getString(R.string.status_add)) {
                addNewWord()
            } else {
                changeWord()
            }

        }
        // 戻るボタン押したとき

        buttonBack2.setOnClickListener { finish() }


    }

    override fun onResume() {
        super.onResume()
        // Realmインスタンス取得
        realm = Realm.getDefaultInstance()


    }

    override fun onPause() {
        super.onPause()
        realm.close()

    }

    private fun addNewWord() {
        //登録
        realm.beginTransaction()
        val wordDB = realm.createObject(WordDB::class.java)
        wordDB.strQuestion = editTextQuestion2.text.toString()
        wordDB.strAnswer = editTextAnwer.text.toString()

        realm.commitTransaction()
        // クリア
        editTextQuestion2.setText("")
        editTextAnwer.setText("")
        //   登録完了
        Toast.makeText(this@EditActivity, "登録が完了しました。", Toast.LENGTH_SHORT).show()


    }

    private fun changeWord() {
        // 抽出
       val result =  realm.where(WordDB::class.java).findAll().sort(getString(R.string.db_field_question))

        val seledtedDB=result.get(intPosition)

        realm.beginTransaction()

        seledtedDB.strQuestion = editTextQuestion2.text.toString()
        seledtedDB.strAnswer = editTextAnwer.text.toString()

        realm.commitTransaction()
        editTextQuestion2.setText("")
        editTextAnwer.setText("")

        Toast.makeText(this@EditActivity, "修正が完了しました。", Toast.LENGTH_SHORT).show()

        finish()


    }
}

