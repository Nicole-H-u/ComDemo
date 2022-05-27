package com.example.comdemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.comdemo.databinding.ActivitySplashBinding
import com.example.common.base.BaseActivity
import com.example.login.view.LoginActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        Handler().postDelayed(Runnable {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()}, 3000)
    }

    override fun getLayoutId(): Int = R.layout.activity_splash

}