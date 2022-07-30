package br.dipievil.androidaps2.repository

import android.util.Log
import br.dipievil.androidaps2.Statics
import br.dipievil.androidaps2.data.model.TaskItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DbHandler {

    private lateinit var db: DatabaseReference

    fun addTask(description: String, status: Boolean){

        db = FirebaseDatabase.getInstance().reference

        val task = TaskItem(null,description,status)

        var newTask = db.child(Statics.FIREBASE_TASK).push()
        task.id = newTask.key

        newTask.setValue(task)
    }

    fun loadTaskList(dataSnapshot: DataSnapshot) : MutableList<TaskItem> {

        var taskList: MutableList<TaskItem> = mutableListOf()

        var db = Firebase.firestore

        var tasks = db.collection("todo-list")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var id = document.id as String
                    var status = document.data.getValue("status") as Boolean
                    var title = document.data.getValue("title") as String
                    val task = TaskItem(id, title, status)
                    taskList!!.add(task)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("error", "Error getting documents: ", exception)
            }

        return taskList;
    }
}