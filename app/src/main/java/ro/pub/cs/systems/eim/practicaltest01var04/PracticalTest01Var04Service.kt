package ro.pub.cs.systems.eim.practicaltest01var04

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper

class PracticalTest01Var04Service : Service() {
    private val handler = Handler(Looper.getMainLooper())
    private var firstText = ""
    private var secondText = ""
    private var sendFirst = true

    private val broadcaster = object : Runnable {
        override fun run() {
            val action = if (sendFirst) ACTION_FIRST_TEXT else ACTION_SECOND_TEXT
            val text = if (sendFirst) firstText else secondText
            sendBroadcast(Intent(action).setPackage(packageName).putExtra(EXTRA_MESSAGE, text))
            sendFirst = !sendFirst
            handler.postDelayed(this, INTERVAL_MILLIS)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.removeCallbacks(broadcaster)
        firstText = intent?.getStringExtra(EXTRA_FIRST_TEXT).orEmpty()
        secondText = intent?.getStringExtra(EXTRA_SECOND_TEXT).orEmpty()
        sendFirst = true
        handler.post(broadcaster)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        handler.removeCallbacks(broadcaster)
        super.onDestroy()
    }

    companion object {
        const val EXTRA_FIRST_TEXT = "ro.pub.cs.systems.eim.practicaltest01var04.EXTRA_FIRST_TEXT"
        const val EXTRA_SECOND_TEXT = "ro.pub.cs.systems.eim.practicaltest01var04.EXTRA_SECOND_TEXT"
        const val EXTRA_MESSAGE = "ro.pub.cs.systems.eim.practicaltest01var04.EXTRA_MESSAGE"
        const val ACTION_FIRST_TEXT = "ro.pub.cs.systems.eim.practicaltest01var04.action.FIRST_TEXT"
        const val ACTION_SECOND_TEXT = "ro.pub.cs.systems.eim.practicaltest01var04.action.SECOND_TEXT"
        private const val INTERVAL_MILLIS = 5000L
    }
}
