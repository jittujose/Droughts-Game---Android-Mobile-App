package com.example.draughts_assigment_2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class SettingsActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedOptionCell1 by remember { mutableStateOf("Black") }
            var selectedOptionCell2 by remember { mutableStateOf("White") }
            var selectedOptionPlayer1 by remember { mutableStateOf("Green") }
            var selectedOptionPlayer2 by remember { mutableStateOf("Red") }

            Column(Modifier.padding(top = 70.dp)) {
                Row {
                    Text(text = "Cell 1 Colour ")
                    SimpleDropdownMenu(
                        selected = selectedOptionCell1,
                        onItemSelected = { selectedOptionCell1 = it }
                    )
                }
                Row {
                    Text(text = "Cell 2 Colour ")
                    SimpleDropdownMenu(
                        selected = selectedOptionCell2,
                        onItemSelected = { selectedOptionCell2 = it }
                    )
                }
                Row {
                    Text(text = "Player 1 Striker ")
                    SimpleDropdownMenu(
                        selected = selectedOptionPlayer1,
                        onItemSelected = { selectedOptionPlayer1 = it }
                    )
                }
                Row {
                    Text(text = "Player 2 Striker ")
                    SimpleDropdownMenu(
                        selected = selectedOptionPlayer2,
                        onItemSelected = { selectedOptionPlayer2 = it }
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Button(onClick = {
                        selectedCell1.value = selectedOptionCell1
                        selectedCell2.value = selectedOptionCell2
                        selectedPlayer1.value = selectedOptionPlayer1
                        selectedPlayer2.value = selectedOptionPlayer2
                        finishActivity()
                    }) {
                        Text(text = "Apply")
                    }
                }
                Text(text = "Two colors should not be same!")
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun finishActivity(){
        var return_intent = Intent(Intent.ACTION_VIEW)
        val anyTwoEqual = areAnyTwoEqual(selectedCell1.value,selectedCell2.value,selectedPlayer1.value,selectedPlayer2.value)
        if(anyTwoEqual)
        {}else{
        return_intent.putExtra("cellColour1", selectedCell1.value)
        return_intent.putExtra("cellColour2", selectedCell2.value)
        return_intent.putExtra("playerColour1", selectedPlayer1.value)
        return_intent.putExtra("playerColour2", selectedPlayer2.value)
        setResult(ComponentActivity.RESULT_OK,return_intent)
        finish()
    }}
}


var selectedCell1 = mutableStateOf("Option 1")
var selectedCell2 = mutableStateOf("Option 1")
var selectedPlayer1 = mutableStateOf("Option 1")
var selectedPlayer2 = mutableStateOf("Option 1")

fun areAnyTwoEqual(vararg colors: String): Boolean {
    for (i in colors.indices) {
        for (j in i + 1 until colors.size) {
            if (colors[i] == colors[j]) {
                return true // Two colors are equal
            }
        }
    }
    return false // No two colors are equal
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDropdownMenu(selected: String, onItemSelected: (String) -> Unit) {
    val items = listOf("Black", "White", "Green","Red","Blue","Cyan","Magenta","DarkGray")
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Selected: $selected",
            modifier = Modifier.padding(16.dp)
        )
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            TextField(value = selected, onValueChange = {}, readOnly = true, modifier = Modifier.menuAnchor())
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                for (i in items.indices) {
                    DropdownMenuItem(
                        text = { Text(items[i]) },
                        onClick = {
                            onItemSelected(items[i])

                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
