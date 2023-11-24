package com.example.uptodo.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.util.Pair
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uptodo.R
import com.example.uptodo.adapters.PlanAdapter
import com.example.uptodo.databinding.CommonToolbarBinding
import com.example.uptodo.databinding.FragmentEditGoalBinding
import com.example.uptodo.databinding.PlanBottomSheetBinding
import com.example.uptodo.models.Plan
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale


class EditGoalFragment : Fragment() {

    private var _binding: FragmentEditGoalBinding? = null
    private lateinit var toolbarBinding: CommonToolbarBinding
    private val binding get() = _binding!!
    private var plans: MutableList<Plan> = mutableListOf()
    private lateinit var adapter: PlanAdapter
    private var progressValue: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditGoalBinding.inflate(inflater, container, false)
        val view = binding.root
        toolbarBinding = binding.tbIncludedLayout

//         toolbar = binding.tbIncludedLayout.tbMain

        (activity as AppCompatActivity).setSupportActionBar(toolbarBinding.tbMain)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbarDimen()
        initializeRecyclerView(view.context)

        toggleVisibility()

        binding.ibAddPlan.setOnClickListener {
            showPlanDialog()
        }
        binding.btnPeriod.setOnClickListener {
            showDatePickerDialog()
        }
        binding.etGoalTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(characters: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvGoalTitleInsideCard.text = characters.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        toolbarBinding.ivDisplayPic.setOnClickListener{
            showPopUp(it)
        }
    }

    private fun showPopUp(view:View){
        val popUpMenu = PopupMenu(view.context,view)
        popUpMenu.inflate(R.menu.edit_goal_menu)
        popUpMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.resest_data -> {
                    Toast.makeText(view.context, "reset clicked", Toast.LENGTH_SHORT).show()
                }

                R.id.save_data -> {
                    Toast.makeText(view.context, "save  clicked", Toast.LENGTH_SHORT).show()
                }

            }

