package br.ufc.quixada.investidorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.ufc.quixada.investidorapp.model.Investimento
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InvestimentosViewModel(application: Application): AndroidViewModel(application) {
    private val database = FirebaseDatabase.getInstance()
        .reference.child("investimentos")

    private val _investimentos = MutableStateFlow<List<Investimento>>(emptyList())
    val investimentos: StateFlow<List<Investimento>> = _investimentos

    init {

    }

    private fun monitorarAlteracoes() {
        database.addChildEventListener(object : ChildEventListener{
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val nome = snapshot.child("nome").getValue(String::class.java) ?: "Desconhecido"
                val valor = snapshot.child("valor").getValue(Double::class.java) ?: 0.0
                val investimento = Investimento(nome, valor)
            }
        })
    }


}