package com.imranmelikov.futureintern_ad_02.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imranmelikov.futureintern_ad_02.R
import com.imranmelikov.futureintern_ad_02.databinding.FragmentUploadBinding

class UploadFragment : Fragment() {
  private lateinit var binding:FragmentUploadBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentUploadBinding.inflate(inflater,container,false)

        return binding.root
    }
}