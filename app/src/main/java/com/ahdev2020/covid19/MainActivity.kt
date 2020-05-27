package com.ahdev2020.covid19

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahdev2020.covid_19.R
import com.ahdev2020.covid_19.R.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.*


class MainActivity : AppCompatActivity() {
    val TAG="MainActivity"
    private val arrayList = ArrayList<States>()
    private var confirmed: TextView? = null
    private var recovered: TextView? = null
    private var death: TextView? = null
    private var active: TextView? = null
    private var increasedConfirmTV: TextView? = null
    private var increaseActiveTV: TextView? = null
    private var increaseRecoverTV: TextView? = null
    private var increaseDeathTV: TextView? = null
    private var lastUpdate: TextView? = null
    private var requestQueue: RequestQueue? = null
    private lateinit var  statesRV: RecyclerView
    private var confirmImageView: ImageView? = null
    private var deathImageView: ImageView? = null
    private var recoverImageView: ImageView? = null
    private var activeImageView: ImageView? = null
    private var lastDate = 0
    private var currentDate = 0
    private var increasedConfirmInt = 0
    private var increasedActiveInt = 0
    private var increasedDeathInt = 0
    private var increasedRecoveredInt = 0
    private var confirmInteger = 0
    private var deathInteger = 0
    private var recoveredInteger = 0
    private var activeInteger = 0
    private var currentMonth = 0
    private var lastMonth = 0
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "INDIA-भारत COVID-19 Tracker"
        setContentView(layout.activity_main)
        bindViews()
        setupSharedPrefs()
        JsonRequestMethod()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        Log.d(TAG,"Inside Menu")
        inflater.inflate(R.menu.my_menu, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.help -> {
            val viewIntent = Intent("android.intent.action.VIEW",
                    Uri.parse("mailto:ahdev2020@outlook.com?Subject=Help%20User"))
            startActivity(viewIntent)
            true
        }

        R.id.privacyPolicy -> {
            val viewIntent = Intent("android.intent.action.VIEW",
                    Uri.parse("https://c19tracker.blogspot.com/2020/05/covid-19-tracker-privacy-policy.html?m=1"))
            startActivity(viewIntent)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupSharedPrefs() {
        sharedPreferences = applicationContext.getSharedPreferences(AppConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun bindViews() {
        confirmed = findViewById(id.confirmedTextView)
        recovered = findViewById(id.recoveredTextView)
        death = findViewById(id.deathTextView)
        active = findViewById(id.activeTextView)
        increasedConfirmTV = findViewById(id.increaseConfirmTV)
        increaseActiveTV = findViewById(id.increaseActiveTV)
        increaseDeathTV = findViewById(id.increaseDeathTV)
        increaseRecoverTV = findViewById(id.increaseRecoverTV)
        confirmImageView = findViewById(id.confirmImageView)
        deathImageView = findViewById(id.deathImageView)
        activeImageView = findViewById(id.activeImageView)
        recoverImageView = findViewById(id.recoverImageView)
        lastUpdate = findViewById(id.lastUpdate)
        statesRV = findViewById(id.statesRV)
        statesRV.setLayoutManager(LinearLayoutManager(this))
        requestQueue = Volley.newRequestQueue(this)
    }

    private fun JsonRequestMethod() {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
                "https://api.covid19india.org/data.json",
                null, Response.Listener { response ->
            try {
                val stateWiseData = response.getJSONArray("statewise")
                val todayAndTotalDataJsonObject = stateWiseData.getJSONObject(0)
                val totalDeaths = todayAndTotalDataJsonObject.getString("deaths")
                val totalRecover = todayAndTotalDataJsonObject.getString("recovered")
                val totalConfirmed = todayAndTotalDataJsonObject.getString("confirmed")
                val totalActive = todayAndTotalDataJsonObject.getString("active")
                val dateHeader = todayAndTotalDataJsonObject.getString("lastupdatedtime").substring(0, 16)
                confirmed!!.text = totalConfirmed
                recovered!!.text = totalRecover
                death!!.text = totalDeaths
                active!!.text = totalActive
                lastUpdate!!.text = "Recently Updated on  " + dateHeader.substring(0, 5) + " " + dateHeader.substring(11, 16)
                val confirmSharedPref = sharedPreferences!!.getString(AppConstant.CONFIRMED, null)
                val activeSharedPref = sharedPreferences!!.getString(AppConstant.ACTIVE, null)
                val deathSharedPref = sharedPreferences!!.getString(AppConstant.DEATH, null)
                val recoverSharedPref = sharedPreferences!!.getString(AppConstant.RECOVERED, null)
                val dateSharedPref = sharedPreferences!!.getString(AppConstant.UPDATED_DATE, null)
                val monthSharedPref = sharedPreferences!!.getString(AppConstant.UPDATED_MONTH, null)
                if (confirmSharedPref == null || activeSharedPref == null || deathSharedPref == null || recoverSharedPref == null || dateSharedPref == null || monthSharedPref == null || currentDate > lastDate || currentMonth > lastMonth) {
                    setData(totalConfirmed, totalDeaths, totalRecover, totalActive, dateHeader.substring(0, 2), dateHeader.substring(3, 5))
                } else {
                    increasedConfirmInt = confirmSharedPref.toInt()
                    increasedActiveInt = activeSharedPref.toInt()
                    increasedDeathInt = deathSharedPref.toInt()
                    increasedRecoveredInt = recoverSharedPref.toInt()
                    lastDate = dateSharedPref.toInt()
                    lastMonth = monthSharedPref.toInt()
                    confirmInteger = totalConfirmed.toInt()
                    deathInteger = totalDeaths.toInt()
                    recoveredInteger = totalRecover.toInt()
                    activeInteger = totalActive.toInt()
                    currentDate = dateHeader.substring(0, 2).toInt()
                    currentMonth = dateHeader.substring(3, 5).toInt()
                }
                if (recoveredInteger >= increasedRecoveredInt) {
                    increaseRecoverTV!!.text = (recoveredInteger - increasedRecoveredInt).toString()
                    recoverImageView!!.setImageResource(drawable.ic_arrow_upward1)
                    DrawableCompat.setTint(recoverImageView!!.drawable, ContextCompat.getColor(applicationContext, color.green))
                } else if (recoveredInteger < increasedRecoveredInt) {
                    increaseRecoverTV!!.text = (increasedRecoveredInt - recoveredInteger).toString()
                    recoverImageView!!.setImageResource(drawable.ic_arrow_downward1)
                    DrawableCompat.setTint(recoverImageView!!.drawable, ContextCompat.getColor(applicationContext, color.green))
                }
                if (confirmInteger >= increasedConfirmInt) {
                    increasedConfirmTV!!.text = (confirmInteger - increasedConfirmInt).toString()
                    confirmImageView!!.setImageResource(drawable.ic_arrow_upward2)
                    DrawableCompat.setTint(confirmImageView!!.drawable, ContextCompat.getColor(applicationContext, color.red))
                } else if (confirmInteger < increasedConfirmInt) {
                    confirmImageView!!.setImageResource(drawable.ic_arrow_downward)
                    increasedConfirmTV!!.text = (increasedConfirmInt - confirmInteger).toString()
                    DrawableCompat.setTint(confirmImageView!!.drawable, ContextCompat.getColor(applicationContext, color.red))
                }
                if (activeInteger >= increasedActiveInt) {
                    increaseActiveTV!!.text = (activeInteger - increasedActiveInt).toString()
                    activeImageView!!.setImageResource(drawable.ic_arrow_upward3)
                    DrawableCompat.setTint(activeImageView!!.drawable, ContextCompat.getColor(applicationContext, color.blue))
                } else if (activeInteger < increasedActiveInt) {
                    activeImageView!!.setImageResource(drawable.ic_arrow_downward)
                    increaseActiveTV!!.text = (increasedActiveInt - activeInteger).toString()
                    DrawableCompat.setTint(activeImageView!!.drawable, ContextCompat.getColor(applicationContext, color.blue))
                }
                if (deathInteger >= increasedDeathInt) {
                    increaseDeathTV!!.text = (deathInteger - increasedDeathInt).toString()
                    deathImageView!!.setImageResource(drawable.ic_arrow_upward)
                    DrawableCompat.setTint(deathImageView!!.drawable, ContextCompat.getColor(applicationContext, color.gray))
                } else if (deathInteger < increasedDeathInt) {
                    deathImageView!!.setImageResource(drawable.ic_arrow_downward)
                    increaseDeathTV!!.text = (increasedDeathInt - deathInteger).toString()
                    DrawableCompat.setTint(deathImageView!!.drawable, ContextCompat.getColor(applicationContext, color.gray))
                }
                for (i in 1 until stateWiseData.length()) {
                    val stateName = stateWiseData.getJSONObject(i)
                    val state = stateName.getString("state")
                    val confirm = stateName.getString("confirmed")
                    val active = stateName.getString("active")
                    val recover = stateName.getString("recovered")
                    val death = stateName.getString("deaths")
                    val states = States(state, confirm, active, recover, death)
                    arrayList.add(states)
                }
                val recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity, arrayList)
                statesRV.adapter = recyclerViewAdapter
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { })
        requestQueue!!.add(jsonObjectRequest)
    }

    private fun setData(totalConfirmed: String, totalDeaths: String, totalRecover: String, totalActive: String, dateSubstring: String, monthSubstring: String) {
        val editor1 = sharedPreferences!!.edit()
        editor1.putString(AppConstant.CONFIRMED, totalConfirmed)
        editor1.putString(AppConstant.DEATH, totalDeaths)
        editor1.putString(AppConstant.RECOVERED, totalRecover)
        editor1.putString(AppConstant.ACTIVE, totalActive)
        editor1.putString(AppConstant.UPDATED_DATE, dateSubstring)
        editor1.putString(AppConstant.UPDATED_MONTH, monthSubstring)
        editor1.commit()
    }

    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
            ActivityCompat.finishAffinity(this)
            System.exit(0)
        } else {
            Toast.makeText(baseContext, "Press once again to exit", Toast.LENGTH_SHORT).show()
            back_pressed = System.currentTimeMillis()
        }
    }

    companion object {
        private var back_pressed: Long = 0
    }
}


