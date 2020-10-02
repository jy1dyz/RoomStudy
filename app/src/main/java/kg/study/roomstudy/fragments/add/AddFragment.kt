package kg.study.roomstudy.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kg.study.roomstudy.R
import kg.study.roomstudy.viewmodel.UserViewModel
import kg.study.roomstudy.model.User
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private val TAG = "AddFragment"

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        Log.v(TAG, "onCreateView")
        Log.v(TAG, "ViewModelProvider")
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.button.setOnClickListener {
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        Log.w(TAG, "insertDataToDatabase")
        val firstName = et_firstName.text.toString()
        val lastName = et_lastName.text.toString()
        val age = et_age.text

      if (inputCheck(firstName, lastName, age)) {
          // Create User object
          val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))
          // Add Data to Database
          userViewModel.addUser(user)
          Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
          //Navigate back
          findNavController().navigate(R.id.action_addFragment_to_listFragment)
      } else {
          Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show()
      }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable):Boolean {
        Log.w(TAG, "inputCheck")
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }


}