package com.qnet.qnetclient.onboardingscreen_local

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.qnet.qnetclient.R
import com.qnet.qnetclient.loginregister_local.login_register_screen_local
import com.qnet.qnetclient.loginregister_usuario.login_register_screen
import kotlinx.android.synthetic.main.activity_onboarding_screen.*

class onboarding_screen_local : AppCompatActivity() {

    lateinit var preference2: SharedPreferences
    val pref_show_intro2 = "Intro2"
    lateinit var activity : Activity

    private val introSlideAdapterLocal=
        IntroSlideAdapterLocal(
            listOf(
                IntroSlideLocal(
                    "Registrate",
                    "Ingresa los datos necesarios para registrar tu local y poder usar nuestra plataforma.",
                    R.drawable.registrate
                ),
                IntroSlideLocal(
                    "Espera",
                    "Espera que los clientes se sumen a la fila online.",
                    R.drawable.esperar2
                ),
                IntroSlideLocal(
                    "Controla",
                    "Al llegar un cliente, verifica a través del lector de código QR que sea el correcto. Luego, elimina al cliente de la fila así puede venir el siguiente.",
                    R.drawable.controla
                )
            )
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding_screen_local)
        activity = this
        preference2 = getSharedPreferences("IntroSlider2" , Context.MODE_PRIVATE)

        if(!preference2.getBoolean(pref_show_intro2,true)){
            Intent(applicationContext, login_register_screen_local::class.java).also{
                startActivity(it)
                finish()
            }
        }

        introSliderViewPager.adapter=introSlideAdapterLocal
        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        buttonNext.setOnClickListener{
            if(introSliderViewPager.currentItem+1<introSlideAdapterLocal.itemCount){
                introSliderViewPager.currentItem+=1
            }else{
               /* Intent(applicationContext, login_register_screen_local::class.java).also{
                    startActivity(it)
                }*/
                goToLoginRegisterScreen()
            }
        }
        textSkipIntro.setOnClickListener{
           /* Intent(applicationContext, login_register_screen_local::class.java).also{
                startActivity(it)
            }*/
            goToLoginRegisterScreen()
        }
    }

    private fun setupIndicators(){
        val indicators= arrayOfNulls<ImageView>(introSlideAdapterLocal.itemCount)
        val layoutParams: LinearLayout.LayoutParams=
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i]= ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext,
                    R.drawable.indicator_inactive
                )
                )

                this?.layoutParams=layoutParams
            }
            indicatorContainer.addView(indicators[i])
        }
    }
    private fun setCurrentIndicator(index:Int){
        val childCount=indicatorContainer.childCount
        for(i in 0 until childCount){
            val imageView= indicatorContainer[i] as ImageView
            if(i==index){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext,
                    R.drawable.indicator_active))
            }else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext,
                    R.drawable.indicator_inactive))
            }
        }
    }

    fun goToLoginRegisterScreen(){
        Intent(applicationContext, login_register_screen_local::class.java).also{
            val editor2 = preference2.edit()
            editor2.putBoolean(pref_show_intro2,false)
            editor2.apply()
            startActivity(it)
            finish()
        }


    }

}
