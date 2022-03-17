package com.example.testtaskcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.testtaskcompose.R
import com.example.testtaskcompose.retrofit.CommonProfile

@Composable
fun UsersListItem(
    profile: CommonProfile,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),

//        backgroundColor = Color.LightGray.copy(alpha = 0.3f),
    ) {
        Row(
            modifier = Modifier.padding(15.dp)
        ) {
            AvatarImage(
                url = profile.avatarUrl ?: "",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )
            Divider(
                Modifier
                    .width(20.dp)
                    .height(0.dp)
            )
            Text(
                text = profile.login ?: "",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun AvatarImage(
    url: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .placeholder(R.drawable.ic_launcher_foreground)
            .build(),
        contentDescription = "CommonProfileAvatar",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
fun PUsersList() {
    UsersListItem(
        CommonProfile(
            login = "Test Testovich",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4"
        )
    )
}