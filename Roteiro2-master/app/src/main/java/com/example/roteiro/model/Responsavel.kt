package com.example.roteiro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "responsaveis")
data class Responsavel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val aluno: String, // Nome do aluno dependente
    val endereco: String,
    val telefone: String
)
