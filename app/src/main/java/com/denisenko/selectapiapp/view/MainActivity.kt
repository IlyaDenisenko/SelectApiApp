package com.denisenko.selectapiapp.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.denisenko.selectapiapp.DogModel
import com.denisenko.selectapiapp.R
import com.denisenko.selectapiapp.databinding.ActivityMainBinding
import com.denisenko.selectapiapp.retrofit.DogApiServices
import com.denisenko.selectapiapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var settingsBut: ImageButton
    private lateinit var requestBut: Button
    private lateinit var nameApiText: TextView
    private lateinit var requestEditText: EditText
    private lateinit var dogImage: ImageView
    private lateinit var dogApiService: DogApiServices
    private lateinit var prefs: SharedPreferences
    val bundle: Bundle = Bundle()

    var urlImageDog = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        dogImage = binding.imageDog
        settingsBut = binding.settingButton
        requestBut = binding.sendRequest
        nameApiText = binding.nameApi
        requestEditText = binding.enterRequest
        dogApiService = RetrofitClient.getDogApi()
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        settingsBut.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        dogImage.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, ImageFragment())
                .commit()
            bundle.putString("PHOTO_URL", urlImageDog)
            supportFragmentManager.setFragmentResult("IMAGE_FRAGMENT", bundle)
        }
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        val currentApi = prefs.getString("reply", "Dog API")!!
        nameApiText.text = currentApi
        when (currentApi) {
            "Dog API"-> {
                requestEditText.text.clear()
                requestEditText.setHint(R.string.hint_for_dog_api)
                dogImage.visibility = View.VISIBLE
                requestBut.setOnClickListener {
                    if (requestEditText.text.isEmpty())
                        sendRequestRandomDog()
                    else
                        sendRequestBreedDog(requestEditText.text.toString())
                }
            }
            "Nationalize.io" -> {
                requestEditText.text.clear()
                requestEditText.setHint(R.string.hint_for_nationalize_api)
                dogImage.visibility = View.GONE
                requestBut.setOnClickListener {
                    if (requestEditText.text.isEmpty())
                        Toast.makeText(this@MainActivity, "The input field is empty", Toast.LENGTH_SHORT).show()
                    else{
                        val namesStr = requestEditText.text.toString()
                        val namesArray: Array<String> = namesStr.split(",").toTypedArray()
                        val namesArrayWithoutSpace: Array<String> = Array(namesArray.size){
                            namesArray[it].replace("\\s".toRegex(), "")
                        }
                        startNameFragmentDialog(namesArrayWithoutSpace)
                    }
                }
            }
            "Yours API" -> {
                requestEditText.text.clear()
                requestEditText.setHint(R.string.hint_for_your_api)
                dogImage.visibility = View.GONE
                requestBut.setOnClickListener {
                    nameApiText.text = requestEditText.text
                }
            }
        }
    }

    private fun sendRequestRandomDog() {
        dogApiService.getRandomDog().enqueue(object : Callback<DogModel> {
            override fun onResponse(call: Call<DogModel>, response: Response<DogModel>) {
                if (response.isSuccessful) {
                    Glide.with(this@MainActivity)
                        .load(response.body()?.message)
                        .into(dogImage)
                    urlImageDog = response.body()!!.message
                }
            }

            override fun onFailure(call: Call<DogModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendRequestBreedDog(breed: String) {
        dogApiService.getDogByBreed(breed).enqueue(object : Callback<DogModel> {
            override fun onResponse(call: Call<DogModel>, response: Response<DogModel>) {
                if (response.isSuccessful) {
                    Glide.with(this@MainActivity)
                        .load(response.body()?.message)
                        .into(dogImage)
                    urlImageDog = response.body()!!.message
                } else Toast.makeText(this@MainActivity, "Not found this breed", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<DogModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun startNameFragmentDialog(name: Array<String>) {
        val nameFragmentDialog = NameFragmentDialog()
        bundle.putStringArray("NAMES_DATA", name)
        nameFragmentDialog.arguments = bundle
        nameFragmentDialog.show(supportFragmentManager, "dialog")

    }

}