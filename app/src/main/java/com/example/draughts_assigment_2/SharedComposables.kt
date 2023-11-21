package com.example.draughts_assigment_2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.floor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun draughtsView(game_state: MutableList<MutableList<Int>>,cell1Color: Color,cell2Color: Color,player1Color: Color,player2Color: Color,onAttemptMove:(x:Int,y:Int) ->Unit){
    var width by remember { mutableStateOf(0.0f) }
    var height by remember { mutableStateOf(0.0f) }
    var cell_width by remember { mutableStateOf(0.0f) }
    var cell_height by remember { mutableStateOf(0.0f) }
    var isBlack = remember { mutableStateOf(false) }



    Canvas(modifier = Modifier
        .padding(5.dp)
        .aspectRatio((1.0f))
        .pointerInput(key1 = Unit) {
            detectTapGestures(
                onTap = {
                    var cell_x = floor(it.x / cell_width).toInt()
                    var cell_y = floor(it.y / cell_height).toInt()
                    onAttemptMove(cell_x, cell_y)
                }
            )
        }){
        width = size.width
        height = size.height

        cell_width = width / 8.0f
        cell_height =width/8.0f

        for (i in 0 until 8) {
            for (j in 0 until 8) {
                val color = if (isBlack.value) cell1Color else cell2Color

                drawRect(color = color, topLeft = Offset(i * cell_width, j * cell_height), size = Size(cell_width, cell_height))


                isBlack.value = !isBlack.value
            }
            isBlack.value = !isBlack.value
        }
        for(i in 0 until 8){
            for (j in 0 until 8){
                if (game_state[i][j]==3 || game_state[i][j]==5 || game_state[i][j] == 8 || game_state[i][j]==9 ){
                    drawRect(color = Color.Gray, topLeft = Offset((i) * cell_width, (j) * cell_height), size = Size(cell_width, cell_height))

                }
                if (game_state[i][j]==4){
                    drawRect(color = Color.Yellow, topLeft = Offset((i) * cell_width, (j) * cell_height), size = Size(cell_width, cell_height))

                }
            }
        }


        //go through each cell in the board and draw relevant pieces
        for (i in 0 until 8){
            for (j in  0 until 8){
                if (game_state[i][j] == 1 || game_state[i][j] == 3){
                    withTransform({
                        translate(i*cell_width,j*cell_height)
                    }){
                        drawOval(color= player1Color, topLeft = Offset(0.0f,0.0f),size = Size(cell_width,cell_height))
                    }
                }else if(game_state[i][j] == 2 || game_state[i][j]==5){
                    withTransform({
                        translate(i*cell_width,j*cell_height)
                    }){
                        drawOval(color= player2Color, topLeft = Offset(0.0f,0.0f),size = Size(cell_width,cell_height))
                    }
                }else if (game_state[i][j] == 6 || game_state[i][j]==8){
                    withTransform({
                        translate(i * cell_width, j * cell_height)
                    }) {
                        drawOval(color = Color.Yellow, topLeft = Offset(0.0f, 0.0f), size = Size(cell_width, cell_height))

                        // Drawing the yellow circle inside the green oval
                        val goldenCircleSize = 0.8f // Adjust this ratio to fit the circle inside the oval
                        val goldenCircleOffsetX = (cell_width * (1 - goldenCircleSize)) / 2
                        val goldenCircleOffsetY = (cell_height * (1 - goldenCircleSize)) / 2
                        drawOval(
                            color = player1Color,
                            topLeft = Offset(goldenCircleOffsetX, goldenCircleOffsetY),
                            size = Size(cell_width * goldenCircleSize, cell_height * goldenCircleSize)
                        )
                    }
                }else if (game_state[i][j] == 7 || game_state[i][j]==9){
                    withTransform({
                        translate(i * cell_width, j * cell_height)
                    }) {
                        drawOval(color = Color.Yellow, topLeft = Offset(0.0f, 0.0f), size = Size(cell_width, cell_height))

                        // Drawing the yellow circle inside the red oval
                        val goldenCircleSize = 0.8f // Adjust this ratio to fit the circle inside the oval
                        val goldenCircleOffsetX = (cell_width * (1 - goldenCircleSize)) / 2
                        val goldenCircleOffsetY = (cell_height * (1 - goldenCircleSize)) / 2
                        drawOval(
                            color = player2Color,
                            topLeft = Offset(goldenCircleOffsetX, goldenCircleOffsetY),
                            size = Size(cell_width * goldenCircleSize, cell_height * goldenCircleSize)
                        )
                    }
                }
            }
        }
    }


}