package kg.study.roomstudy.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kg.study.roomstudy.R
import kg.study.roomstudy.model.User
import kg.study.roomstudy.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val TAG = "UpdateFragment"
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        Log.v(TAG, "onCreateView")
        view.et_firstName_update.setText(args.currentUser.firstName)
        view.et_lastName_update.setText(args.currentUser.lastName)
        view.et_age_update.setText(args.currentUser.age.toString())

        Log.w(TAG, "ViewModelProvider")
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.button_update.setOnClickListener {
            updateItem()
        }

        // add menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        Log.w(TAG, "updateItem")
        val firstName = et_firstName_update.text.toString()
        val lastName = et_lastName_update.text.toString()
        val age = Integer.parseInt(et_age_update.text.toString())

        if (inputCheck(lastName, firstName, et_age_update.text)) {
            // create User object
            val updatedUser = User(args.currentUser.id, firstName, lastName, age)
            // update current user
            userViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        Log.w(TAG, "inputCheck")
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.w(TAG, "onCreateOptionsMenu")
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.w(TAG, "onOptionsItemSelected")
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        Log.w(TAG, "deleteUser")
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            userViewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(),
                "Successfully removed ${args.currentUser.firstName}!",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ ->
        }
        builder.setTitle("Delete ${args.currentUser.firstName}")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
        builder.create().show()
    }


}