package com.example.studykotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.studykotlin.databinding.ActivityMainBinding

// MainActivityはアプリのエントリーポイント。UIを表示するAppCompatActivityを継承。
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBindingを使用する場合
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ツールバーをActionBarとして設定
        setSupportActionBar(binding.toolbar)

        // NavHostFragmentを取得
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        navController = navHostFragment?.navController
            ?: throw IllegalStateException("NavHostFragment not found")

        // アクションバーとナビゲーションコントローラを統合 (戻るボタンなどを有効にするため)
        setupActionBarWithNavController(navController)
    }

    // 上部のバーの戻るボタンを有効に
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}