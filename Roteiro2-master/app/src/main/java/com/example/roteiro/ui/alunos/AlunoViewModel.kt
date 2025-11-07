package com.example.roteiro.ui.alunos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.model.Aluno
import kotlinx.coroutines.launch

class AlunoViewModel(application: Application) : AndroidViewModel(application) {

    private val alunoDao = AppDatabase.getDatabase(application).alunoDao()

    private val _alunos = MutableLiveData<List<Aluno>>()
    val alunos: LiveData<List<Aluno>> = _alunos

    fun carregarAlunos() {
        viewModelScope.launch {
            _alunos.value = alunoDao.getAll()
        }
    }
}
