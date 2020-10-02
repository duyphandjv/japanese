package com.phanduy.tekotest.viewmodel

import androidx.lifecycle.*
import com.phanduy.tekotest.data.repository.ProductApiRepository
import com.phanduy.tekotest.data.service.*
import com.phanduy.tekotest.data.service.model.SearchRequest
import com.phanduy.tekotest.data.service.model.SearchResponse
import com.phanduy.tekotest.di.AssistedSavedStateViewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


class ListProductViewModel @AssistedInject constructor(
        apiRepository: ProductApiRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<ListProductViewModel>

    init {
        setLoadingState(LOADING_REFRESH)
    }

    val products : LiveData<SearchResponse> = getSearchRequest().switchMap {

        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
           val response = try {
               apiRepository.searchProduct(it.query, "","pv_online","CP01", it.page, 10)
           } catch (ex: Exception) {
               SearchResponse(null, null).also {
                   it.setException(ex)
               }
           }
           emit(response)
        }
    }

    val loadingDataState = getLoadingState()

    companion object {
        public const val SEARCH_REQUEST_SAVED_STATE_KEY = "SEARCH_REQUEST_SAVED_STATE_KEY"
        public const val PAGE_SAVED_STATE_KEY = "PAGE_SAVED_STATE_KEY"
        public const val LOADING_SAVED_STATE_KEY = "LOADING_SAVED_STATE_KEY"

        public const val LOADING_MORE = 1
        public const val LOADING_REFRESH = 2
        public const val COMPLETE = 0
        public const val DISCONNECT = -1
    }

    fun isRefresgState() : Boolean {
        return loadingDataState.value == LOADING_REFRESH
    }

    fun setLoadingState(state: Int) {
        savedStateHandle.set(LOADING_SAVED_STATE_KEY, state)
    }



    fun setPageSate(page: Int) {
        savedStateHandle.set(PAGE_SAVED_STATE_KEY, page)
    }

    fun setSearchRequest(q : String, pageIndex : Int) {

        var request = getSearchRequest().value

        if(request == null) {
            request = SearchRequest("", 1)
        }

        if(request.query != q) {
            savedStateHandle.set(LOADING_SAVED_STATE_KEY, LOADING_REFRESH)
        }

        request.apply {
            query = q
            page = pageIndex
        }



        savedStateHandle.set(SEARCH_REQUEST_SAVED_STATE_KEY, request)
    }

    fun nextPage() {
        var request = getSearchRequest().value

        if(request == null) {
            request = SearchRequest("", 1)
        } else {
            request.nextPage()
        }
        savedStateHandle.set(LOADING_SAVED_STATE_KEY, LOADING_MORE)
        savedStateHandle.set(SEARCH_REQUEST_SAVED_STATE_KEY, request)
    }

    fun refreshData() {
        var request = getSearchRequest().value

        if(request == null) {
            request = SearchRequest("", 1)
        } else {
            request.apply {
                page = 1
            }
        }
        savedStateHandle.set(SEARCH_REQUEST_SAVED_STATE_KEY, request)
    }

    fun getPage(): MutableLiveData<Int> {
        return savedStateHandle.getLiveData<Int>(PAGE_SAVED_STATE_KEY, 1)
    }

    fun getLoadingState(): MutableLiveData<Int> {
        return savedStateHandle.getLiveData<Int>(LOADING_SAVED_STATE_KEY, 0)
    }

    private fun getSearchRequest(): MutableLiveData<SearchRequest> {
        return savedStateHandle.getLiveData<SearchRequest>(SEARCH_REQUEST_SAVED_STATE_KEY, SearchRequest("", 1))
    }
}