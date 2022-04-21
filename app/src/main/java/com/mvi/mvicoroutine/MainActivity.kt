package com.mvi.mvicoroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mvi.mvicoroutine.ui.theme.MVICoroutineTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVICoroutineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MVIView(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun MVIView(viewModel: MainViewModel) {
    val state: MainViewModel.State by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { viewModel.sendEvent(MainViewModel.Event.Increment) }) {
            Text(stringResource(id = R.string.increment))
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            text = stringResource(id = R.string.value, state.counter),
            textAlign = TextAlign.Center
        )
        Button(onClick = { viewModel.sendEvent(MainViewModel.Event.Decrement) }) {
            Text(stringResource(id = R.string.decrement))
        }
        Button(onClick = { viewModel.sendEvent(MainViewModel.Event.DecrementIncrement) }) {
            Text(stringResource(id = R.string.postprocessor))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MVICoroutineTheme {
        MVIView(viewModel = MainViewModel())
    }
}