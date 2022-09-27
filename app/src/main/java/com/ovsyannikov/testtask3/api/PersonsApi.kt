package com.ovsyannikov.testtask3.api

import com.ovsyannikov.testtask3.data.person.Person
import com.ovsyannikov.testtask3.data.persons.Persons
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonsApi {

    @GET("character")
    suspend fun fetchPersons(@Query("page") query: Int): Persons

    @GET("character/{id}")
    suspend fun fetchPerson(@Path("id") query: Int): Person

}