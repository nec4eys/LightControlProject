package com.example.lightcontrolproject

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.serialization.json.Json

class RoomsSwitchActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms_switch)

        SetDataWithRoomsToRecyclerView()
    }

    fun ExitButton(v: View?)
    {
        finish()
    }

    fun SetDataWithRoomsToRecyclerView()
    {
        val roomName : TextView = findViewById(R.id.roomName)
        val shared: SharedPreferences = this.getSharedPreferences("Preferences", AppCompatActivity.MODE_PRIVATE)
        val name : String? = shared.getString("room", "r0")
        //roomName.text = name

        val recyclerView : RecyclerView = findViewById(R.id.roomsRecyclerView)
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
                val res = Json.decodeFromString<LightObj>(response)

                for(r in res.switches)
                {
                    if(name == r.room)
                    {
                        switches.add(r)
                    }
                }

                roomName.text = res.rooms.find { it.id == name }?.name.toString()

                switches.sortBy { it.id.replace("sw", "").toInt() }
                recyclerView.adapter = SwitchAdapter(switches)
            },
            { e ->
                e.printStackTrace()
            })

        queue.add(stringRequest)
    }
}