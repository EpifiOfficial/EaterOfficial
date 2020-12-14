package com.epifistudios.eater.Search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.epifistudios.eater.R
import com.epifistudios.eater.RecetaActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), (SearchModel) -> Unit {

    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var searchList:List<SearchModel> = ArrayList()
    private val searchListAdapter =
        SearchListAdapter(searchList, this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        backBtn.setOnClickListener(View.OnClickListener {
            finish()
        })

        RVSearch.hasFixedSize()
        RVSearch.layoutManager = LinearLayoutManager(this)
        RVSearch.adapter = searchListAdapter
        EtSearch.addTextChangedListener(object: TextWatcher{

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText: String = EtSearch.text.toString()
                searchInFirebase(searchText.toLowerCase())
            }

        })

    }
    private fun searchInFirebase(searchText:String){
        progressBar.visibility = View.VISIBLE

        //.orderBy("search_word").startAt(searchText).endAt("$searchText\uf8ff")
        firebaseFireStore.collection("recipes").whereArrayContains("search_keywords",searchText).get().addOnCompleteListener{
            if (it.isSuccessful){
                    searchList = it.result!!.toObjects(SearchModel::class.java)
                    searchListAdapter.searchList = searchList
                    searchListAdapter.notifyDataSetChanged()
                    progressBar.visibility = View.INVISIBLE
                    Tv1Error.visibility = View.INVISIBLE
                    Tv2Error.visibility = View.INVISIBLE
                    if(searchListAdapter.itemCount == 0){
                        Tv1Error.visibility = View.VISIBLE
                        Tv2Error.visibility = View.VISIBLE
                    }
                }else{
                    Log.d("Firebase","Error:${it.exception!!.message}")
                    Toast.makeText(this,"Error:${it.exception!!.message}",Toast.LENGTH_LONG).show()
                    Tv1Error.visibility = View.VISIBLE
                    Tv2Error.visibility = View.VISIBLE
                }
            }
    }

    override fun invoke(p1: SearchModel) {
        val intent = Intent(this, RecetaActivity::class.java)
        intent.putExtra(INTENT_PARCELABLE, p1)
        startActivity(intent)
    }

}





















