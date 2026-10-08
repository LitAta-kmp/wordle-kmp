package org.example.wordle.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.wordle.domain.LetterState

@Composable
fun LetterTile(
    letter: Char?,
    state: LetterState?,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (state) {
        LetterState.CORRECT -> Color(0xFF6AAA64)
        LetterState.PRESENT -> Color(0xFFC9B458)
        LetterState.ABSENT -> Color(0xFF787C7E)
        null -> Color.Transparent
    }
    val borderColor = when {
        state != null -> backgroundColor
        letter != null -> Color(0xFF565758)
        else -> Color(0xFF3A3A3C)
    }
    val textColor = if (state == null) MaterialTheme.colorScheme.onSurface else Color.White

    Box(
        modifier = modifier
            .size(52.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(6.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter?.toString() ?: "",
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}