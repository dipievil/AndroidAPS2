package br.dipievil.androidaps2.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.dipievil.androidaps2.R
import br.dipievil.androidaps2.data.model.TaskItem
import br.dipievil.androidaps2.repository.DbHandler

class TaskAdapter(
    private var tasks: MutableList<TaskItem>
) : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private val dbHandler: DbHandler = DbHandler()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.task_list,
            parent,
            false
        )
        Log.i("onCreateViewHolder", "ViewHolder Created")

        var dbTasks = dbHandler.getTasks()
        if(dbTasks.isNotEmpty())
            tasks = dbTasks;
        return TaskHolder(view)
    }

    fun addTask(taskItem: TaskItem){
        tasks.add(taskItem)

        dbHandler.addTask(taskItem.title,taskItem.status)

        notifyItemInserted(tasks.size - 1)
        notifyDataSetChanged()
        Log.i("addTask",taskItem.title)
    }

    fun deleteDones(){
        for(task in tasks){
            if(!task.status){
                dbHandler.deleteTask(task.id as String)
            }
        }
        tasks.removeAll{ item ->
            item.status
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {

        holder.tvTaskTitle.text = tasks[position].title
        holder.cbDone.isChecked = tasks[position].status

        Log.i("Bind new task",tasks[position].title)

        holder.cbDone.setOnCheckedChangeListener { _, isChecked ->
            toggleStrike(holder.tvTaskTitle, isChecked)
            tasks[position].status = !tasks[position].status
        }
    }

    class TaskHolder(view: View): RecyclerView.ViewHolder(view){
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

    fun loadTasks(){
        //var tasks = dbHandler.loadTaskList()
    }

    override fun getItemCount(): Int {
        Log.i("getItemCount",tasks.size.toString())
        return tasks.size
    }
}