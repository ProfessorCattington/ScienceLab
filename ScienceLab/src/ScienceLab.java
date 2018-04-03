import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


public class ScienceLab {


	static void fairRations(int[] subjects) {

		int output = 0;
		
		for(int i = 0; i < subjects.length; i++) {
			
			if(i < subjects.length - 1 && subjects[i] % 2 != 0) {
				
				if(i < subjects.length -1) {
					
					subjects[i]++;
					subjects[i+1]++;
					output += 2;
				}
			}
			else if(i == subjects.length -1  && subjects[i] % 2 != 0) {
				
				output = -1;
			}
		}

		if(output == -1) {
			
			System.out.println("NO");
			
		}
		else {
		
			System.out.println(output);
		}
	}
	
	static String[] cavityMap(String[] grid) {
		
		String[] output = new String[grid.length];
		
		//make a 2d container out of the array of strings
		ArrayList<char[]> numArray = new ArrayList<char[]>();
		
		for(int i = 0; i < grid.length; i++) {
			
			String gridLine = grid[i];
			
			char[] numbersAsChar = gridLine.toCharArray();

			numArray.add(numbersAsChar);
			
		}
		
		//go through each element in the arraylist and check it against its neighbors
		//but first make sure this is a grid we should actually check
		if(grid.length < 3) {
			
			return grid;
		}
		
		for(int x = 0; x < numArray.size(); x++) {
				
			output[x] = "";
			
			char[] row = numArray.get(x);
			
			for(int y = 0; y < row.length; y++ ) {
				
				//we don't check border units
				if(x > 0 && x < numArray.size() -1 &&
					y > 0 && y < row.length -1) {

					//get above, below, left and right
					char above = numArray.get(x -1)[y];
					char below = numArray.get(x + 1)[y];
					char left = numArray.get(x)[y -1];
					char right = numArray.get(x)[y +1];
					
					//if all the directions are less than our x,y position then add an 'X' to our output matrix
					char position = numArray.get(x)[y];
					if(above < position && below < position && left < position && right < position) {
						
						output[x] += 'X';
					}
					else {
						
						output[x] += numArray.get(x)[y];
					}
				}
				else {
					
					output[x] += numArray.get(x)[y];
				}
			}
		}
		
		return output;
    }
	
	static String stones(int n, int a, int b) {
        
		
		String output = "";
		//seriously over thought this in previous submissions. doing permutations is unsuitable because we only care about the final values.
		//  a sequence 0 1 2 3 4 and 0 1 3 2 4 both end at the same value
		//after reviewing some of the discussions we can figure out the number of possible end values by simply multiplying the difference of a and b 
		//by i for the number of stones
		
		//check to see if we have two different values or not for a and b
		if(a == b) {
			
			output += a * n;
			return output;
		}
		
		int difference = Math.abs(a - b);
		
		int smallest = Math.min(a, b) * (n-1);
		
		for(int i = 0; i < n; i++) {
			
			output += (smallest  + (i * difference)) + " ";
		}
		
		return output;
    }
	
