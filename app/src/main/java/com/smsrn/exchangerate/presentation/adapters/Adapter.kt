package com.smsrn.exchangerate.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.smsrn.exchangerate.BR
import com.smsrn.exchangerate.presentation.adapters.listeners.OnItemListener

class Adapter<T> internal constructor(
    @LayoutRes private val layout: Int,
    private var itemListener: OnItemListener<T>? = null
) : RecyclerView.Adapter<Adapter<T>.MyViewHolder>() {

    var selectedPosition = -1

    var items: ArrayList<T> = ArrayList()
        set(value) {
            field = value
            if (itemsFiltered.size > 0)
                itemsFiltered.clear()
            itemsFiltered.addAll(value)
            notifyDataSetChanged()
        }

    var itemsFiltered: ArrayList<T> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setListeners(itemListener: OnItemListener<T>) {
        this.itemListener = itemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layout, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsFiltered[position]
        holder.itemView.setOnClickListener {
            itemListener?.onItemClick(it, position, item, selectedPosition)
            selectedPosition = position
        }
        holder.itemView.setOnLongClickListener {
            itemListener?.onItemLongClick(it, position, item, selectedPosition)
            selectedPosition = position
            true
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemsFiltered.size
    }

    fun submitData(newList: ArrayList<T>) {
//        val diffCallback = ItemListDiffUtil(itemsFiltered, newList)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        diffResult.dispatchUpdatesTo(this)
        items = newList
    }

    inner class MyViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, itemListener)
            binding.setVariable(BR.position, adapterPosition)
            binding.setVariable(BR.previousPosition, selectedPosition)
            binding.executePendingBindings()
        }
    }

    fun notifyItemChanged(item: T) {
        notifyItemChanged(itemsFiltered.indexOf(item))
    }

    fun notifyItemRemoved(item: T) {
        notifyItemRemoved(itemsFiltered.indexOf(item))
    }

    fun clear() {
        val size: Int = itemsFiltered.size
        if (size > 0) {
            for (i in 0 until size) {
                itemsFiltered.removeAt(0)
            }
            notifyItemRangeRemoved(0, size)
        }
    }
}

