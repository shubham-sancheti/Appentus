package com.shubham_dev.appentus.Model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ResponseModel {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("author")
    @Expose
    var author: String? = null

    @SerializedName("width")
    @Expose
    var width: Int? = null

    @SerializedName("height")
    @Expose
    var height: Int? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("download_url")
    @Expose
    var downloadUrl: String? = null

}