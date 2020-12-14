package com.epifistudios.eater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.widget.*

import androidx.constraintlayout.solver.widgets.ConstraintWidget.GONE
import androidx.constraintlayout.solver.widgets.ConstraintWidget.INVISIBLE
import androidx.constraintlayout.widget.ConstraintSet.GONE

import com.epifistudios.eater.Ingredients.IngredientsActivity
import com.epifistudios.eater.Search.SearchActivity
import com.epifistudios.eater.Search.SearchModel
import com.epifistudios.eater.Steps.StepsActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.google.firebase.firestore.core.View
import com.squareup.picasso.Picasso
import io.grpc.InternalChannelz.id

import kotlinx.android.synthetic.main.activity_receta.*
import kotlinx.android.synthetic.main.native_ad_layout.*
import java.util.*

const val ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110"
var currentNativeAd: UnifiedNativeAd? = null
class RecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receta)

        //Native ad
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this) {}
        nativeAd()


        val datos = intent.getParcelableExtra<SearchModel>(SearchActivity.INTENT_PARCELABLE)
        val imgSrc = findViewById<ImageView>(R.id.IvReceta)
        val recetaName = findViewById<TextView>(R.id.TvRecetaName)
        val tiempoPreparacion = findViewById<TextView>(R.id.TvTiempoPreparacion)
        val equipment1 = findViewById<TextView>(R.id.TvEquipment1)
        val equipment2 = findViewById<TextView>(R.id.TvEquipment2)
        val RestaurantsAvailable = findViewById<TextView>(R.id.TvNumberRestaurantsAvailable)
        BackBtn.setOnClickListener {
            finish()
        }
        Picasso.get()
            .load(datos.imageUrl)
            .fit()
            .into(imgSrc)
        recetaName.text = datos.name_recipe
        tiempoPreparacion.text = "Ready in "+ datos.tiempo_Preparacion+" min"
        equipment1.text = datos.equipments_1
        equipment2.text = datos.equipments_2
        RestaurantsAvailable.text = datos.numeroRestaurantesDisponibles.toString()+" restaurants available"

        btnIngredients.setOnClickListener {
            val intent = Intent(this,
                IngredientsActivity::class.java)
            intent.putExtra(SearchActivity.INTENT_PARCELABLE,datos)
            startActivity(intent)

        }
        btnSteps.setOnClickListener {
            val intent = Intent(this,
                StepsActivity::class.java)
            intent.putExtra(SearchActivity.INTENT_PARCELABLE,datos)
            startActivity(intent)
        }


    }
    private fun populateUnifiedNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
        // You must call destroy on old ads when you are done with them,
        // otherwise you will have a memory leak.
        currentNativeAd?.destroy()
        currentNativeAd = nativeAd
        // Set the media view.
        adView.mediaView = adView.findViewById<MediaView>(R.id.ad_media)

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_rating)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        adView.mediaView.setMediaContent(nativeAd.mediaContent)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView.visibility = android.view.View.GONE
        } else {
            adView.bodyView.visibility =  android.view.View.GONE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility =  android.view.View.GONE
        } else {
            adView.callToActionView.visibility =  android.view.View.GONE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility =  android.view.View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon.drawable)
            adView.iconView.visibility =  android.view.View.GONE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility =  android.view.View.GONE
        } else {
            adView.priceView.visibility =  android.view.View.GONE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = android.view.View.GONE
        } else {
            adView.storeView.visibility =  android.view.View.GONE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility =  android.view.View.GONE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView.visibility =  android.view.View.GONE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility =  android.view.View.GONE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility =  android.view.View.GONE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd)

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
       /* val vc = nativeAd.videoController

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            videostatus_text.text = String.format(
                Locale.getDefault(),
                "Video status: Ad contains a %.2f:1 video asset.",
                vc.aspectRatio)

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
                override fun onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    videostatus_text.text = "Video status: Video playback has ended."
                    super.onVideoEnd()
                }
            }
        } else {
            videostatus_text.text = "Video status: Ad does not contain a video asset."
        }*/
    }
    private fun nativeAd(){
        val builder = AdLoader.Builder(this, ADMOB_AD_UNIT_ID)

        builder.forUnifiedNativeAd { unifiedNativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            val adView = layoutInflater
                .inflate(R.layout.native_ad_layout, null) as UnifiedNativeAdView
            populateUnifiedNativeAdView(unifiedNativeAd, adView)
            ad_layout.removeAllViews()
            ad_layout.addView(adView)
        }

       /* val videoOptions = VideoOptions.Builder()
            .build()

        val adOptions = NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .build()*/

        //xbuilder.withNativeAdOptions(adOptions)

        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
                Toast.makeText(this@RecetaActivity, "Failed to load native ad: " + errorCode, Toast.LENGTH_SHORT).show()
            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())

    }
    override fun onDestroy() {
        currentNativeAd?.destroy()
        super.onDestroy()
    }

}