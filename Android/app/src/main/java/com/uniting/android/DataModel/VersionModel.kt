package com.uniting.android.DataModel

import com.google.gson.annotations.SerializedName

class VersionModel {
    data class Version(
        @SerializedName("app_version")
        val appVersion : String,
        @SerializedName("update_issue")
        val updateIssue : String
    )
}