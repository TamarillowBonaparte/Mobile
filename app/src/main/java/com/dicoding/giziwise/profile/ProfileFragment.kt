package com.dicoding.giziwise.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.giziwase.R
import com.dicoding.giziwase.databinding.FragmentProfileBinding
import com.dicoding.giziwise.MainActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding as FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences =
            activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("name", "").toString()
        val height = sharedPreferences?.getString("height", "").toString()
        val gender = sharedPreferences?.getString("gender", "").toString()
        val date_birth = sharedPreferences?.getString("date_birth", "").toString()
        val email = sharedPreferences?.getString("email", "").toString()
        val weight = sharedPreferences?.getString("weight", "").toString()

        binding.tvNameValue.text = name
        binding.tvTinggiValue.text = height
        binding.tvGenderValue.text = gender
        binding.tvDateValue.text = date_birth
        binding.tvEmailValue.text = email
        binding.tvBeratValue.text = weight

        binding.btnLogout.setOnClickListener {
            val sharedPreferences =
                activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.clear()
            editor?.apply()

            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}