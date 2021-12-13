package com.example.headsupprep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.headsupprep.databinding.ActivityUpdateDeleteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDeleteActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateDeleteBinding
    private var apiInterface: APIInterface? = null
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        id = intent.extras?.getInt("id")!!
        fetchData(id)
        Log.d("Main","ID: $id")

        binding.btnUpdate.setOnClickListener {
            val name = binding.etName.text.toString()
            val taboo1 = binding.etTaboo1.text.toString()
            val taboo2 = binding.etTaboo2.text.toString()
            val taboo3 = binding.etTaboo3.text.toString()

            if (name.isNotEmpty() && taboo1.isNotEmpty() && taboo2.isNotEmpty() && taboo3.isNotEmpty() ) {
                val newCelebrity = CelebrityItem(id, name, taboo1, taboo2, taboo3)
                updateCelebrity(id, newCelebrity)
            } else {
                Toast.makeText(this@UpdateDeleteActivity,"Please Enter all Fields", Toast.LENGTH_LONG).show()
            }

        }

        binding.btnDelete.setOnClickListener {
            deleteCelebrity(id)
        }
        binding.btnBack.setOnClickListener {
            openMainActivity()
        }
    }

    private fun fetchData(id: Int) {
        apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.getCelebrity(id)?.enqueue(object: Callback<CelebrityItem>{
            override fun onResponse(call: Call<CelebrityItem>, response: Response<CelebrityItem>) {
                val celebrity = response.body()!!
                binding.etName.setText(celebrity.name)
                binding.etTaboo1.setText(celebrity.taboo1)
                binding.etTaboo2.setText(celebrity.taboo2)
                binding.etTaboo3.setText(celebrity.taboo3)
                Log.d("Main","Data fetched correctly!")
            }

            override fun onFailure(call: Call<CelebrityItem>, t: Throwable) {
                Log.d("Main","An error occurred while fetching data")
            }

        })
    }

    private fun updateCelebrity(id: Int, updatedCelebrity: CelebrityItem) {
        apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.updateCelebrity(id, updatedCelebrity)?.enqueue(object: Callback<CelebrityItem>{
            override fun onResponse(call: Call<CelebrityItem>, response: Response<CelebrityItem>) {
                Toast.makeText(this@UpdateDeleteActivity,"updated successfully", Toast.LENGTH_LONG).show()
                openMainActivity()
            }

            override fun onFailure(call: Call<CelebrityItem>, t: Throwable) {
                Log.d("Main","Error: $t")
            }

        })
    }
    private fun deleteCelebrity(id: Int) {
        apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.deleteCelebrity(id)?.enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@UpdateDeleteActivity,"deleted successfully", Toast.LENGTH_LONG).show()
                openMainActivity()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("Main","Error: $t")
            }

        })
    }
    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}