            true

        }
        popUpMenu.show()


    }

    private fun handleItemSwipeAndDrags(dragFlags: Int, swipeFlags: Int) {

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(dragFlags, swipeFlags) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val initialPosition = viewHolder.adapterPosition
                val finalPosition = target.adapterPosition
                Collections.swap(plans, initialPosition, finalPosition)
                adapter.notifyItemMoved(initialPosition, finalPosition)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val plan = plans[viewHolder.adapterPosition]
                val position = viewHolder.adapterPosition
                plans.removeAt(position)
                adapter.notifyItemRemoved(position)
                toggleVisibility()
                toggleProgressBarData()
                val snackbar = Snackbar.make(
                    binding.editFragmentLayout,
                    R.string.undo_deleted,
                    Snackbar.LENGTH_LONG
                )
                snackbar.setAction("Undo") {
                    plans.add(position, plan)
                    adapter.notifyItemInserted(position)
                    toggleVisibility()
                    toggleProgressBarData()
                    snackbar.dismiss()
                }
                snackbar.show()

            }

        }).attachToRecyclerView(binding.rvPlan)
    }

    private fun toggleVisibility() {
        if (adapter.itemCount == 0) {
            binding.tvPlanSuggestion.visibility = View.VISIBLE
            return
        }
        binding.tvPlanSuggestion.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPaused fragment")
    }

    override fun onStop() {
        super.onStop()
//        Log.d(TAG,"onStop fragment")

    }

    private fun showDatePickerDialog() {
        val dateRangePicker: MaterialDatePicker<Pair<Long, Long>> =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select Goal Time Period")
                .build()

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val startDate: Long = selection.first
            val endDate: Long = selection.second

            val simpleDateFormat: SimpleDateFormat =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val startDateString = simpleDateFormat.format(
                Date(startDate)
            )
            val endDateString = simpleDateFormat.format(
                Date(endDate)
            )
            val dateString = "$startDateString-$endDateString"

            binding.tvPeriod.text = dateString

        }

        dateRangePicker.show(parentFragmentManager,"DATE_PICKER")


    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.edit_goal_menu,menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }


    private fun initializeRecyclerView(context: Context) {
        adapter = PlanAdapter(
            context,
            plans,
            object : PlanAdapter.OnCheckboxStateChangedListener {
                override fun onCheckStateChanged(position: Int, isChecked: Boolean) {
                    plans[position].isCompleted = isChecked
                    toggleProgressBarData()
                }
            }
        )
        binding.rvPlan.adapter = adapter
        binding.rvPlan.layoutManager = LinearLayoutManager(context)
        binding.rvPlan.setHasFixedSize(false)
        val dividerItemDecoration =
            MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL)
        binding.rvPlan.addItemDecoration(dividerItemDecoration)

        val dragFlags =
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END
        val swipeFlags = ItemTouchHelper.RIGHT
        handleItemSwipeAndDrags(dragFlags, swipeFlags)
    }

    private fun toggleProgressBarData() {
        binding.pbTaskCompletedPercent.max = adapter.itemCount
        val count = plans.count {
            it.isCompleted == true
        }
        binding.pbTaskCompletedPercent.progress = count

    }

    private fun subtractProgressBarData() {
        val count = plans.count {
            it.isCompleted == true
        }
        binding.pbTaskCompletedPercent.progress = --progressValue
    }

    private fun showPlanDialog() {


        val dialogBinding = PlanBottomSheetBinding.inflate(layoutInflater)


        val dialogBuilder = MaterialAlertDialogBuilder(requireView().context)
            .setView(dialogBinding.root)

        dialogBuilder.setNegativeButton("No") { dialog, which ->
            Log.d(TAG, "no clicked")
            dialog.dismiss()
        }
        dialogBuilder.setPositiveButton("Yes") { dialog, which ->

//                dialogBinding.tilPlanDescription.error = "cannot be empty"


        }
        val alertDialog = dialogBuilder.create()

//        alertDialog.setOnShowListener(object:DialogInterface.OnShowListener{
//            override fun onShow(dialog: DialogInterface?) {
//                val yesBtn : Button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
//                yesBtn.setOnClickListener {
//                    if(dialogBinding.etPlanDescription.text.toString().isEmpty()){
//                        Log.d(TAG,"empty")
//                    }
//                }
//            }
//        })

        alertDialog.show()
        val yesBtn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        yesBtn.setOnClickListener {
            Log.d(TAG, "clicked yes")
            val isPlanDone = dialogBinding.cbPlanDone.isChecked
            val planDescription = dialogBinding.etPlanDescription.text.toString()


            if (planDescription.isNotEmpty()) {
                val plan = Plan(
                    planDescription = planDescription,
                    isCompleted = isPlanDone
                )
                plans.add(plan)
                adapter.notifyItemInserted(plans.size)
                binding.tvPlanSuggestion.visibility = View.GONE
                alertDialog.dismiss()
                toggleProgressBarData()
                return@setOnClickListener
            }
            dialogBinding.tilPlanDescription.error = "Description cannot be empty"
        }
    }

    private fun createList(): MutableList<Plan> {
        val list = mutableListOf<Plan>()
        for (i in 1..100) {
            list.add(
                Plan(
                    "some description $i",
                    i % 2 == 0
                )
            )
        }
        return list
    }

    private fun initializeToolbarDimen() {
        binding.tbIncludedLayout.tvHeading.text = "New Goal"
        binding.tbIncludedLayout.tvSubHeading.text = "create a new goal and track your progress"
        binding.tbIncludedLayout.tvSubHeading.textSize = 12f
        binding.tbIncludedLayout.tvHeading.textSize = 16f
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressedDispatcher?.onBackPressed()
                Log.d(TAG, "home clicked")
                return true
            }
        }


        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "EditGoalFragment"
    }


}