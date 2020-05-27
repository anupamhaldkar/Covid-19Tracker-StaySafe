package com.ahdev2020.covid19

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ahdev2020.covid_19.R
import java.util.*

class StatesActivity : AppCompatActivity() {
    var stateNameTextView: TextView? = null
    var statesConfirmTextView: TextView? = null
    var statesRecoveredTextView: TextView? = null
    var statesDeathTextView: TextView? = null
    private lateinit var districtRV: RecyclerView
    var stateName: String? = null
    private var requestQueue: RequestQueue? = null
    private val arrayList = ArrayList<States>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "COVID-19 Tracker - District wise"
        setContentView(R.layout.activity_states)
        bindViews()
        intentData
        setJsonData()
    }

    private fun setJsonData() {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
                "https://api.covid19india.org/state_district_wise.json",
                null, Response.Listener { response ->
            try {
                val state = response.getJSONObject(stateName)
                val districtData = state.getJSONObject("districtData")
                val keys = districtData.names()
                for (i in 0 until keys.length()) {
                    val district = keys.getString(i)
                    val confirmed = districtData.getJSONObject(district).getString("confirmed")
                    val active = districtData.getJSONObject(district).getString("active")
                    val recovered = districtData.getJSONObject(district).getString("recovered")
                    val death = districtData.getJSONObject(district).getString("deceased")
                    val states = States(district, confirmed, active, recovered, death)
                    arrayList.add(states)
                }
                val districtAdapter = DistrictAdapter(applicationContext, arrayList)
                districtRV.adapter = districtAdapter
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { })
        requestQueue!!.add(jsonObjectRequest)
    }

    private val intentData: Unit
        get() {
            stateName = intent.getStringExtra("state")
            stateNameTextView!!.text = stateName
            statesConfirmTextView!!.text = intent.getStringExtra("confirm")
            statesRecoveredTextView!!.text = intent.getStringExtra("recovered")
            statesDeathTextView!!.text = intent.getStringExtra("death")
        }

    private fun bindViews() {
        stateNameTextView = findViewById(R.id.stateNameTextView)
        statesConfirmTextView = findViewById(R.id.statesConfirmedTextView)
        statesRecoveredTextView = findViewById(R.id.statesRecoveredTextView)
        statesDeathTextView = findViewById(R.id.statesDeathTextView)
        districtRV = findViewById(R.id.districtRV)
        districtRV.setLayoutManager(LinearLayoutManager(this))
        requestQueue = Volley.newRequestQueue(this)
    }
}