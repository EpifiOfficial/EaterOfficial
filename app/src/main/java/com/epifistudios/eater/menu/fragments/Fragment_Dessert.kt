package com.epifistudios.eater.Menu.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.epifistudios.eater.Menu.MenuListAdapter
import com.epifistudios.eater.Menu.dessertMenuModel
import com.epifistudios.eater.Menu.menuModel
import com.epifistudios.eater.R
import com.epifistudios.eater.RecetaActivity
import com.epifistudios.eater.Search.SearchActivity
import com.epifistudios.eater.Search.SearchModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_desserts.*
import kotlinx.android.synthetic.main.fragment_main_dishes.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_Dessert.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_Dessert : Fragment() , (dessertMenuModel) -> Unit {
    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var Rv: RecyclerView
   // lateinit var Btn: ImageView
    lateinit var SwipeRefreshLayout: SwipeRefreshLayout


    private var dessertMenuList:List<dessertMenuModel> = ArrayList()
    private val dessertMenuListAdapter = com.epifistudios.eater.Menu.dessertMenuListAdapter(dessertMenuList,this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_desserts, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Rv = view.findViewById(R.id.RvDessertsMenu)
       // Btn = view.findViewById(R.id.Iv_expand)
        SwipeRefreshLayout = view.findViewById(R.id.SRLDessert)

        Rv.hasFixedSize()
        Rv.layoutManager = LinearLayoutManager(activity)
        Rv.adapter = dessertMenuListAdapter
        SwipeRefreshLayout.setOnRefreshListener {
            val menuRef = activity?.intent?.getStringExtra("menuRef").toString()
            searchInFirebase(menuRef.toLowerCase())
            SwipeRefreshLayout.isRefreshing = false
        }
       /* Btn.setOnClickListener {
            scrollViewDesserts.post { scrollViewDesserts.fullScroll(ScrollView.FOCUS_DOWN)
            }
        }*/
        val menuRef = activity?.intent?.getStringExtra("menuRef").toString()
        searchInFirebase(menuRef.toLowerCase())
    }
    private fun searchInFirebase(searchText:String){
        //.orderBy("search_word").startAt(searchText).endAt("$searchText\uf8ff")
        //.whereArrayContains("search_keywords",searchText)
        progressBarDesserts.visibility = View.VISIBLE

        firebaseFireStore.collection("Desserts").orderBy("menuRef").startAt(searchText).endAt("$searchText\uf8ff").get().addOnCompleteListener{
            if (it.isSuccessful){
                progressBarDesserts.visibility = View.INVISIBLE
                RvDessertsMenu.adapter = dessertMenuListAdapter
                dessertMenuList = it.result!!.toObjects(dessertMenuModel::class.java)
                dessertMenuListAdapter.dessertMenuList = dessertMenuList
                dessertMenuListAdapter.notifyDataSetChanged()
                if(dessertMenuListAdapter.itemCount == 0){
                    Tv1ErrorFD.visibility = View.VISIBLE
                    Tv2ErrorFD.visibility = View.VISIBLE
                }
            }else{
                Log.d("Firebase","Error:${it.exception!!.message}")
                progressBarDesserts.visibility = View.VISIBLE
                Toast.makeText(activity,"Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun invoke(p1: dessertMenuModel) {

    }

}