//************************************************************************
// Programmer      :  Thomas Lepage
// Language        :  C++
// Compiler        :  g++
// Platform        :  OMEGA
// Assignment      :  Lab Assignment No.9
// Assigned        :  Wednesday, November 8, 2000
// Due             :  Wednesday, November 16, 2000
// Filed as        :  post09.cpp
// History         :  Sunday, November 12, 2000 original, TWL
//
//------------------------------------------------------------------------
// Program Description: This program reads two text data files containing
// student information such as grades and classes.
// The program separates all the data into separate structures, one for
// each student.  The program calculates all grades, grade points, gpa's
// and can print all those to the screen.
// The program contains a menu function that allows the user to print a
// summary of all the data present, individual grade reports, sort
// alphabetically, sort numerically, and exit.
//
// Description of Inputs:
// Students.dat < a pre-typed data file containing all student info >
// grades.dat < a pre-typed data file containing student grade info >
//
// Description of Outputs:
// All outputs are displayed on the screen
//
// Prompt Messages:
// <Main Menu Function>
// 1) Print a summary report
// 2) Print grade reports
// 3) Sort by name alphabetically
// 4) Sort by student number ascending
// 5) Exit
// Your Choice?
//
//
// Display of Results( No specific output form )
// structured and formatted output of student data in UTA format
//
// Program Assumptions:
// This program assumes that the strings read in are valid.
// ***********************************************************************
#include <iostream.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <fstream.h>
#include <stdio.h>

#define TRUE 1
#define FALSE 0
/* Defines */

#define EXIT_NUMBER 5
#define MAX_STUDENTS 50
#define MAX_CLASSES_STUDENT 10
#define STUDENT_NUMBER_WIDTH 5
#define STUDENT_NAME_WIDTH 80
#define COURSE_INFO_WIDTH 4
#define SMALL_INFO_WIDTH 1

/* Typedef structure for class */
typedef struct
{
   int hours, grade_points; // hours in the course, grade points earned
   char course_number[COURSE_INFO_WIDTH + 1]; // string for course number
   char dept_name[COURSE_INFO_WIDTH + 1]; // string for dept name
   char grade[SMALL_INFO_WIDTH + 1]; // string for letter grade
   unsigned active_class : 1; // determines class activity
}class_struct;

/* Typedef structure for student */
typedef struct
{
  int hoursCom_current, hoursCom_total; // hours for semester, total hours
  float gpa_current, gpa_total; // gpa for semester, gpa total
  char number[STUDENT_NUMBER_WIDTH + 1]; // string for student #
  char name[STUDENT_NAME_WIDTH + 1]; // string for student name
  class_struct classes[MAX_CLASSES_STUDENT]; // classes taken
  char year[SMALL_INFO_WIDTH + 1]; // string for student class year
  char major[COURSE_INFO_WIDTH + 1]; // string for student major
  char sex[SMALL_INFO_WIDTH + 1]; // string for student sex
  unsigned active_student : 1; // determines student activity
}student_struct;

/* Function Prototypes */

/* String Functions */
void ClearString(char[]);

/* Structure Reset Functions */
void ResetClass(class_struct *);
void ResetStudent(student_struct *);
void ResetStudents(student_struct *);

/* Get File/String functions */
int GetLine(FILE *, char [], int);
void AdvanceStringPointer(char *);
void FixString(char []);
void MakeLowerName(char []);
void GetAlphaString(char [], char [], int, int, int);
void GetString(char [], char [], int, int);
void GetDigitString(char [], char[], int, int);

/* Grades/Students functions */
void ReadGrades(FILE *, student_struct []);
int HaveGrades(student_struct [], char *);
void ReadStudents(FILE *, student_struct []);

/* Calculate info functions */
int CourseHours(char [COURSE_INFO_WIDTH+1]);
int GradeAmount(char [SMALL_INFO_WIDTH+1]);
void CalculateInfo(student_struct []);

/* Printing info functions */
int GiveNumberStudents(student_struct []);
void ReturnYear(char [SMALL_INFO_WIDTH+1], char []);
void PrintCommonData(student_struct *);
void PrintSummaryReport(int, student_struct []);
void PrintGradeReport(int, student_struct []);

/* Sort/Swap functions */
void Swap(student_struct *, student_struct *);
void SortStudentsNumeric(student_struct []);
void SortStudentsAlphabetic(student_struct []);

/* User choice fucntions */
int ValidChoice(int *, int, int);
int Main_Menu(void);
void Handle_Event(int, student_struct []);

