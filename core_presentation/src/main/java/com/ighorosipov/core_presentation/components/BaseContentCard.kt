package com.ighorosipov.core_presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette

@Composable
fun BaseContentCard(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = LocalCustomColorsPalette.current.secondaryContainer,
        contentColor = LocalCustomColorsPalette.current.primary
    ),
    title: String? = null,
    content: ContentCardScope.() -> Unit,
) {
    val scope = ContentCardScope().apply(content)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = colors
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 12.dp)
        ) {
            title?.let {
                Text(
                    text = title,
                    color = colors.contentColor,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            scope.items.forEachIndexed { index, item ->
                item()
                if (index < scope.items.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

class ContentCardScope {
    val items = mutableListOf<@Composable () -> Unit>()

    fun item(content: @Composable () -> Unit) {
        items += content
    }
}