package com.bosla.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bosla.template.databinding.ActivityMainBinding

data class Task(
    val title: String,
    val description: String,
    var isCompleted: Boolean = false
)

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onDeleteClick: (Int) -> Unit,
    private val onCheckChanged: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbTask: CheckBox = view.findViewById(R.id.cbTask)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.tvTitle.text = task.title
        holder.tvDescription.text = task.description
        holder.cbTask.isChecked = task.isCompleted

        holder.cbTask.setOnmaterialCheckedChangeListener = null
        holder.cbTask.setOnCheckedChangeListener { _, isChecked ->
            onCheckChanged(position, isChecked)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int = tasks.size
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val taskList = mutableListOf<Task>()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskAdapter = TaskAdapter(
            tasks = taskList,
            onDeleteClick = { position ->
                if (position in taskList.indices) {
                    taskList.removeAt(position)
                    taskAdapter.notifyItemRemoved(position)
                    updateEmptyView()
                }
            },
            onCheckChanged = { position, isChecked ->
                if (position in taskList.indices) {
                    taskList[position].isCompleted = isChecked
                }
            }
        )

        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = taskAdapter

        binding.btnAdd.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (title.isNotEmpty()) {
                val newTask = Task(title, description)
                taskList.add(0, newTask)
                taskAdapter.notifyItemInserted(0)
                binding.rvTasks.scrollToPosition(0)

                binding.etTitle.text.clear()
                binding.etDescription.text.clear()
                updateEmptyView()
            }
        }

        updateEmptyView()
    }

    private fun updateEmptyView() {
        if (taskList.isEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvTasks.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.rvTasks.visibility = View.VISIBLE
        }
    }
}
