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
import androidx.compose.runtime.snapshots.SnapshotStateList
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
    private fun placePiece(x:Int,y:Int) {
        if (player.value == 1){      //Movements of player 1
            if (array[x][y] == 1) {
                removeGrey(x,y)
                array[x][y] = 3
                removeYellow(x,y)
                updateArray(4, x - 1, y - 1)
                updateArray(4, x + 1, y - 1)

            } else if (array[x][y] == 4) {  //checking it is yellow
                if(x+1<8 && y+1<8)
                if (array[x + 1][y + 1] == 3 ) { //checking gray is right side down or not
                    updateArray(1, x, y)
                    updateArray(0, x + 1, y + 1)
                }
                if(x-1>=0 && y+1<8)
                if (array[x - 1][y + 1] == 3) {  //checking gray is left side down or not
                    updateArray(1, x, y)
                    updateArray(0, x - 1, y + 1)

                }
                removeYellow(x,y)
                removeGrey(x,y)
                player.value=2
            }

    }else{  //Movements for player 2
            if (array[x][y] == 2) {
                removeGrey(x,y)
                array[x][y] = 5
                removeYellow(x,y)
                updateArray(4, x - 1, y + 1)
                updateArray(4, x + 1, y + 1)

            } else if (array[x][y] == 4) {  //checkking it is yellow
                if(x+1<8 && y+1<8)
                    if (array[x + 1][y - 1] == 5 ) { //checking gray is right side up or not
                        updateArray(2, x, y)
                        updateArray(0, x + 1, y - 1)
                    }
                if(x-1>=0 && y+1<8)
                    if (array[x - 1][y - 1] == 5) {  //checking gray is left side up or not
                        updateArray(2, x, y)
                        updateArray(0, x - 1, y - 1)

                    }
                removeYellow(x,y)
                removeGrey(x,y)
                player.value=1
            }
    }

    }
    //helper function that will reset the game
    private fun resetGame() {
        val specifiedState = listOf(
            listOf(0, 2, 0, 0, 0, 1, 0, 1),
            listOf(2, 0, 2, 0, 0, 0, 1, 0),
            listOf(0, 2, 0, 0, 0, 1, 0, 1),
            listOf(2, 0, 2, 0, 0, 0, 1, 0),
            listOf(0, 2, 0, 0, 0, 1, 0, 1),
            listOf(2, 0, 2, 0, 0, 0, 1, 0),
            listOf(0, 2, 0, 0, 0, 1, 0, 1),
            listOf(2, 0, 2, 0, 0, 0, 1, 0)
        )

        // Assuming 'array' is previously defined and is mutable
        for (row in 0 until specifiedState.size) {
            for (column in 0 until specifiedState[row].size) {
                array[row][column] = specifiedState[row][column]
            }
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

    //function to update array for the movement
    fun updateArray(value: Int, i: Int, j: Int) {
        if (i in 0 until 8 && j in 0 until 8) {
            if(value ==4) {
                if (array[i][j] == 0) {
                    array[i][j] = value
                }

            }else{
            array[i][j] = value }

        }
    }

    //that remove other yellow colour in cells after movement and gray color also
    fun removeYellow(x: Int, y: Int){
        for (i in 0 until 8)
            for (j in 0 until 8)
            {
                if (array[i][j]==4 && (x != i || y!=j)){
                    array[i][j]=0
                }
            }
    }
    fun removeGrey(x: Int,y:Int){
        for (i in 0 until 8)
            for (j in 0 until 8)
            {
                if(array[i][j]==3){
                array[i][j]=1
            }
                if(array[i][j]==5){
                    array[i][j]=2
                }
            }
    }


    //array contain tictocview state
    var array = mutableStateListOf<MutableList<Int>>(
        mutableStateListOf(0,2,0,0,0,1,0,1),mutableStateListOf(2,0,2,0,0,0,1,0),
        mutableStateListOf(0,2,0,0,0,1,0,1),mutableStateListOf(2,0,2,0,0,0,1,0),
        mutableStateListOf(0,2,0,0,0,1,0,1),mutableStateListOf(2,0,2,0,0,0,1,0),
        mutableStateListOf(0,2,0,0,0,1,0,1),mutableStateListOf(2,0,2,0,0,0,1,0)
    )
    //current player
    private var player = mutableStateOf(1)
    //the string that is played after every player move
    private var game_status = mutableStateOf("current player is 1")

    var indicator= mutableStateOf(true)
}