void main(void)
{
    student_struct student_list[MAX_STUDENTS]; // Array of students
    int user_choice=0;                         // User choice

    FILE *grades=NULL, *students=NULL; // Files for student/grade info

//
// Open and check for errors in grades file
//
    if ((grades = fopen("grades.dat", "r")) == NULL)
    {
	 cerr << "Error opening grades.dat!\n";
	 exit(1);
    }

//
// Open and check for error in student file
//
    if ((students = fopen("Students.dat", "r")) == NULL)
    {
	 cerr << "Error opening students.dat!\n";
	 exit(1);
    }

//
// Reset all student structures
//
    ResetStudents(student_list);

//
// Read the grades in from the grades file
//
    ReadGrades(grades, student_list);

//
// Read the students in from the students file
//
    ReadStudents(students, student_list);
    cout << "Student file read.\n";

//
// Calculate needed student information
//
    CalculateInfo(student_list);
    cout << "Grades updated.\n\n\n";

//
// Loop to run functions chosen by user
//
    do
    {
	 user_choice = Main_Menu(); // Get user choice from menu screen

	 Handle_Event(user_choice, student_list); // Handle user choices

    }while(user_choice != EXIT_NUMBER); // End loop when user enters exit
								// condition

//
// Close both files
//
    fclose(grades);
    fclose(students);
}

/********************************************************************/
// Function: ClearString()
// Description: This function clears the string by setting the
// first value to the terminating null character.
// Inputs: The string to clear
// Outputs: Nothing
// Preconditions: must be valid string
// Postconditions: clears inputted string
/********************************************************************/
void ClearString(/*InOut*/char string[])
{

//
// Set first character of string to terminating null character
//
    string[0]='\0';
}

/********************************************************************
// Function: ResetClass()
// Description: This function resets all data in a class structure.
// Input: Class Structure
// Output: Nothing
// Preconditions: valid structure data must be passed in
// Postcondtions: resets structure data

/********************************************************************/
void ResetClass(/*InOut*/class_struct *c)
{

//
// Reset numerical data by setting to zero
//
    c->active_class = 0;
    c->hours = c->grade_points=0;

//
// Reset string data by clearing the strings
//
    ClearString(c->course_number);
    ClearString(c->dept_name);
    ClearString(c->grade);
}

/********************************************************************
// Function: ResetStudent()
// Description: This function resets all data in a student structure
// Input: Student Structure
// Output: Nothing
// Preconditions: valid student structure must be passed in
// Postcondtions: resets student structure
/********************************************************************/
void ResetStudent(/*InOut*/student_struct *s)
{
   int j = 0; // Loop variable

//
// Reset all numerical data by setting to zero
//
    s->active_student = 0;
    s->hoursCom_current = s->hoursCom_total = 0;
    s->gpa_current = s->gpa_total = 0.00;

//
// Reset all string data by clearing the strings
//
    ClearString(s->number);
    ClearString(s->name);
    ClearString(s->year);
    ClearString(s->major);

//
// Reset all classes by running loop to reset each class
//
    while(j < MAX_CLASSES_STUDENT)ResetClass(&s->classes[j++]);
}

/********************************************************************
// Function: ResetStudents()
// Description: This function resets all student structures.
// Input: Array of student structures
// Output: Nothing
// Preconditions: valid structures must be passed in
// Postcondtions: resets all student structures
/********************************************************************/
void ResetStudents(/*InOut*/student_struct s[])
{
    int j = 0; // Loop variable*/

//
// Reset all students by running loop to reset each student
//
    while(j < MAX_STUDENTS)ResetStudent(&s[j++]);
}

/********************************************************************
// Function: GetLine()
// Description: This function grabs a line of text from a file.
// Input: File pointer, return buffer, and maximum amount of chars.
// Output: Whether or not the line was read
// Preconditions: valid file pointer must be passed
// Postconditions: grabs a line of text
 ********************************************************************/
int GetLine(/*In*/FILE *fp,
		  /*In*/char *buffer,
		  /*In*/int max)
{
    int rt = TRUE; // Return value

//
// If the line grab was unsuccessful do this.
//
    if ((fgets(buffer, max, fp))==NULL)

	 rt = FALSE;   // Set return value to FALSE

	 return(rt); // Return the value
}

/********************************************************************
// Function: FixString()
// Description: This function removes any unnecessary spaces from
   the string.
// Input: String buffer
// Output: Nothing
// Preconditions: must be a valid buffer
// Postconditions: clears any unnecessary spaces from passed string
/********************************************************************/
void FixString(/*InOut*/char buffer[])
{
    int j = 0; // For loop variable
    int length = strlen(buffer); // Length of string
    int space_mark = -1; // Space marker in string

//
// Loop through the string
//
    for (j = 0; j < length; j++)
    {
//
// If space mark has not been set and a space was encountered,
// then mark the space with space marker
	 if ((space_mark == -1)&&(isspace(buffer[j])))space_mark = j;

//
// If space marker has been set and a non-space character
// is found later in the string, reset the space marker

	 if (space_mark != -1)
	 {
		 if (!isspace(buffer[j]))
		 {
		   space_mark = -1;
		 };
	 };
    };

//
// If space marker was set, make the space marker the terminating
// null character to end the string

    if (space_mark != -1)buffer[space_mark] = '\0';
}

