package com.example.mc_project.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mc_project.domain.model.TextSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingPreferencesScreen(
    viewModel: ReadingPreferencesViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val preferences by viewModel.readingPreferences.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Reading Preferences") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // Text Size Section
            Text(
                text = "Text Size",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextSize.values().forEach { textSize ->
                    val isSelected = preferences.textSize == textSize

                    OutlinedButton(
                        onClick = { viewModel.updateTextSize(textSize) },
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.outline
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected)
                                MaterialTheme.colorScheme.primaryContainer
                            else
                                MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(
                            text = when (textSize) {
                                TextSize.SMALL -> "Small"
                                TextSize.MEDIUM -> "Medium"
                                TextSize.LARGE -> "Large"
                                TextSize.EXTRA_LARGE -> "XL"
                            },
                            color = if (isSelected)
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Font Family Section
            Text(
                text = "Font Family",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Font options
            val fontOptions = listOf(
                "Default" to FontFamily.Default,
                "Serif" to FontFamily.Serif,
                "Sans Serif" to FontFamily.SansSerif,
                "Monospace" to FontFamily.Monospace
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                fontOptions.forEach { (name, fontFamily) ->
                    val isSelected = preferences.fontFamily == fontFamily

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.updateFontFamily(fontFamily) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { viewModel.updateFontFamily(fontFamily) }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = name,
                            fontFamily = fontFamily
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Preview Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Preview",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "This is how your article text will appear with the selected preferences.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize * preferences.textSize.scaleFactor,
                            fontFamily = preferences.fontFamily
                        )
                    )
                }
            }
        }
    }
}