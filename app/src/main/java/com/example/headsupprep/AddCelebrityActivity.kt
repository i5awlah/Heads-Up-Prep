package com.example.headsupprep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.headsupprep.databinding.ActivityAddCelebrityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCelebrityActivity : AppCompatActivity() {
    private var apiInterface: APIInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddCelebrityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val taboo1 = binding.etTaboo1.text.toString()
            val taboo2 = binding.etTaboo2.text.toString()
            val taboo3 = binding.etTaboo3.text.toString()

            if (name.isNotEmpty() && taboo1.isNotEmpty() && taboo2.isNotEmpty() && taboo3.isNotEmpty() ) {
                val newCelebrity = CelebrityItem(0, name, taboo1, taboo2, taboo3)
                addCelebrity(newCelebrity)
            } else {
                Toast.makeText(this,"Please Enter all Fields", Toast.LENGTH_LONG).show()
            }



        }

        binding.btnBack.setOnClickListener {
            openMainActivity()
        }
    }
    private fun addCelebrity(newCelebrity: CelebrityItem) {
        apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.addCelebrity(newCelebrity)?.enqueue(object: Callback<CelebrityItem> {
            override fun onResponse(call: Call<CelebrityItem>, response: Response<CelebrityItem>) {
                Toast.makeText(this@AddCelebrityActivity,"Added successfully",Toast.LENGTH_LONG).show()
                openMainActivity()
            }

            override fun onFailure(call: Call<CelebrityItem>, t: Throwable) {
                Log.d("Main","Error: $t")
            }

        })
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}