	static String gridSearch(String[] G, String[] P) {

		String output = "NO";
		
		//right off the bat check to see if the pattern exceeds the grid in either dimension
		int gridHeight = G.length;
		int gridWidth = G[0].length();
		
		int patternHeight = P.length;
		int patternWidth = P[0].length();
		
		if(patternHeight > gridHeight || patternWidth > gridWidth) {
			
			return output;
		}
		
		//start comparing elements of the pattern and the grid. 
		int gridLineMatchCount = 0;
		
		int maxHeight = gridHeight - patternHeight + 1;
		
		for(int i = 0; i < maxHeight; i++) {

			//get the index to start comparing properly
			char[] gridLine = G[i].toCharArray();
			char[] patternLine = P[0].toCharArray();
			
			int gridIndex = 0;
			int patternLength = patternLine.length;
			
			int maxLength = gridLine.length - patternLength +1;
			//loop through each line and compare elements of the grid with the pattern
			while(gridIndex < maxLength) {
				
				int patternCount = 0;
				int currentGridIndex = gridIndex;
				
				for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
					
					if(gridLine[currentGridIndex] == patternLine[patternIndex]) {
						
						currentGridIndex++;
						patternCount++;
					}
					else {//break out and start the while loop over from our grid index
						
						patternCount = 0;
						break;
					}

					if(patternCount == patternLength) {
						
						gridLineMatchCount = 1;

						//now make sure the rest of the pattern matches inside the grid by searching at the same index for each line
						//this avoids false positives when part of the pattern occurs or reoccurs at a different point in the grid
						for(int j = 1; j < patternHeight; j++ ) {
							
							gridLine = G[i + j].toCharArray();
							patternLine = P[j].toCharArray();
							
							boolean patternIsBroken = false;
							
							currentGridIndex = gridIndex;
							
							for(int k = 0; k < patternLine.length; k++) {
								
								if(gridLine[currentGridIndex] == patternLine[k]) {
									
									currentGridIndex++;
								}
								else {
									
									patternIsBroken = true;
									break;
								}
							}
							
							if(patternIsBroken) {
								
								gridLineMatchCount = 0;
								
								gridLine = G[i].toCharArray();
								patternLine = P[0].toCharArray();
								
								break;
							}
							
							gridLineMatchCount++;
						}
						
						if(gridLineMatchCount == P.length) {
							
							output = "YES";
						}
					}
				}
				
				gridIndex++;
			}
		}
		
