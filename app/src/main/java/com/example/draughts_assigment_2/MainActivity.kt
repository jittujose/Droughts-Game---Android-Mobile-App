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
                Text(text = "Score of Player 1: ${scorePlayer1.value}")
                Text(text = "Score of Player 2: ${scorePlayer2.value}")
            }
        }
    }
    //helper function that will do a placement of a piece
    private fun placePiece(x:Int,y:Int) {
        if (player.value == 1){      //Movements of player 1
            if (array[x][y] == 1) {
                removeGrey(x,y)
                array[x][y] = 3  //grey for player 1
                removeYellow(x,y)
                updateArray(4, x - 1, y - 1,1)
                updateArray(4, x + 1, y - 1,2)
            } else if (array[x][y] == 4) {  //checking it is yellow
                ifYellowPlayer1(x,y)
            } else if(array[x][y] == 6){ //Movements of king
                removeGrey(x,y)
                array[x][y] = 8  //grey king for player 1
                removeYellow(x,y)
                updateArray(4, x - 1, y - 1,1)
                updateArray(4, x + 1, y - 1,2)
                updateArray(4,x-1,y+1,5)
                updateArray(4,x+1,y+1,6)
            }



    }else{  //Movements for player 2
            if (array[x][y] == 2) {
                removeGrey(x,y)
                array[x][y] = 5 //gray is 5
                removeYellow(x,y)
                updateArray(4, x - 1, y + 1,3)
                updateArray(4, x + 1, y + 1,4)

            } else if (array[x][y] == 4) {  //checkking it is yellow
                ifyellowPlayer2(x,y)
            }else if(array[x][y] == 7){ //Movements of king
                removeGrey(x,y)
                array[x][y] = 9  //grey for player 2
                removeYellow(x,y)
                updateArray(4, x - 1, y + 1,3)
                updateArray(4, x + 1, y + 1,4)
                updateArray(4,x-1,y-1,7)
                updateArray(4,x+1,y-1,8)
            }
    }

        //Adding King behaviour
        for (i in 0 until 8)
            for (j in 0 until 8){
                if (j==0 && array[i][j]==1){
                    array[i][j]=6    //King striker for player 1
                }
                if (j==7 && array[i][j]==2){
                    array[i][j]=7   //King striker for player 2
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
        scorePlayer1.value=0
        scorePlayer2.value=0
    }

    //helper function that will update the game status
    private fun updateGameStatus(){
        if (player.value ==1)
            game_status.value = "current player is 1"
        else if (player.value==2)
            game_status.value="current player is 2"
    }

    //function to update array for the movement
    fun updateArray(value: Int, i: Int, j: Int,m:Int
    ) {
        if (i in 0 until 8 && j in 0 until 8) {
            if(value ==4) {
                if (array[i][j] == 0) {
                    array[i][j] = value
                }

            }else
            {
            array[i][j] = value }
            //code for setting yellow to  catch enemy
            if(array[i][j]==2 && m==1){     //for player 1
                updateArray(4,i-1,j-1,0)
            }
            if(array[i][j]==2 && m==2){     //for player 1
                updateArray(4,i+1,j-1,0)
            }
            if(array[i][j]==1 && m==3){     //for player 2
                updateArray(4,i-1,j+1,0)
            }
            if(array[i][j]==1 && m==4){     //for player 2
                updateArray(4,i+1,j+1,0)
            }
            if(array[i][j]==2 && m==5){    //for player 1 king
                updateArray(4,i-1,j+1,0)
            }
            if(array[i][j]==2 && m==6){    //for player 1 king
                updateArray(4,i+1,j+1,0)
            }
            if(array[i][j]==1 && m==7){     //for player 2 king
                updateArray(4,i-1,j-1,0)
            }
            if(array[i][j]==1 && m==8){     //for player 2 king
                updateArray(4,i+1,j-1,0)
            }

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
                array[i][j]=1 //player 1
            }
                if(array[i][j]==5){
                    array[i][j]=2 // player 2
                }
                if(array[i][j]==8){
                    array[i][j]=6 //King player 1
                }
                if(array[i][j]==9){
                    array[i][j]=7 //King player 2
                }
            }
    }

    fun ifYellowPlayer1(x: Int,y: Int){
        if(x+1<8 && y+1<8){
            if (array[x + 1][y + 1] == 3 ) { //checking gray is right side down or not
                updateArray(1, x, y,0)
                updateArray(0, x + 1, y + 1,0)
            }
            //catching enemy
            if (x+2<8 && y+2<8)
                if (array[x + 1][y + 1] == 2 && array[x+2][y+2]==3) { //checking enemy(red) and grey is right side down or not
                    updateArray(1, x, y,0)
                    updateArray(0, x + 1, y + 1,0)
                    updateArray(0,x+2,y+2,0)
                    scorePlayer1.value = scorePlayer1.value+1
                    captureFlag.value=true
                }
        }
        if(x-1>=0 && y+1<8){
            if (array[x - 1][y + 1] == 3) {  //checking gray is left side down or not
                updateArray(1, x, y,0)
                updateArray(0, x - 1, y + 1,0)

            }   //catching enemy
            if(x-2>=0 && y+2<8)
                if (array[x - 1][y + 1] == 2 && array[x-2][y+2]==3) {  //checking enemy(red) and grey is left side down or not
                    updateArray(1, x, y,0)
                    updateArray(0, x - 1, y + 1,0)
                    updateArray(0,x-2,y+2,0)
                    scorePlayer1.value = scorePlayer1.value+1
                    captureFlag.value=true
                }
        }
        //yellowExtraMovesKingPlayer1(x,y)
        removeYellow(x,y)
        removeGrey(x,y)
        chainFlag.value=true
        if(x+2<8 && y-2>=0 && captureFlag.value){ //chaining catch right move
            if(array[x+1][y-1]==2 && array[x+2][y-2]==0){
                chainFlag.value=false
                array[x][y]=3
                array[x+2][y-2]=4

            }
        }
        if(x-2>=0 && y-2>=0 && captureFlag.value){ //chaining catch left move
            if(array[x-1][y-1]==2 && array[x-2][y-2]==0){
                chainFlag.value=false
                array[x][y]=3
                array[x-2][y-2]=4

            }
        }

        captureFlag.value=false
        if (chainFlag.value){
        player.value=2}
    }

    fun ifyellowPlayer2(x: Int,y: Int){
        if(x+1<8 && y-1>=0) {
            if (array[x + 1][y - 1] == 5 ) { //checking gray is right side up or not
                updateArray(2, x, y,0)
                updateArray(0, x + 1, y - 1,0)
            }
            //catching enemy
            if (x+2<8 && y-2>=0)
                if (array[x + 1][y - 1] == 1 && array[x+2][y-2]==5) { //checking enemy(green) and grey is right side up or not
                    updateArray(2, x, y,0)
                    updateArray(0, x + 1, y - 1,0)
                    updateArray(0,x+2,y-2,0)
                    scorePlayer2.value=scorePlayer2.value+1
                    captureFlag.value=true
                }
        }
        if(x-1>=0 && y-1>=0){
            if (array[x - 1][y - 1] == 5) {  //checking gray is left side up or not
                updateArray(2, x, y,0)
                updateArray(0, x - 1, y - 1,0)

            }
            //catching enemy
            if (x-2>=0 && y-2>=0)
                if (array[x - 1][y - 1] == 1 && array[x-2][y-2]==5) {  //checking enemy(green) and grey is left side up or not
                    updateArray(2, x, y,0)
                    updateArray(0, x - 1, y - 1,0)
                    updateArray(0,x-2,y-2,0)
                    scorePlayer2.value=scorePlayer2.value+1
                    captureFlag.value=true
                }
        }
        removeYellow(x,y)
        removeGrey(x,y)
        //Chaining
        chainFlag.value=true
        if(x+2<8 && y+2<8 && captureFlag.value){ //chaining catch right move
            if(array[x+1][y+1]==1 && array[x+2][y+2]==0){
                chainFlag.value=false
                array[x][y]=5
                array[x+2][y+2]=4

            }
        }
        if(x-2>=0 && y+2<8 && captureFlag.value){ //chaining catch left move
            if(array[x-1][y+1]==1 && array[x-2][y+2]==0){
                chainFlag.value=false
                array[x][y]=5
                array[x-2][y+2]=4

            }
        }
        captureFlag.value=false
        if (chainFlag.value){
        player.value=1}
    }

    fun yellowExtraMovesKingPlayer1(x : Int,y: Int){
        if(x+1<8 && y+1<8){
            if (array[x + 1][y + 1] == 8 ) { //checking gray is right side down or not
                updateArray(6, x, y,0)
                updateArray(0, x + 1, y + 1,0)
            }
            //catching enemy
            if (x+2<8 && y+2<8)
                if (array[x + 1][y + 1] == 2 && array[x+2][y+2]==8) { //checking enemy(red) and grey is right side down or not
                    updateArray(6, x, y,0)
                    updateArray(0, x + 1, y + 1,0)
                    updateArray(0,x+2,y+2,0)
                    scorePlayer1.value = scorePlayer1.value+1
                    captureFlag.value=true
                }
        }
        if(x-1>=0 && y+1<8){
            if (array[x - 1][y + 1] == 8) {  //checking gray is left side down or not
                updateArray(6, x, y,0)
                updateArray(0, x - 1, y + 1,0)

            }   //catching enemy
            if(x-2>=0 && y+2<8)
                if (array[x - 1][y + 1] == 2 && array[x-2][y+2]==8) {  //checking enemy(red) and grey is left side down or not
                    updateArray(6, x, y,0)
                    updateArray(0, x - 1, y + 1,0)
                    updateArray(0,x-2,y+2,0)
                    scorePlayer1.value = scorePlayer1.value+1
                    captureFlag.value=true
                }
        }
        ///////////
        if(x+1<8 && y-1>=0) {
            if (array[x + 1][y - 1] == 8 ) { //checking gray is right side up or not
                updateArray(6, x, y,0)
                updateArray(0, x + 1, y - 1,0)
            }
            //catching enemy
            if (x+2<8 && y-2>=0)
                if (array[x + 1][y - 1] == 2 && array[x+2][y-2]==8) { //checking enemy(green) and grey is right side up or not
                    updateArray(6, x, y,0)
                    updateArray(0, x + 1, y - 1,0)
                    updateArray(0,x+2,y-2,0)
                    scorePlayer2.value=scorePlayer2.value+1
                    captureFlag.value=true
                }
        }
        if(x-1>=0 && y-1>=0){
            if (array[x - 1][y - 1] == 8) {  //checking gray is left side up or not
                updateArray(6, x, y,0)
                updateArray(0, x - 1, y - 1,0)

            }
            //catching enemy
            if (x-2>=0 && y-2>=0)
                if (array[x - 1][y - 1] == 2 && array[x-2][y-2]==8) {  //checking enemy(green) and grey is left side up or not
                    updateArray(6, x, y,0)
                    updateArray(0, x - 1, y - 1,0)
                    updateArray(0,x-2,y-2,0)
                    scorePlayer2.value=scorePlayer2.value+1
                    captureFlag.value=true
                }
        }
        //////////
        removeYellow(x,y)
        removeGrey(x,y)
        chainFlag.value=true
        if(x+2<8 && y-2>=0 && captureFlag.value){ //chaining catch right move
            if(array[x+1][y-1]==2 && array[x+2][y-2]==0){
                chainFlag.value=false
                array[x][y]=8
                array[x+2][y-2]=4

            }
        }
        if(x-2>=0 && y-2>=0 && captureFlag.value){ //chaining catch left move
            if(array[x-1][y-1]==2 && array[x-2][y-2]==0){
                chainFlag.value=false
                array[x][y]=8
                array[x-2][y-2]=4

            }
        }
        if(x+2<8 && y+2<8 && captureFlag.value){ //chaining catch right move
            if(array[x+1][y+1]==2 && array[x+2][y+2]==0){
                chainFlag.value=false
                array[x][y]=8
                array[x+2][y+2]=4

            }
        }
        if(x-2>=0 && y+2<8 && captureFlag.value){ //chaining catch left move
            if(array[x-1][y+1]==1 && array[x-2][y+2]==0){
                chainFlag.value=false
                array[x][y]=8
                array[x-2][y+2]=4

            }
        }
        captureFlag.value=false
        if (chainFlag.value){
            player.value=2}
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

    var captureFlag= mutableStateOf(false)
    var chainFlag= mutableStateOf(true)
    var scorePlayer1= mutableStateOf(0)
    var scorePlayer2= mutableStateOf(0)
}
