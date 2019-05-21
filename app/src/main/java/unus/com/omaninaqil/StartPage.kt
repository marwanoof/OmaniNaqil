package unus.com.omaninaqil

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager

class StartPage : AppCompatActivity() {

    lateinit var viewpager:ViewPager
    lateinit var sliderAdapter: SliderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)
        viewpager = findViewById(R.id.viewpager)
        sliderAdapter = SliderAdapter(this)
        viewpager.adapter = sliderAdapter
    }
}
