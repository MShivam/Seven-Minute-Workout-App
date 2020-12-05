package com.example.sevenminuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_b_m_i.*
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_historyActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "History"
        }
        toolbar_historyActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllCompletedDates()
    }

    private fun getAllCompletedDates() {
        val dbHandler = SqliteOpenHelper(this, null)
        val allCompletedDatesList = dbHandler.getAllCompletedDateList()

        if(allCompletedDatesList.size>0){
            textView_History.visibility = View.VISIBLE
            recyclerView_History.visibility = View.VISIBLE
            textView_noDataAvailable.visibility = View.GONE

            recyclerView_History.layoutManager = LinearLayoutManager(this)

            val historyAdapter = HistoryAdapter(this, allCompletedDatesList)
            recyclerView_History.adapter = historyAdapter
        } else{
            textView_History.visibility = View.GONE
            recyclerView_History.visibility = View.GONE
            textView_noDataAvailable.visibility = View.VISIBLE
        }
    }
}