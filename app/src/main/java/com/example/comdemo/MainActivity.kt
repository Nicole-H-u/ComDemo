package com.example.comdemo

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.comdemo.databinding.ActivityMainBinding
import com.example.common.base.BaseActivity
import com.example.common.support.Constants

@Route(path = Constants.PATH_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var currentNavController: LiveData<NavController>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}