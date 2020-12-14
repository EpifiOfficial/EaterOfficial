package com.epifistudios.eater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.epifistudios.eater.Search.SearchActivity
import com.epifistudios.eater.Search.SearchListAdapter
import com.epifistudios.eater.Search.SearchModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_lunch.*
import kotlinx.android.synthetic.main.activity_search.*

class LunchActivity : AppCompatActivity(), (SearchModel) -> Unit {
    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var searchList:List<SearchModel> = ArrayList()
    private val searchListAdapter =
        SearchListAdapter(searchList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch)

        val intentData = intent.getStringExtra("randomNumber")
        Toast.makeText(this,intentData,Toast.LENGTH_LONG).show()
        //searchInFirebase(intentData.toLowerCase())
        searchInFirebase("1")
        RvRandomLunch.hasFixedSize()
        RvRandomLunch.layoutManager = LinearLayoutManager(this)
        RvRandomLunch.adapter = searchListAdapter

        BackBtn.setOnClickListener {
            finish()
        }
    }


    private fun searchInFirebase(searchText:String){
        //.orderBy("search_word").startAt(searchText).endAt("$searchText\uf8ff")
        firebaseFireStore.collection("recipes").whereEqualTo("numero_Receta",searchText).get().addOnCompleteListener{
            if (it.isSuccessful){
                searchList = it.result!!.toObjects(SearchModel::class.java)
                searchListAdapter.searchList = searchList
                searchListAdapter.notifyDataSetChanged()

            }else{
                Log.d("Firebase","Error:${it.exception!!.message}")
            }
        }
    }

    override fun invoke(p1: SearchModel) {
        val intent = Intent(this, RecetaActivity::class.java)
        intent.putExtra(SearchActivity.INTENT_PARCELABLE, p1)
        startActivity(intent)
    }
}
