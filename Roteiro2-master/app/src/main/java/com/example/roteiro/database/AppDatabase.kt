package com.example.roteiro.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roteiro.dao.AlunoDao
import com.example.roteiro.dao.ResponsavelDao
import com.example.roteiro.dao.UsuarioDao
import com.example.roteiro.model.Aluno
import com.example.roteiro.model.Responsavel
import com.example.roteiro.model.Usuario

@Database(entities = [Aluno::class, Usuario::class, Responsavel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun alunoDao(): AlunoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun responsavelDao(): ResponsavelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "roteiro_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
