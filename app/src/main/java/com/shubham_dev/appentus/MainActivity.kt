package com.shubham_dev.appentus

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.shubham_dev.appentus.API.ApiClient
import com.shubham_dev.appentus.API.ApiInterface
import com.shubham_dev.appentus.Model.ResponseModel
import com.shubham_dev.appentus.adapters.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var layoutmanger: GridLayoutManager
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var pbar: ProgressBar
    val list: ArrayList<ResponseModel> = ArrayList()
    var loading = true
    var pageNo=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Places"
        shimmerFrameLayout = findViewById(R.id.shimmerLayout)
        pbar=findViewById(R.id.pbar)
        shimmerFrameLayout.startShimmer()
        Handler(Looper.getMainLooper()).postDelayed({ apiCall(pageNo) }, 2000)
        recyclerView = findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter(this)
        layoutmanger = GridLayoutManager(this, 2)
        recyclerView.layoutManager=layoutmanger
        recyclerView.adapter = recyclerAdapter
        recyclerAdapter.setListItems(list)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutmanger.childCount
                val totalItemCount = layoutmanger.itemCount
                val firstVisibleItemPosition = layoutmanger.findFirstVisibleItemPosition()

                if (!loading) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        pbar.visibility = View.VISIBLE
                        pageNo++
                        loading=true
                        apiCall(pageNo)
                    }
                }
            }
        })

    }

    private fun apiCall(pageno: Int) {
        var apiInterface=ApiClient.client.create(ApiInterface::class.java)
        apiInterface.getPhotos(pageno).enqueue(object : Callback<List<ResponseModel>> {
            override fun onResponse(
                call: Call<List<ResponseModel>>?,
                response: Response<List<ResponseModel>>?
            ) {

                if (response?.body() != null) {
                    loading = false
                    pbar.visibility = View.GONE
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    list.addAll(response.body()!!)
                    recyclerAdapter.notifyDataSetChanged()
                }
            }


            override fun onFailure(call: Call<List<ResponseModel>>, t: Throwable) {
                Toast.makeText(applicationContext, "Internet Not Working", Toast.LENGTH_LONG).show()
            }
        })
    }
}