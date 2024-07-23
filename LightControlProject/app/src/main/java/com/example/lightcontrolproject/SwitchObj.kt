package com.example.lightcontrolproject

import kotlinx.serialization.Serializable

@Serializable
data class SwitchObj(val id: String, val name: String, val room: String, val state: Boolean)