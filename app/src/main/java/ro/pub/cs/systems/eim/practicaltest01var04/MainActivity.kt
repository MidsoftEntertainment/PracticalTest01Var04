package ro.pub.cs.systems.eim.practicaltest01var04

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ro.pub.cs.systems.eim.practicaltest01var04.ui.theme.PracticalTest01Var04Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticalTest01Var04Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.remove(VIEW_HIERARCHY_STATE_KEY)
    }

    private companion object {
        const val VIEW_HIERARCHY_STATE_KEY = "android:viewHierarchyState"
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val emptyFieldError = stringResource(R.string.empty_field_error)
    val resultOk = stringResource(R.string.result_ok)
    val resultCanceled = stringResource(R.string.result_canceled)

    val secondaryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        val message = if (activityResult.resultCode == Activity.RESULT_OK) resultOk else resultCanceled
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    var firstChecked by rememberSaveable(key = "first_checked") { mutableStateOf(false) }
    var secondChecked by rememberSaveable(key = "second_checked") { mutableStateOf(false) }
    var firstText by rememberSaveable(key = "first_text") { mutableStateOf("") }
    var secondText by rememberSaveable(key = "second_text") { mutableStateOf("") }
    var result by rememberSaveable(key = "result") { mutableStateOf("") }

    fun selectedText(): String {
        val parts = buildList {
            if (firstChecked) add(firstText)
            if (secondChecked) add(secondText)
        }
        return parts.joinToString(separator = "\n")
    }

    fun validateInput(): Boolean {
        val hasEmptyChecked = (firstChecked && firstText.isEmpty()) ||
            (secondChecked && secondText.isEmpty())
        if (hasEmptyChecked) {
            Toast.makeText(context, emptyFieldError, Toast.LENGTH_SHORT).show()
        }
        return !hasEmptyChecked
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    if (!validateInput()) return@Button
                    val intent = Intent(context, PracticalTest01Var04SecondaryActivity::class.java).apply {
                        putExtra(PracticalTest01Var04SecondaryActivity.EXTRA_VALUES, selectedText())
                    }
                    secondaryLauncher.launch(intent)
                }
            ) {
                Text(text = stringResource(R.string.transfer))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CheckedField(
            checked = firstChecked,
            onCheckedChange = { firstChecked = it },
            text = firstText,
            onTextChange = { firstText = it },
            label = stringResource(R.string.first_field)
        )
        CheckedField(
            checked = secondChecked,
            onCheckedChange = { secondChecked = it },
            text = secondText,
            onTextChange = { secondText = it },
            label = stringResource(R.string.second_field)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (validateInput()) {
                    result = selectedText()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.show))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = result,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun CheckedField(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text(text = label) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PracticalTest01Var04Theme {
        MainScreen()
    }
}