/********************************************************************
// Function: MakeLowerName()
// Description: This function takes a string and makes every character
// except for the first one a lowercase letter.
// Input: String to make lowercase
// Output: Nothing
// Preconditions: must be valid string passed in
// Postconditions: changes all characters other than first to lowercase
/********************************************************************/
void MakeLowerName(/*InOut*/char string[]){

    char grab = '0'; // Grabber character
    int length = strlen(string); // Length of string
    int j = 0; // For loop variable

//
// Loop through the string
//
    for (j = 1; j < length; j++)
    {
//
// Get the string value
//
	  grab = string[j];

//
// Turn the string value into lowercase
//
	 string[j] = tolower(grab);
    };
}

/********************************************************************
// Function Name : GetAlphaString
// Description : This function takes a buffer string and reads a
// certain amount of alphabetic characters from it.
// Input: Reading buffer, return buffer, amount to read, offset in
// buffer, whether or not to read spaces
// Output : Nothing
/********************************************************************/
void GetAlphaString(/*In*/char buffer[],
				/*In*/char alpha[],
				/*In*/int amount,
				/*In*/int offset,
				/*In*/int space)
{
    int j = 0; // For loop variable
    char *buffer_ptr = NULL; // Pointer to input buffer
    char *alpha_ptr = NULL; // Pointer to output buffer
    char holder = '0'; // Holder character

//
// Assign buffer pointer to offset position
//
    buffer_ptr = &buffer[offset];

//
// Assign alpha pointer to starting position
//
    alpha_ptr = &alpha[0];

//
// If offset+amount is greater than the string length, fix it
//
    if ((offset+amount) > strlen(buffer))amount = strlen(buffer) - offset;

//
// Loop through the string
//
    for (j = offset; j < (offset+amount); j++)
    {

	    switch(space) // Check for space status
	    {

		    case 0: // If no spaces needed
				  // If buffer pointer is alphabetic
			    if (isalpha((*buffer_ptr)))
			    {
				    // Scan the buffer pointer for character
				    sscanf(buffer_ptr, "%c", &holder);

				    // Put the character in the alpha string
				    *alpha_ptr = holder;

				    // Increment alpha pointer
				    alpha_ptr++;

			    }break;

		    // If need spaces
		    case 1:
			    // If buffer pointer is alphabetic or space
			    if ((isalpha((*buffer_ptr)))||(isspace((*buffer_ptr))))
			    {
				    // Scan the buffer pointer for character
				    sscanf(buffer_ptr, "%c", &holder);

				    // Put the character in the alpha string
				    *alpha_ptr = holder;

				    // Increment alpha pointer
				    alpha_ptr++;

			    }break;
	    };
	    // Increment buffer pointer
	    buffer_ptr++;
    };
//
// Set the end of the alpha string to the terminating null character
//
    *alpha_ptr = '\0';
}

/********************************************************************
// Function: GetDigitString()
// Description: This function reads all numeric characters from
// the buffer string.
// Input: The input buffer, the output buffer, the amount to read, the
// offset.
// Output: Nothing
// Preconditions: valid strings must be passed in
// Postconditions: <none>
/********************************************************************/
void GetDigitString(/*In*/char buffer[],
				/*In*/char digit[],
				/*In*/int amount,
				/*In*/int offset)
{
    int j = 0; // For loop variable
    char *buffer_ptr = NULL; // Buffer pointer variable
    char *digit_ptr = NULL; // Digit pointer variable
    char holder = '0'; // Holder character

//
// Assign buffer pointer to offset position
//
    buffer_ptr = &buffer[offset];

//
// Assign digit pointer to the starting position
//
    digit_ptr = &digit[0];

//
// If offset+amount is greater than the string length, fix it
//
    if ((offset+amount) > strlen(buffer))amount = strlen(buffer) - offset;

//
// Loop through the string
//
    for (j = offset; j < (offset+amount); j++)
    {
	    // If buffer pointer is numeric
	    if (isdigit((*buffer_ptr)))
	    {
		    // Scan buffer pointer for character
		    sscanf(buffer_ptr, "%c", &holder);

		    // Put that character in the digit string
		    *digit_ptr = holder;

		    // Increment digit pointer
		    digit_ptr++;
	    };
	    // Increment buffer pointer*/
	    buffer_ptr++;
    };
    // Set the end of the digit string to the terminating null character
    *digit_ptr = '\0';
}

