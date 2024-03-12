package com.poke.montty.visual.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.poke.montty.visual.ui.theme.PokeSetUp
import com.poke.montty.visual.ui.theme.PokeTheme

@Preview
@Composable
fun PokeToolbarPreview(
    @PreviewParameter(ThemeProvider::class) theme: PokeTheme
) {
    PokeSetUp.setPokeTheme(theme)
    PokeTheme {
        Scaffold(
            topBar = {
                PokeToolbar()
            }
        ) {

            LazyColumn(modifier = Modifier.padding(it)) {
                item {
                    Text(text = theme.name)
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeToolbar(
    title: String = "",
) {
    TopAppBar(
        modifier = Modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            //containerColor = PokemonTheme.colors.primary,
            containerColor = Color.White,
        ),
        title = {
            Text(title)
        }
    )
}
