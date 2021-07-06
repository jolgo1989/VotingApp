package com.example.voting.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "voters_table")
data class Voters(
    @PrimaryKey(autoGenerate = true)
    val votersId: Int,
    val firstName: String,
    val lastName: String,
    val votingCard: String,
    val img: String
)
