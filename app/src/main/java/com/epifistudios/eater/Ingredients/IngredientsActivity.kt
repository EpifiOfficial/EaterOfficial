package com.epifistudios.eater.Ingredients

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.epifistudios.eater.*
import com.epifistudios.eater.Search.SearchActivity
import com.epifistudios.eater.Search.SearchModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_ingredients.*

class IngredientsActivity : AppCompatActivity(), (IngredientsModel) -> Unit {
    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var ingredientsList:List<IngredientsModel> = ArrayList()
    private val ingredientsListAdapter =
        IngredientsListAdapter(
            ingredientsList,
            this
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)
        val datos = intent.getParcelableExtra<SearchModel>(
            SearchActivity.INTENT_PARCELABLE
        )

        val searchText: String = datos.numero_Receta
        searchInFirebase(searchText.toLowerCase())

        TvNameRecipe.text = datos.name_recipe
        RvIngredients.hasFixedSize()
        RvIngredients.layoutManager = LinearLayoutManager(this)
        RvIngredients.adapter = ingredientsListAdapter


        BackBtn.setOnClickListener {
            finish()
        }
    }



    private fun searchInFirebase(searchText:String){
        //.orderBy("search_word").startAt(searchText).endAt("$searchText\uf8ff")
        //.whereArrayContains("search_keywords",searchText)
        firebaseFireStore.collection("ingredients").orderBy("numero_Receta").startAt(searchText).endAt("$searchText\uf8ff").get().addOnCompleteListener{
            if (it.isSuccessful){
                ingredientsList = it.result!!.toObjects(IngredientsModel::class.java)
                ingredientsListAdapter.ingredientList = ingredientsList
                ingredientsListAdapter.notifyDataSetChanged()

            }else{
                Log.d("Firebase","Error:${it.exception!!.message}")
            }
        }
    }

    override fun invoke(p1: IngredientsModel) {
        TODO("Not yet implemented")
    }
}