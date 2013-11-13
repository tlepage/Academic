//************************************************************************
// Programmer      :  Thomas Lepage
// Language        :  C++
// Compiler        :  g++
// Platform        :  OMEGA
// Assignment      :  Lab Assignment No.8
// Assigned        :  Wednesday, November 1, 2000
// Due             :  Wednesday, November 8, 2000
// Filed as        :  post07.cpp
// History         :  Sunday, November 6, 2000 original, TWL
//
//------------------------------------------------------------------------
// Program Description: This program is a rewrite of three of the basic
// C++ string library functions: strcat, substr, and strpbrk.  All three
// are done in two different forms, array notation and pointer notation.
//
// Description of Inputs:
// Victor's predefined strings
//
// Description of Outputs:
// All outputs are displayed on the screen
//
// Prompt Messages:
// <none>
//
// Display of Results( No specific output form )
// <main loop>
// "The resulting character using mystrpbrkPN is :"
// "The resulting character using mystrpbrkAN is :"
// Program Assumptions:
// This program assumes that the strings entered are valid.
//
// ***********************************************************************

#include <iostream.h>
#include <conio.h>

int my_strlenPN(const char *s);

//1.)function myStrpbrkPN;
//myStrpbrkPN returns a pointer to the first occurrence in string s1
//of any character from string s2, or a NULL pointer if no character
//from s2 exists in s1. This function must be implemented in pointer
//and pointer arithmetic notations.

char * myStrpbrkPN(char *s1, char *s2);

//2.)function myStrpbrkAN:
//myStrpbrkAN returns a pointer to the first occurrence in string s1
//of any character from string s2, or a NULL pointer if no character
//from s2 exists in s1. This function must be implemented in array notation.
char * myStrpbrkAN(char s1[ ], char s2[ ]);


//3. & 4.)function myStrcat:
//given 2 strings s1, s2, append s2 to s1, result in a new string,
//and print out the new string
//Note: Similarly, do this in both notation as described in 1) & 2)
void myStrcatPN(char* s1, char* s2);
void myStrcatAN(char s1[], char s2[]);


//5 & 6.)function mySubString:
//given 2 strings, s1, s2 (in that order), return true if s1 is
//substring of s2, return false otherwise.
//Note: Similarly, do this in both notation as described in 1) & 2)
int mySubStringPN(char* s1, char* s2);
int mySubStringAN(char s1[], char s2[]);

void main(void)
{
   cout<<"******************************************************\n";
   cout<<"* The following is testing strcatAN function\n";
   cout<<"******************************************************\n";

// Note : Print your answer out in your myStrcat function

   myStrcatAN("berry", "strawberry");

   myStrcatAN("rry", "strawberry");

   myStrcatAN("rryy", "strawberry");

   cout<<"\n\n";

   getch();

   cout<<"******************************************************\n";
   cout<<"* The following is testing strcatPN function\n";
   cout<<"******************************************************\n";

   myStrcatPN("berry", "strawberry");

   myStrcatPN("rry", "strawberry");

   myStrcatPN("rryy", "strawberry");

   cout<<"\n\n";

   getch();

   cout<<"******************************************************\n";
   cout<<"* The following is testing subStringAN function\n";
   cout<<"******************************************************\n";

   if  (mySubStringAN("berry", "strawberry"))
	   cout<<"\nYES! berry is substring of strawberry using AN\n";
   else
	   cout<<"\nNO! berry is not substring of strawberry using ANn";


   if  (mySubStringAN("rry", "strawberry"))
	   cout<<"\nYES! rry is substring of strawberry using AN\n";
   else
	   cout<<"\nNO! rry is not substring of strawberry using AN\n";

   if  (mySubStringAN("rryy", "strawberry"))
	   cout<<"\nYES! rryy is substring of strawberry using AN\n";
   else
	   cout<<"\nNO! rryy is not substring of strawberry using AN\n";

   cout<<"\n\n";

   getch();

   cout<<"******************************************************\n";
   cout<<"* The following is testing mySubStringPN function\n";
   cout<<"******************************************************\n";

   if  (mySubStringPN("berry", "strawberry"))
	   cout<<"\nYES! berry is substring of strawberry using PN\n";
   else
	   cout<<"\nNO! berry is not substring of strawberry using PN\n";


   if  (mySubStringPN("rry", "strawberry"))
	   cout<<"\nYES! rry is substring of strawberry using PN\n";
   else
	   cout<<"\nNO! rry is not substring of strawberry using PN\n";


   if  (mySubStringPN("rryy", "strawberry"))
	   cout<<"\nYES! rryy is substring of strawberry using PN\n";
   else
	   cout<<"\nNO! rryy is not substring of strawberry using PN\n";

   cout<<"\n\n";

   getch();

   cout<<"******************************************************\n";
   cout<<"* The following is testing myStrpbrkPN function\n";
   cout<<"******************************************************\n";

// print out answer within the myStrcat function
   myStrpbrkPN ("berry", "strawberry");

   myStrpbrkPN ("erry", "strawberry");

   myStrpbrkPN("rry", "strawberry");


   cout<<"\n\n";

   getch();

   cout<<"******************************************************\n";
   cout<<"* The following is testing myStrpbrkAN function\n";
   cout<<"******************************************************\n";

   myStrpbrkAN ("berry", "strawberry");

   myStrpbrkAN ("erry", "strawberry");

   myStrpbrkAN("rry", "strawberry");
   getch();
   cout<<"\n\n";
}

