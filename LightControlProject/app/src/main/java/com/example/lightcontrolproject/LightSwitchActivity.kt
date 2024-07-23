package com.example.lightcontrolproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.serialization.json.Json

import java.util.Timer
import java.util.TimerTask

class LightSwitchActivity : AppCompatActivity()
{
    //private val recyclerView : RecyclerView = findViewById(R.id.recyclerView)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_switch)

        SetDataToRecyclerView()
    }

    fun ExitButton(v: View?)
    {
        finish()
    }

    fun SetDataToRecyclerView()
    {
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val switches = mutableListOf<SwitchObj>()

        val queue = Volley.newRequestQueue(this)
        val url = "http://84.237.51.142:5000/light_control/switches"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                //val json = Json {ignoreUnknownKeys = true}
                //val events : Array<GitHubEvent> = json.decodeFromString(response.toString())
                val res = Json.decodeFromString<List<SwitchObj>>(response)
                //val res = Json.decodeFromString<List<SwitchAdapter.SwitchObj>>(response)

                /*for(r in res)
                {
                    //val sw = SwitchAdapter.SwitchObj(r.key, r.value)
                    //switches[r.key.replace("sw", "").toInt()] = SwitchAdapter.SwitchObj(r.key, r.value)
                    switches.add(SwitchAdapter.SwitchObj(r.key, r.value))
                }*/
                //switches.sortBy { it.id.replace("sw", "").toInt() }
                recyclerView.adapter = SwitchAdapter(res)
            },
            { e ->
                e.printStackTrace()
            })

        queue.add(stringRequest)
    }

    fun SetDataWithRoomsToRecyclerView()
    {
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val rooms = mutableListOf<RoomObj>()
        val switches = mutableListOf<SwitchObj>()

        val queue = Volley.newRequestQueue(this)
        val url = "http://84.237.51.142:5000/light_control"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                //val json = Json {ignoreUnknownKeys = true}
                //val events : Array<GitHubEvent> = json.decodeFromString(response.toString())
                //val res = Json.decodeFromString<List<SwitchAdapter.SwitchObj>>(response)
                val res = Json.decodeFromString<List<LightObj>>(response)

                /*for(r in res)
                {
                    //val sw = SwitchAdapter.SwitchObj(r.key, r.value)
                    //switches[r.key.replace("sw", "").toInt()] = SwitchAdapter.SwitchObj(r.key, r.value)
                    switches.add(SwitchAdapter.SwitchObj(r.key, r.value))
                }*/
                //switches.sortBy { it.id.replace("sw", "").toInt() }
                //recyclerView.adapter = SwitchAdapter(res)
            },
            { e ->
                e.printStackTrace()
            })

        queue.add(stringRequest)
    }
}