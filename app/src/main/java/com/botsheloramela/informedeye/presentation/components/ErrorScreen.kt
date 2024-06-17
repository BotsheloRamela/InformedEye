package com.botsheloramela.informedeye.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.botsheloramela.informedeye.R
import java.net.ConnectException
import java.net.SocketTimeoutException

private var ICON_NETWORK_ERROR = R.drawable.ic_network_error
private val ICON_NO_DOCS = R.drawable.ic_no_docs

@Composable
fun ErrorScreen(error: LoadState.Error? = null) {
    // Parse error message and set initial state
    var message by remember { mutableStateOf(parseErrorMessage(error)) }
    var icon by remember { mutableStateOf(if (error != null) ICON_NETWORK_ERROR else ICON_NO_DOCS) }
    var startAnimation by remember { mutableStateOf(true) } // Initialize based on error presence

    if (error == null){
        message = "You have not saved news so far!"
    }

    // Animate alpha value based on startAnimation flag
    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    // Display error content with animated alpha
    ErrorContent(alphaAnim = alphaAnimation, message = message, iconId = icon)
}

@Composable
fun ErrorContent(alphaAnim: Float, message: String, iconId: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .size(100.dp)
                .alpha(alphaAnim)
        )
        Text(
            text = message,
            modifier = Modifier
                .padding(10.dp)
                .alpha(alphaAnim),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

// Function to parse error messages remains unchanged
fun parseErrorMessage(error: LoadState.Error?): String {
    return when (error?.error) {
        is SocketTimeoutException -> "Server Unavailable."
        is ConnectException -> "Internet Unavailable."
        else -> "Unknown Error."
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ErrorScreenPreview() {
    ErrorContent(
        alphaAnim = 0.3f,
        message = "Internet Unavailable.",
        R.drawable.ic_network_error
    )
}