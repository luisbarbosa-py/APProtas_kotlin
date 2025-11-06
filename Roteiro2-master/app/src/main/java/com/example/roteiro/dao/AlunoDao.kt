package com.example.roteiro.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.roteiro.model.Aluno

@Dao
interface AlunoDao {
    @Insert
    suspend fun insert(aluno: Aluno)

    @Update
    suspend fun update(aluno: Aluno)

    @Query("SELECT * FROM alunos")
    suspend fun getAll(): List<Aluno>

    @Query("SELECT * FROM alunos WHERE id = :id")
    suspend fun getById(id: Long): Aluno?

    @Query("DELETE FROM alunos WHERE id = :id")
    suspend fun delete(id: Long)
}
