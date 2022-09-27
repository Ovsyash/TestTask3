package com.ovsyannikov.testtask3.persons

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ovsyannikov.testtask3.PaginatedLoading
import com.ovsyannikov.testtask3.api.PersonsApi
import com.ovsyannikov.testtask3.api.RetrofitInstance
import com.ovsyannikov.testtask3.data.persons.Result
import kotlinx.coroutines.flow.Flow

class PersonsViewModel : ViewModel() {

    val personsApi: PersonsApi

    init {
        personsApi = RetrofitInstance.getRetrofitInstance().create(PersonsApi::class.java)
    }

    fun getListData(): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = { PaginatedLoading(personsApi) }).flow.cachedIn(viewModelScope)

    }
}