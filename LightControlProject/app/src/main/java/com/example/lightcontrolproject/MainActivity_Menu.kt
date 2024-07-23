package com.example.lightcontrolproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity_Menu : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
    }

    fun ButtonLightSwitchActivity(v: View?)
    {
        val activity = Intent(this, LightSwitchActivity::class.java)
        startActivity(activity)
    }

    fun ButtonRoomsActivity(v: View?)
    {
        val activity = Intent(this, RoomsActivity::class.java)
        startActivity(activity)
    }
}