package com.epifistudios.eater.Steps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.epifistudios.eater.Ingredients.IngredientsListAdapter
import com.epifistudios.eater.Ingredients.IngredientsModel
import com.epifistudios.eater.R
import com.epifistudios.eater.Search.SearchActivity
import com.epifistudios.eater.Search.SearchModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_ingredients.*
import kotlinx.android.synthetic.main.activity_ingredients.BackBtn
import kotlinx.android.synthetic.main.activity_ingredients.TvNameRecipe
import kotlinx.android.synthetic.main.activity_steps.*

class StepsActivity : AppCompatActivity(), (StepsModel) -> Unit {
    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var stepsList:List<StepsModel> = ArrayList()
    private val stepsListAdapter = StepsListAdapter(stepsList, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_steps)


        val datos = intent.getParcelableExtra<SearchModel>(
            SearchActivity.INTENT_PARCELABLE
        )
        val searchText: String = datos.numero_Receta
        searchInFirebase(searchText.toLowerCase())


        TvNameRecipe.text = datos.name_recipe
        RvSteps.hasFixedSize()
        RvSteps.layoutManager = LinearLayoutManager(this)
        RvSteps.adapter = stepsListAdapter

        Backbtn.setOnClickListener {
            finish()
        }
    }
    private fun searchInFirebase(searchText:String){
        //.orderBy("search_word").startAt(searchText).endAt("$searchText\uf8ff")
        //.whereArrayContains("search_keywords",searchText)
        firebaseFireStore.collection("steps").orderBy("numero_Receta").startAt(searchText).endAt("$searchText\uf8ff").get().addOnCompleteListener{
            if (it.isSuccessful){
                stepsList = it.result!!.toObjects(StepsModel::class.java)
                stepsListAdapter.stepsList = stepsList
                stepsListAdapter.notifyDataSetChanged()

            }else{
                Log.d("Firebase","Error:${it.exception!!.message}")
            }
        }
    }

    override fun invoke(p1: StepsModel) {
        TODO("Not yet implemented")
    }
}