/********************************************************************
// Function: GetString()
// Description: This function reads all characters from
// the buffer string.
// Input: The input buffer, the output buffer, the amount to read, the
// offset.
// Output: Nothing.
// Preconditions: must be valid strings passed in
// Postconditions: <none>
/********************************************************************/
void GetString(/*In*/char buffer[],
			/*In*/char string[],
			/*In*/int amount,
			/*In*/int offset)
{
    int j = 0; // For loop variable
    char *buffer_ptr = NULL; // Buffer pointer variable
    char *string_ptr = NULL; // String pointer variable
    char holder = '0'; // Holder character

//
// Assign buffer pointer to offset position
//
    buffer_ptr = &buffer[offset];

//
// Assign string pointer to starting position
//
    string_ptr = &string[0];

//
// If offset+amount is greater than the string length, fix it
//
    if ((offset+amount) > strlen(buffer))amount = strlen(buffer) - offset;

//
// Loop through the string
//
    for (j = offset; j < (offset+amount); j++)
    {
	    // Scan buffer pointer for character
	    sscanf(buffer_ptr, "%c", &holder);

	    // Put that character in the string
	    *string_ptr = holder;

	    // Increment string pointer
	    string_ptr++;

	    // Increment buffer pointer
	    buffer_ptr++;
    };
    // Set the end of the string to the terminating null character
    *string_ptr = '\0';
}

/********************************************************************
// Function: ReadGrades()
// Description: This function takes the grade information and
// puts it into the student structures
// Input: File pointer to grades, student structure array
// Output: Nothing
// Preconditions: must be valid file pointer and student structure
// Postcondtions: <none>
/********************************************************************/
void ReadGrades(/*In*/FILE *grades,
			 /*In*/student_struct students[])
{
    int student_count = 0; // Counter for students
    int class_count = 0; // Counter for classes
    int line_status = 0; // Whether or not the line was valid
    char buffer[80]; // File line holding buffer
    char temp_buffer[80]; // Temp holding buffer
    student_struct *t = NULL; // Student structure pointer
    class_struct *c = NULL; // Class structure pointer

//
// Reset grades file to the beginning.
//
    rewind(grades);

//
// Get the first line of text from file.
//
    line_status = GetLine(grades, buffer, 80);

//
// Loop through the grades.dat file.
//
    do
    {
//
// Assign student pointer to (student_count)student.
//
	    t = &students[student_count];

//
// Get the student number from the buffer.
//
	    GetDigitString(buffer, t->number, STUDENT_NUMBER_WIDTH, 0);

//
// Loop through all classes assigned to this student number.
//
	    do
	    {
//
// Assign class pointer to (class_count)class.
//
		    c = &t->classes[class_count];

//
// Get the dept. name from the buffer.
//
		    GetAlphaString(buffer, c->dept_name, COURSE_INFO_WIDTH, 6, 1);

//
// Get the course number from the buffer.
//
		    GetDigitString(buffer, c->course_number, COURSE_INFO_WIDTH, 11);

//
// Get the grade from the buffer.
//
		    GetAlphaString(buffer, c->grade, SMALL_INFO_WIDTH, 16, 0);

//
// Set the class to active.
//
		    c->active_class = TRUE;

//
// Increment the class counter.
//
		    class_count++;

//
// Grab another line of text from file.
//
		    line_status = GetLine(grades, buffer, 80);

//
// If line was grabbed, put student number into the temp buffer.
//
		    if (line_status)GetDigitString(buffer, temp_buffer,
									STUDENT_NUMBER_WIDTH, 0);

//
// Loop while student numbers match and class count is less than max.
//
	    }while( (!strcmp(t->number, temp_buffer)) &&
			  (class_count < MAX_CLASSES_STUDENT) && (line_status));

//
// Set the student to active.
//
	    t->active_student = TRUE;

//
// Increment the student counter
//
	    student_count++;

//
// Reset the class count
//
	    class_count=0;

//
// Loop while student count is less than max and that line_status is valid
//
    }while((student_count < MAX_STUDENTS)&&(line_status));
}

/********************************************************************
// Function: HaveGrades()
// Description: This function determines whether or not this student
// has grades by searching through the student array.
// Input: Student structure array, String->student number
// Output: Whether or not this student has grades
// Preconditions: parameters must be valid
// Postconditions: <none>
/********************************************************************/
int HaveGrades(/*In*/student_struct list[],
			/*In*/char student_number[STUDENT_NUMBER_WIDTH+1])
{
    int j = 0; // Loop variable
    int rt = FALSE;  // Return variable

//
// While the student does not have grades and the counter is less than max
//
    while((!rt)&&(j < MAX_STUDENTS))
    {
//
// If the student is active
//
	    if (list[j].active_student)
	    {
//
// If the students number matches given number
//
		    if (!strcmp(student_number, list[j].number))
		    {
			   rt = TRUE; // Set the return to TRUE
		    };
	    };

	    j++; // Increment loop variable
    };

    return(rt); // Return the value
}