/************************************************************************/
// Function: my_strlenPN()
// Description: accepts a string and returns length of string minus the
// null terminating character
// Inputs: string
// Outputs: length of string <integer>
// Preconditions: input must be a string
// Postconditions: outputs length of string
/************************************************************************/
int my_strlenPN(/*In*/const char *s)
{
   int counter = 0;
   const char *s_ptr = s;    // pointer to string

//
// Count all valid string elements
//

   while( *s_ptr != '\0' )
   {
	 s_ptr++;
	 counter++;
   }

   return counter;
}

/************************************************************************/
// Function: mySubStringAN() Array Notation
// Description: string1 is a substring of string2 if a sequence in
// string2 (may start from anywhere in string2) matches string1 exactly.
// Inputs: 2 strings
// Outputs: a returned boolean value
// Preconditions: must be valid strings
// Postconditions: <none>
/************************************************************************/
int mySubStringAN(/*In*/char s1[],
			   /*In*/char s2[])
{
   int max1 = 0, max2 = 0, r = 0, flag = 1, index1 = 0, index2 = 0;

   max1 = my_strlenPN(s1);         // find string length
   max2 = my_strlenPN(s2);

//
// Run through both strings to check for equality.
//
   for(index2 = 0; index2 <= max2; index2++)
   {
	 if((s2[index2]) == (s1[index1]))
	 {
	    flag = 1;
	    index1++;
	    r++;
		  if (r == max1)
		  {
			index2 = max2;
		  }
	 }
	 else
	 {
	    r = 0;
	    flag = 0;
	    index1 = 0;
	 }
   }

return flag;
}

/************************************************************************/
// Function: myStringPN() Pointer Notation
// Description: string1 is a substring of string2 if a sequence in
// string2 (may start from anywhere in string2) matches string1 exactly.
// Inputs: 2 strings
// Outputs: a returned boolean value
// Preconditions: must be valid strings
// Postconditions: <none>
/************************************************************************/

int mySubStringPN(char *s1, char *s2)
{
   int max1 = 0, max2 = 0, t, r=0, flag = 1;
   char *index1 = s1, *index2 = s2;

   max1 = my_strlenPN(s1);
   max2 = my_strlenPN(s2);

   for(t = 0; t <= max2; t++)
   {
	 index2 = &s2[t];
	 if((*index2) == (*index1))
	 {
	    flag = 1;
	    index1++;
	    r++;
		  if (r == max1)
		  {
			t = max2;
		  }
	 }
	 else
	 {
	    r = 0;
	    flag = 0;
	    index1 = s1;
	 }
   }
return flag;
}

