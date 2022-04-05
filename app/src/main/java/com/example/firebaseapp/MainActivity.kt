package com.example.firebaseapp

import android.app.Person
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaseapp.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    // creating database
    private val personCollectionRef = Firebase.firestore.collection("persons")

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // added data to firestore
        binding.btnSubmit.setOnClickListener {

            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            val age = binding.age.text.toString()

            val persons = Person(firstName, lastName, age)
            savePersons(persons)
        }


    }

    // function for adding data in firestore
    private fun savePersons(person: com.example.firebaseapp.Person) = CoroutineScope(Dispatchers.IO).launch {
        try {
            personCollectionRef.add(person)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Successfully added", Toast.LENGTH_SHORT).show()
            }

            }catch (e: Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@MainActivity, e.message , Toast.LENGTH_SHORT).show()
            }
        }

    }
}