package ro.pub.cs.systems.eim.practicaltest01var04

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class PracticalTest01Var04BroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action.orEmpty()
        val message = intent?.getStringExtra(PracticalTest01Var04Service.EXTRA_MESSAGE).orEmpty()
        Log.d(TAG, "action=$action message=$message")
    }

    private companion object {
        const val TAG = "PracticalTest01Var04"
    }
}
