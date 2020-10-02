package com.phanduy.tekotest.data.service.model

import android.os.Parcel
import android.os.Parcelable

data class SearchRequest(var query: String, var page: Int) : Parcelable {

    fun nextPage (){
        page++
    }

    fun changeSearchKey(q : String) {
        query = q
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(query)
        parcel.writeInt(page)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchRequest> {
        override fun createFromParcel(parcel: Parcel): SearchRequest {
            return SearchRequest(parcel)
        }

        override fun newArray(size: Int): Array<SearchRequest?> {
            return arrayOfNulls(size)
        }
    }
}