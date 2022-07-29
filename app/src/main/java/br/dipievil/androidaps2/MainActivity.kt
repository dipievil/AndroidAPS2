package br.dipievil.androidaps2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.dipievil.androidaps2.adapter.TaskListAdapter
import br.dipievil.androidaps2.model.TaskItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var listItemsAdapter : TaskListAdapter

    private lateinit var rvItems : RecyclerView
    private lateinit var btnAdd : Button
    private lateinit var etTitle : EditText
    private lateinit var btnDeleteDone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listItemsAdapter = TaskListAdapter(mutableListOf(),this)

        rvItems = findViewById(R.id.rvItems)
        btnAdd = findViewById(R.id.btnAdd)
        etTitle = findViewById(R.id.etTitle)
        btnDeleteDone = findViewById(R.id.btnDeleteDone)

        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = listItemsAdapter

        btnAdd.setOnClickListener{
            val taskTitle = etTitle.text.toString()
            Log.i("text",taskTitle)
            if(taskTitle.isNotEmpty()){
                val taskItem = TaskItem(taskTitle)
                listItemsAdapter.addTask(taskItem)
                etTitle.text.clear()
            }
        }

        btnDeleteDone.setOnClickListener{
            listItemsAdapter.deleteDones()
        }
    }

    var _taskListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            loadTaskList(dataSnapshot)
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
        }
    }
}