package com.example.lightcontrolproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.util.Timer
import java.util.TimerTask

class SwitchAdapter(val switches: List<SwitchObj>) : RecyclerView.Adapter<SwitchAdapter.SwitchHolder>()
{

    class SwitchHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val switch : Switch = itemView.findViewById(R.id.switchView)
        val idText : TextView = itemView.findViewById(R.id.message)

        var id : String = ""

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : SwitchHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.switch_item, parent, false)

        return SwitchHolder(itemView)
    }

    override fun onBindViewHolder(holder: SwitchHolder, position: Int)
    {

        holder.switch.text = switches[position].name +" in " + switches[position].room
        holder.idText.text = switches[position].id
        holder.id = switches[position].id

        holder.switch.isChecked = switches[position].state

        holder.switch.setOnCheckedChangeListener{ _, isChecked ->
            holder.switch.isChecked = isChecked

            val queue = Volley.newRequestQueue(holder.switch.context)
            val url = "http://84.237.51.142:5000/light_control/switches/states"
            val requestObj = JSONObject("{${holder.id}:$isChecked}")
            val stringRequest = JsonObjectRequest(
                Request.Method.POST, url, requestObj,
                {response ->
                    val res = Json.decodeFromString<Map<String, Boolean>>(response.toString())
                    if (res[holder.id] != isChecked) {
                        holder.switch.isChecked = !holder.switch.isChecked
                    }
                },
                {e ->
                    holder.switch.isChecked = !holder.switch.isChecked
                }
            )

            queue.add(stringRequest)
        }

        val timer = Timer()//было бы не плохо решить проблему с вылетом но хз, может потом
        timer.schedule(object : TimerTask()
        {
            override fun run()
            {

                val queue = Volley.newRequestQueue(holder.switch.context)
                val url = "http://84.237.51.142:5000/light_control/switches/states"

                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        val res = Json.decodeFromString<Map<String, Boolean>>(response)

                        if(holder.switch.isChecked != res[holder.id])
                        {
                            holder.switch.isChecked = res[holder.id] == true
                        }
                    },
                    { e ->
                        e.printStackTrace()
                    })

                queue.add(stringRequest)
            }
        }, 2 * 1000, 2 * 1000)
    }

    override fun getItemCount(): Int
    {
        return switches.size
    }
}
