package com.example.assignment

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import java.util.regex.Pattern

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        var pwChecked = false
        var idChecked = false

        val id :EditText = findViewById(R.id.EditView_id)
        val pw :EditText = findViewById(R.id.EditView_pw)
        val pwWarning :TextView = findViewById(R.id.pwWarning)
        val name :EditText = findViewById(R.id.EditView_name)
        val phone :EditText = findViewById(R.id.EditView_phone)
        val address :EditText = findViewById(R.id.EditView_address)
        val personalInfo : RadioGroup = findViewById(R.id.RadioGroup_personalInfo)
        val accept :RadioButton = findViewById(R.id.radio_accept)
        val decline :RadioButton = findViewById(R.id.radio_decline)

        //헤더의 뒤로가기 버튼
        val back :ImageView = findViewById(R.id.ImageView_back)
        back.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        //아이디 입력 변경 시, 경고문 출력 & 회원가입 버튼 활성화 설정
        val idWarning :TextView = findViewById(R.id.TextView_idWarning)
        id.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                idChecked = false
                if(id.text.isEmpty()){
                    idWarning.visibility=View.GONE
                }
                signUpHandler(idChecked, pwChecked, name.text.toString(), phone.text.toString(),
                    address.text.toString(),accept)
            }
        })

        //아이디 중복 확인 버튼 & 회원가입 활성화 설정
        val idCertification :Button = findViewById(R.id.Button_idCertification)
        idCertification.setOnClickListener(){
            val shared = getSharedPreferences(id.text.toString(),0)
            val isOverlap = shared.getString("id", "isNotOverlap")
            //중복 X, 사용가능 문구 보이기
            if(isOverlap=="isNotOverlap"){
                if(id.text.isEmpty()){
                    idWarning.visibility = View.GONE
                }else {
                    idChecked = true
                    idWarning.text = "사용가능한 아이디입니다."
                    idWarning.visibility = View.VISIBLE
                    signUpHandler(
                        idChecked, pwChecked, name.text.toString(), phone.text.toString(),
                        address.text.toString(), accept)
                }
            }else{
                //중복 O, 경고문 보이기
                idChecked = false
                idWarning.text = "이미 존재하는 아이디입니다."
                idWarning.visibility = View.VISIBLE
                signUpHandler(idChecked, pwChecked, name.text.toString(), phone.text.toString(),
                    address.text.toString(),accept)
            }
        }

        //비밀번호 조건 성립 유무에 따른 경고문 & 회원가입 활성화 설정
        pw.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if(pwCheck(pw.text.toString())){
                    pwChecked = true
                    pwWarning.visibility = View.GONE
                }else{
                    pwChecked = false
                    pwWarning.visibility = View.VISIBLE
                }
                signUpHandler(idChecked, pwChecked, name.text.toString(), phone.text.toString(),
                    address.text.toString(),accept)
            }
        })

        //이름 입력 유무에 따른 & 회원가입 활성화 설정
        name.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                signUpHandler(idChecked, pwChecked, name.text.toString(), phone.text.toString(),
                    address.text.toString(),accept)
            }
        })

        //전화번호 입력 유무에 따른 & 회원가입 활성화 설정
        phone.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                signUpHandler(idChecked, pwChecked, name.text.toString(), phone.text.toString(),
                    address.text.toString(),accept)
            }
        })

        //주소 입력 유무에 따른 & 회원가입 활성화 설정
        address.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                signUpHandler(idChecked, pwChecked, name.text.toString(), phone.text.toString(),
                    address.text.toString(),accept)
            }
        })

        //개인정보동의 입력 변경 시, 회원가입 버튼 활성화 판단
        personalInfo.setOnCheckedChangeListener{group,checkedId->
            signUpHandler(idChecked, pwChecked, name.text.toString(), phone.text.toString(),
                address.text.toString(),accept)
        }

        //회원가입 버튼
        val signUp :Button = findViewById(R.id.Button_signUp)
        signUp.setOnClickListener {
            val id = id.text.toString()
            val pw = pw.text.toString()
            val name = name.text.toString()
            val phone = phone.text.toString()
            val address = address.text.toString()

            val sharedPreference = getSharedPreferences(id, 0)
            val editor = sharedPreference.edit()

            editor.putString("id", id)
            editor.putString("pw", pw)
            editor.putString("name", name)
            editor.putString("phone", phone)
            editor.putString("address", address)
            editor.apply()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    //비밀번호 조건 확인
    private fun pwCheck(pw:String):Boolean{
        val pwPattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,12}$"
        val pwPattern2 = "^(?=.*[0-9])(?=.*[$@$!%*#?.])[[0-9]$@$!%*#?.]{8,12}$"
        val pwPattern3 = "^(?=.*[A-Za-z])(?=.*[$@$!%*#?.])[A-Za-z$@$!%*#?.]{8,12}$"
        val pwPattern4 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?.])[A-Za-z[0-9]$@$!%*#?.]{8,12}$"

        return Pattern.matches(pwPattern1,pw)||Pattern.matches(pwPattern2,pw)||Pattern.matches(pwPattern3,pw)||Pattern.matches(pwPattern4,pw)
    }

    //아이디 중복확인, 비밀번호 조건충족을 포함한 모든 입력 완료 시 회원가입 버튼 활성화
    private fun signUpHandler(idChecked:Boolean, pwChecked:Boolean, name:String, phone:String,
                              address:String,accept:RadioButton){
        val signUp :Button = findViewById(R.id.Button_signUp)
        if(idChecked && pwChecked &&name.isNotBlank()&&phone.isNotBlank()&&address.isNotBlank()
            &&accept.isChecked){
            signUp.setEnabled(true)
        }else{
            signUp.setEnabled(false)
        }
    }
}