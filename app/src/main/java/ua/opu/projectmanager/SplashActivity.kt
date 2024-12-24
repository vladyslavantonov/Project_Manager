package ua.opu.projectmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.navigateToMain.observe(this, Observer { navigate ->
            if (navigate) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }
}
