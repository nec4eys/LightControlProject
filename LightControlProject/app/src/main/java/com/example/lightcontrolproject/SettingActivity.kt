package com.example.lightcontrolproject

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.CorrectionInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.serialization.json.Json

class SettingActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        SetDataWithRoomsToRecyclerView()
    }

    fun ExitButton(v: View?)
    {
        finish()
    }

    fun SetDataWithRoomsToRecyclerView()
    {
        //val roomName : TextView = findViewById(R.id.roomName)
        //val shared: SharedPreferences = this.getSharedPreferences("Preferences", AppCompatActivity.MODE_PRIVATE)
        //val name : String? = shared.getString("room", "r0")
        //roomName.text = name

        val recyclerView : RecyclerView = findViewById(R.id.settingRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val shared: SharedPreferences = this.getSharedPreferences("Preferences", AppCompatActivity.MODE_PRIVATE)
        val coordinates = mutableListOf<SettingAdapter.CoordinateHolder>()
        val rooms = mutableListOf<RoomObj>()
        val switches = mutableListOf<Int>()

        val queue = Volley.newRequestQueue(this)
        val url = "http://84.237.51.142:5000/light_control"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                //val json = Json {ignoreUnknownKeys = true}
                //val events : Array<GitHubEvent> = json.decodeFromString(response.toString())
                //val res = Json.decodeFromString<List<SwitchAdapter.SwitchObj>>(response)
                val res = Json.decodeFromString<LightObj>(response)
                for(r in res.rooms)
                {
                    rooms.add(RoomObj(r.id, r.name))

                    val x1 = shared.getInt(r.id+"/x1", 0)
                    val y1 = shared.getInt(r.id+"/y1", 0)
                    val x2 = shared.getInt(r.id+"/x2", 0)
                    val y2 = shared.getInt(r.id+"/y2", 0)
                    val coord = SettingAdapter.CoordinateHolder(x1, y1, x2, y2)
                    coordinates.add(coord)
                }

                for(i in 0..<rooms.size)
                {
                    switches.add(0)
                    for(j in res.switches)
                    {
                        if(j.room == rooms[i].id)
                        {
                            switches[i]++
                        }
                    }
                }

                recyclerView.adapter = SettingAdapter(rooms, switches, coordinates)
            },
            { e ->
                e.printStackTrace()
            })

        queue.add(stringRequest)
    }
}
