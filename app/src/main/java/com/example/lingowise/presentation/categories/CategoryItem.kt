package com.example.lingowise.presentation.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.request.ImageResult
import com.example.lingowise.R

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: (id: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(0.8f)
            .padding(10.dp)
            .clickable { onClick(category.id) }
            .background(Color.Black.copy(alpha = 0.8f), shape = RoundedCornerShape(40.dp)),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(category.image)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_launcher_background)
            )
            Box(modifier = Modifier
                .clip(shape = RoundedCornerShape(40.dp))
                .background(Color(0xFF5C42F0))
                .padding(horizontal = 65.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center,){
                Text(text = category.name, color = Color.White, fontSize = 24.sp)
            }

        }
    }
}

@Composable
@Preview
fun CIP(){
    CategoryItem(category = Category(1, "Тест", "https://vk.com/doc265472153_673732575?hash=a1341olTRXmMqhP0d0lYWxulO4UJeUNQ0o7HWkgtWZH&dl=9mBWvnzrILzfjCIpCPc1ilPZXfMgHG87ZuqcosJ2GSg"), onClick = {})
}