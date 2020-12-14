package com.epifistudios.eater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.epifistudios.eater.Search.SearchActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_ramdomize.*

class RamdomizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ramdomize)

        Searchbtn.setOnClickListener {
            val  intent =  Intent(this,
                SearchActivity::class.java)
            startActivity(intent)
        }
        Menubtn.setOnClickListener {
            Toasty.info(this,"Coming soon!",Toast.LENGTH_SHORT,true).show()

        }

        motionLayout.setTransitionListener(object : TransitionAdapter() {

            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                when (currentId) {
                    R.id.SwipeLeft,
                    R.id.SwipeLeft -> {
                        val random = (1000..1999).random()
                        val intent = Intent(this@RamdomizeActivity,DesertActivity::class.java)
                        intent.putExtra("randomNumber",random.toString())
                        startActivity(intent)
                        finish()
                    }
                    R.id.SwipeRight,
                        R.id.SwipeRight ->{
                            val random = (0..1000).random()
                            val intent = Intent(this@RamdomizeActivity,LunchActivity::class.java)
                            intent.putExtra("randomNumber",random.toString())
                            startActivity(intent)
                            finish()
                    }
                }
            }

        })

    }
}
