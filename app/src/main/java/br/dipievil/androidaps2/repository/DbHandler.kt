package br.dipievil.androidaps2.repository

import android.util.Log
import br.dipievil.androidaps2.Statics
import br.dipievil.androidaps2.model.Task
import br.dipievil.androidaps2.model.TaskItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DbHandler {

    private lateinit var _db: DatabaseReference

    fun addTask(description: String, status: Boolean){

        _db = FirebaseDatabase.getInstance().reference

        val task = Task.create()

        task.title = description
        task.status = status

        val newTask = _db.child(Statics.FIREBASE_TASK).push()
        task.id = newTask.key

        newTask.setValue(task)
    }

    fun loadTaskList(dataSnapshot: DataSnapshot) : MutableList<Task> {

        _db = FirebaseDatabase.getInstance().reference

        var taskList: MutableList<Task> = mutableListOf()

        Log.d("MainActivity", "loadTaskList")

        val tasks = dataSnapshot.children.iterator()

        if (tasks.hasNext()) {

            taskList!!.clear()

            val listIndex = tasks.next()
            val itemsIterator = listIndex.children.iterator()

            while (itemsIterator.hasNext()) {

                val currentItem = itemsIterator.next()
                val task = Task.create()

                val map = currentItem.getValue() as HashMap<String, Any>

                task.id = currentItem.key
                task.status = map.get("status") as Boolean?
                task.title = map.get("title") as String?
                taskList!!.add(task)
            }
        }

        return taskList
    }
}