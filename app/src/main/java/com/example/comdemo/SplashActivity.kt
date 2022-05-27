package com.example.comdemo

import android.content.Intent
import android.os.Bundle
import com.example.comdemo.databinding.ActivitySplashBinding
import com.example.common.base.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getLayoutId(): Int = R.layout.activity_splash

}