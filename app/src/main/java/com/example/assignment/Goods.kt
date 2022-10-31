package com.example.assignment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Goods : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goods)

        //리사이클러뷰 상품 목록
        val goodsList = arrayListOf(
            GoodsForm(ContextCompat.getDrawable(this, R.drawable.watermelon)!!, "널 사랑할 수박 에"),
            GoodsForm(ContextCompat.getDrawable(this, R.drawable.plum)!!, "자두 자두 졸려"),
            GoodsForm(ContextCompat.getDrawable(this, R.drawable.mango)!!, "요고는 얼 망고"),
            GoodsForm(ContextCompat.getDrawable(this, R.drawable.grape)!!, "나를 보고시 포도 참어"),
            GoodsForm(ContextCompat.getDrawable(this, R.drawable.melon)!!, "내 상태가 메론"),
            GoodsForm(ContextCompat.getDrawable(this, R.drawable.cherry)!!, "정신들 체리세요"),

        )

        //그리드 형태 리사이클러뷰 생성
        val rv : androidx.recyclerview.widget.RecyclerView= findViewById(R.id.goodsRecycler)
        rv.layoutManager = GridLayoutManager(this,2)
        rv.setHasFixedSize(true)
        rv.adapter = GoodsAdapter(goodsList,
            onClickImage = {
                goodsList.remove(it)
                rv.adapter?.notifyDataSetChanged()
            })

        //헤더의 뒤로가기 버튼
        val back: ImageView = findViewById(R.id.ImageView_back)
        back.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        //회원정보 버튼(인텐트에 담겨온 정보를 통해 로그인 유무를 판단, 다른 내용의 dialog 띄움)
        val loginUser = intent.getBooleanExtra("loginUser",false)
        val userId = intent.getStringExtra("userId")
        val userInfo :ImageButton = findViewById(R.id.ImageButton_userInfo)
        userInfo.setOnClickListener{
            //로그인 유저
            if(loginUser){
                val shared = getSharedPreferences(userId.toString(),0)
                val id = shared.getString("id","").toString()
                val pw = shared.getString("pw","")
                val name = shared.getString("name","")
                val phone = shared.getString("phone","")
                val address = shared.getString("address","")
                //회원정보 dialog
                val builder = AlertDialog.Builder(this)
                builder.setTitle("회원정보")
                    .setMessage("아이디 : $id\n비밀번호 : $pw\n이름 : $name\n전화번호 : $phone\n주소 : $address")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->  })
                builder.show()
            }//비로그인 유저
            else{
                // 가입유도 dialog
                val builder = AlertDialog.Builder(this)
                builder.setTitle("아직 회원이 아니신가요?")
                    .setMessage("서비스 이용을 위해선 회원가입이 필요합니다.")
                    .setPositiveButton("회원가입하기", DialogInterface.OnClickListener { dialogInterface, i ->
                        val intent = Intent(this, SignUp::class.java)
                        startActivity(intent)
                        finish()
                    })
                builder.show()
            }
        }

        //입력된 이미지명에 맞는 사진과 상품명으로 리사이클러뷰 아이템 추가
        val imageMap = mapOf("cherry" to 0, "grape" to 1,"mango" to 2,"plum" to 3,"melon" to 4,"watermelon" to 5)
        val imageList: List<Int> = listOf(
            R.drawable.cherry, R.drawable.grape, R.drawable.mango, R.drawable.plum,
            R.drawable.melon, R.drawable.watermelon
        )
        val imageName : TextView= findViewById(R.id.EditText_imageName)
        val title : TextView= findViewById(R.id.EditText_title)
        val addWarning:TextView = findViewById(R.id.TextView_addWarning)
        val add : Button = findViewById(R.id.Button_add)
        add.setOnClickListener {
            //주어진 이미지명 중 선택 & 상품명 입력 시
            if(imageMap.containsKey(imageName.text.toString()) && title.text.isEmpty()==false){
                val imageNum = imageMap[imageName.text.toString()]
                goodsList.add(GoodsForm(ContextCompat.getDrawable(this,imageList[imageNum!!])!!, title.text.toString()))
                rv.adapter?.notifyDataSetChanged()
                addWarning.visibility=View.GONE
                imageName.text=""
                title.text=""
            }else{
                //그렇지 않을 경우, 경고문 보이기
                addWarning.visibility=View.VISIBLE
            }

        }
    }
}