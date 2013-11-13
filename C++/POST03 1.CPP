//************************************************************************
// Programmer      :  Thomas Lepage
// Language        :  C++
// Compiler        :  g++
// Platform        :  OMEGA
// Assignment      :  Lab Assignment No.3
// Assigned        :  Wednesday, October 04, 2000
// Due             :  Wednesday, October 11, 2000
// Filed as        :  POST04.cpp
// History         :  Wednesday, October 04, 2000 original, TWL
//
//------------------------------------------------------------------------
// Program Description: The program runs a menu system allowing the user
// solve and evaluate the quadratic equation (a*x^2 + b*x + c) by
// entering the coefficient values (solve) or by entering the initial,
// final, and increment values of x (evaluate).  The program also allows
// the user to evaluate a polynomial equation to an arbitrary degree by
// inputting the initial x value and the coefficients of x.
//
// Description of Inputs:
// All inputs are taken from keyboard.
// Coefficients: A, B, C <integers>
// X values: integers
// X increment: integer
// X final: integer
//
// Description of Outputs:
// All outputs are displayed on the screen
//
// Prompt Messages:
// Prompt for coefficients: "Please input the coefficient (a, b, c): "
// Prompt for x values: Please input the initial, final, and increment
// values for x: "
// Prompt for initial x: "Please input the value for x: "
// Prompt for x coefficients(polynomial): "Enter the coefficient for x^:"
// Prompt for continue or exit: "0 - continue; 1 - exit"
//
// Display of Results( No specific output form )
// If imaginary value error: "Error: b*b - 4*a*c < 0, the solution
// involves an imaginary component!"
// If x initial < x final error: "The final value of x is less than its
// initial value!"
// "The results of the equation (a)*x^2 + (b)*x + (c) = 0 are:
// "x1 = #, x2 = #; if x1 and x2 are equal, only one x value is printed
// "When x = #: f(x) = #"
// "The value of the polynomial function is: # "
//
// Program Assumptions:
// This program assumes that the values entered are integers.
//
// ***********************************************************************

#include <iostream.h> // cout, cin
#include <math.h>     // sqrt(), pow()
#include <conio.h>    // clrscr(), getch()
#include <iomanip.h>  // setprecision()

int Menu();
void solveQuadratic();
void evalQuadratic();
void evalPolynomial();

/*************************************************************************
// Function: main(void)
// Description : Gets input from user, passes it into functions,
// and puts certain outputs to the screen.
// Inputs: Reads inputs from the keyboard.
// Outputs: Writes output to the screen.
// Preconditions: <none>
// Postconditions: Prints to screen and takes input from keyboard
//***********************************************************************/
void main( void )
{
   clrscr();
   int menuChoice;              // holds users menu choice

   do
   {
	 menuChoice = Menu();      // runs menu function and returns choice

	 switch(menuChoice)        // runs functions in correlation to choice
	 {
	    case 1:  cout << "\nSolving the quadratic equation "
				   << "a*x^2 + b*x + c = 0...\n";

			   solveQuadratic();       // solves quadratic
			   break;

	    case 2:  cout << "\nEvaluating the quadratic function"
				   << " f(x) = a*x^2 + b*x + c...\n";

			   evalQuadratic();       // evaluates quadratic
			   break;

	    case 3:  cout << "Evaluating the polynomial function\n";

			   evalPolynomial();      // evaluates polynomial
			   break;

	    case 9:  cout << "Goodbye!";    // exits program
			   break;

	    default: cout << "Reenter a valid menu choice\n"; // if bad value
			   break;
	 }

   }while(menuChoice != 9);   // checks for exit condition

   getch();
}

//************************************************************************
// Function: Menu()
// Description: Prompts user to enter a menu command
// Inputs: All inputs are taken from keyboard
// Outputs: returns user choice to main loop
// Preconditions: <none>
// Postconditions: returns menu choice
//************************************************************************
int Menu()
{
   int choice;

//
// Prompt user for a menu choice.
//
   cout << "\nMenu:\n\n"
	   << "1 Solve quadratic equations\n"
	   << "2 Evaluate quadratic functions\n"
	   << "3 Evaluate polynomial functions\n"
	   << "9 Exit\n"
	   << "? ";

   cin >> choice;
   return( choice );
}

