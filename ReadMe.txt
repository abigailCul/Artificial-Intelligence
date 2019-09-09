Abigail Culkin .. G00334291
Artificial Intelligence Project!

The objective of the game should be to escape from amaze and either avoid or fight off the enemy characters that move around in the game environment.

My game is is using Fuzzy Logic and Neural Networks to work. The enemies are spiders which are controlled by different systems, Neural network and fuzzy logic.

To run my jar file i have uploaded you run java –cp ./game.jar ie.gmit.sw.ai.GameRunner.
My jar file would not open on my laptop but it was made correctly. I also could not open the old ones i have made so it is a problem with my laptop. It is running from the command line.

My Game:
My game contains items you have to collect and spiders. 
FuzzyLog class:
The green spider in the maze starts training a neural network when you get within the range. 
This Neural network is set up in the FightingNN class.

The spiders are threaded characters. This is implemented in the fuzzyLog file
The  The spiders from 6 - 8 use fuzzy logic to find the player. 
The heuristic search methods they are using is A* Traversator for these particular spiders.
In the spider class i have used a switch to control the spiders from 10 - 13 using different searches.
The depth first search and best first search are used in the cases to make the follows find and follow the player. 
I tried this using the nodes in the maze to find the position of the player.

The FCL file is implementing the fuzzy logic rules. 
The input variables are : 
weapon which is the weapon type the player has.
enemy is the life the spider has and takes off you.
the output variable is:
life this is the life of the player that goes down
The rules are also in this file. This helps with the player health in the game.

The player health is decreased using the fuzzy files.
The player health is in the FightFuzzy class and implemented in the player class to control the value of the life going down.

Game functions:
When the player moves around the maze and goes near the spiders the players life is decreased.
If the player walks to the spider when life is less then 0 the game will end and you will be prompted with a player died screen and this will exit the game.
When the player goes to the weapon they collect the weapon. This will add to the players health.
The weapon health is not completed the way i wanted as I do not have is adding to the players new health when it is decreased.

The help object will show the player how the game works when they walk up to the help icon.
The game runner and player class contain the code that controls this.