package com.example.draughts_assigment_2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){
                cellColour1.value=it.data?.getStringExtra("cellColour1").toString()
                cellColour2.value=it.data?.getStringExtra("cellColour2").toString()
                playerColour1.value=it.data?.getStringExtra("playerColour1").toString()
                playerColour2.value=it.data?.getStringExtra("playerColour2").toString()
            }
            var cell1Colour= getColorFromString(cellColour1.value)
            var cell2Colour= getColorFromString(cellColour2.value)
            var player1Colour = getColorFromString(playerColour1.value)
            var player2Colour = getColorFromString(playerColour2.value)
            Column(Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                draughtsView(game_state = array,cell1Colour,cell2Colour,player1Colour,player2Colour){x,y ->
                    placePiece(x,y)
                    updateGameStatus()
                }
                Text(text = "current player is ${player.value}")
                if (player.value==1){
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.clip(CircleShape)
                            .size(46.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = player1Colour,
                            contentColor = Color.White
                        )
                    ) {

                    }
                }else{
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.clip(CircleShape)
                            .size(46.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = player2Colour,
                            contentColor = Color.White
                        )
                    ) {

                    }
                }
                Button(onClick = { resetGame() }) {
                    Text(text = "Reset game")
                }
                if(scorePlayer1.value==12){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier.clip(CircleShape)
                                .size(46.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = player1Colour,
                                contentColor = Color.White
                            )
                        ) {}
                        Text(text = "Player 1 Won")
                    }
                }else if(scorePlayer2.value==12){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier.clip(CircleShape)
                                .size(46.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = player2Colour,
                                contentColor = Color.White
                            )
                        ) {}
                        Text(text = "Player 2 Won")
                    }
                }else{
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.clip(CircleShape)
                            .size(46.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = player1Colour,
                            contentColor = Color.White
                        )
                    ) {}
                    Text(text = "Score of Player 1: ${scorePlayer1.value}")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.clip(CircleShape)
                            .size(46.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = player2Colour,
                            contentColor = Color.White
                        )
                    ) {}
                    Text(text = "Score of Player 2: ${scorePlayer2.value}")
                }}
                Button(onClick = { launcher.launch(createIntentSettingsActivity()) }
                    ) {
                    Text(text = "Settings")
                }

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
            if((array[i][j]==2 ||array[i][j]==7) && m==1){     //for player 1
                updateArray(4,i-1,j-1,0)
            }
            if((array[i][j]==2 || array[i][j]==7) && m==2){     //for player 1
                updateArray(4,i+1,j-1,0)
            }
            if((array[i][j]==1 || array[i][j]==6) && m==3){     //for player 2
                updateArray(4,i-1,j+1,0)
            }
            if((array[i][j]==1 || array[i][j]==6) && m==4){     //for player 2
                updateArray(4,i+1,j+1,0)
            }
            if((array[i][j]==2 || array[i][j]==7) && m==5){    //for player 1 king
                updateArray(4,i-1,j+1,0)
            }
            if((array[i][j]==2 || array[i][j]==7) && m==6){    //for player 1 king
                updateArray(4,i+1,j+1,0)
            }
            if((array[i][j]==1 || array[i][j]==6) && m==7){     //for player 2 king
                updateArray(4,i-1,j-1,0)
            }
            if((array[i][j]==1 || array[i][j]==6) && m==8){     //for player 2 king
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
                if ((array[x + 1][y + 1] == 2 || array[x+1][y+1]==7) && (array[x+2][y+2]==3)) { //checking enemy(red) and grey is right side down or not
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
                if ((array[x - 1][y + 1] == 2 || array[x-1][y+1]==7) && (array[x-2][y+2]==3)) {  //checki || array[)[n==9g enemy(red) and grey is left side down or not
                    updateArray(1, x, y,0)
                    updateArray(0, x - 1, y + 1,0)
                    updateArray(0,x-2,y+2,0)
                    scorePlayer1.value = scorePlayer1.value+1
                    captureFlag.value=true
                }
        }

        yellowExtraMovesKingPlayer(x,y,6,8,2)
        removeYellow(x,y)
        removeGrey(x,y)
        chainFlag.value=true
        if(x+2<8 && y-2>=0 && captureFlag.value){ //chaining catch right move
            if((array[x+1][y-1]==2 || array[x+1][y-1]==7) && array[x+2][y-2]==0){
                chainFlag.value=false
                array[x][y]=3
                array[x+2][y-2]=4
                placePiece(x+2,y-2)
            }
        }
        if(x-2>=0 && y-2>=0 && captureFlag.value){ //chaining catch left move
            if((array[x-1][y-1]==2 || array[x-1][y-1]==7) && array[x-2][y-2]==0){
                chainFlag.value=false
                array[x][y]=3
                array[x-2][y-2]=4
                placePiece(x-2,y-2)
            }
        }
        chainingKing(x,y,6,8,2)

        captureFlag.value=false
        captureFlagKing.value=false
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
                if ((array[x + 1][y - 1] == 1|| array[x+1][y-1]==6) && (array[x+2][y-2]==5)) { //checking enemy(green) and grey is right side up or not
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
                if ((array[x - 1][y - 1] == 1|| array[x-1][y-1]==6) && (array[x-2][y-2]==5)) {  //checking enemy(green) and grey is left side up or not
                    updateArray(2, x, y,0)
                    updateArray(0, x - 1, y - 1,0)
                    updateArray(0,x-2,y-2,0)
                    scorePlayer2.value=scorePlayer2.value+1
                    captureFlag.value=true
                }
        }

        yellowExtraMovesKingPlayer(x,y,7,9,1)
        removeYellow(x,y)
        removeGrey(x,y)
        chainFlag.value=true
        //Chaining

        if(x+2<8 && y+2<8 && captureFlag.value){ //chaining catch right move
            if((array[x+1][y+1]==1 || array[x+1][y+1]==6) && array[x+2][y+2]==0){
                chainFlag.value=false
                array[x][y]=5
                array[x+2][y+2]=4
                placePiece(x+2,y+2)
            }
        }
        if(x-2>=0 && y+2<8 && captureFlag.value){ //chaining catch left move
            if((array[x-1][y+1]==1 || array[x-1][y+1]==6) && array[x-2][y+2]==0){
                chainFlag.value=false
                array[x][y]=5
                array[x-2][y+2]=4
                placePiece(x-2,y+2)
            }
        }
        chainingKing(x,y,7,9,1)
        captureFlag.value=false
        captureFlagKing.value=false
        if (chainFlag.value){
        player.value=1}
    }

    fun yellowExtraMovesKingPlayer(x : Int,y: Int,king: Int,gray: Int, enemy: Int){
        var kingEnemy= mutableStateOf(0)
        if(enemy==2){
             kingEnemy.value=7
        }else if(enemy==1){
            kingEnemy.value=6
        }
        if(x+1<8 && y+1<8){
            if (array[x + 1][y + 1] == gray ) { //checking gray king is right side down or not
                updateArray(king, x, y,0)
                updateArray(0, x + 1, y + 1,0)
            }

        }
        if(x-1>=0 && y+1<8){
            if (array[x - 1][y + 1] == gray) {  //checking gray is left side down or not
                updateArray(king, x, y,0)
                updateArray(0, x - 1, y + 1,0)

            }
        }
        if(x+1<8 && y-1>=0) {
            if (array[x + 1][y - 1] == gray ) { //checking gray is right side up or not
                updateArray(king, x, y,0)
                updateArray(0, x + 1, y - 1,0)
            }

        }
        if(x-1>=0 && y-1>=0){
            if (array[x - 1][y - 1] == gray) {  //checking gray is left side up or not
                updateArray(king, x, y,0)
                updateArray(0, x - 1, y - 1,0)

            }

        }
        var score= mutableStateOf(0)
        //catching enemy
        if (x+2<8 && y+2<8)
            if ((array[x + 1][y + 1] == enemy || array[x+1][y+1]==kingEnemy.value) && (array[x+2][y+2]==gray)) { //checking enemy(red) and grey is right side down or not
                updateArray(king, x, y,0)
                updateArray(0, x + 1, y + 1,0)
                updateArray(0,x+2,y+2,0)
                score.value=1
                captureFlagKing.value=true
            }
        //catching enemy
        if(x-2>=0 && y+2<8)
            if ((array[x - 1][y + 1] == enemy || array[x-1][y+1]==kingEnemy.value) && (array[x-2][y+2]==gray)) {  //checki || array[)[n==9g enemy(red) and grey is left side down or not
                updateArray(king, x, y,0)
                updateArray(0, x - 1, y + 1,0)
                updateArray(0,x-2,y+2,0)
                score.value=1
                captureFlagKing.value=true
            }
        //catching enemy
        if (x+2<8 && y-2>=0)
            if ((array[x + 1][y - 1] == enemy|| array[x+1][y-1]==kingEnemy.value) && (array[x+2][y-2]==gray)) { //checking enemy(green) and grey is right side up or not
                updateArray(king, x, y,0)
                updateArray(0, x + 1, y - 1,0)
                updateArray(0,x+2,y-2,0)
                score.value=1
                captureFlagKing.value=true
            }
        //catching enemy
        if (x-2>=0 && y-2>=0)
            if ((array[x - 1][y - 1] == enemy|| array[x-1][y-1]==kingEnemy.value) && (array[x-2][y-2]==gray)) {  //checking enemy(green) and grey is left side up or not
                updateArray(king, x, y,0)
                updateArray(0, x - 1, y - 1,0)
                updateArray(0,x-2,y-2,0)
                score.value=1
                captureFlagKing.value=true
            }
        if (king==6 && score.value==1){
            scorePlayer1.value=scorePlayer1.value+1
        }
        if (king==7 && score.value==1){
            scorePlayer2.value=scorePlayer2.value+1
        }

    }
