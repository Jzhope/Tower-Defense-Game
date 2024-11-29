# My Personal Project: Tetris Game Project

## A Java-based classic arcade game

# Introduction and background:
- This application is used to write a Tower Defense game. Users will be able to add arbitary number of towers to the map to attack the enemies according to 4 different levels. With different levels, the enemies can have different numbers and health. But the towers will have only one damage. You can have arbitary number of round to play this game and you can delete any tower at the very beginning of every round.
- The application is designed for anyone who enjoys Tower Defense game.
- I personally enjoyed playing games and I am also interested in developing games. It's a manageable but challenging project that allows for creativity in game mechanics and UI design while being a well-known project to demonstrate to future employers.

# User story:
- As a user, I want to be able to add towers to the map.
- As a user, I want to see if I defeat the enemies.
- As a user, I want to remove any tower on the map.
- As a user, I want to try difficult level of enemies.
- As a user, I want to quilt the game.
- As a user, I want to save the map and the towers on the map.
- As a user. I wang to load the map and the towers on the map.

# Instructions for End User

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by click any grid of the ui.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by click any grid of the ui.
- You can locate my visual component by click any button.
- You can save the state of my application by click the save button.
- You can reload the state of my application by click the load button.

# Phase 4: Task 2
- You can see what event happened in the console when the program is running or ends. For example:
        
        Thu Nov 28 13:07:23 PST 2024
        Placed Tower at (4,4)


        Thu Nov 28 13:07:24 PST 2024
        Placed Tower at (4,2)


        Thu Nov 28 13:07:25 PST 2024
        Removed tower at (3,2).


        Thu Nov 28 13:07:25 PST 2024
        Placed Tower at (6,3)

# Phase 4: Task 3
- Some of the classes like GameApp and GamePanel have too many methods which are hard to read.
I can split the class GameApp into several different classes including GameController and GamePreparing;
- Some methods have the same name but created and used in different classes like addEnemy. They can be put into a public tool class   thus all the class can use them more conveniently. 