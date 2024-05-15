package com.harbourspace.unsplash.ui.login

import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harbourspace.unsplash.R

@Composable
fun AuthenticationDialog(
    message: String?,
    dismissAction: () -> Unit,
    confirmAction: () -> Unit
) {
    
    AlertDialog(
        onDismissRequest = { dismissAction() },
        confirmButton = { 
            Button(onClick = { confirmAction() }) {
                Text(text = stringResource(id = R.string.dialog_auth_retry))
            }
        },
        dismissButton = {
            Button(onClick = { dismissAction() }) {
                Text(text = stringResource(id = R.string.dialog_auth_cancel))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.dialog_auth_title))
        },
        text = {
            Text(text = message ?: "")
        }
    )
}