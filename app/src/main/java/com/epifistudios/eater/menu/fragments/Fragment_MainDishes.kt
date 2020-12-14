package com.epifistudios.eater.Menu.fragments

import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.epifistudios.eater.Menu.MenuListAdapter
import com.epifistudios.eater.Menu.dessertMenuModel
import com.epifistudios.eater.Menu.drinkMenuModel
import com.epifistudios.eater.Menu.menuModel
import com.epifistudios.eater.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_drinks.*
import kotlinx.android.synthetic.main.fragment_main_dishes.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER




class Fragment_MainDishes : Fragment() , (menuModel) -> Unit{
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var Rv:RecyclerView
    lateinit var Btn: ImageView
    lateinit var SwipeRefreshLayout:SwipeRefreshLayout
    private var menuList:List<menuModel> = ArrayList()
    private val menuListAdapter = MenuListAdapter(menuList,this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_main_dishes, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Rv = view.findViewById(R.id.RvMainDishesMenu)
       // Btn = view.findViewById(R.id.Iv_expandMd)
        SwipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayoutMainDishes)
        Rv.hasFixedSize()
        Rv.layoutManager = LinearLayoutManager(activity)
        Rv.adapter = menuListAdapter
       /* Btn.setOnClickListener {
            scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN)
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
        progressBar.visibility = View.VISIBLE

        firebaseFireStore.collection("MainDishes").orderBy("menuRef").startAt(searchText).endAt("$searchText\uf8ff").get().addOnCompleteListener{
            if (it.isSuccessful){
                    progressBar.visibility = View.INVISIBLE
                    RvMainDishesMenu.adapter = menuListAdapter
                    menuList = it.result!!.toObjects(menuModel::class.java)
                    menuListAdapter.menuList = menuList
                    menuListAdapter.notifyDataSetChanged()
                if(menuListAdapter.itemCount == 0){
                    Tv1ErrorFMD.visibility = View.VISIBLE
                    Tv2ErrorFMD.visibility = View.VISIBLE
                }
            }else{
                Log.d("Firebase","Error:${it.exception!!.message}")
                progressBar.visibility = View.VISIBLE
                Toast.makeText(activity,"Something went wrong",Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun invoke(p1: menuModel) {

    }
}