//Chaining function for king
fun chainingKing(x : Int,y: Int,king: Int,gray: Int, enemy: Int){
    var kingEnemy= mutableStateOf(0)
    if(enemy==2){
        kingEnemy.value=7
    }else if(enemy==1){
        kingEnemy.value=6
    }
    //chaining
    if(x+2<8 && y-2>=0 && captureFlagKing.value){ //chaining catch right move
        if((array[x+1][y-1]==enemy || array[x+1][y-1]==kingEnemy.value) && array[x+2][y-2]==0){
            chainFlag.value=false
            array[x][y]=gray
            array[x+2][y-2]=4
            placePiece(x+2,y-2)
        }
    }
    if(x-2>=0 && y-2>=0 && captureFlagKing.value){ //chaining catch left move
        if((array[x-1][y-1]==enemy || array[x-1][y-1]==kingEnemy.value) && array[x-2][y-2]==0){
            chainFlag.value=false
            array[x][y]=gray
            array[x-2][y-2]=4
            placePiece(x-2,y-2)
        }
    }
    if(x+2<8 && y+2<8 && captureFlagKing.value){ //chaining catch right move
        if((array[x+1][y+1]==enemy || array[x+1][y+1]==kingEnemy.value) && array[x+2][y+2]==0){
            chainFlag.value=false
            array[x][y]=gray
            array[x+2][y+2]=4
            placePiece(x+2,y+2)
        }
    }
    if(x-2>=0 && y+2<8 && captureFlagKing.value){ //chaining catch left move
        if((array[x-1][y+1]==enemy || array[x-1][y+1]==kingEnemy.value) && array[x-2][y+2]==0){
            chainFlag.value=false
            array[x][y]=gray
            array[x-2][y+2]=4
            placePiece(x-2,y+2)
        }
    }

}



    //array contain draughts view state
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

    var captureFlag= mutableStateOf(false) //it is used for apply chaining
    var captureFlagKing= mutableStateOf(false) //it is used for apply chaining for king
    var chainFlag= mutableStateOf(true)   //it is used in chaining
    var scorePlayer1= mutableStateOf(0)    //score of player one
    var scorePlayer2= mutableStateOf(0)    //score of player 2

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createIntentSettingsActivity(): Intent {
        var intent: Intent = Intent(this,SettingsActivity::class.java)
        return intent
    }
    var cellColour1 = mutableStateOf("Black")
    var cellColour2 = mutableStateOf("White")
    var playerColour1 = mutableStateOf("Green")
    var playerColour2 = mutableStateOf("Red")
}
fun getColorFromString(colorString: String): Color {
    return when (colorString) {
        "Red" -> Color.Red
        "Blue" -> Color.Blue
        "Green" -> Color.Green
        "White" -> Color.White
        "Cyan" -> Color.Cyan
        "Magenta" -> Color.Magenta
        "DarkGray" ->Color.DarkGray
        "Black" -> Color.Black
        else -> Color.Black // Default color if the string doesn't match
    }
}