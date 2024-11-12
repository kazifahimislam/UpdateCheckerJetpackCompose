package com.example.learningcompose




import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.learningcompose.ui.theme.LearningComposeTheme
import com.example.learningcompose.update.ShowUpdateDialog
import com.example.learningcompose.update.UpdateChecker
import com.example.learningcompose.update.UpdateChecker.checkForUpdates
import com.google.firebase.database.*

class MainActivity : ComponentActivity() {

    private var showDialog by mutableStateOf(false)
    private var apkUrl: String? by mutableStateOf(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            LearningComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Greeting(name = "Fahim")
                        Layout()

                        // Show the update dialog if needed
                        if (showDialog && apkUrl != null) {
                            ShowUpdateDialog(
                                onDismiss = { showDialog = false },
                                onUpdate = { apkUrl?.let { downloadAndInstallApk(it) } }
                            )
                        }
                    }
                }
            }
        }

        // Check for updates when the activity is created
        checkForUpdates()
    }

    // Check for updates and show the dialog if an update is available
    private fun checkForUpdates() {
        val context = this

        UpdateChecker.checkForUpdates(context) { url ->
            apkUrl = url
            showDialog = true
        }
    }

    private fun downloadAndInstallApk(apkUrl: String) {
        // Launch the browser to open the APK download URL
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(apkUrl))
        startActivity(intent)
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Composable
    fun Layout() {
        Column {
            for (i in 1..10) {
                Text(text = "This is a for loop item $i")
            }
        }
    }
}
