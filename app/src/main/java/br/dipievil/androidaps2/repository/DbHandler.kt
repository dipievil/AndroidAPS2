package br.dipievil.androidaps2.repository

import android.util.Log
import br.dipievil.androidaps2.data.model.TaskItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DbHandler {

    private lateinit var db: DatabaseReference



    fun addTask(description: String, status: Boolean){

        var db = Firebase.firestore

        val task = TaskItem(null,description,status)

        db.collection("todo-list")
            .add(task)
            .addOnSuccessListener { documentReference ->
                Log.d("FIREBASE", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FIREBASE", "Error adding document", e)
            }
    }

    fun getTasks() : MutableList<TaskItem> {

        var taskList: MutableList<TaskItem> = mutableListOf()

        var db = Firebase.firestore

        db.collection("todo-list")
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
                Log.e("error", "Error getting documents: ", exception)
            }

        return taskList;
    }

    fun deleteTask(id : String){
        var db = Firebase.firestore

        db.collection("cities").document(id)
            //.whereEqualTo("id", id).get()
            .delete()
            .addOnSuccessListener { Log.d("FIREBASE", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("FIREBASE", "Error deleting document", e) }
    }
}