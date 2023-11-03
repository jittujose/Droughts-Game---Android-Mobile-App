package com.example.draughts_assigment_2

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                ticTacToeView(game_state = array){x,y ->
                    placePiece(x,y)
                    updateGameStatus()
                }
                Text(text = "current player is ${player.value}")
                Button(onClick = { resetGame() }) {
                    Text(text = "Reset game")
                }
            }
        }
    }
    //helper function that will do a placement of a piece
    private fun placePiece(x:Int,y:Int){
        if(array[x][y]==0 && player.value==1){
            array[x][y]=1
            player.value=2
        }else if(array[x][y]==0 && player.value==2){
            array[x][y]=2
            player.value=1
        }
    }
    //helper function that will reset the game
    private fun resetGame() {
        for (i in 0 until 8)
            for (j in 0 until 8){
                array[i][j]=0
            }
        //reset the player and the string
        player.value=1
        game_status.value = "current player is 1"
    }

    //helper function that will update the game status
    private fun updateGameStatus(){
        if (player.value ==1)
            game_status.value = "current player is 1"
        else if (player.value==2)
            game_status.value="current player is 2"
    }

    //array contain tictocview state
    private var array = mutableStateListOf<MutableList<Int>>(
        mutableStateListOf(2,0,2,0,0,1,0,1),mutableStateListOf(0,2,0,0,0,0,1,0),
        mutableStateListOf(2,0,2,0,0,1,0,1),mutableStateListOf(0,2,0,0,0,0,1,0),
        mutableStateListOf(2,0,2,0,0,1,0,1),mutableStateListOf(0,2,0,0,0,0,1,0),
        mutableStateListOf(2,0,2,0,0,1,0,1),mutableStateListOf(0,2,0,0,0,0,1,0)
    )
    //current player
    private var player = mutableStateOf(1)
    //the string that is played after every player move
    private var game_status = mutableStateOf("current player is 1")
}
