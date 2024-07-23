package com.example.lightcontrolproject

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.Serializable


class SettingAdapter (val rooms: List<RoomObj>, val switchCount: List<Int>, val coordinates: List<CoordinateHolder>) : RecyclerView.Adapter<SettingAdapter.SettingHolder>()
{
    @Serializable
    data class CoordinateHolder(val x1: Int, val y1: Int, val x2: Int, val y2: Int)

    class SettingHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val roomName : TextView = itemView.findViewById(R.id.Room)
        val roomId : TextView = itemView.findViewById(R.id.RoomId)
        val switchCount : TextView = itemView.findViewById(R.id.switchCount)

        val cX1 : EditText = itemView.findViewById(R.id.editTextX1)
        val cY1 : EditText = itemView.findViewById(R.id.editTextY1)
        val cX2 : EditText = itemView.findViewById(R.id.editTextX2)
        val cY2 : EditText = itemView.findViewById(R.id.editTextY2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.room_setting_item, parent, false)

        return SettingHolder(itemView)
    }

    override fun onBindViewHolder(holder: SettingHolder, position: Int)
    {
        holder.roomName.text = rooms[position].name
        holder.roomId.text = rooms[position].id
        holder.switchCount.text = "switch count = " + switchCount[position].toString()

        holder.cX1.setText(coordinates[position].x1.toString())
        holder.cY1.setText(coordinates[position].y1.toString())
        holder.cX2.setText(coordinates[position].x2.toString())
        holder.cY2.setText(coordinates[position].y2.toString())

        holder.cX1.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus)
            {
                //holder.roomId.text = rooms[position].id + " in focus"
                val shared: SharedPreferences = holder.itemView.context.getSharedPreferences("Preferences", AppCompatActivity.MODE_PRIVATE)
                val n : String = holder.cX1.text.toString()
                shared.edit().putInt(holder.roomId.text.toString() + "/x1",n.toInt()).apply()
            }
        }

        holder.cY1.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus)
            {
                //holder.roomId.text = rooms[position].id + " in focus"
                val shared: SharedPreferences = holder.itemView.context.getSharedPreferences("Preferences", AppCompatActivity.MODE_PRIVATE)
                val n : String = holder.cY1.text.toString()
                shared.edit().putInt(holder.roomId.text.toString() + "/y1" ,n.toInt()).apply()
            }
        }

        holder.cX2.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus)
            {
                //holder.roomId.text = rooms[position].id + " in focus"
                val shared: SharedPreferences = holder.itemView.context.getSharedPreferences("Preferences", AppCompatActivity.MODE_PRIVATE)
                val n : String = holder.cX2.text.toString()
                shared.edit().putInt(holder.roomId.text.toString() + "/x2",n.toInt()).apply()
            }
        }

        holder.cY2.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus)
            {
                //holder.roomId.text = rooms[position].id + " in focus"
                val shared: SharedPreferences = holder.itemView.context.getSharedPreferences("Preferences", AppCompatActivity.MODE_PRIVATE)
                val n : String = holder.cY2.text.toString()
                shared.edit().putInt(holder.roomId.text.toString() + "/y2",n.toInt()).apply()
            }
        }

    }

    override fun getItemCount(): Int
    {
        return rooms.size
    }

    fun getCoordinatesData(): List<CoordinateHolder>
    {
        return coordinates
    }
}