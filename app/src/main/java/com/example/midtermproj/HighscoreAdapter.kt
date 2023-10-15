package com.example.midtermproj

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.midtermproj.databinding.HighscoreBinding

class DiffItemCallback : DiffUtil.ItemCallback<Highscore>() {
    override fun areItemsTheSame(oldItem: Highscore, newItem: Highscore)
            = (oldItem.highscoreId == newItem.highscoreId)
    override fun areContentsTheSame(oldItem: Highscore, newItem: Highscore) = (oldItem == newItem)
}

class HighscoreAdapter(val onItemClicked: (id: Int) -> Unit, val onDeleteClicked: (id: Int) -> Unit)
    : ListAdapter<Highscore, HighscoreAdapter.TaskItemViewHolder>(DiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClicked, onDeleteClicked)
    }

    class TaskItemViewHolder(val binding: HighscoreBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HighscoreBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)
            }
        }
        fun bind(item: Highscore, onItemClicked: (id: Int) -> Unit, onDeleteClicked: (id: Int) -> Unit) {
            binding.textHighscore.text = "${item.playerName} : ${item.numAttempts}"
            binding.bDelete.setOnClickListener { onDeleteClicked(item.highscoreId) }
            // can also access onItemClick here if need be
        }
    }
}