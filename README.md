# Android Checkers Game

This is an Android Checkers game built using **Kotlin** and **Jetpack Compose**. The app allows two players to play a game of checkers with smooth touch interactions, dynamic canvas rendering, and game logic for both regular and king pieces. Players can customize the look of the board and pieces, and game progress can be reset at any time.

## Features

- **MainActivity & SharedComposables**: The project starts with the `MainActivity.kt` and contains a separate file `SharedComposables.kt` where all composable UI functions are stored.
- **Dynamic Canvas for Checkerboard**: A single canvas is used to render an 8x8 checkerboard. The canvas adapts to different screen sizes and always maintains a square aspect ratio.
- **Resizable Checkerboard Grid**: The grid dynamically adjusts its size to the width and height of the canvas.

- **Initial Game Rendering**: The checkerboard displays the initial game state, with pieces in their starting positions.
- **Touch Interaction**: Users can select a piece and then select an adjacent space to move the piece.
- **Diagonal Movement**: Player 1 can move pieces diagonally up and Player 2 can move diagonally down, with restrictions on movement based on adjacent empty spaces.

- **Piece Capturing**: Players can capture their opponent’s pieces using diagonal jumps, and the logic ensures that the capture follows the movement rules.
- **Chained Captures**: If possible, players must continue chaining captures in a single turn.
- **Kinging Pieces**: When a piece reaches the opposite side of the board, it is "kinged," which allows it to move both forward and backward diagonally.
- **King Movement**: Kings can move diagonally in all directions.

- **Backward Capturing for Kings**: Kings can capture pieces in both forward and backward directions.
- **Chaining for Kings**: Kings can chain multiple captures in any direction.
- **UI Elements**: The app includes a display for the current player’s turn, a count of remaining pieces for each player, and a button to reset the game.
- **Settings Activity**: Users can customize the color theme of the board and pieces through a settings activity. The board and pieces will reflect the user-defined colors dynamically.
- **UI Design**: The interface is designed to be user-friendly, intuitive, and visually appealing.

## Technologies Used

- **Kotlin**
- **Jetpack Compose**
- **Canvas API** for rendering the checkerboard
- **Visual Studio IDE**