/********************************************************************
// Function: ReadStudents()
// Description: This function takes the student file and assigns
// the data into the student structure array.
// Input: File pointer to students, student structure array
// Output: Nothing
// Preconditions: parameters must be valid
// Postconditions: <none>
/********************************************************************/
void ReadStudents(/*In*/FILE *students,
			   /*In*/student_struct s_list[]){

    int count = 0; // Student count
    float gpa_temp = 0.00; // Temp holding gpa
    char buffer1[80], buffer2[80]; // File grab buffers 1 and 2
    char temp_number[STUDENT_NUMBER_WIDTH+1]; // Temp student number
    char temp_last[16], temp_first[11], temp_middle[16]; // Temp name buffers
    char temp_buffer[20]; // Temp buffer
    student_struct *s = NULL; // Student pointer

//
// Reset students file
//
    rewind(students);

//
// Loop while the line grabs are both valid and count is less than max
//
    while((GetLine(students, buffer1, 80))&&
		(GetLine(students, buffer2, 80))&&
		(count < MAX_STUDENTS))
    {
//
// Get temp student number from buffer1
//
	    GetDigitString(buffer1, temp_number, STUDENT_NUMBER_WIDTH, 0);

//
// Assign student pointer to (count)student
//
	    s = &s_list[count];

//
// If student is active
//
	    if (s->active_student)
	    {
//
// If the student has grades
//
		    if (HaveGrades(s_list, temp_number))
		    {
			    // Get the last name and put in temp_last
			    GetAlphaString(buffer1, temp_last, 15, 5, 0);
			    // Make last name lowercase
			    MakeLowerName(temp_last);

			    // Get the first name and put in temp_first
			    GetAlphaString(buffer1, temp_first, 10, 20, 0);
			    // Make first name lowercase
			    MakeLowerName(temp_first);

			    // Get the middle name and put in temp middle
			    GetAlphaString(buffer1, temp_middle, 15, 30, 1);
			    // Fix spaces in middle string
			    FixString(temp_middle);
			    // Make middle name lowercase
			    MakeLowerName(temp_middle);

			    // Print all temp name buffers into student name string
			    sprintf(s->name, "%s, %s, %c", temp_last, temp_first,
					  temp_middle[0]);

			    // Get the year from the second buffer
			    GetDigitString(buffer2, s->year, SMALL_INFO_WIDTH, 33);

			    // Get the major from the second buffer
			    GetAlphaString(buffer2, s->major, COURSE_INFO_WIDTH,
						    34, 1);

			    // Get the hoursCom and gpa from second buffer
			    GetString(buffer2, temp_buffer, 20, 39);

			    // Fix spaces in temp buffer
			    FixString(temp_buffer);

			    // Scan the total hours and gpa_temp from temp_buffer
			    sscanf(temp_buffer, "%d %f", &s->hoursCom_total,
					 &gpa_temp);

			    // Assign gpa_temp to gpa_total
			    s->gpa_total = gpa_temp;

			    // Increment counter
			    count++;
		    };
	    };
    };
}

/********************************************************************
// Function: CourseHours()
// Description: This function takes the value of the course number
// hours and returns it.
// Input: String->Course Number
// Output: The numeric value of the course hours
// Preconditions: parameter must be valid
// Postconditions: returns value of the course number hours
/********************************************************************/
int CourseHours(/*In*/char course_number[COURSE_INFO_WIDTH+1])
{
    int rt = -1; // Return value
    char grab[SMALL_INFO_WIDTH+1]; // Grab string

//
// Grab the course hours and assign null character at end
//
    grab[0] = course_number[1];
    grab[1] = '\0';

//
// Scan this string and grab the course hours
//
    sscanf(grab, "%d", &rt);

    return(rt); // Return the value
}

/********************************************************************
// Function: GradeAmount()
// Description: This function takes the grade string and return
// the numeric grade value.
// Input: String->Grade
// Output: The grade number based on alpha-grade character
// Preconditions: parameter must be valid
// Postcondtions: returns the numeric grade value
/********************************************************************/
int GradeAmount(char grade[SMALL_INFO_WIDTH+1])
{
    int rt = -1;   // Return value
    char grab = '0'; // Grab character

//
// Grab the character from the string
//
    grab = grade[0];

//
// Do case statements and assign proper grade values
//
    switch(grab){
	 case 'A' : rt = 4;break;
	 case 'B' : rt = 3;break;
	 case 'C' : rt = 2;break;
	 case 'D' : rt = 1;break;
	 case 'F' : rt = 0;break;
    };

    return(rt); // Return the value
}

