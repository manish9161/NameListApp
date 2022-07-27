package com.demo.namelistapp.feature_name.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.namelistapp.core.hide
import com.demo.namelistapp.core.show
import com.demo.namelistapp.databinding.ActivityNameBinding
import com.demo.namelistapp.db.table.NameItem
import com.demo.namelistapp.feature_name.domain.NamesViewModel
import com.demo.namelistapp.network.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteActivity: AppCompatActivity() {

    private lateinit var binding: ActivityNameBinding
    private val namesViewModel: NamesViewModel by viewModels()
    private val nameAdapter = NameAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvName.layoutManager = LinearLayoutManager(this)
        binding.rvName.adapter = nameAdapter
        namesViewModel.noteLiveData.observe(this) { networkResult ->
            when(networkResult) {
                is NetworkResult.Error -> {
                    binding.progressCircular.hide()
                    binding.txtNoItem.hide()
                    binding.txtError.show()
                    binding.txtError.text = networkResult.message.toString()
                    binding.rvName.hide()
                }
                is NetworkResult.Loading -> {
                    binding.progressCircular.show()
                    binding.txtError.hide()
                    binding.txtNoItem.hide()
                    binding.rvName.hide()
                }
                is NetworkResult.Success -> {
                    binding.progressCircular.hide()
                    binding.txtError.hide()
                    binding.txtNoItem.hide()
                    binding.rvName.show()

                    setAdapter(networkResult.data)

                }
            }
        }
    }

    private fun setAdapter(list: List<NameItem>?) {
        list?.let {
            nameAdapter.submitItemList(it)
        }

    }
}