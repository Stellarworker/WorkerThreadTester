package com.stellarworker.workerthreadtester.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.stellarworker.workerthreadtester.R
import com.stellarworker.workerthreadtester.workerthread.WorkerThread

private const val WORKER_THREAD_LOG_TAG = "WORKER_THREAD"
private const val TEST_MESSAGE1 = "POST 1"
private const val TEST_MESSAGE2 = "POST 2"
private const val TEST_MESSAGE3 = "POST 3"
private const val TEST_DELAY2 = 2000L
private const val TEST_DELAY3 = 4000L

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
    }

    private fun test() {
        val workerThread = WorkerThread()
        workerThread.start()
        workerThread.post {
            Log.d(WORKER_THREAD_LOG_TAG, TEST_MESSAGE1)
        }
        workerThread.post(TEST_DELAY2) {
            Log.d(WORKER_THREAD_LOG_TAG, TEST_MESSAGE2)
        }
        workerThread.post(TEST_DELAY3) {
            Log.d(WORKER_THREAD_LOG_TAG, TEST_MESSAGE3)
        }
        workerThread.stop()
    }
}