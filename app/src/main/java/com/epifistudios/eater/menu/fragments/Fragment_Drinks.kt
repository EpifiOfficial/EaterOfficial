package com.epifistudios.eater.Menu.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.epifistudios.eater.Menu.MenuListAdapter
import com.epifistudios.eater.Menu.dessertMenuModel
import com.epifistudios.eater.Menu.drinkMenuModel
import com.epifistudios.eater.Menu.menuModel
import com.epifistudios.eater.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_desserts.*
import kotlinx.android.synthetic.main.fragment_drinks.*
import kotlinx.android.synthetic.main.fragment_main_dishes.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_Drinks.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_Drinks : Fragment() , (drinkMenuModel) -> Unit{
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var Rv: RecyclerView
    lateinit var Btn: ImageView
    lateinit var SwipeRefreshLayout: SwipeRefreshLayout

    private var drinksMenuList:List<drinkMenuModel> = ArrayList()
    private val drinkMenuListAdapter =
        com.epifistudios.eater.Menu.drinkMenuListAdapter(drinksMenuList,this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_drinks, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Rv = view.findViewById(R.id.RvDrinksMenu)
        //Btn = view.findViewById(R.id.Iv_expandD)
        SwipeRefreshLayout = view.findViewById(R.id.SRLDrinks)

        Rv.hasFixedSize()
        Rv.layoutManager = LinearLayoutManager(activity)
        Rv.adapter = drinkMenuListAdapter
        /*Btn.setOnClickListener {
            scrollViewD.post { scrollViewD.fullScroll(ScrollView.FOCUS_DOWN)
               Toast.makeText(activity,"clik",Toast.LENGTH_LONG).show()
            }
        }*/
        SwipeRefreshLayout.setOnRefreshListener {
            val menuRef = activity?.intent?.getStringExtra("menuRef").toString()
            searchInFirebase(menuRef.toLowerCase())
            SwipeRefreshLayout.isRefreshing = false
        }
        val menuRef = activity?.intent?.getStringExtra("menuRef").toString()
        searchInFirebase(menuRef.toLowerCase())
    }
    private fun searchInFirebase(searchText:String){
        //.orderBy("search_word").startAt(searchText).endAt("$searchText\uf8ff")
        //.whereArrayContains("search_keywords",searchText)
        progressBarD.visibility = View.VISIBLE

        firebaseFireStore.collection("Drinks").orderBy("menuRef").startAt(searchText).endAt("$searchText\uf8ff").get().addOnCompleteListener{
            if (it.isSuccessful){
                progressBarD.visibility = View.INVISIBLE
                RvDrinksMenu.adapter = drinkMenuListAdapter
                drinksMenuList = it.result!!.toObjects(drinkMenuModel::class.java)
                drinkMenuListAdapter.drinksMenuList = drinksMenuList
                drinkMenuListAdapter.notifyDataSetChanged()
                if(drinkMenuListAdapter.itemCount == 0){
                    Tv1ErrorFDrinks.visibility = View.VISIBLE
                    Tv2ErrorFDrinks.visibility = View.VISIBLE
                }
            }else{
                Log.d("Firebase","Error:${it.exception!!.message}")
                progressBarD.visibility = View.VISIBLE
                Toast.makeText(activity,"Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun invoke(p1: drinkMenuModel) {

    }
}