package com.denisenko.selectapiapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.denisenko.selectapiapp.databinding.FragmentImageBinding


class ImageFragment : Fragment() {

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        val view = binding.root
        imageView = binding.image


        setFragmentResultListener("IMAGE_FRAGMENT"){
            requestKey, bundle -> val url = bundle.getString("PHOTO_URL")
            Glide.with(requireContext())
                .load(url)
                .into(imageView)
        }

        imageView.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
        return view
    }
}