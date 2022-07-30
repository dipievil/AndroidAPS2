package br.dipievil.androidaps2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.dipievil.androidaps2.adapter.TaskAdapter
import br.dipievil.androidaps2.data.model.TaskItem


class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter : TaskAdapter

    private lateinit var rvItems : RecyclerView
    private lateinit var btnAdd : Button
    private lateinit var etTitle : EditText
    private lateinit var btnDeleteDone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume(){
        super.onResume()
        viewAdapter = TaskAdapter(mutableListOf())

        val emptyTask = TaskItem(null,"Tarefa de Exemplo",false)
        viewAdapter.addTask(emptyTask)

        rvItems = findViewById(R.id.rvItems)
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = viewAdapter

        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener{
            etTitle = findViewById(R.id.etTitle)
            val taskTitle = etTitle.text.toString()
            if(taskTitle.isNotEmpty()){
                val taskItem = TaskItem(null,taskTitle,false)
                viewAdapter.addTask(taskItem)
                etTitle.text.clear()
            }
        }

        btnDeleteDone = findViewById(R.id.btnDeleteDone)
        btnDeleteDone.setOnClickListener{
            viewAdapter.deleteDones()
        }
    }
}