//------------------------------------------------------------------------
// Program Description: The objective of this game is to make the user
// guess a 4-digit number, which is randomly generated within the program.
// The player gets 10 guesses, and if he/she loses, the result is displayed
// along with a prompt to play again.

// Description of Inputs:
// All inputs are taken from keyboard.
// Answer: 4 number integer, with no repeating digits(program checks)

// Description of Outputs:
// All outputs are displayed on the screen
//
// Prompt Messages:
// Prompt for guess: "<try #>) Enter your guess: "
// Prompt for retry: "The answer was <answer>.  Do you want to play
// again(Y/N)?: "
//
// Display of Results( No specific output form )
// "Bulls = <calculated # of bulls>, Cows = <calculated # of cows>"
// If a repeated digit is present:
// "Your guess should NOT contain any repeating digits"
// If WIN: "Congratulations! You WON!!!"

// Program Assumptions:
// This program assumes that the integers are correctly entered
// as a four digit integer with non-negative integers.  The program
// itself checks for repeating digits.
// ***********************************************************************

#include<iostream.h> //cin, cout
#include<stdlib.h>   //abs(), rand()
#include<time.h>     //time

void ExtractDigits(int, int &, int &, int &, int &);
bool BullsCows(int, int, int, int, int);
int GenerateRandomNumber();
void PrintWelcomeMessage();
bool CheckRepeatingDigits(int, int, int, int);

/*************************************************************************
// Function: main(void)
// Description : Gets guesses from user, inputs it into functions,
// and outputs results to screen.
// Inputs: Reads inputs from the keyboard.
// Outputs: Writes output to the screen.
// Preconditions: <none>
// Postconditions: Prints to screen and takes input from keyboard
//***********************************************************************/
void main(void)
{
   bool checkRepeat,        // boolean flag signifying repeating digit
	   chkWin;             // boolean flag signifying a win or loss

   int d4, d3, d2, d1,      // integers for spliced 4-digit number
	  count,               // count for the for loop
	  answer,              // players 4-digit guess
	  bullcow;             // 4-digit random answer

   char chkAgain = 'Y';     // checks for players "play again" answer

//
// to setup the time of day as the initial seed for the random number
// generator.  This ensures that the same number will NOT be generated
// every time thegame is played.
//
   srand(( unsigned )time(NULL));


   PrintWelcomeMessage();               // Tells player game rules
   bullcow = GenerateRandomNumber();    // generates random number

//
// Gets user input and checks for repeating digits or win
//

   while(chkAgain == 'Y' || chkAgain == 'y')
   {
	 for(count = 0; count < 10; count++)
	 {
	    cout << count + 1 << ") Enter your guess: ";
	    cin >> answer;

//
// Takes 4-digit number and splices it
//
	    ExtractDigits(answer, d4, d3, d2, d1);

//
// Takes the four digits and checks for repetitions
//
	    checkRepeat = CheckRepeatingDigits(d4, d3, d2, d1);

//
// repeats process, if necessary
//
	    while(checkRepeat != false)
	    {
		  cout << "Your guess should NOT contain any repeating digits\n\n";

		  cout << count + 1 << ") Enter your guess: ";
		  cin >> answer;
		  ExtractDigits(answer, d4, d3, d2, d1);
		  checkRepeat = CheckRepeatingDigits(d4, d3, d2, d1);
	    }

//
// main game function
//
	    chkWin = BullsCows(bullcow, d4, d3, d2, d1);

//
// checks for a win
//

	    if(chkWin == true)
	    {
		  count = 10;
		  cout << "Congratulations! You WON!!!\n\n\nDo you want to"
			  << " play another game?(Y/N): ";
		  cin >> chkAgain;
	    }
	 }

//
// checks for a loss
//

	 if(chkWin == false)
	 {
	    cout << "\n\n\nThe answer was " << bullcow;
	    cout << "\nDo you want to play another game?(Y/N): ";
	    cin >> chkAgain;
	 }
   }
}

/*************************************************************************
// Function: ExtractDigits()
// Description: Takes 4-digit number and splices it into 4 numbers
// Inputs: A 4-digit number generated from computer or typed by user
// Outputs: 4 integers, passed by reference
// Preconditions: guessNum needs to be a non-negative, integer created by
// computer or via the keyboard and user
// Postconditions: no output to screen; d4, d3, d2, and d1 are modified
//***********************************************************************/
void ExtractDigits(/*In */ int guessNum, // 4-digit number passed in
/*Out */ int &d4,                        // spliced integers
/*Out */ int &d3,
/*Out */ int &d2,
/*Out */ int &d1)
{

   int temp;  // Temporary holding integer

//
// Splices the 4-digit integer into 4 separate integers
//

   d4 = guessNum / 1000;
   temp = guessNum - (d4 * 1000);
   d3 = temp / 100;
   temp -= (d3 * 100);
   d2 = temp / 10;
   temp -= (d2 * 10);
   d1 = temp;
}

