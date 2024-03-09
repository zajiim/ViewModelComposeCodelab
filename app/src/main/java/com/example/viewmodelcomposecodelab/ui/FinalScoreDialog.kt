package com.example.viewmodelcomposecodelab.ui


import android.app.Activity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodelcomposecodelab.R

@Composable
fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val activity = (LocalContext.current as Activity)
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = {
            Text(
                text = stringResource(id = R.string.congratulations)
            )
        },
        text = { Text(text = stringResource(id = R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = { activity.finish() }) {
                Text(text = stringResource(id = R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(id = R.string.play_again))
            }
        },
    )
}

@Preview
@Composable
private fun AlertDialogPreview() {
    FinalScoreDialog(score = 45, onPlayAgain = { /*TODO*/ })
}