package com.example.uptodo.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.uptodo.R
import com.example.uptodo.databinding.PlanItemBinding
import com.example.uptodo.models.Plan


class PlanAdapter(
    private val context: Context,
    private val list: MutableList<Plan>,
    private val checkboxStateChangedListener: OnCheckboxStateChangedListener
) : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    interface OnCheckboxStateChangedListener {
        fun onCheckStateChanged(position: Int,isChecked:Boolean)
    }

    class ViewHolder(val binding: PlanItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(plan: Plan, position: Int,checkboxStateChangedListener: OnCheckboxStateChangedListener) {
            binding.planState.isChecked = plan.isCompleted
            binding.planState.text = plan.planDescription
            binding.planState.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(checkbox: CompoundButton?, isChecked: Boolean) {
                    checkboxStateChangedListener.onCheckStateChanged(position,isChecked)
                    Log.d("PlanAdapter",plan.toString())
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val planItemBinding = PlanItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(planItemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = list[position]
        holder.bind(plan,position,checkboxStateChangedListener)

//        holder.binding.planState.startAnimation(
//            AnimationUtils.loadAnimation(
//                context,
//                R.anim.item_slide_in
//            )
//        )
    }
}