/*************************************************************************
// Function: BullsCows()
// Description: Runs the game loop and checks for win or loss
// Inputs: The answer is entered from computer, but all other
// input is via the keyboard and user
// Outputs: All prompts and results are output to the screen, except for
// a boolean flag, cfinal, which is not outputted to screen.
// Preconditions: only non-negative integers are expected
// Postconditions: Output is displayed on screen.  No inputted variables
// are modified.  Only the boolean flag, cfinal, is modified and returned
// for other function protocol.
//***********************************************************************/
bool BullsCows(/*In */int answer, // 4-digit integer;actual answer from CPU
/*In */int d4,                    // spliced-integer guesses from user
/*In */int d3,
/*In */int d2,
/*In */int d1)
{
   int bull = 0, cow = 0;  // game result tallies
   int t1, t2, t3, t4;     // spliced actual-answer integers

   bool c1 = false,        // boolean check flags
	   c2 = false,
	   c3 = false,
	   c4 = false,
	   cfinal = false;

//
// Splices actual answer into 4 integers.
//

   ExtractDigits(answer, t4, t3, t2, t1);

//
// Checks all user-entered integers to actual-answer integers.
//

   if(d4 == t4 || d4 == t3 || d4 == t2 || d4 == t1)
   {
	 if(d4 == t4)
	 {
	    bull++;
	    c1 = true;
	    cout << "Check 1\n";
	 }
	 else
	 {
	    cow++;
	    cout << "Cow1\n";
	 }
   }


   if(d3 == t4 || d3 == t3 || d3 == t2 || d3 == t1)
   {
	 if(d3 == t3)
	 {
	    bull++;
	    c2 = true;
	    cout << "Check 2\n";
	 }else{ cow++; cout << "Cow2\n";};

   }

   if(d2 == t4 || d2 == t3 || d2 == t2 || d2 == t1)
   {
	 if(d2 == t2)
	 {
	    bull++;
	    c3 = true;
	    cout << "Check 3\n";
	 }else{ cow++; cout << "Cow3\n";};
   }

   if(d1 == t4 || d1 == t3 || d1 == t2 || d1 == t1)
   {
	 if(d1 == t1)
	 {
	    bull++;
	    c4 = true;
	    cout << "Check 4\n";
	 }else{cow++; cout << "Cow4\n";};
   }

//
// Outputs current results to screen.
//

   cout << "\nBulls = " << bull << ", Cows = " << cow << "\n";

//
// Invokes final check to determine a win or not.
//

   if(c1 == true && c2 == true && c3 == true && c4 == true)cfinal = true;

   return(cfinal);
}

/*************************************************************************
// Function: GenerateRandomNumber()
// Description: generates a random 4-digit number
// Inputs: <None>
// Outputs: a 4-digit random number; nothing displayed on the screen
// Preconditions: <None>
// Postconditions: a non-negative, 4-digit number must be created and
// transferred to other function protocol
// **********************************************************************/
int GenerateRandomNumber()
{
   int a, b, c, d, final;
   bool check = true;

//
// Check for repeating digits, none of which are over 9.
//
   while(check == true)
   {
	 a = rand() % 9;
	 b = rand() % 9;
	 c = rand() % 9;
	 d = rand() % 9;

	 check = CheckRepeatingDigits(a, b, c, d);
   }

//
// If there is no repeating digits, create one complete integer,
// and make the value positive.
//
   if(check == false)
   {
	 a *= 1000;
	 b *= 100;
	 c *= 10;

	 final = abs(a + b + c + d);
   }

   return(final);
}

/*************************************************************************
// Function: PrintWelcomeMessage()
// Description: Outputs rules to screen
// Inputs: <None>
// Outputs: <None>
// Preconditions: <None>
// Postconditions: <None>
//***********************************************************************/
void PrintWelcomeMessage()
{
   cout << "Welcome to the game of BULLS and COWS.\nThe objective"
	   << " of this game is for you to guess a 4-digit number within"
	   << " 10\nguesses.  For each guess you make, the computer responds"
	   << " by saying how close\nyour guess is to the target number."
	   << "\nIts response will be in terms of the following:\n"
	   << "BULLS = Number of common digits with exact matches.\n"
	   << "COWS = Number of common digits in the wrong position.\n";
}

/*************************************************************************
// Function: CheckRepeatingDigits()
// Description: Checks spliced integers for a repetition
// Inputs: 4-digits from keyboard or spliced-randomly generated number
// Outputs: a boolean check flag; no screen display
// Preconditions: all values must be integers and non-negative
// Postconditions: only boolean flag, ch, is modified
//***********************************************************************/
bool CheckRepeatingDigits(/*In */int d4,   // spliced integers
/*In */int d3,
/*In */int d2,
/*In */int d1)
{
   bool ch = false;  // boolean flag checking for repetition

   if(d4 == d3 || d4 == d2 || d4 == d1)ch = true;
   if(d3 == d2 || d3 == d1 || d2 == d1)ch = true;

   return ch;
}