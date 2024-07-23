package com.example.lightcontrolproject

import kotlinx.serialization.Serializable

@Serializable
data class LightObj(val rooms : List<RoomObj>, val switches : List<SwitchObj>)