/********************************************************************
// Function: CalculateInfo()
// Description: This function calculates the gpa values, grade_points
// and total hours for each student.
// Input: Student structure array
// Output: Nothing
// Preconditions: parameter must be valid
// Postconditions: <none>
/********************************************************************/
void CalculateInfo(/*In*/student_struct students[])
{
    int student_count = 0; // Student counter
    int class_count = 0; // Class counter
    int temp_grades_total = 0; // Temp grades holder
    student_struct *s = NULL; // Student pointer
    class_struct *c = NULL; // Class pointer

//
// Loop while student is active
//
    while (students[student_count].active_student)
    {
//
// Assign student pointer to (student_count)student
//
	    s = &students[student_count];

//
// Assign class pointer to first class
//
	    c = &s->classes[0];

	    class_count = 0; // Reset class count

	    temp_grades_total = 0; // Reset temp_grades_total

//
// Loop while class is active
//
	    while(c->active_class)
	    {
		    // If grade is not a W
		    if (strcmp(c->grade, "W"))
		    {
//
// Get the course hours from course number
//
			    c->hours = CourseHours(c->course_number);

//
// Get the grade points from hours * GradeAmount
//
			    c->grade_points = c->hours * GradeAmount(c->grade);

//
// Add up the total current semester hours
//
			    s->hoursCom_current += c->hours;

//
// Add up the total student hours
//
			    s->hoursCom_total += c->hours;

//
// Add up the current student grade points
//
			    temp_grades_total += c->grade_points;

//
// Increment class count
//
			    class_count++;
		    };
//
// Increment class pointer
//
		    c++;
	    };
//
// Calculate current gpa by dividing total grade points by
// semester hours
//
	    s->gpa_current = (float)((float)temp_grades_total /
						    (float)s->hoursCom_current);

//
// Calculate total gpa by adding two gpas together and
// dividing by two
//
	    s->gpa_total = (s->gpa_current + s->gpa_total) / 2;
	    student_count++;
    };
}

/********************************************************************
// Function Name: GiveNumberStudents()
// Description: Gets the number of active students from the array
// Input: Student structure array
// Output: The number of students
// Preconditions: parameter must be valid
// Postconditions: <none>
 ********************************************************************/
int GiveNumberStudents(/*In*/student_struct s_list[])
{
    int rt = 0; // Return value

//
// Loop through active students and counting them up
//
    while(s_list[rt].active_student)rt++;

    // Return the value
    return(rt);
}

/********************************************************************
// Function: ReturnYear()
// Description: This function returns the matching year string based on
// the year of the student.
// Input: String->Year, Return Buffer
// Output: Nothing
// Preconditions: parameters must be valid
// Postconditions: returns the matching year string
/********************************************************************/
void ReturnYear(/*In*/char year[SMALL_INFO_WIDTH+1],
			 /*In*/char buffer[])
{
    char grab = year[0]; // Character grab of year character

//
// Case statements of grab characters, returning proper strings
//
    switch(grab){
	    case '1' : strcpy(buffer, "Fr");break;
	    case '2' : strcpy(buffer, "So");break;
	    case '3' : strcpy(buffer, "Jr");break;
	    case '4' : strcpy(buffer, "Sr");break;
	    case '5' : strcpy(buffer, "Grad");break;
	    default : strcpy(buffer, "NULL");break;
    };
}

/********************************************************************
// Function: PrintCommonData()
// Description: This function formats the proper data for the
// student and puts it on the screen
// Input: Student Structure Pointer
// Output: Student number, name, year, and major to the screen
// Preconditions: structure pointer must be valid
// Postconditions: prints information to the screen
/********************************************************************/
void PrintCommonData(/*In*/student_struct *s)
{
    char year[5]; // Year buffer string

    printf("%5s ", s->number);  // Print student number

    printf("%30s  ", s->name);  // Print student name

//
// Get year buffer and print year
//
    ReturnYear(s->year, year);
    printf("%4s ", year);

    printf("%4s    ", s->major); // Print student major
}

