package com.uniting.android.Login

import com.google.gson.annotations.SerializedName

class UniversityItem {
    data class University(
        @SerializedName("univ_name")
        val univName : String,
        @SerializedName("univ_mail")
        val univMail : String
    )

    data class Department(
        @SerializedName("dept_name")
        val deptName : String
    )
}