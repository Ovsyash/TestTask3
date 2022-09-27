package com.ovsyannikov.testtask3.person

import androidx.lifecycle.ViewModel
import com.ovsyannikov.testtask3.api.PersonsApi
import com.ovsyannikov.testtask3.api.RetrofitInstance
import com.ovsyannikov.testtask3.data.person.Person

class PersonViewModel : ViewModel() {

    val personsApi: PersonsApi

    init {
        personsApi = RetrofitInstance.getRetrofitInstance().create(PersonsApi::class.java)
    }

    suspend fun getItemPerson(id: Int): Person {
        val person = personsApi.fetchPerson(id)
        return person
    }
}