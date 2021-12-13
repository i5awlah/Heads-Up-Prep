package com.example.headsupprep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.headsupprep.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var celebrityRV: RecyclerView
    lateinit var celebrityAdapter: CelebrityAdapter

    var celebrities = arrayListOf<CelebrityItem>()
    private var apiInterface: APIInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchData()

        binding.btnAddCelebrity.setOnClickListener {
            openAddCelebrityActivity()
        }
        binding.btnSubmit.setOnClickListener {
            val name = binding.etCelebrityName.text.toString()
            for (celebrity in celebrities) {
                if(celebrity.name == name)
                    openUpdateDeleteCelebrityActivity(celebrity.pk)
                else {
                    Toast.makeText(this, "Name not found",Toast.LENGTH_LONG).show()
                }
            }

        }
    }
    private fun fetchData() {
        apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.getCelebrity()?.enqueue(object: Callback<Celebrity>{
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {
                val data = response.body()!!
                for (i in 0 until data.size) {
                    val pk = data[i].pk
                    val name = data[i].name
                    val taboo1 = data[i].taboo1
                    val taboo2 = data[i].taboo2
                    val taboo3 = data[i].taboo3
                    val celebrityItem = CelebrityItem(pk, name, taboo1, taboo2, taboo3)
                    celebrities.add(celebrityItem)
                }
                celebrityAdapter.notifyDataSetChanged()
                Log.d("Main","Data fetched correctly!")
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {
                Log.d("Main","An error occurred while fetching data")
            }

        })
    }

    private fun setupRecyclerView() {
        celebrityRV = binding.rvCelebrity
        celebrityAdapter = CelebrityAdapter(celebrities)
        celebrityRV.adapter = celebrityAdapter
        celebrityRV.layoutManager = LinearLayoutManager(this)
    }
    private fun openAddCelebrityActivity() {
        val intent = Intent(this, AddCelebrityActivity::class.java)
        startActivity(intent)
    }
    private fun openUpdateDeleteCelebrityActivity(id: Int) {
        val intent = Intent(this, UpdateDeleteActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}