		return output;
    }
	
	public static String happyLadyBugs(String b) {
	
		String output = "YES";
		
		char[] charArray = b.toCharArray();
		
		//check to see if the string was only 1 character long and if it was an underscore
		if(charArray.length== 1) {
			
			if(charArray[0] != '_') {
				
				return "NO";
			}
			else {

				return "YES";
			}
		}
		
		//move stuff into a hashmap.
		HashMap<Character, Integer> characterMap = new HashMap<Character, Integer>();
		
		for(char c : charArray) {
			
			if(characterMap.containsKey((c))){
				
				int value = characterMap.get(c);
				value++;
				characterMap.put(c, value);
			}
			else {
				
				characterMap.put(c, 1);
			}
		}
		
		//if the hashmap contains a key that only has 1 as a value and it's not an underscores then we return no
		for(Character c : characterMap.keySet()) {
			
			boolean isUnderscore = c == '_';
			
			if(characterMap.get(c) == 1 && !isUnderscore) {
				
				return "NO";
			}
		}
		
		//if the hashmap doesn't contain underscores then we need to go through the character array and see if characters are next to each other
		if(!characterMap.containsKey('_')) {
			
			ArrayList<Integer> counts = new ArrayList<Integer>();
			int counter = 1;
			//need to go through the string and make sure all similar characters are next to each other
			for(int i = 0; i < charArray.length-1; i++) {
				
				char nextChar = charArray[i+1];
				if(charArray[i] != nextChar) {
					
					counts.add(counter);
				}
				else {
					
					counter++;
				}
			}
			
			for(Integer i : counts) {
				
				if(i == 1) {
					
					return "NO";
				}
			}
		}
		
		return output;
	}
	
	 static long strangeCode(long t) {

		 long output = 0;
		 
		 long columnHeight = 3;
		 long maxPos = 3;
		 
		 for(int i = 0; ; i++) {

			 if(t <= maxPos) {

				 break;
			 }
			 else {
				 
				 columnHeight *= 2;
				 maxPos += columnHeight;
			 }

		 }
		 
		 long startingPos = columnHeight -2;
		 long difference = Math.abs(startingPos - t);
		 
		 output = columnHeight - difference;
		 
		 return output;
	    }

	 static int surfaceArea(int[][] A) {
		 	
		 int area = A.length * A[0].length * 2;
		 
		 for(int i = 0; i < A.length; i++) {
			 
			 //blocks on the borders will have their surface area added
			 boolean topOrBottomBlock = false;
			 
			 if(i == 0 || i == A.length-1) {
				 
				topOrBottomBlock = true;
			 }
			 
			 int[] row = A[i];
			 
			 for(int j = 0; j < row.length; j++) {
				 
				 boolean leftOrRightBlock = false;
				 
				 if(j == 0 || j == row.length -1) {
					 
					 leftOrRightBlock = true;
				 }
				 
				 //check for blocks on the outer perimeter
				 if(topOrBottomBlock) {
					 
					 if(i == 0) {
						 
						 area += A[i][j];
					 }
					 
					 if(i == A.length -1) {
						 
						 area += A[i][j];
					 }
				 }
				 
				 if(leftOrRightBlock) {
					 
					 if(j == 0) {
						 
						 area += A[i][j];
					 }
					 
					 if(j == row.length -1) {
						 
						 area += A[i][j];
					 }
				 }
				 
				 //check the blocks above and below
				 int neighborBlockHeight = 0;
				 if(i != 0) {
					 
					 neighborBlockHeight = A[i -1][j];
					 
					 if(neighborBlockHeight < A[i][j]) {
						 
						 area += A[i][j] - neighborBlockHeight;
					 }
				 }
				 
				 if(i != A.length -1) {
					 
					 neighborBlockHeight = A[i+1][j];
					 
					 if(neighborBlockHeight < A[i][j]) {
						 
						 area += A[i][j] - neighborBlockHeight;
					 } 
				 }

				 //check the blocks to the left and right
				 neighborBlockHeight = 0;
				 if(j != 0) {
					 
					 neighborBlockHeight = A[i][j-1];
					 
					 if(neighborBlockHeight < A[i][j]) {
						 
						 area += A[i][j] - neighborBlockHeight;
					 }
		
				 }
				 
				 if(j != row.length -1) {
					 
					 neighborBlockHeight = A[i][j+1];
					 
					 if(neighborBlockHeight < A[i][j]) {
						 
						 area += A[i][j] - neighborBlockHeight;
					 }
				 }
			 }
		 }
		 
		 return area;
	 }
	 
	 
	 //note: save states. if you detect a repeat then we are done.
	 static String[] bomberMan(int n, String[] grid) {
 
		 if(n == 1) {
			 
			 return grid;
		 }
		 
		 if (n > 8) {
			 
			 n = (n % 4) + 4;
	 	 }

		 //convert the initial O to 0 so we can increment clearly
		 for(int y = 0; y < grid.length; y++) {
			 
			 char[] row = grid[y].toCharArray();
			 
			 for(int x = 0; x < row.length; x++) {
				 
				 if(row[x] == 'O') {
					 
					 row[x] = '3';
				 }
			 }
			 
			 grid[y] = ReturnCharArrayToString(row);
		 }

		 //perform a loop for each turn/second
		 for(int i = 1; i <= n; i++) {
			 
			//decrement the timer on each bomb
			 for(int y = 0; y < grid.length; y++) {
			 
				 char[] row = grid[y].toCharArray();
				 
				 for(int x = 0; x < row.length; x++) {
					 
					 if(row[x] != '.') {
						 
						 row[x] --;
					 }
				 }
				 
				 grid[y] = ReturnCharArrayToString(row);
			 }
			 
			//if this is an even second plant bombs on empty spaces
				if(i > 1 && i % 2 == 0) {
						 
					for(int y = 0; y < grid.length; y++) {
							 
						char[] row = grid[y].toCharArray();
							 
						for(int x = 0; x < row.length; x++) {
								 
							if(row[x] == '.') {
									 
								row[x] = '3';
							}
						}
							 
					 grid[y] = ReturnCharArrayToString(row);
					 
					}
				}
			 
			//check for bombs that hit 1 and explode them
			for(int k = 0; k < grid.length; k++) {
				 
				 char[] row = grid[k].toCharArray();
				 
				 for(int j = 0; j < row.length; j++) {
					 
					 if(row[j] == '0') {
						 
						 row[j] = '.';
						 
						 //check above
						 if(k > 0) {
							 
							 char[] rowAbove = grid[k-1].toCharArray();
							 
							 if(rowAbove[j] > '0') {
								 
								 rowAbove[j] = '.';
								 grid[k-1] = ReturnCharArrayToString(rowAbove);
							 }
						 }
						 //below
						 if(k < grid.length -1) {
							 
							 char[] rowBelow = grid[k+1].toCharArray();
							 
							 if(rowBelow[j] > '0') {
								 
								 rowBelow[j] = '.';
								 grid[k+1] = ReturnCharArrayToString(rowBelow);
							 }
						 }
						 //to the left
						 if(j > 0) {
							 
							 if(row[j-1] > '0') {
								 
								 row[j -1] = '.';
							 }
						 }
						 //to the right
						 if(j < row.length -1) {
							 
							 if(row[j+1] > '0') {
								 
								 row[j+1] = '.';
							 }
						 }
						 
						 grid[k] = ReturnCharArrayToString(row);
					 }
				 }
			 }
		 }
		 
		 //change the grid into the expected output format
		 for(int y = 0; y < grid.length; y++) {
			 
			 char[] row = grid[y].toCharArray();
			 
			 for(int x = 0; x < row.length; x++) {
				 
				 if(row[x] != '.') {
					 
					 row[x] = 'O';
				 }
			 }
			 
			 grid[y] = ReturnCharArrayToString(row);
		 }
		 
		 return grid;
	 }
	
	 private static String ReturnCharArrayToString(char[] charArray) {
		 
		 String output = "";
		 
		 for(int i = 0; i < charArray.length; i++) {
			 
			 output += charArray[i];
		 }
		 
		 return output;
	 }
	 
	 static int twoPluses(String[] grid) {

		 //make the string grid into an int grid so its easier to understand.
		 int[][] intGrid = ConvertToIntMatrix(grid);
		 
		 int length = intGrid.length;
		 int width = intGrid[0].length;
		 
		 //check to see if this grid has dimensions larger than 2. If not we just return an area of 1
		 if(length == 2 || width == 2) {
			 
			 return 1;
		 }
		 
		 //spin through the grid and find pluses. make a new 'plus' object if we find a valid cross pattern		 
		 ArrayList<Plus> plusList = BuildPlusList(intGrid);

		 //go through the list of plus objects to find the two largest

		 //initialize the largest and second largest with dummy values
		 Position dummyPos = new Position(-1, -1);
		 ArrayList<Position> dummyList = new ArrayList<Position>();
		 dummyList.add(dummyPos);
		 
		 //find the largest area
		 int areaProduct = 0;
		 
		 for(int i = 0; i < plusList.size() - 1; i++) {
			 
			 Plus thisPlus = plusList.get(i);
			 
			 for(int j = i + 1; j < plusList.size(); j++) {

				 Plus nextPlus = plusList.get(j);

				 if(!thisPlus.DoesThisOverLapAnother(nextPlus)){
				
					 int thisProduct = thisPlus.area * nextPlus.area;
					 
					 if(thisProduct > areaProduct) {
						 
						 areaProduct = thisProduct;
					 }
				 }
			 }
		 }
		 
		 return areaProduct;
	 }
	 
	 static ArrayList<Plus> BuildPlusList(int[][] intGrid){
		 
		 int length = intGrid.length;
		 int width = intGrid[0].length;
		 
		//start from inside the perimeter of the grid because we can't make meaningful pluses from the edges.
		 ArrayList<Plus> plusList = new ArrayList<Plus>();
		 
		 for(int i = 1; i < length -1; i++) {
			 
			 for(int j = 1; j < width -1; j++) {
				 
				 //check to see if this a valid starting point for a plus. if so begin checking the positions around it and building a 'plus' object
				 if(intGrid[i][j] == 1) {
					 
					 ArrayList<Position> positions = new ArrayList<Position>();
					 ArrayList<Position> startingPositions = new ArrayList<Position>();
					 
					 Position startingPosition = new Position(i, j);
					 
					 positions.add(startingPosition);
					 startingPositions.add(startingPosition);
					 //this counter is used to track area of the plus and detect fully formed pluses
					 int sideLength = 0;
					 int area = 1;
					 
					 //add the starting 'plus' even though its a single position
					 Plus plus = new Plus(startingPositions, area);
					 plusList.add(plus);
					 
					 boolean buildingPlus = true;
					 
					 while(buildingPlus) {
						 
						 buildingPlus = false;
						 
						 sideLength++;
						 
						 int sidesCounter = 0;

						 //this chunk of checks can probably be shortened into a long gross andif check
						 //above
						 if(i - sideLength >= 0 && intGrid[i - sideLength][j] != 0) {
							
							 sidesCounter++;
						 }
						 //below
						 if(i + sideLength <= length -1 && intGrid[i + sideLength][j] != 0) {
							 
							 sidesCounter++;
						 }
						 //left
						 if(j - sideLength >= 0 && intGrid[i][j - sideLength] != 0) {
							 
							 sidesCounter++;
						 }
						 //right
						 if(j + sideLength <= width -1 && intGrid[i][j + sideLength] != 0) {
							 
							 sidesCounter++;
						 }

						 if(sidesCounter == 4) {
							 
							 //valid plus. continue incrementing and checking outward.
							 positions.add(new Position(i - sideLength, j));
							 positions.add(new Position(i + sideLength, j));
							 positions.add(new Position(i, j - sideLength));
							 positions.add(new Position(i, j + sideLength));

							 
							 ArrayList<Position> newPositions = new ArrayList<Position>();
							 
							 for(Position pos : positions) {
								 
								 newPositions.add(pos.CopyThis());
							 }

							 area = sidesCounter * sideLength + 1;
							 
							 Plus newPlus = new Plus(newPositions, area);
							 
							 plusList.add(newPlus);
							 
							 buildingPlus = true;
						 }
					 }
				 }
			 }
		 }
			 
		 return plusList;
	 }
	 
	 static int[][] ConvertToIntMatrix(String[] grid){
		 
		 int length = grid.length;
		 int width = grid[0].length();
		 
		 int[][] intGrid = new int[length][width];
		 
		 for(int i  = 0; i < length; i++) {
			 
			 String line = grid[i];
			 
			 for(int j = 0; j < width; j++) {
				 
				 if(line.charAt(j) =='B') {
					 
					 intGrid[i][j] = 0;
				 }
				 else {
					 
					 intGrid[i][j] = 1;
				 }
				  
			 }
		 }
		 
		 return intGrid;
	 }
	 
	 static void almostSorted(int[] arr) {

		 int[] sortedArray = new int[arr.length];
		 
		 boolean canSwap = true;
		 boolean canReverse = true;
		 
		 int startingPos = 0;
		 int endingPos = 0;
		 
		 int pos1 = 0;
		 int pos2 = 0;
		 
		 System.arraycopy(arr, 0, sortedArray, 0, arr.length);
		 
		 //make a copy of the original array for comparison. sort it and check to see if the two arrays are the same
		 Arrays.sort(sortedArray);
		 
		//if they are then we don't need to go any further
		 if(AreTheseArraysTheSame(arr, sortedArray)) {
			 
			System.out.println("yes");
		 }
		 //if the array is only 2 values long then we can probably just swap the two values
		 else if(arr.length == 2) {
			 
			 canReverse = false;
			 pos2 = 1;
		 }
		 else {

			 //check to see if there's a reversed sub array and get the starting position
			 for(int i = 0; i < arr.length -1; i++) {
				 
				 if(arr[i +1] < arr[i]) {
					 
					 startingPos = i;
					 break;
				 }
			 }
			 
			 //find the ending position
			 for(int i = startingPos; i < arr.length -1; i++) {
				 
				 if(arr[i + 1] > arr[i]) {
					 
					 endingPos = i;
					 break;
				 }

			 }
			//reverse the sub array between the two positions and compare it to the sorted array
			int[] newArray = new int[arr.length];
			System.arraycopy(arr, 0, newArray, 0, arr.length);
			
			int j = endingPos;
			for(int i = startingPos; i <= endingPos; i++) {
				
				newArray[i] = arr[j];
				j--;
			}
			
			//compare the new array to teh original sorted array to see if they are the same
			if(!AreTheseArraysTheSame(newArray, sortedArray)){
				
				canReverse = false;
			}
			 
			 //check to see if we can correct the array by swapping two positions
			 
			 
			 for(int i = 0; i < arr.length -1; i++) {
				 
				 if(arr[i + 1] < arr[i]) {
					 
					 pos1 = i;
					 break;
				 }
			 }
			 for(int i  = pos1; i < arr.length -1; i++) {
	
				 if(arr[i + 1] < arr[i]) {
					 
					 pos2 = i + 1;
				 }
			 }
			 
			 //swap the values at the positions and compare the arrays again
			int[] swapArray = new int[arr.length];
			System.arraycopy(arr, 0, swapArray, 0, arr.length);
			
			int temp = swapArray[pos1];
			swapArray[pos1] = swapArray[pos2];
			swapArray[pos2] = temp;
			
			if(!AreTheseArraysTheSame(swapArray, sortedArray)) {
				
				canSwap = false;
				
			}
		 }
		 
		 if(canSwap || (canSwap && canReverse)) {
				
				System.out.println("yes");
				System.out.println("swap " + (pos1+1) + " " + (pos2+1));
			}
			else if(!canSwap && !canReverse) {
				 
				 System.out.println("no");
			}
			else if(canReverse) {
				
				System.out.println("yes");
				System.out.println("reverse " + (startingPos +1) + " " + (endingPos+ 1));
			}
	}
	 
	public static boolean AreTheseArraysTheSame(int[] arr1, int[] arr2) {
		
		boolean same = true;
		
		for(int i = 0; i < arr1.length; i++) {
			 
			 if(arr1[i] != arr2[i]) {
				 
				 same = false;
			 }
		 }
		 
		return same;
	}
	
	//get the max number of 'turns' for a full rotation of each layer of the matrix
	public static ArrayList<Integer> GetPerimetersOfMatrix(int[][] matrix){
		
		ArrayList<Integer> perimeters = new ArrayList<Integer>();
		
		int height = matrix.length / 2;
		int width = matrix[0].length /2;
		
		int smaller = height > width ? width : height;
		
		for(int i = 0; i < smaller; i++) {
			
			int perimeter = 0;
			
			perimeter += (matrix[i].length - (2 * i)) * 2; //horizontal length
			perimeter += (matrix.length - (2 * i)) * 2; //vertical length
			
			perimeter -= 4; //subtract 4 to account for the corner positions
			
			perimeters.add(perimeter);
		}
		
		return perimeters;
	}
	
	//since r can go to 10^9 let's get rid of all the unnecessary rotations
	public static ArrayList<Integer> GetRotationsForEachPerimeter(ArrayList<Integer> perimeters, int rotations){
		
		ArrayList<Integer> actualRotations = new ArrayList<Integer>();
		
		for (int i = 0; i < perimeters.size(); i++) {
			
			int perimeterSize = perimeters.get(i);
			
			int actualRotation = 0;
			
			if(rotations >=  perimeterSize) {
				
				actualRotation = rotations % perimeterSize;
			}
			else {
				
				actualRotation = rotations;
			}
			
			actualRotations.add(actualRotation);
		}
		
		return actualRotations;
	}
	
	//turn the perimeters into arrays that we will reconstruct into a matrix again
	public static ArrayList<ArrayList<Integer>> MakePerimetersAsArrays(int[][] matrix, ArrayList<Integer> perimeterSizes){
		
		int numberOfPerimeters = perimeterSizes.size();
		
		ArrayList<ArrayList<Integer>> perimetersAsArrays = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < numberOfPerimeters; i++) {
			
			ArrayList<Integer> perimeter = new ArrayList<Integer>();
			
			//top row
			for(int j = i; j <= (matrix[0].length - 1) - i; j++){
				
				perimeter.add(matrix[i][j]);
			}
			//right column
			for(int j = i + 1; j <= (matrix.length -1) - i; j++) {
				
				int rightIndex = (matrix[0].length -1) -i;
				perimeter.add(matrix[j][rightIndex]);
			}
			//bottom row
			for(int j = (matrix[0].length - 2) - i; j >= i ; j--) {
				
				int bottomIndex = (matrix.length - 1) - i;
				perimeter.add(matrix[bottomIndex][j]);
			}
			//left column
			for(int j = (matrix.length -2) - i; j >= i + 1; j--) {
			
				perimeter.add(matrix[j][i]);
			}
			perimetersAsArrays.add(perimeter);
		}
		
		return perimetersAsArrays;
	}
	
	//perform the rotations on each associated perimeter
	public static ArrayList<ArrayList<Integer>> RotatePerimeters(ArrayList<ArrayList<Integer>> perimetersAsArrays, ArrayList<Integer> actualRotations) {
		
		ArrayList<ArrayList<Integer>> rotatedPerimeters = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < perimetersAsArrays.size(); i++) {
			
			int rotationStart = actualRotations.get(i);
			
			ArrayList<Integer> newPerimeter = new ArrayList<Integer>();
			
			ArrayList<Integer> perimeter = perimetersAsArrays.get(i);
			
			if(rotationStart > 0) {

				for(int j = rotationStart; j < perimeter.size(); j++) {
					
					newPerimeter.add(perimeter.get(j));
				}
				
				for(int j = 0; j < rotationStart; j++) {
					
					newPerimeter.add(perimeter.get(j));
				}
			}
			else {
				
				for(int j = 0; j < perimeter.size(); j++) {
					
					newPerimeter.add(perimeter.get(j));
				}
			}
			
			rotatedPerimeters.add(newPerimeter);
		}
			
		return rotatedPerimeters;
	}
	
	//rebuild the perimeters back into a matrix of ints
	public static int[][] RebuildRotatedPerimeters(int[][] matrix, ArrayList<ArrayList<Integer>> rotatedPerimeters){
		
		int height = matrix.length;
		int width = matrix[0].length;
		
		int[][] newMatrix = new int[height][width];
		
		for(int i = 0; i < rotatedPerimeters.size(); i++) {
			
			int index = 0;
			ArrayList<Integer> perimeter = rotatedPerimeters.get(i);
			//top rows
			for(int j = i; j <= (width -1) - i; j++) {
				
				newMatrix[i][j] = perimeter.get(index);
				index++;
			}
			//right columns
			for(int j = i + 1; j <= (height -1) - i; j++) {
				
				int rightIndex = (width - 1) - i;
				newMatrix[j][rightIndex] = perimeter.get(index);
				index++;
			}
			//bottom rows
			for(int j = (width - 2) - i; j >= i; j--) {
				
				int bottomIndex = (matrix.length - 1) - i;
				newMatrix[bottomIndex][j] = perimeter.get(index);
				index++;
			}
			//left columns
			for(int j = (height -2) - i; j >= i +1; j--) {
			
				newMatrix[j][i] = perimeter.get(index);
				index++;
				
			}
		}
		
		return newMatrix;
	}
	
	public static void PrintRotatedMatrix(int[][] rotatedMatrix) {
		
		for(int i = 0; i < rotatedMatrix.length; i++) {
			
			String line = "";
			for(int j = 0; j < rotatedMatrix[i].length; j++) {
				
				line += rotatedMatrix[i][j] + " ";
			}
			
			System.out.println(line);
		}
	}
	
	static void matrixRotation(int[][] matrix, int rotations) {


		ArrayList<Integer> perimeterSizes = GetPerimetersOfMatrix(matrix);
		ArrayList<Integer> actualRotations = GetRotationsForEachPerimeter(perimeterSizes, rotations);
		ArrayList<ArrayList<Integer>> perimetersAsArrays = MakePerimetersAsArrays(matrix, perimeterSizes);
		
		ArrayList<ArrayList<Integer>> rotatedPerimeters = RotatePerimeters(perimetersAsArrays, actualRotations);
		
		int[][] rotatedMatrix = RebuildRotatedPerimeters(matrix, rotatedPerimeters);
		
		PrintRotatedMatrix(rotatedMatrix);
    }
	
	static String super_reduced_string(String s){
		
		String output = "";
		
		output = reduceTheString(s);
		
		if(output.equals("")) {
			
			output = "Empty String";
		}
		return output;
    }
	
	static String reduceTheString(String s) {
		
		String output = "";
		
		for(int i = 0; i < s.length(); i++) {
			
			if((i < s.length() -1) && s.charAt(i) == s.charAt(i+1)) {
				
				i+=1;
			}
			else if(i == s.length() -1 && s.charAt(i -1) != s.charAt(i)) {
				
				output+= s.charAt(s.length() -1);
			}
			else {
				
				output += s.charAt(i);
			}
		}
		
		if(!output.equals(s)) {
			
			output = reduceTheString(output);
		}
		
		return output;
	}
	
	static int camelcase(String s) {

		int counter = 0;

		for(int i = 0; i < s.length(); i++) {
			
			char upperCase = Character.toUpperCase(s.charAt(i));
			
			if(upperCase == s.charAt(i)) {
				
				counter++;
			}
		}
		
		return counter;
    }
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
        String s = in.next();
        int result = camelcase(s);
        System.out.println(result);
        in.close();
		
		/*Scanner in = new Scanner(System.in);
        String s = in.next();
        String result = super_reduced_string(s);
        System.out.println(result);*/
		
		/*Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int n = in.nextInt();
        int r = in.nextInt();
        int[][] matrix = new int[m][n];
        for(int matrix_i = 0; matrix_i < m; matrix_i++){
            for(int matrix_j = 0; matrix_j < n; matrix_j++){
                matrix[matrix_i][matrix_j] = in.nextInt();
            }
        }
        matrixRotation(matrix, r);
        in.close();*/
		
		/*
		Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] arr = new int[n];
        for(int arr_i = 0; arr_i < n; arr_i++){
            arr[arr_i] = in.nextInt();
        }
        almostSorted(arr);
        in.close();*/
		
		/*Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        String[] grid = new String[n];
        for(int grid_i = 0; grid_i < n; grid_i++){
            grid[grid_i] = in.next();
        }
        int result = twoPluses(grid);
        System.out.println(result);
        in.close();*/
		
		/*Scanner in = new Scanner(System.in);
        int r = in.nextInt();
        int c = in.nextInt();
        int n = in.nextInt();
        String[] grid = new String[r];
        for(int grid_i = 0; grid_i < r; grid_i++){
            grid[grid_i] = in.next();
        }
        String[] result = bomberMan(n, grid);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + (i != result.length - 1 ? "\n" : ""));
        }
        System.out.println("");


        in.close();*/
		
		/*Scanner in = new Scanner(System.in);
        int H = in.nextInt();
        int W = in.nextInt();
        int[][] A = new int[H][W];
        for(int A_i = 0; A_i < H; A_i++){
            for(int A_j = 0; A_j < W; A_j++){
                A[A_i][A_j] = in.nextInt();
            }
        }
        int result = surfaceArea(A);
        System.out.println(result);
        in.close();*/
		
		/*Scanner in = new Scanner(System.in);
        long t = in.nextLong();
        long result = strangeCode(t);
        System.out.println(result);
        in.close();*/
		
		/*Scanner in = new Scanner(System.in);
        int Q = in.nextInt();
        for(int a0 = 0; a0 < Q; a0++){
            int n = in.nextInt();
            String b = in.next();
            
            String result = happyLadyBugs(b);
            System.out.println(result);
        }*/
		
		/*Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int R = in.nextInt();
            int C = in.nextInt();
            String[] G = new String[R];
            for(int G_i = 0; G_i < R; G_i++){
                G[G_i] = in.next();
            }
            int r = in.nextInt();
            int c = in.nextInt();
            String[] P = new String[r];
            for(int P_i = 0; P_i < r; P_i++){
                P[P_i] = in.next();
            }
            String result = gridSearch(G, P);
            System.out.println(result);
        }
        in.close();*/
		
		/*Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for(int a0 = 0; a0 < T; a0++){
        	
            int n = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            
            String result = stones(n, a, b);
            System.out.print(result);
            System.out.println("");


        }
        in.close();*/
		
		
		/*Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String[] grid = new String[n];
        for(int grid_i = 0; grid_i < n; grid_i++){
            grid[grid_i] = in.next();
        }
        String[] result = cavityMap(grid);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + (i != result.length - 1 ? "\n" : ""));
        }
        System.out.println("");


        in.close();*/
        
		// TODO Auto-generated method stub

		/*Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int[] B = new int[N];
        for(int B_i = 0; B_i < N; B_i++){
            B[B_i] = in.nextInt();
        }
        
        fairRations(B);
        
        in.close();*/
		
	}

}
