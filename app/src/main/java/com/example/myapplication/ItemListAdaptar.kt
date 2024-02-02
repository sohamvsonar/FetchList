package com.example.myapplication
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.myapplication.databinding.ItemRowBinding
import android.widget.TableRow
import android.graphics.Color
import androidx.core.view.setPadding
import android.widget.Filter
import android.widget.Filterable

class ItemListAdapter : RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>(), Filterable {
    private var groupedItems: Map<Int, List<Item>> = emptyMap()
    private var filteredItems: Map<Int, List<Item>> = emptyMap()

    fun setItems(groupedItems: Map<Int, List<Item>>) {
        this.groupedItems = groupedItems
        this.filteredItems = groupedItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val listId = filteredItems.keys.sorted()[position]
        val itemList = filteredItems[listId]
        holder.bind(listId, itemList ?: emptyList())
    }

    override fun getItemCount(): Int = filteredItems.size

    inner class ItemViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listId: Int, itemList: List<Item>) {
            binding.listIdTextView.text = "List ID: $listId"

            binding.tableLayout.removeAllViews()

            // Adding row title
            val titleRow = TableRow(binding.root.context)
            val titleTextView = TextView(binding.root.context)
            titleTextView.text = "List of Items                  "
            titleTextView.setBackgroundColor(Color.YELLOW)
            titleTextView.setPadding(8)
            titleRow.addView(titleTextView)
            binding.tableLayout.addView(titleRow)

            // Adding items to the table layout
            itemList.forEach { item ->
                val row = TableRow(binding.root.context)
                val nameTextView = TextView(binding.root.context)
                nameTextView.text = item.name
                nameTextView.setBackgroundColor(Color.WHITE)
                nameTextView.setPadding(8)
                nameTextView.setBackgroundResource(R.drawable.border)
                row.addView(nameTextView)
                binding.tableLayout.addView(row)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableMapOf<Int, List<Item>>()
                if (constraint.isNullOrEmpty()) {
                    filteredList.putAll(groupedItems)
                } else {
                    val filterPattern = constraint.toString().lowercase().trim()
                    for ((key, value) in groupedItems) {
                        val filteredItems = value.filter { it.name.lowercase().contains(filterPattern) }
                        if (filteredItems.isNotEmpty()) {
                            filteredList[key] = filteredItems
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as Map<Int, List<Item>>
                notifyDataSetChanged()
            }
        }
    }
}
