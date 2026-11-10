package ro.pub.cs.systems.eim.practicaltest01var04

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ro.pub.cs.systems.eim.practicaltest01var04.ui.theme.PracticalTest01Var04Theme

class PracticalTest01Var04SecondaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val values = intent.getStringExtra(EXTRA_VALUES).orEmpty()
        setContent {
            PracticalTest01Var04Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SecondaryScreen(
                        values = values,
                        onOk = { finishWithResult(Activity.RESULT_OK) },
                        onCancel = { finishWithResult(Activity.RESULT_CANCELED) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun finishWithResult(resultCode: Int) {
        setResult(resultCode)
        finish()
    }

    companion object {
        const val EXTRA_VALUES = "ro.pub.cs.systems.eim.practicaltest01var04.EXTRA_VALUES"
    }
}

@Composable
fun SecondaryScreen(
    values: String,
    onOk: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = values,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onOk) {
                Text(text = stringResource(R.string.ok))
            }
            Button(onClick = onCancel) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    }
}