/*************************************************************************
// Function: solveQuadratic()
// Description: Performs the calculations and prints the results
// Inputs: All input is taken from the keyboard
// Outputs: All printed to screen
// Preconditions: <none>
// Postconditions: prints results to screen
//***********************************************************************/
void solveQuadratic()
{
   int a, b, c;          // coefficient values
   double x1,            // x values
		x2,
		temp;          // temporary value holder

//
// Prompt user for input.
//
   cout << "\nPlease input the coefficient a: ";
   cin >> a;
   cout << "\nPlease input the coefficient b: ";
   cin >> b;
   cout << "\nPlease input the coefficient c: ";
   cin >> c;

   if(a == 0)cout << "\nError: the equation is not quadratic (a = 0)\n";

//
// Calculate both x values.
//
   else
   {
	  x1 = ( -b + sqrt((b*b) - (4 * a * c))) / ( 2 * a );
	  x2 = ( -b - sqrt((b*b) - (4 * a * c))) / ( 2 * a );

	  temp = ((b*b) - (4 * a * c));

	  if(temp < 0)
		cout << "\nError: b*b - 4*a*c < 0, "
			<< "the solution involves an imaginary component!\n";

//
// Output results to screen.
//
	  else
	  {
		if(x1 == x2)
		{
		   cout << "\nThe result for the equation "
			   << (double) a << "*x^2 + " << (double) b
			   << "*x + " << (double) c << " = 0 is:\n"
			   << "x = " << x1;
		}
		else
		{
		    cout << "\nThe results for the equation "
			   << (double) a << "*x^2 + " << (double) b
			   << "*x + " << (double) c << " = 0 are:\n"
			   << "x1 = " << x1 << ", x2 = " << x2 << "\n\n";
		}
	  }
   }
}

//***********************************************************************/
// Function: evalQuadratic()
// Description: Performs the calculations and prints results on screen
// Inputs: All inputs are taken from keyboard
// Outputs: All results are printed to screen
// Preconditions: <none>
// Postconditions: prints results to screen
//***********************************************************************/
void evalQuadratic()
{
   int a, b, c,       // coefficient values
	  xInitial,      // initial x value
	  xIncrement,    // incremental x value
	  xFinal,        // final x value
	  check = 1;     // condition check

   double y;          // y value

//
// Prompt user for input.
//
	 cout << "\nPlease input the coefficient a: ";
	 cin >> a;
	 cout << "\nPlease input the coefficient b: ";
	 cin >> b;
	 cout << "\nPlease input the coefficient c: ";
	 cin >> c;

	 while(check == 1)
	 {
	    cout << "\nPlease input the initial, final,"
		    << " and increment values for x: ";

	    cin >> xInitial >> xFinal >> xIncrement;

	    if(xInitial > xFinal)
	    {
		  cout << "\nThe final value of x is less than"
			  << " its initial value!\n";

		  check = 1;
	    }
	    else check = 0;
	 }

//
// Calculate y value.
//
	 while(xInitial <= xFinal)
	 {
	    y = (a * (xInitial * xInitial)) + (b * xInitial) + c;

//
// Output results to screen.
//
	    cout << "\nWhen x = " << xInitial << ", f(x) = " << y << "\n\n";

	    xInitial += xIncrement;
	 }
}

//***********************************************************************/
// Functions: evalPolynomial()
// Description: Performs calculations and returns results to screen
// Inputs: All inputs are taken from keyboard
// Outputs: All results are printed to screen
// Preconditions: <none>
// Postconditions: results are printed to screen
//***********************************************************************/
void evalPolynomial()
{
   int xInitial,       // initial x value
	  xCoefficient,   // x coefficient value
	  check = 0,      // condition checker
	  t = 0;          // counter

   double x = 0;       // x value

//
// Prompt the user for input
//

   while(check != 1)
   {
	 cout << "\nPlease input the value for x: ";
	 cin >> xInitial;

	 do
	 {
	    cout << "Enter the coefficient for x^" << t << ": ";
	    cin >> xCoefficient;

//
// Prompt user for continuance.
//
	    if(xCoefficient == 0)
	    {
		  cout << "\nContinue? 0 - continue; 1 - exit: ";
		  cin >> check;
//
// Calculate x value.
//
	    }else
	    {
		  x += ((xCoefficient)*(pow(xInitial, t))); // A * (x^t)
		  t++;
	    }

	 }while(check != 1);

//
// Output results to screen.
//
	 cout << "\nThe value of the polynomial function is " << x << ".\n\n";
   }
}