package com.qnet.qnetclient.onboardingscreen_usuario

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.qnet.qnetclient.R
import com.qnet.qnetclient.loginregister_usuario.login_register_screen
import kotlinx.android.synthetic.main.activity_onboarding_screen.*

class onboarding_screen : AppCompatActivity() {

    lateinit var preference: SharedPreferences
    val pref_show_intro = "Intro"
    lateinit var activity : Activity


   private val introSlideAdapter=
       IntroSlideAdapter(
           listOf(
               IntroSlide(
                   "Busca",
                   "Podes buscar cualquier local que este cerca tuyo. Te vamos a mostrar los locales disponibles que coincidan con tu busqueda o esten relacionados y sumate a la cola.",
                   R.drawable.buscar
               ),
               IntroSlide(
                   "Espera",
                   "Espera a que sea tu turno. Cuando sea el momento, te vamos a avisar que vayas yendo al local.",
                   R.drawable.esperar
               ),
               IntroSlide(
                   "Anda",
                   "Llega puntual al local porque sino podrias perder tu turno. Mostra tu codigo para que podamos verificar tus datos y ¡disfruta tu compra!",
                   R.drawable.moverse
               )
           )
       )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding_screen)
        activity = this
        preference = getSharedPreferences("IntroSlider" , Context.MODE_PRIVATE)

        if(!preference.getBoolean(pref_show_intro,true)){
            Intent(applicationContext, login_register_screen::class.java).also{
                startActivity(it)
                finish()
            }
        }

        introSliderViewPager.adapter=introSlideAdapter
        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        buttonNext.setOnClickListener {
            if(introSliderViewPager.currentItem+1<introSlideAdapter.itemCount){
                introSliderViewPager.currentItem+=1
            }else{
          /*  Intent(applicationContext, login_register_screen::class.java).also{
                startActivity(it)
            }*/
                goToLoginRegisterScreen()
            }
        }
        textSkipIntro.setOnClickListener{
           /* Intent(applicationContext, login_register_screen::class.java).also{
                startActivity(it)
            }*/
            goToLoginRegisterScreen()
        }
    }

    private fun setupIndicators(){
        val indicators= arrayOfNulls<ImageView>(introSlideAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams=
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i]= ImageView(applicationContext)
            indicators[i].apply {
               this?.setImageDrawable(ContextCompat.getDrawable(applicationContext,
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
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext,
                    R.drawable.indicator_active))
            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext,
                    R.drawable.indicator_inactive))
            }
        }
    }

    fun goToLoginRegisterScreen(){
        Intent(applicationContext, login_register_screen::class.java).also{
            val editor = preference.edit()
            editor.putBoolean(pref_show_intro,false)
            editor.apply()
            startActivity(it)
            finish()
        }


    }

}
