Maze Test
=========

User Story 1
------------

As a world famous explorer of Mazes I would like a maze to exist
So that I can explore it

Acceptance Criteria

* A Maze (as defined in Maze1.txt consists of walls 'X', Empty spaces ' ', one and only one Start point 'S' and one and only one exit 'F'
* After a maze has been created the number of walls and empty spaces should be available to me
* After a maze has been created I should be able to put in a co ordinate and know what exists at that point

User Story 2
------------

As a world famous explorer of Mazes I would like to exist in a maze and be able to navigate it
So that I can explore it

Acceptance Criteria

* Given a maze the explorer should be able to drop in to the Start point (facing north)
* An explorer on a maze must be able to move forward
* An explorer on a maze must be able to turn left and right (changing direction the explorer is facing)
* An explorer on a maze must be able to declare what is in front of them
* An explorer on a maze must be able to declare all movement options from their given location
* An explorer on a maze must be able to report a record of where they have been in an understandable fashion


Instructions to use this program
----------------------------------

The source has a java file called uk/gov/dwp/maze/MazeStarter.java, which has the main method and acts as a gateway.

The MazeStarter can be executed without arguments from the IDE.

The MazeStarter can also optionally take an argument - the location of the Maze file

Another method to execute the code would be to export the code to an executable jar
 and running from a command line.

The Screen would display a content as below - :
```
Please make the next move in the maze.
Your current position is 4-4
Your have explored these cells [4-4]
Your available directions from the current position are [RIGHT]
Enter 'END' to exit the maze  
Enter the next position to move :
```

The User Input for the next position to move may be RIGHT

The User can continue to explore the Maze.

If the User decides to exit, the user may enter a 'END'

Note: Please note that JRE8 is required in classpath

Design
------

The Explorer is designed to be a Visitor visiting the Maze.

The Maze consists of cells. 

Every cell has an identity. Every cell has links to its neighbouring Cells.

The explorer can visit a particular cell.

The explorer can move to an available neighbouring cell using instruction
 such as LEFT, RIGHT, UP or DOWN.

The Explorer is a Visitor.

The Cell is Navigable (Visitable).

A Cell with a Wall is considered as Blocked for the Explorer

A Cell without a Wall is considered as Open for the Explorer.

The Explorer can make Moves to reach the Destination.