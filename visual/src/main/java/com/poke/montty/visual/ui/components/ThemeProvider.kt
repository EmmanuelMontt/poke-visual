package com.poke.montty.visual.ui.components

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.poke.montty.visual.ui.theme.PokeTheme

class ThemeProvider : PreviewParameterProvider<PokeTheme> {
    override val values: Sequence<PokeTheme>
        get() = sequenceOf(
            PokeTheme.PIKACHU,
            PokeTheme.BULBASAUR,
            PokeTheme.SQUIRTLE
        )

}