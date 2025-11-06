package com.example.roteiro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alunos")
data class Aluno(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val turno: String,
    val escola: String,
    val nomeResponsavel: String,
    val telefoneResponsavel: String
)