/********************************************************************
// Function: PrintSummaryReport()
// Description : This function formats all necessary student info.
// for a summary report and puts it on the screen.
// Input: Number of students, student structure array
// Output : The summary report to the screen
// Preconditions: parameters must be valid
// Postconditions: prints info to screen
/********************************************************************/
void PrintSummaryReport(/*In*/int number_students,
				    /*In*/student_struct s_list[])
{
    int count = 0; // Student count
    int max_students = 0; // Max students
    char buffer[20]; // Temp buffer
    student_struct *s = NULL; // Student pointer

//
// Get number students in array
//
    max_students = GiveNumberStudents(s_list);

//
// If number students requested is greater than max_students,
// assign number_students to max_students
//
    if (number_students > max_students)
	    number_students = max_students;

//
// If there is only one student to print
//
    if (number_students == 1)
    {
//
// Print single student intro
//
	    printf("Summary Report for 1 student: \n\n");

    }else
    {
//
// Print multiple student intro
//
	    printf("Summary Report for %d students: \n\n", number_students);
    };

//
// Print table headers
//
    sprintf(buffer, "Current      Overall");
    printf("%70s\n", buffer);
    sprintf(buffer, "Hours GPA    Hours GPA");
    printf("%71s\n", buffer);

//
// Loop through the number of students requested
//

    while(count < number_students){
//
// Assign student pointer to (count)student
//
	    s = &s_list[count];

//
// Print common data on screen
//
	    PrintCommonData(s);

//
// Print the current semester info
//
	    printf("%d  ", s->hoursCom_current);
	    printf("%3.2f    ", s->gpa_current);

//
// Print the total college career info
//
	    printf("%3d  ", s->hoursCom_total);
	    printf("%3.2f\n", s->gpa_total);

//
// Increment counter
//
	    count++;
    };
    printf("\n\n");
}

/********************************************************************
// Function: PrintGradeReport()
// Description : This function takes all student info and formats
// it for output to the screen.
// Input: The number of students and the student structure array
// Output: The grade report to the screen
// Preconditions: parameters must be valid
// Postconditions: prints info to screen
/********************************************************************/
void PrintGradeReport(/*In*/int number_students,
				  /*In*/student_struct s_list[])
{
    int count = 0; // Student count
    int max_students = 0; // Max students allowed
    char buffer[30]; // Temp buffer
    student_struct *s = NULL; // Student pointer
    class_struct *c = NULL; // Class pointer

//
// Get number students in array
//
    max_students = GiveNumberStudents(s_list);

//
// If number students requested is greater than max_students,
// assign number_students to max_students
//
    if (number_students > max_students)
	    number_students = max_students;

//
// If there is only one student to print
//
    if (number_students == 1)
    {
//
// Print single student intro
//
	    printf("Grade Report for 1 student: \n\n");

    }else
    {
//
// Print multiple student intro
//
	    printf("Grade Report for %d students: \n\n", number_students);
    };

//
// Loop through the number of students requested
//
    while(count < number_students)
    {
//
// Assign student pointer to (count)student
//
	    s = &s_list[count];

//
// Print common data on screen
//
	    PrintCommonData(s);
	    printf("\n\n");

//
// Print grade header
//
	    sprintf(buffer, "   Course    Grade  GrPts\n");
	    printf("%20s", buffer);

//
// Assign class pointer to start of classes
//
	    c = &s->classes[0];

//
// Loop while class is active
//
	    while(c->active_class)
	    {
//
// If class is not a W
//
		    if (strcmp(c->grade, "W"))
		    {
//
// Print the department name
//
			    printf("   %4s ", c->dept_name);

//
// Print the course number
//
			    printf("%4s   ", c->course_number);

//
// Print the grade
//
			    printf("%1s      ", c->grade);

//
// Print the grade points
//
			    printf("%2d\n", c->grade_points);
		    };
//
// Increment class pointer
//
		    c++;
	    };
//
// Print current semester info
//
	    printf("\nCurrent Semester:  %3d Hours, %3.2f GPA\n",
			 s->hoursCom_current, s->gpa_current);

//
// Print overall info
//
	    printf("Overall:           %3d Hours, %3.2f GPA\n",
	    s->hoursCom_total, s->gpa_total);
	    printf("\n\n");

//
// Increment student count
//
	    count++;
    };

    printf("\n\n");
}

/********************************************************************
// Function: Swap()
// Description: This function swaps 2 student structures
// Input: Student structure 1 and 2
// Output: Nothing
// Precondtions: both student structures must be valid
// Postconditions: student structures are swapped
/********************************************************************/
void Swap(/*In*/student_struct *s1,
		/*In*/student_struct *s2)
{
    student_struct temp; // Temp student structure

//
// Assign first structure to temp
//
    temp = *s1;

//
// Assign second structure to the first
//
    *s1 = *s2;

//
// Assign temp to second structure
//
    *s2 = temp;
}

