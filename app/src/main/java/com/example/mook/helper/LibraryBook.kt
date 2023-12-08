package com.example.mook.helper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mook.R

@Composable
fun LibraryBook() {
    ConstraintLayout(
        modifier = Modifier
            .height(400.dp)
            .width(300.dp),
    ) {
        val (image, title, author) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.baseline_square_24),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(280.dp)
                .height(350.dp)
                .background(Color.Red),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "Title",
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(Color.Blue)

        )
        Text(
            text = "Author",
            modifier = Modifier
                .constrainAs(author) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(Color.Green)

        )

    }
}
