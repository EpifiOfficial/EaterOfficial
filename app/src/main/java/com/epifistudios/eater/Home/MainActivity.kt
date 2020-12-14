package com.epifistudios.eater.Home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.epifistudios.eater.R
import com.epifistudios.eater.RamdomizeActivity
import com.epifistudios.eater.RecetaActivity
import com.epifistudios.eater.Search.SearchActivity
import com.epifistudios.eater.Search.SearchListAdapter
import com.epifistudios.eater.Search.SearchModel
import com.epifistudios.eater.menu.MenuActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*

class MainActivity : AppCompatActivity(), (SearchModel) -> Unit {
    lateinit var currentScannedRestaurantMenu : String
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var searchList:List<SearchModel> = ArrayList()
    private val searchListAdapter =
        SearchListAdapter(searchList, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Button normal state
        BtnScanner.setButtonColor(getResources().getColor(R.color.colorFabQr))
        //Button pressed state
        BtnScanner.setButtonColorPressed(getResources().getColor(R.color.colorFabQrPressed));
        BtnScanner.setImageResource(R.drawable.ic_iconqr)
        BtnScanner.imageSize = 33.0F

        BtnScanner.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()
        }

        //Buttons Set up
        searchBtn.setOnClickListener(View.OnClickListener {
            val  intent =  Intent(this,
                SearchActivity::class.java)
            startActivity(intent)
        })
        BtnEater.setOnClickListener {
            val intent = Intent(this, RamdomizeActivity::class.java)
             startActivity(intent)

        }
        menuBtn.setOnClickListener {
            //Toast.makeText(this,"Coming soon",Toast.LENGTH_SHORT).show()
            Toasty.info(this, "Coming soon", Toast.LENGTH_SHORT, true).show()
        }



        //Recycler View Set Up
        RVHome.hasFixedSize()
        RVHome.layoutManager = LinearLayoutManager(this)
        RVHome.adapter = searchListAdapter
        val random = (0..50).random()

        searchInFirebase("0")


    }
    private fun searchInFirebase(searchText:String){
        progressBarMainActivity.visibility = View.VISIBLE

        //.orderBy("search_word").startAt(searchText).endAt("$searchText\uf8ff")
        firebaseFireStore.collection("recipes").orderBy("ranking").startAt(searchText).endAt("$searchText\uf8ff").get().addOnCompleteListener{
            if (it.isSuccessful){
                searchList = it.result!!.toObjects(SearchModel::class.java)
                searchListAdapter.searchList = searchList
                searchListAdapter.notifyDataSetChanged()
                progressBarMainActivity.visibility = View.INVISIBLE
                Tv1ErrorMainActivity.visibility = View.INVISIBLE
                Tv2ErrorMainActivity.visibility = View.INVISIBLE
                if(searchListAdapter.itemCount == 0){
                    Tv1ErrorMainActivity.visibility = View.VISIBLE
                    Tv2ErrorMainActivity.visibility = View.VISIBLE
                }
            }else{
                Log.d("Firebase","Error:${it.exception!!.message}")
                Toast.makeText(this,"Error:${it.exception!!.message}",Toast.LENGTH_LONG).show()
                Tv1ErrorMainActivity.visibility = View.VISIBLE
                Tv2ErrorMainActivity.visibility = View.VISIBLE
            }
        }
    }

    override fun invoke(p1: SearchModel) {
        val intent = Intent(this, RecetaActivity::class.java)
        intent.putExtra(SearchActivity.INTENT_PARCELABLE, p1)
        startActivity(intent)
    }

    //Scan code action
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    //currentScannedRestaurantMenu = result.contents.toString()
                    val intent = Intent(this,
                        MenuActivity::class.java)
                    intent.putExtra("menuRef",result.contents.toString())
                    startActivity(intent)
                    //Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}







