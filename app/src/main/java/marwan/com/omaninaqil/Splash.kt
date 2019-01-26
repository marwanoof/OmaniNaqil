package marwan.com.omaninaqil


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView


class Splash : AppCompatActivity() {

    lateinit var circle1:ImageView
    lateinit var circle2:ImageView
    lateinit var circle3:ImageView
    lateinit var circle4:ImageView
    lateinit var circle5:ImageView
    lateinit var circle6:ImageView
    lateinit var logocircle:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        circle1 = findViewById(R.id.cir1)
        circle2 = findViewById(R.id.cir2)
        circle3 = findViewById(R.id.cir3)
        circle4 = findViewById(R.id.cir4)
        circle5 = findViewById(R.id.cir5)
        circle6 = findViewById(R.id.cir6)
        logocircle = findViewById(R.id.logo)
        Handler().postDelayed({
            var next = Intent(baseContext,LoginPage::class.java)
            startActivity(next)
        }, 6000)
        animateLogo()

    }
    fun animateLogo(){
        var animateCir1Fade:Animation=AnimationUtils.loadAnimation(baseContext,R.anim.fadin)
        circle1.startAnimation(animateCir1Fade)
        var animateCir2Fade:Animation=AnimationUtils.loadAnimation(baseContext,R.anim.fadin)
        circle2.startAnimation(animateCir2Fade)
        var animateCir3Fade:Animation=AnimationUtils.loadAnimation(baseContext,R.anim.fadin)
        circle3.startAnimation(animateCir3Fade)
        var animateCir4Fade:Animation=AnimationUtils.loadAnimation(baseContext,R.anim.fadin)
        circle4.startAnimation(animateCir4Fade)
        var animateCir5Fade:Animation=AnimationUtils.loadAnimation(baseContext,R.anim.fadin)
        circle5.startAnimation(animateCir5Fade)
        var animateCir6Fade:Animation=AnimationUtils.loadAnimation(baseContext,R.anim.fadin)
        circle6.startAnimation(animateCir6Fade)
        var animateLogoFade:Animation=AnimationUtils.loadAnimation(baseContext,R.anim.fadin)
        var animateCir2Rotate:Animation = AnimationUtils.loadAnimation(baseContext,R.anim
            .rotate_clockwise_8)
        var animateCir3Rotate:Animation = AnimationUtils.loadAnimation(baseContext,R.anim.rotate_anicloclwise_6)
        var animateCir4Rotate:Animation = AnimationUtils.loadAnimation(baseContext,R.anim.rotate_clockwise_7)
        var animateCir5Rotate:Animation = AnimationUtils.loadAnimation(baseContext,R.anim.rotate_aniclockwise_5)
        animateLogoFade.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                circle2.startAnimation(animateCir2Rotate)
                circle3.startAnimation(animateCir3Rotate)
                circle4.startAnimation(animateCir4Rotate)
                circle5.startAnimation(animateCir5Rotate)
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })
        logocircle.startAnimation(animateLogoFade)
    }
}
