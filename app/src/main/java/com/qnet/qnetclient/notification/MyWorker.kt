package com.qnet.qnetclient.notification

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(appContext: Context, workParams: WorkerParameters): Worker(appContext, workParams) {
    override fun doWork(): ListenableWorker.Result {
        Log.d("notifications", "Performing long running task in scheduled job")
        return ListenableWorker.Result.success()
    }
}