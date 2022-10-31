package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val login: Button = findViewById(R.id.Button_login)
        val idInput: EditText = findViewById(R.id.EditText_id)
        val pwInput: EditText = findViewById(R.id.editText_pw)
        val loginWarning: TextView = findViewById(R.id.TextView_loginWarning)
        var idChcek = false
        var pwCheck = false

        //아이디와 비번을 통해 로그인
        login.setOnClickListener{
            val idInputText = idInput.text.toString()
            val pwInputText = pwInput.text.toString()
            val shared = getSharedPreferences(idInputText, 0)
            val idCorrect = shared.getString("id", "notExist")
            val pwCorrect = shared.getString("pw", "notCorrect")
            idChcek = idCorrect != "notExist"
            pwCheck = pwInputText==pwCorrect
            //아이디가 존재하며 그에 적합한 비밀번호일 경우, 로그인
            if(idChcek&&pwCheck){
                val intent = Intent(this, Goods::class.java)
                intent.putExtra("loginUser", true)
                intent.putExtra("userId", idInputText)
                startActivity(intent)
                finish()
            }else{
                //그렇지 않을 경우, 경고문 보이기
                loginWarning.visibility=View.VISIBLE
            }
        }

        //회원가입 페이지로 이동
        val toSignUp: TextView = findViewById(R.id.TextView_toSignUp)
        toSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }

        //로그인 없이 상품페이지로 이동
        val toGoods: TextView = findViewById(R.id.TextView_toGoods)
        toGoods.setOnClickListener {
            val intent = Intent(this, Goods::class.java)
            intent.putExtra("loginUser", false)
            startActivity(intent)
            finish()
        }
    }
}