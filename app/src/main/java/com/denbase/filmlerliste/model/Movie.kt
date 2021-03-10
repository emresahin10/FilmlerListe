package com.denbase.filmlerliste.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity // Get/set
data class Movie(

        @ColumnInfo(name = "isim")
        @SerializedName("isim")
        val movieName: String?,
        @ColumnInfo(name = "tur")
        @SerializedName("tur")
        val movieCategory: String?,
        @ColumnInfo(name = "imdb")
        @SerializedName("imdb")
        val movieIMDB: String?,
        @ColumnInfo(name = "sure")
        @SerializedName("sure")
        val movieTime: String?,
        @ColumnInfo(name = "konu")
        @SerializedName("konu")
        val movieSubject: String?,
        @ColumnInfo(name = "gorsel")
        @SerializedName("gorsel")
        val movieImage: String?
        ) {

        @PrimaryKey(autoGenerate = true)
        var uuid: Int = 0
}