package com.example.socialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialapp.daos.PostDao
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        postButton.setOnClickListener{
            val input=postInput.text.toString().trim()
            if(input.isNotEmpty()){
               val postDao=PostDao()
                postDao.addPost(input)
                finish()
            }

        }
    }
}