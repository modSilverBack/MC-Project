package com.example.mc_project.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LanguageSelector(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    val languages = listOf(
        "en" to "English",
        "hi" to "हिंदी",
        "bn" to "বাংলা",
        "mr" to "मराठी",
        "ur" to "اردو",
        "gu" to "ગુજરાતી",
        "ja" to "日本語",
        "ko" to "한국어",
        "ru" to "Русский",
    )

    Column {
        Text("Choose App Language")
        languages.forEach { (code, name) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLanguageSelected(code) }
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = selectedLanguage == code,
                    onClick = { onLanguageSelected(code) }
                )
                Text(text = name, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}
