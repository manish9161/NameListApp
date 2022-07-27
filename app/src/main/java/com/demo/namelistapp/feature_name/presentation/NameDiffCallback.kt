package com.demo.namelistapp.feature_name.presentation

import androidx.recyclerview.widget.DiffUtil
import com.demo.namelistapp.db.table.NameItem

class NameDiffCallback: DiffUtil.ItemCallback<NameItem>() {
    override fun areItemsTheSame(oldItem: NameItem, newItem: NameItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: NameItem, newItem: NameItem): Boolean {
        return  oldItem == newItem
    }
}