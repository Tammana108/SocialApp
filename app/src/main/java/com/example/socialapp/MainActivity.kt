package com.example.socialapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialapp.daos.PostDao
import com.example.socialapp.models.Post
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IPostAdapter {
    private lateinit var postDao:PostDao
    private lateinit var adapter:PostAdapter
//    lateinit var mGoogleSignInClient: GoogleSignInClient
//    // val auth is initialized by lazy
//    private val auth by lazy {
//        FirebaseAuth.getInstance()
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }
        setUpRecyclerView()
//        signOutButton.setOnClickListener {
//            Firebase.auth.signOut()
//        }
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build()
//        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
    }

    private fun setUpRecyclerView() {
        postDao= PostDao()
        val postCollections=postDao.postCollection
        val query=postCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions=FirestoreRecyclerOptions.Builder<Post>().setQuery(
                query,
                Post::class.java
        ).build()
        adapter=PostAdapter(recyclerViewOptions, this)
       recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
          postDao.updateLikes(postId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(OnCompleteListener<Void>(){
            val intent1 = Intent(this, SignInActivity::class.java)

            startActivity(intent1)
        })

        return true
    }

}