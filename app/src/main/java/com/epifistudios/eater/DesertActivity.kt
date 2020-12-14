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
import kotlinx.android.synthetic.main.activity_desert.*
import kotlinx.android.synthetic.main.activity_lunch.*

class DesertActivity : AppCompatActivity(), (SearchModel) -> Unit {
    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var searchList:List<SearchModel> = ArrayList()
    private val searchListAdapter =
        SearchListAdapter(searchList, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desert)

        val intentData = intent.getStringExtra("randomNumber")
        Toast.makeText(this,intentData, Toast.LENGTH_LONG).show()
        //searchInFirebase(intentData.toLowerCase())
        //The numbers from 1000 to 1999 are for dessert recipes
        //The numbers from 1 to 999 are for lunch recipes

        searchInFirebase("1000")
        RvRandomDesert.hasFixedSize()
        RvRandomDesert.layoutManager = LinearLayoutManager(this)
        RvRandomDesert.adapter = searchListAdapter
        BackBtnDesertActivity.setOnClickListener {
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