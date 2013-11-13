//************************************************************************
// Programmer      :  Thomas Lepage
// Language        :  C++
// Compiler        :  g++
// Platform        :  OMEGA
// Assignment      :  Lab Assignment No.6
// Assigned        :  Wednesday, October 18, 2000
// Due             :  Wednesday, October 25, 2000
// Filed as        :  post06.cpp
// History         :  Tuesday, October 24, 2000 original, TWL
//
//------------------------------------------------------------------------
// Program Description: This program allows a user to enter an integer
// and the program prints it backwards.
//
// Description of Inputs:
// All inputs are taken from keyboard.
// Number: integer
//
// Description of Outputs:
// All outputs are displayed on the screen
//
// Prompt Messages:
// "Enter a number" <integer>
//
// Display of Results( No specific output form )
// PrintReverse(number): Output #, then call PrintReverse(#)...
// PrintReverse(number): returns.
//
// Program Assumptions:
// This program assumes that the integers are correctly entered.
// ***********************************************************************
#include <iostream.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

void PrintReverse( char *, char **, int, int );

/*************************************************************************
// Function: main(void)
// Description : Gets number from user, inputs it into a function,
// and outputs results to screen.
// Inputs: Reads inputs from the keyboard.
// Outputs: Writes output to the screen.
// Preconditions: <none>
// Postconditions: Prints to screen and takes input from keyboard
//***********************************************************************/
void main(void)
{
   char number[20], *numptr;  // char string, pointer to string
   int UserNumber, length;    // user number, and string length

//
// Get user input.
//
   cout << "Enter an integer: ";
   cin >> UserNumber;

//
// Determine string length.
//
   sprintf(number, "%d", UserNumber);
   length = strlen(number);

//
// Set pointer equal to last entered number and call function
// recursively.
//
   numptr = &number[length - 1];
   PrintReverse(number, &numptr, length - 1, length - 1);

}

/*************************************************************************/
// Function: PrintReverse()
// Description: takes user-entered number, converts it into a string,
// prints the number backwards, and outputs it to screen.
// Inputs: starting pointer, double pointer to number array, a counter
// and length of string variable
// Outputs: all outputs are printed to screen
// Preconditions: pointers must point to valid address
// Postconditions: <none>
/*************************************************************************/
void PrintReverse( /*In*/char *start,
                   /*In*/char **number,
                   /*In*/int counter,
                   /*In*/int length )
{
   int j = 0;

//
// Get starting position, and begin printing the number backwards
//
   if(counter >= 0)
   {
      cout << "\nPrintReverse(";

      for(j = 0; j <= counter;j++)
      {
         cout << start[j];
      }

      cout << "): Output " << **number;

      (*number)--;


//
// Set base case and call function recursively
//
      if((counter - 1) > -1)
      {
         cout << ", then call ";
         PrintReverse(start, number, counter - 1, length);
      }
   }
   cout << "\nPrintReverse(";

   for(j = 0; j <= counter;j++)
   {
      cout << start[j];
   }
   cout << "): returns.\n";
}