/********************************************************************
// Function: SortStudentsNumeric()
// Description: This function numerically sorts the students based
// on student number.
// Input: Student structure array
// Output: Nothing
// Preconditions: student structure must be valid
// Postconditions: sorts student structure
/********************************************************************/
void SortStudentsNumeric(/*In*/student_struct s_list[])
{
    int number_students; // Number of students
    int j, k; // For loop variables

//
// Get number of students
//
    number_students = GiveNumberStudents(s_list);

//
// Loop from zero to number_students-1
//
    for (j = 0; j < number_students-1; j++)
    {
//
// Loop from j to number_students
//
	    for (k = j; k < number_students; k++)
	    {
//
// If student[k] number is less than student[j] number
//
		    if ( (atoi(s_list[k].number)) < (atoi(s_list[j].number)) )
		    {
//
// Swap the structures
//
			    Swap(&s_list[k], &s_list[j]);
		    };
	    };
    };
}

/********************************************************************
// Function: SortStudentsAlphabetic()
// Description: This function alphabetically sorts the students based
// on student name.
// Input: Student structure array
// Output: Nothing
// Preconditions: student structure must be valid
// Postconditions: sorts student structure
/********************************************************************/
void SortStudentsAlphabetic(student_struct s_list[])
{
    int number_students=0; // Number students
    int j, k; // For loop variables

//
// Get number of students
//
    number_students = GiveNumberStudents(s_list);

//
// Loop from zero to number_students-1
//
    for (j = 0; j < number_students-1; j++)
    {
//
// Loop from j to number_students
//
	    for (k = j; k < number_students; k++)
	    {
//
// If student[k] name is less than student[j] name
//
		    if (strcmp(s_list[k].name, s_list[j].name) < 0)
		    {
//
// Swap the structures
//
			    Swap(&s_list[k], &s_list[j]);
		    };
	    };
    };
}

/********************************************************************
// Function: ValidChoice()
// Description: This function determines the validity of the choice
// variable based on min and max.
// Input: Choice variable, minimum and maximum values
// Output: Whether or not the choice was valid
// Preconditions: choice pointer must be valid
// Postconditions: returns validity
/********************************************************************/
int ValidChoice(/*In*/int *choice,
			 /*In*/int min,
			 /*In*/int max)
{
    int rt = FALSE; // Return value

//
// If choice is valid
//
    if ((*choice >= min) && (*choice <= max))
//
// Set the return to TRUE
//
	    rt = TRUE;

//
// If choice is invalid, print warning statement
//
    if (!rt)printf("Invalid Choice!\n");

//
// Return the value
//
    return(rt);
}

/********************************************************************
// Function: Main_Menu()
// Description : This function prints the menu for the user then
// prompts for the choice
// Input: Nothing
// Output: The user choice and the menu
// Preconditions: <none>
// Postconditions: <none>
/********************************************************************/
int Main_Menu(void)
{
    int choice = -1; // User choice

//
// Loop while choice is not valid.
//
    do
    {
//
// Print information to screen.
//
	 cout << "1) Print a summary report\n";
	 cout << "2) Print grade reports\n";
	 cout << "3) Sort by name alphabetically\n";
	 cout << "4) Sort by student number ascending\n";
	 cout << "5) Exit\n";
	 cout << "\nYour Choice? ";

//
// Flush input and get choice.
//
	 fflush(stdin);
	 scanf("%d", &choice);
    }while (!ValidChoice(&choice, 1, EXIT_NUMBER));

//
// Return choice value.
//
    return(choice);
}

/********************************************************************
// Function: Handle_Event()
// Description: This function handles all user options.
// Input: Event value, student structure list
// Output: Nothing
// Preconditions: student structure must be valid
// Postconditions: <none>
 ********************************************************************/
void Handle_Event(int event, student_struct s_list[])
{
    int number_students = -1; // Number of students

//
// Do case statements for each user option
//
    switch(event)
    {
	 /*Summary Report Case*/
	 case 1 :  // Loop while number_students is not valid
			 do
			 {
//
// Prompt user and get amount
//
				printf("How many students(999 for all)? ");
				scanf("%d", &number_students);

			 }while(!ValidChoice(&number_students, 1, 999));
//
// Print summary reports
//
			 PrintSummaryReport(number_students, s_list);
			 break;

	 /*Grade Report Case*/
	 case 2 : // Loop while number_students is not valid
			do
			{
//
// Prompt user and get amount
//
				printf("How many students(999 for all)? ");
				scanf("%d", &number_students);

			}while(!ValidChoice(&number_students, 1, 999));

//
// Print grade reports
//
			PrintGradeReport(number_students, s_list);
			break;

	 /*Sort Alphabetic Case*/
	 case 3 : // Sort students alphabetically
			SortStudentsAlphabetic(s_list);
			printf("Alphabetic sort completed.\n\n");
			break;
	 /*Sort Numeric Case*/
	 case 4 : // Sort students numerically
			SortStudentsNumeric(s_list);
			printf("Numeric sort completed.\n\n");
			break;
	 };
}

