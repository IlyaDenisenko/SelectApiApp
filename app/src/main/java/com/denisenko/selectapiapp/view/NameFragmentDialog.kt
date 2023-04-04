package com.denisenko.selectapiapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denisenko.selectapiapp.NationalityNameAdapter
import com.denisenko.selectapiapp.NationalizeModel
import com.denisenko.selectapiapp.R
import com.denisenko.selectapiapp.retrofit.NationalizeApiServices
import com.denisenko.selectapiapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NameFragmentDialog : DialogFragment() {

    private lateinit var nationalizeApiService: NationalizeApiServices
    private lateinit var recyclerView: RecyclerView
    private lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nationalizeApiService = RetrofitClient.getNationalizeApi()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        alertDialog = AlertDialog.Builder(requireContext())
        val args = arguments
        val arrayNames = args?.getStringArray("NAMES_DATA")
        val view = activity?.layoutInflater?.inflate(R.layout.fragment_name_dialog, null)
        recyclerView = view?.findViewById(R.id.recycler_name)!!
        getRequestNationalizeNames(*arrayNames as Array<String>)

        alertDialog
            .setTitle("Nationality")
            .setView(view)
            .setPositiveButton("Ok", object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    dismiss()
                }
            })
        return alertDialog.create()
    }

    private fun initRecycler(names: Array<NationalizeModel>){
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = NationalityNameAdapter(names)
    }

    private fun getRequestNationalizeNames(vararg name: String){
        nationalizeApiService.getNationalizeArrayNames(*name).enqueue(object :
            Callback<Array<NationalizeModel>> {
            override fun onResponse(call: Call<Array<NationalizeModel>>, response: Response<Array<NationalizeModel>>) {
                if (response.isSuccessful){
                    val arrayNames: Array<NationalizeModel> = response.body()!!
                    initRecycler(arrayNames)
                }
                else{
                    Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Array<NationalizeModel>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}