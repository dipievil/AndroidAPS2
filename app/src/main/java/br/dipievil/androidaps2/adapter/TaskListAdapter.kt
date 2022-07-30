package br.dipievil.androidaps2.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.dipievil.androidaps2.R
import br.dipievil.androidaps2.model.Task
import br.dipievil.androidaps2.model.TaskItem
import br.dipievil.androidaps2.repository.DbHandler
import com.google.firebase.database.DataSnapshot

class TaskListAdapter(
    private val tasks: MutableList<TaskItem>, private val context: Context
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val dbHandler: DbHandler = DbHandler()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.task,
            parent,
            false
        )
        return TaskViewHolder(view)
    }

    fun addTask(taskItem: TaskItem){
        tasks.add(taskItem)
        Log.i("getCount",tasks.size.toString())
        notifyItemInserted(tasks.size - 1)

        dbHandler.addTask(taskItem.title,taskItem.status)

        Toast.makeText(context, "Tarefa adicionada com sucesso", Toast.LENGTH_SHORT).show()
    }

    fun deleteDones(){
        tasks.removeAll{ item ->
            item.status
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val curItem = tasks[position]
        holder.tvTaskTitle.text = curItem.title
        holder.cbDone.isChecked = curItem.status

        holder.itemView.apply{
            Log.i("Apply",curItem.title)
            holder.tvTaskTitle.text = curItem.title
            holder.cbDone.isChecked = curItem.status

            toggleStrike(holder.tvTaskTitle,holder.cbDone.isChecked)

            holder.cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrike(holder.tvTaskTitle, isChecked)
                curItem.status = !curItem.status
            }
        }
    }

    fun loadTasks(dataSnapshot: DataSnapshot){
        //tasks = dbHandler.loadTaskList(dataSnapshot)
    }

    override fun getItemCount(): Int {
        Log.i("getCount",tasks.size.toString())
        return tasks.size
    }

    inner class TaskViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvTaskTitle : TextView = view.findViewById(R.id.tvTaskTitle)
        var cbDone : CheckBox = view.findViewById(R.id.cbTaskStatus)
    }

    private fun toggleStrike(tvTask : TextView, isChecked : Boolean){
        if(isChecked){
            tvTask.paintFlags = tvTask.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTask.paintFlags = tvTask.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}