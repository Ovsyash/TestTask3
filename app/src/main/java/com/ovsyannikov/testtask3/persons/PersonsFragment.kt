package com.ovsyannikov.testtask3.persons

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ovsyannikov.testtask3.R
import com.ovsyannikov.testtask3.databinding.PersonsFragmentBinding
import com.ovsyannikov.testtask3.databinding.PersonsItemFragmentBinding
import com.ovsyannikov.testtask3.data.persons.Result
import com.bumptech.glide.Glide
import com.ovsyannikov.testtask3.person.PersonFragment
import kotlinx.coroutines.flow.collectLatest

class PersonsFragment : Fragment() {

    private lateinit var personsRecyclerView: RecyclerView
    private lateinit var binding: PersonsFragmentBinding
    private var personsAdapter = PersonsAdapter()
    private lateinit var personsViewModel: PersonsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PersonsFragmentBinding.inflate(inflater)
        personsRecyclerView = binding.personsRecyclerView
        personsRecyclerView.layoutManager = LinearLayoutManager(context)
        personsRecyclerView.adapter = personsAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPersonsViewModel()
    }

    inner class PersonsAdapter() :
        PagingDataAdapter<Result, PersonsAdapter.PersonsHolder>(DiffUtilCallBack()) {

        inner class PersonsHolder(view: View) :
            RecyclerView.ViewHolder(view), View.OnClickListener {
            val binding = PersonsItemFragmentBinding.bind(view)
            lateinit var personsItem: Result

            fun bind(data: Result) {

                binding.apply {
                    name.text = getString(R.string.name, data.name)
                    species.text = getString(R.string.species, data.species)
                    gender.text = getString(R.string.gender, data.gender)

                    Glide.with(imageView)
                        .load(data.image)
                        .into(imageView)
                }
            }

            fun bindPersons(item: Result) {
                personsItem = item
            }

            override fun onClick(p0: View?) {
                val fragment = PersonFragment.newInstance(urlPerson = personsItem.id)

                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            }

            init {
                view.setOnClickListener(this)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.persons_item_fragment, parent, false)

            return PersonsHolder(view)
        }

        override fun onBindViewHolder(holder: PersonsHolder, position: Int) {
            holder.bind(getItem(position)!!)
            holder.bindPersons(getItem(position)!!)
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.name == newItem.name && oldItem.species == newItem.species
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.name == newItem.name
        }
    }

    private fun initPersonsViewModel() {
        personsViewModel = ViewModelProvider(this).get(PersonsViewModel::class.java)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            personsViewModel.getListData().collectLatest {
                Log.d("Result", "OOO")
                personsAdapter.submitData(it)
            }
        }
    }

    companion object {
        fun newInstance() = PersonsFragment()
    }
}