package com.ovsyannikov.testtask3.person

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ovsyannikov.testtask3.R
import com.ovsyannikov.testtask3.databinding.PersonItemFragmentBinding

private const val ARG_URL_PERSON = "ARG_URL_PERSON"

class PersonFragment : Fragment() {

    private lateinit var binding: PersonItemFragmentBinding
    private lateinit var personViewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PersonItemFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPersonViewModel()
    }

    @SuppressLint("StringFormatInvalid")
    private fun initPersonViewModel() {
        personViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            val person = personViewModel.getItemPerson(getUrlPerson())

            binding.apply {
                namePerson.text = getString(R.string.name, person.name)
                statusPerson.text = getString(R.string.status, person.status)
                speciesPerson.text = getString(R.string.species, person.species)
                genderPerson.text = getString(R.string.gender, person.gender)
                lastLocation.text = getString(R.string.last_location, person.location.name)
                numberOfEpisodes.text = getString(R.string.number_of_episodes, person.episode.size.toString())

                Glide.with(imageView)
                    .load(person.image)
                    .into(imageView)
            }
        }
    }

    private fun getUrlPerson(): Int = requireArguments().getInt(ARG_URL_PERSON)

    companion object {
        fun newInstance(urlPerson: Int): PersonFragment {
            val args = Bundle().apply {
                putInt(ARG_URL_PERSON, urlPerson)
            }

            val fragment = PersonFragment()
            fragment.arguments = args
            return fragment

        }
    }
}