/************************************************************************/
// Function: myStrcatAN() Array Notation
// Description: two strings are entered, the second string is appended on
// to the first one
// Inputs: must be two valid strings
// Outputs: appended string
// Preconditions: must be valid strings
// Postconditions: appended string is changed
/************************************************************************/
void myStrcatAN(/*InOut*/char s1[],
			 /*In*/char s2[])
{
   int index1 = 0, index2 = 0, index0 = 0;
   char output[80];

//
// Find end of first string.
//
   while( s1[index1] != '\0' )
   {
	 output[index0] = s1[index1];
	 index0++;
	 index1++;
   }

//
// Append second string onto first one.
//
   do
   {
	 output[index0] = s2[index2];
	 index0++;
	 index2++;
   }
   while( s2[index2] != '\0');

   output[index0] = '\0';
   cout << output << "\n";
}

/************************************************************************/
// Function: myStrcatPN() Pointer Notation
// Description: two strings are entered, the second string is appended on
// to the first one
// Inputs: must be two valid strings
// Outputs: appended string
// Preconditions: must be valid strings
// Postconditions: appended string is changed
/************************************************************************/
void myStrcatPN(/*InOut*/char *s1,
			 /*In*/char *s2)
{
   char *index1 = s1, *index2 = s2;
   char output[80], *index0;

//
// Find end of first string.
//
   index0 = output;
   while( *index1 != '\0' )
   {
	 *index0 = *index1;
	 index1++;
	 index0++;
   }

//
// Append second string onto the first one.
//
   while( *index2 != '\0')
   {
	 *index0 = *index2;
	 index2++;
	 index0++;
   }

   *index0 = '\0';
   cout << output << "\n";

}

/************************************************************************/
// Function: myStrpbrkPN() Pointer Notation
// Description: returns the first occurence in string1 of any character
// from string2, or a NULL pointer if no character from s2 exists in s1.
// Inputs: two valid strings
// Outputs: a valid character from a string
// Preconditions: must be valid strings
// Postconditions: <none>
/************************************************************************/
char *myStrpbrkPN(/*In*/char *StrArr1,
			   /*In*/char *StrArr2)
{
   int length1, length2, counter1 = 0, counter2 = 0;
   char *s1 = StrArr1, *s2 = StrArr2, *return_char = NULL;

   length1 = my_strlenPN(s1);   // find the length of both strings
   length2 = my_strlenPN(s2);

//
// Run through both strings to find first occurence.
//
   for(counter1 = 0; counter1 < length1; counter1++)
   {
	 for(counter2 = 0; counter2 < length2; counter2++)
	 {
	    if(*s1 == *s2)
	    {
		  return_char = s1;
		  counter2 = length2;
		  counter1 = length1;
	    }
	    else s2++;
	  }
	  s1++;
    }

//
// Output resulting character.
//
   cout << "\nThe resulting character using myStrpbrkPN is: "
	   << *return_char;

   return return_char;
}

/************************************************************************/
// Function: myStrpbrkAN() Array Notation
// Description: returns the first occurence in string1 of any character
// from string2, or a NULL pointer if no character from s2 exists in s1.
// Inputs: two valid strings
// Outputs: a valid character from a string
// Preconditions: must be valid strings
// Postconditions: <none>
/************************************************************************/
char *myStrpbrkAN(/*In*/char StrArr1[],
			   /*In*/char StrArr2[])
{
   int length1, length2, counter1 = 0, counter2 = 0;
   char *return_char = NULL;

   length1 = my_strlenPN(StrArr1);   // find length of both strings
   length2 = my_strlenPN(StrArr2);

//
// Check both strings for similar occurence.
//
   for(counter1 = 0; counter1 < length1; counter1++)
   {
	 for(counter2 = 0; counter2 < length2; counter2++)
	 {
	    if(StrArr1[counter1] == StrArr2[counter2])
	    {
		  return_char = &StrArr1[counter1];
		  counter2 = length2;
		  counter1 = length1;
	    }

	  }
    }

//
// Output resulting character.
//
   cout << "\nThe resulting character using myStrpbrkAN is: "
	   << *return_char;

   return return_char;
}

