package com.demo.namelistapp.feature_name.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.namelistapp.databinding.ChildNameItemBinding
import com.demo.namelistapp.db.table.NameItem

class NameAdapter: RecyclerView.Adapter<NameAdapter.ViewHolder>() {

	private var nameItemList: MutableList<NameItem> = mutableListOf()

	inner class ViewHolder(val binding: ChildNameItemBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ChildNameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		with(holder){
			with(nameItemList[position]){
				binding.txtName.text = this.name
			}
		}
	}
	override fun getItemCount(): Int = nameItemList.size

	fun submitItemList(nameList: List<NameItem>) {
		this.nameItemList.clear()
		this.nameItemList.addAll(nameList)
		notifyItemRangeChanged(0, this.nameItemList.size)
	}
}
