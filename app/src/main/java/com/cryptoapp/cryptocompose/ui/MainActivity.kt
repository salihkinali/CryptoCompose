package com.cryptoapp.cryptocompose.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cryptoapp.cryptocompose.R
import com.cryptoapp.cryptocompose.ui.model.CoinUiModel
import com.cryptoapp.cryptocompose.ui.model.CoinUiState
import com.cryptoapp.cryptocompose.ui.theme.CryptoComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketList()
        }
    }
}

@Composable
fun MarketList(
    viewModel: CoinViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.getMarkets()
    }

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        when (uiState) {
            is CoinUiState.Error -> Log.e(
                "Error State",
                (uiState as CoinUiState.Error).errorMessage
            )

            CoinUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            is CoinUiState.Success -> {
                CoinListScreen(list = (uiState as CoinUiState.Success).list,viewModel)
            }

            else -> Unit
        }
    }

}

@Composable
fun CoinListScreen(list: List<CoinUiModel>,viewModel: CoinViewModel) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(top = 40.dp),
        columns = GridCells.Fixed(5)
    ) {
        itemsIndexed(list) { index, item ->
            CoinItem(item = item,viewModel,index)
        }
    }
}

@Composable
fun CoinItem(item: CoinUiModel,viewModel: CoinViewModel,index: Int) {

    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    LaunchedEffect(isPressed) {
        if (isPressed) {
            // Küçülme animasyonu
            val shrinkAnim = Animatable(0.95f)
            shrinkAnim.animateTo(
                targetValue = 0.85f, // Küçülme son hedef değeri
                animationSpec = tween(
                    durationMillis = 300, // Küçülme süresi
                    easing = LinearEasing
                )
            )

            // Bekleme
            delay(300)

            // Büyüme animasyonu
            shrinkAnim.animateTo(
                targetValue = 1f, // Eski hedef değeri
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

            // Basılmış durumu kapat
            isPressed = false
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(5.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale) // Ölçek animasyonu
            .clickable {
                isPressed = !isPressed
                viewModel.changeData(index)
            },
        border = BorderStroke(1.dp, Color.DarkGray),
        colors = CardDefaults.cardColors(
            containerColor = if (isPressed) Color.LightGray else Color.White ,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isPressed) 8.dp else 5.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .width(width = 50.dp)
                        .height(height = 50.dp)
                        .padding(top = 5.dp),
                    painter = rememberAsyncImagePainter(item.image),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 2.dp),
                    text = item.name,
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 14.sp),
                    color = Color.DarkGray,
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = item.currentPrice,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 12.sp),
                color = colorResource(id = R.color.teal_700)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = "High 24h: ${item.highCoinAmount}",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 9.sp),
                color = Color.Gray
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(4.dp),
                text = "Low 24h: ${item.lowCoinAmount}",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 9.sp),
                color = Color.Gray
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, start = 4.dp, end = 6.dp, bottom = 4.dp),
                text = item.date,
                textAlign = TextAlign.End,
                style = TextStyle(fontSize = 9.sp),
                color = Color.LightGray
            )
        }


    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoComposeTheme {
        // Greeting("Android")
        //CoinItem(item = CoinUiModel("1", "df", "df", "sfsfsfsdf", "gd", "2424", "3542"))
    }
}