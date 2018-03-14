package uk.gov.dwp.maze;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;
import uk.gov.dwp.maze.exception.MazeCreationException;
import uk.gov.dwp.maze.utils.MazeUtils;

import static uk.gov.dwp.maze.utils.MazeUtils.Direction.*;

class MazeStarter {
	private static final Logger LOGGER = Logger.getLogger(MazeStarter.class);
	private Maze maze;
	private Visitor explorer;
	private final String defaultMazeFileName = new File("").getAbsolutePath() + "/src/main/resources/Maze1.txt";

	private void createMaze(String mazeFileName) throws MazeCreationException {
		String fileName;
		if (mazeFileName != null)
			fileName = mazeFileName;
		else
			fileName = defaultMazeFileName;
		File file = new File(fileName);
		LOGGER.debug(file.getAbsolutePath());
		try {
			maze = MazeUtils.createMaze(file);
		} catch (MazeCreationException e) {
			LOGGER.error("The Maze File is not accessible",e);
			throw e;
		}
	}

	private Maze getMaze() {
		return maze;
	}

	private Visitor getExplorer() {
		return explorer;
	}

	private String recieveUserInput(Scanner in) {
		String currentCell = getExplorer().getCurrentCell();
		List<String> visitHistory = getExplorer().getPreviousMoves();
		List<MazeUtils.Direction> availableDirections = getMaze().availableNextSteps(getExplorer());
		System.out.println("");
		System.out.println("Please make the next move in the maze.");
		System.out.println("Your current position is " + currentCell);
		System.out.println(MessageFormat.format("# Walls, Spaces are {0}, {1} respectively", maze.getCountOfWalls(), maze.getCountOfSpaces()));
		System.out.println("Your have explored these cells " + visitHistory);
		System.out.println("Your available directions from the current position are " + availableDirections);
		System.out.println("Enter 'END' to exit the maze  ");
		System.out.println("Enter the next position to move : ");
		String userInput = in.nextLine();
		if (userInput != null
				&& !(isValidDirection(availableDirections, userInput.trim().toUpperCase())) && !userInput.trim().equals("END")) {
			System.out.println("userInput is Invalid " + userInput);
		} else if (userInput != null) {
			userInput = userInput.trim().toUpperCase();
			System.out.println("userInput is " + userInput);
		}
		return userInput;
	}
	
	private boolean isValidDirection(List<MazeUtils.Direction> availableDirections, String direction) {
		return availableDirections.stream().anyMatch(d->d.name().equals(direction));
	}

	private void operateMaze(String mazeFile) throws MazeCreationException {
		String userInput;
		createMaze(mazeFile);
		createVisitor();
		Visitor myVisitor = getExplorer();
		getMaze().permitExplorer(myVisitor);
		boolean moved;
		Scanner in = new Scanner(System.in);
		while (!("END").equals(userInput = recieveUserInput(in))) {
			moved = false;
			if (userInput.equals(UP.name()))
				moved = maze.moveUp(myVisitor);
			if (userInput.equals(DOWN.name()))
				moved = maze.moveDown(myVisitor);
			if (userInput.equals(LEFT.name()))
				moved = maze.moveLeft(myVisitor);
			if (userInput.equals(RIGHT.name()))
				moved = maze.moveRight(myVisitor);
			if (moved) {
				System.out.println("Moved " + userInput);
			} else {
				System.out.println("Not able to Move for the given input");
			}
			if (maze.reachedDestination(myVisitor)) {
				System.out.println("Well done! You have completed the game");
				maze.sayByeToVisitor(myVisitor);
				break;
			}
		}
		in.close();
		System.out.println("Thanks for exploring the Maze.");
	}

	public static void main(String[] args) {
		String mazeFile = null;
		if (args.length == 1 && args[0].trim().length() > 1) {
			mazeFile = args[0];
		}
		MazeStarter mazeStarter = new MazeStarter();
		try {
			mazeStarter.operateMaze(mazeFile);
		} catch (MazeCreationException e) {
			LOGGER.error("Unable to create a Maze ",e);
			System.err.println("Unable to create a Maze");
		}
	}

	private void createVisitor() {
		explorer = MazeUtils.createExplorer(1);
	}
}
