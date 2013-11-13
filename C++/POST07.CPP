//************************************************************************
// Programmer      :  Thomas Lepage
// Language        :  C++
// Compiler        :  g++
// Platform        :  OMEGA
// Assignment      :  Lab Assignment No.7
// Assigned        :  Wednesday, October 25, 2000
// Due             :  Wednesday, November 1, 2000
// Filed as        :  post07.cpp
// History         :  Sunday, October 29, 2000 original, TWL
//
//------------------------------------------------------------------------
// Program Description:  This program allows the user to run a menu
// that generates an array of random integers, sort the array in ascending
// order, descending order, search for a number in the array, and quit.
//
// Description of Inputs:
// All inputs are taken from keyboard.
// Choice (menu) <integer>
// Number (for search) <integer>
//
// Description of Outputs:
// All outputs are displayed on the screen
//
// Prompt Messages:
// Prompt Menu "(menu)"
// Prompt for Choice "Choice-> "
// Prompt for number to search "Enter number to search for: "
//
// Display of Results( No specific output form )
// "Unorganized Array: " <array elements>
// "Sort Ascending: " <array elements>
// "Sort Descending: " <array elements>
// "The number, <number>, exists at array position <t>"
// "That number doesn't exist in this array."
//
// Program Assumptions:
// This program assumes that the values entered are integers.
//
// ***********************************************************************

#include <iostream.h> //cin, cout
#include <stdlib.h> //rand, srand
#include <time.h> // time

#define ARRAY_SIZE 15

enum bool{ FALSE, TRUE };

void Insertion_Sort(int *, int, bool);
void Search_Array(int *, int);

/*************************************************************************
// Function: main(void)
// Description : Gets input from user, passes it into functions,
// and puts certain outputs to the screen.
// Inputs: Reads inputs from the keyboard.
// Outputs: Writes output to the screen.
// Preconditions: <none>
// Postconditions: Prints to screen and takes input from keyboard
//***********************************************************************/
void main(void)
{
   int array[ARRAY_SIZE], length = ARRAY_SIZE, choice, t, number;
   bool Ascending;

   srand(time(NULL));    // invokes random generation

//
// Run menu until exit condition is entered.
//

   while(choice != 5)
   {
	 cout << "\n\n---Insertion Sort---\n\n";
	 cout << "1. Generate Array\n2. Sort Ascending\n3. Sort Descending\n"
		 << "4. Search\n5. Quit\n\n";
	 cout << "Choice-> ";
	 cin >> choice;


	 switch(choice)
	 {
//
// Generate random numbers to fill array.
//
	    case 1 :
			    for(t = 0; t < ARRAY_SIZE; t++)
			    {
				  if(t <= 6)
				  {
					array[t] = (((rand() % 40) + 10) * -1);
				  }
					else
					{
					   array[t] = ((rand() % 50) + 50);
					}
				  }

				 cout << "\nUnorganized Array: ";

				 for(t = 0; t < ARRAY_SIZE; t++)
				 {
				    cout << array[t] << " ";
				 }
				 break;

//
// Sort array into an ascending order.
//

	    case 2 :  cout << "\nSort Ascending...\n";
			    Insertion_Sort(array, length, TRUE);
				  for(t = 0; t < ARRAY_SIZE; t++)
				  {
					cout << array[t] << " ";
				  }
			    break;

//
// Sort array into a descending order.
//

		case 3 :  cout << "\nSort Descending...\n";
				Insertion_Sort(array, length, FALSE);
				   for(t = 0; t < ARRAY_SIZE; t++)
				   {
					 cout << array[t] << " ";
				   }
				break;

//
// Prompt user to enter a number, function searches for it in array.
//

		case 4 :  cout << "\nEnter number to search for: ";
				cin >> number;
				Search_Array(array, number);
				break;

//
// Say goodbye.
//

		case 5 :  cout << "\nGoodbye.";
				break;

		default :  cout << "\nEnter a valid menu choice.\n";
				break;
	 }
   }

}

/*************************************************************************/
// Function: Insertion_Sort()
// Description: sorts a passed array into ascending or descending order
// Inputs: pointer to array, length parameter, and flag
// Outputs: passes organized array to caller
// Preconditions: array must have values in elements
// Postconditions: sorted array
/*************************************************************************/
void Insertion_Sort(/*InOut*/int *array,
				/*In*/int length,
				/*In*/bool Ascending)
{
   int index, temp, k, done;

   for(index = 0; index < length; index++)
   {
	 temp = array[index];
	 k = index;
	 done = 0;

	 while((k >= 1) && (!done))
	 {
//
// Determine whether ascending or descending
//

	    if((temp < array[k - 1]) && (Ascending))
	    {
		  array[k] = array[k - 1];
		  k = k - 1;
	    }else if((temp > array[k - 1])&&(!Ascending))
	    {
		  array[k] = array[k - 1];
		  k = k - 1;
	    }else
	    {
		  done = 1;
	    }
	 }
   array[k] = temp;
   }
}

/*************************************************************************/
// Function: Search_Array()
// Description: searches array for entered number
// Inputs: pointer to array, and user-entered number
// Outputs: prints whether number is present or not
// Preconditions: number must be an integer
// Postconditions: <none>
/*************************************************************************/
void Search_Array(/*InOut*/int *array,
			   /*In*/int number)
{
   int t, count = 0;

//
// Run through array and check for number.
//

   for(t = 0; t < ARRAY_SIZE; t++)
   {
	 if(array[t] == number)
	 {
	    cout << "The number, " << number << ", exists at array"
		    << " position " << t << "\n";
	    count++;
	 }
   }
   if(!count)cout << "That number doesn't exist in this array.\n";
}
