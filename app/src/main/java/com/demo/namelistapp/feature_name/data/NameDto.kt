package com.demo.namelistapp.feature_name.data

import com.demo.namelistapp.db.table.NameItem
import com.google.gson.annotations.SerializedName

class NameDto(
    @SerializedName("title")
    val name: String? = ""
) {
    fun toNameItem(): NameItem {
        return NameItem(name = if(name.isNullOrEmpty()) "test" else name)
    }
}