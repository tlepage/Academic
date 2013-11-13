/********************************************************************
 *Description : This program reads two text data files containing
 *              student information such as grades and classes.
 *		The program then breaks all of this data down and
 *		puts it into a linked list, one node for each student.  The
 *		program then recalculates all grades, grade points,
 *		and gpas for each student.  The program then allows
 *		the user to perform several functions onto these student
 *		structures such as sorting by alphabetic/numeric, print
 *		summary reports and grade reports. This program will then also
 *        allow the read/write of the data from a binary file for immediate
 *        transfer into the linked list.
 *        Original Class Structure Size->24
 *        Original Student Structure Size->352
 *        New Class Structure Size->Same because single bit at the
 *           end is going to always waste 7bits and 3 bytes.
 *        New Student Structure Size->Same because of single bit wasting
 *           7bits and there always being one left over byte from the 5char
 *           major string.
 ********************************************************************/

/*Includes*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <conio.h>

/*Defines for true/false*/
#define TRUE 1
#define FALSE 0

/*Defines for misc program info*/
#define EXIT_NUMBER 8
#define MAX_STUDENTS 100
#define MAX_CLASSES_STUDENT 10
#define STUDENT_NUMBER_WIDTH 5
#define STUDENT_NAME_WIDTH 80
#define COURSE_INFO_WIDTH 4
#define SMALL_INFO_WIDTH 1

/*Typedef for a structure containing class information*/
typedef struct{
int hours, grade_points; /*Hours in the course, grade points earned*/
char course_number[COURSE_INFO_WIDTH+1]; /*String->course number*/
char dept_name[COURSE_INFO_WIDTH+1]; /*String->department name*/
char grade[SMALL_INFO_WIDTH+1]; /*String->letter grade*/
unsigned active_class : 1; /*Whether or not the class is active*/
}class_struct;
/*End of class_struct typedef*/

/*Typedef for a structure containing student information*/
typedef struct{
int hoursCom_current, hoursCom_total; /*Hours this semester, hours total*/
float gpa_current, gpa_total; /*Gpa this semester, gpa total*/
char number[STUDENT_NUMBER_WIDTH+1]; /*String->student number*/
char name[STUDENT_NAME_WIDTH+1]; /*String->student name*/
class_struct classes[MAX_CLASSES_STUDENT]; /*Classes taken by student*/
char year[SMALL_INFO_WIDTH+1]; /*String->student class year*/
char major[COURSE_INFO_WIDTH+1]; /*String->student major*/
unsigned active_student : 1; /*Whether or not the student is active*/
}student_struct;
/*End of student_struct typedef*/

/*Structure for linked list*/
struct student_node{
student_struct data; /*Data storage for student*/
struct student_node *next; /*Pointer to next node in list*/
};


/*Function Prototypes*/

/*Basic string functions*/
void ClearString(char []);

/*Structure reset functions*/
void ResetClass(class_struct *);
void ResetStudent(student_struct *);

/*Get File/String functions*/
int GetLine(FILE *, char [], int);
void FixString(char []);
void MakeLowerName(char []);
void GetAlphaString(char [], char [], int, int, int);
void GetString(char [], char [], int, int);
void GetDigitString(char [], char [], int, int);

/*Linked list functions*/
struct student_node *GetStudentNodeMemory(void);
void InsertEnd(struct student_node **, struct student_node *);
void Release(struct student_node **);
student_struct *FindStudent(struct student_node *, char [STUDENT_NUMBER_WIDTH+1]);
void DeleteStudent(struct student_node **, char [STUDENT_NUMBER_WIDTH+1]);

/*Grades/Students functions(Linked list)*/
void ReadGradesLINKED(FILE *, struct student_node *);
void ReadStudentsLINKED(FILE *, struct student_node **);

/*Calculate info functions*/
int CourseHours(char [COURSE_INFO_WIDTH+1]);
int GradeAmount(char [SMALL_INFO_WIDTH+1]);
int IsEnrolled(student_struct *);
void CalculateInfoLINKED(struct student_node *);

/*Printing info functions*/
void ReturnYear(char [SMALL_INFO_WIDTH+1], char []);
void PrintCommonData(student_struct *);
void PrintSummaryReportLINKED(int, struct student_node *);
void PrintGradeReportLINKED(int, struct student_node *);

/*Sorting functions*/
void Swap(student_struct *, student_struct *);
void SortStudentsNumericLINKED(struct student_node *);
void SortStudentsAlphabeticLINKED(struct student_node *);

/*Binary Read/Write Functions*/
void WriteBinaryStudentsLINKED(struct student_node *);
int ReadBinaryStudentsLINKED(struct student_node **, int, int);

/*User choice handlers*/
int ValidChoice(int *, int, int);
int Main_Menu(void);
void Handle_EventLINKED(int, struct student_node **);

/***************************MAIN PROGRAM****************************/
void main(void)
{
    struct student_node *list_head=NULL;
    int user_choice=0; /*User choice*/

    FILE *grades=NULL, *students=NULL; /*Files for student/grade info*/

    /*Open and error check grades file*/
    if ((grades = fopen("grades.dat", "r"))==NULL)
    {
	 printf("Error opening grades.dat!\n");
	 exit(1);
    }

    /*Open and error check students file*/
    if ((students = fopen("students.dat", "r"))==NULL)
    {
	 printf("Error opening students.dat!\n");
	 exit(1);
    }

    /*Read the students in from the students file*/
	 ReadStudentsLINKED(students, &list_head);
	 printf("Student file read.\n");

    /*Read the grades in from the grades file*/
	 ReadGradesLINKED(grades, list_head);
	 printf("Read grades.");

    /*Calculate needed student information*/
	 CalculateInfoLINKED(list_head);
	 printf("Grades updated.\n\n\n");

    /*Loop to run functions chosen by user*/
    do
    {
	 /*Get user choice from menu screen*/
	 user_choice = Main_Menu();

	 /*Handle user choice functions*/
	 Handle_EventLINKED(user_choice, &list_head);

    /*End loop when user enters exit number*/
    }while(user_choice != EXIT_NUMBER);

    /*Destroy data list*/
    Release(&list_head);

    /*Close both files*/
    fclose(grades);
    fclose(students);
}

/********************************************************************
 *Function Name : ClearString
 *Input : The string to clear
 *Output : Nothing
 *Description : This function clears the string by setting the
 *              first value to the terminating null character.
 ********************************************************************/
void ClearString(char string[])
{
    /*Set first character of string to terminating null character*/
    string[0]='\0';
}

/********************************************************************
 *Function Name : ResetClass
 *Input : Class Structure
 *Output : Nothing
 *Description : This function resets all data in a class structure.
 ********************************************************************/
void ResetClass(class_struct *c)
{
    /*Reset numerical data by setting to zero*/
    c->active_class = 0;
    c->hours=c->grade_points=0;

    /*Reset string data by clearing the strings*/
    ClearString(c->course_number);
    ClearString(c->dept_name);
    ClearString(c->grade);
}

/********************************************************************
 *Function Name : ResetStudent
 *Input : Student Structure
 *Output : Nothing
 *Description : This function resets all data in a student structure
 ********************************************************************/
void ResetStudent(student_struct *s)
{
    int j=0; /*Loop variable*/

    /*Reset all numerical data by setting to zero*/
    s->active_student = 0;
    s->hoursCom_current = s->hoursCom_total = 0;
    s->gpa_current = s->gpa_total = 0.00;

    /*Reset all string data by clearing the strings*/
    ClearString(s->number);
    ClearString(s->name);
    ClearString(s->year);
    ClearString(s->major);

    /*Reset all classes by running loop to reset each class*/
    while(j < MAX_CLASSES_STUDENT)ResetClass(&s->classes[j++]);
}

/********************************************************************
 *Function Name : GetLine
 *Input : File pointer, return buffer, and maximum amount of chars.
 *Output : Whether or not the line was read
 *Description : This function grabs a line of text from a file.
 ********************************************************************/
int GetLine(FILE *fp, char *buffer, int max)
{
    int rt=TRUE; /*Return value*/

    /*If the line grab was unsuccessful*/
    if ((fgets(buffer, max, fp))==NULL)
	 /*Set return value to FALSE*/
	 rt = FALSE;

    /*Return the value*/
    return(rt);
}

/********************************************************************
 *Function Name : FixString
 *Input : String buffer
 *Output : Nothing
 *Description : This function removes any unnecessary spaces from
 *		the string.
 ********************************************************************/
void FixString(char buffer[])
{
    int j=0; /*For loop variable*/
    int length=strlen(buffer); /*Length of string*/
    int space_mark=-1; /*Space marker in string*/

    /*Loop through the string*/
    for (j = 0; j < length; j++)
    {
	 /*If space mark has not been set and a space was encountered,
	  *then mark the space with space marker*/
	 if ((space_mark == -1)&&(isspace(buffer[j])))space_mark = j;

	 /*If space marker has been set and a non-space character
	  *is found later in the string, reset the space marker*/
	 if (space_mark != -1)
	 {
		 if (!isspace(buffer[j]))
		 {
		   space_mark = -1;
		 };
	 };
    };

    /*If space marker was set, make the space marker the terminating
	*null character to end the string*/
    if (space_mark != -1)buffer[space_mark] = '\0';
}

/********************************************************************
 *Function Name : MakeLowerName
 *Input : String to make lowercase
 *Output : Nothing
 *Description : This function takes a string and makes every character
 *              except for the first one a lowercase letter.
 ********************************************************************/
void MakeLowerName(char string[]){
    char grab='0'; /*Grabber character*/
    int length=strlen(string); /*Length of string*/
    int j=0; /*For loop variable*/

    /*Loop through the string*/
    for (j = 1; j < length; j++)
    {
	 /*Get the string value*/
	 grab = string[j];

	 /*Turn the string value into lowercase*/
	 string[j] = tolower(grab);
    };
}

/********************************************************************
 *Function Name : GetAlphaString
 *Input : Reading buffer, return buffer, amount to read, offset in
 *	  buffer, whether or not to read spaces
 *Output : Nothing
 *Description : This function takes a buffer string and reads a
 *              certain amount of alphabetic characters from it.
 ********************************************************************/
void GetAlphaString(char buffer[], char alpha[], int amount, int offset, int space){
    int j=0; /*For loop variable*/
    char *buffer_ptr=NULL; /*Pointer to input buffer*/
    char *alpha_ptr=NULL; /*Pointer to output buffer*/
    char holder='0'; /*Holder character*/

    /*Assign buffer pointer to offset position*/
    buffer_ptr = &buffer[offset];

    /*Assign alpha pointer to starting position*/
    alpha_ptr = &alpha[0];

    /*If offset+amount is greater than the string length, fix it*/
    if ((offset+amount) > strlen(buffer))amount = strlen(buffer) - offset;

    /*Loop through the string*/
    for (j = offset; j < (offset+amount); j++)
    {
	    /*Check for space status*/
	    switch(space)
	    {
		    /*If no spaces needed*/
		    case 0:
			    /*If buffer pointer is alphabetic*/
			    if (isalpha((*buffer_ptr)))
			    {
				    /*Scan the buffer pointer for character*/
				    sscanf(buffer_ptr, "%c", &holder);

				    /*Put the character in the alpha string*/
				    *alpha_ptr = holder;

				    /*Increment alpha pointer*/
				    alpha_ptr++;
			    };break;
		    /*If need spaces*/
		    case 1:
			    /*If buffer pointer is alphabetic or space*/
			    if ((isalpha((*buffer_ptr)))||(isspace((*buffer_ptr))))
			    {
				    /*Scan the buffer pointer for character*/
				    sscanf(buffer_ptr, "%c", &holder);

				    /*Put the character in the alpha string*/
				    *alpha_ptr = holder;

				    /*Increment alpha pointer*/
				    alpha_ptr++;
			    };break;
	    };
	    /*Increment buffer pointer*/
	    buffer_ptr++;
    };
    /*Set the end of the alpha string to the terminating null character*/
    *alpha_ptr = '\0';
}

/********************************************************************
 *Function Name : GetDigitString
 *Input : The input buffer, the output buffer, the amount to read, the
 *        offset.
 *Output : Nothing
 *Description : This function reads all numeric characters from
 *              the buffer string.
 ********************************************************************/
void GetDigitString(char buffer[], char digit[], int amount, int offset){
    int j=0; /*For loop variable*/
    char *buffer_ptr=NULL; /*Buffer pointer variable*/
    char *digit_ptr=NULL; /*Digit pointer variable*/
    char holder='0'; /*Holder character*/

    /*Assign buffer pointer to offset position*/
    buffer_ptr = &buffer[offset];

    /*Assign digit pointer to the starting position*/
    digit_ptr = &digit[0];

    /*If offset+amount is greater than the string length, fix it*/
    if ((offset+amount) > strlen(buffer))amount = strlen(buffer) - offset;

    /*Loop through the string*/
    for (j = offset; j < (offset+amount); j++)
    {
	    /*If buffer pointer is numeric*/
	    if (isdigit((*buffer_ptr)))
	    {
		    /*Scan buffer pointer for character*/
		    sscanf(buffer_ptr, "%c", &holder);

		    /*Put that character in the digit string*/
		    *digit_ptr = holder;

		    /*Increment digit pointer*/
		    digit_ptr++;
	    };
	    /*Increment buffer pointer*/
	    buffer_ptr++;
    };
    /*Set the end of the digit string to the terminating null character*/
    *digit_ptr = '\0';
}

/********************************************************************
 *Function Name : GetString
 *Input : The input buffer, the output buffer, the amount to read, the
 *        offset.
 *Output : Nothing.
 *Description : This function reads all characters from
 *              the buffer string.
 ********************************************************************/
void GetString(char buffer[], char string[], int amount, int offset)
{
    int j=0; /*For loop variable*/
    char *buffer_ptr=NULL; /*Buffer pointer variable*/
    char *string_ptr=NULL; /*String pointer variable*/
    char holder='0'; /*Holder character*/

    /*Assign buffer pointer to offset position*/
    buffer_ptr = &buffer[offset];

    /*Assign string pointer to starting position*/
    string_ptr = &string[0];

    /*If offset+amount is greater than the string length, fix it*/
    if ((offset+amount) > strlen(buffer))amount = strlen(buffer) - offset;

    /*Loop through the string*/
    for (j = offset; j < (offset+amount); j++)
    {
	    /*Scan buffer pointer for character*/
	    sscanf(buffer_ptr, "%c", &holder);

	    /*Put that character in the string*/
	    *string_ptr = holder;

	    /*Increment string pointer*/
	    string_ptr++;

	    /*Increment buffer pointer*/
	    buffer_ptr++;
    };
    /*Set the end of the string to the terminating null character*/
    *string_ptr = '\0';
}

/********************************************************************
 *Function Name : GetStudentNodeMemory
 *Input : Nothing
 *Output : The pointer to the newly allocated student node memory
 *Description : This function calls malloc to allocate enough memory
 *              for one student_node structure.
 ********************************************************************/
struct student_node *GetStudentNodeMemory(void)
{
    struct student_node *ReturnPtr=NULL; /*Return pointer to memory*/

    /*Allocate memory for student_node*/
    ReturnPtr = (struct student_node *)malloc(sizeof(struct student_node));

    /*Make sure the memory was allocated*/
    if (!ReturnPtr)
    {
	    printf("No memory left for new nodes!\n");
	    exit(1);
    };

    /*Return the pointer*/
    return(ReturnPtr);
}
/********************************************************************
 *Function Name : InsertEnd
 *Input : The linked list of nodes
 *Output : Nothing
 *Description : This function will insert a new node at the end
 *              of the linked list.
 ********************************************************************/
void InsertEnd(struct student_node **list_head, struct student_node *new_node)
{
    struct student_node *curPtr=NULL; /*Current position in the list*/

    /*Check to see if head is null*/
    if (*list_head == NULL)
    {
	    /*Assign list head to new node*/
	    *list_head = new_node;

	    /*Assign next to null*/
	    (*list_head)->next = NULL;
    }else
    {
	   /*Assign pointer to list head*/
	   curPtr = *list_head;

	   /*Loop to get to the end of the list*/
	   while(curPtr->next)
	   {
		    curPtr = curPtr->next;
	   };

	   /*Put new node in the list*/
	   curPtr->next = new_node;
	   new_node->next = NULL;
    };
}

/********************************************************************
 *Function Name : Release
 *Input : The pointer to the head of the linked list
 *Output : Nothing
 *Description : Frees all memory currently allocated by the linked list.
 ********************************************************************/
void Release(struct student_node **list_head)
{
    struct student_node *curPtr=NULL; /*Current list pointer*/
    struct student_node *tempPtr=NULL; /*Temp holding list pointer*/

    /*Assign current pointer to list head*/
    curPtr = *list_head;

    /*Loop to end of list*/
    while(curPtr)
    {
	    /*Grab next list position*/
	    tempPtr = curPtr->next;

	    /*Free current node*/
	    free((void *)curPtr);

	    /*Reassign current pointer to next list position*/
	    curPtr = tempPtr;
    };

    /*Null the head pointer*/
    *list_head = NULL;
}

/********************************************************************
 *Function Name : FindStudent
 *Input : The the head of the linked list and the number to match with
 *Output : The pointer to the student being searched for
 *Description : This function finds a student in the list based on his/her
 *              student number.
 ********************************************************************/
student_struct *FindStudent(struct student_node *list_head, char number[STUDENT_NUMBER_WIDTH+1])
{
    int finish=FALSE; /*Whether or not the loop is finished*/
    struct student_node *curPtr=NULL;  /*Current pointer to the list*/
    student_struct *retPtr=NULL; /*Return pointer*/

    /*Start at list head*/
    curPtr = list_head;

    /*Loop until end of list is reached or loop is finished*/
    while((curPtr)&&(!finish))
    {
	    /*Get pointer to data in this node*/
	    retPtr = &curPtr->data;

	    /*If the two string numbers compare, then end the loop*/
	    if (!strcmp(retPtr->number, number))
	    {
		    /*Ends the loop*/
		    finish = TRUE;
	    }else
	    {
		    /*Otherwise increment the list pointer*/
		    curPtr = curPtr->next;
	    };
    };

    /*Return the pointer*/
    return(retPtr);
}

/********************************************************************
 *Function Name : DeleteStudent
 *Input : The the head of the linked list and the number to delete
 *Output : Nothing
 *Description : This function finds a student in the list based on his/her
 *              student number and deletes them.
 ********************************************************************/
void DeleteStudent(struct student_node **list_head, char number[STUDENT_NUMBER_WIDTH+1])
{
    int finish=FALSE; /*Whether or not the loop is finished*/
    struct student_node *curPtr=NULL;  /*Current pointer to the list*/
    struct student_node *tmpPtr=NULL;  /*Temp pointer to the list*/
    struct student_node *prvPtr=NULL; /*Previous pointer to the list*/
    student_struct *retPtr=NULL; /*Return pointer*/

    /*Start at list head*/
    curPtr = *list_head;

    /*Loop until end of list is reached or loop is finished*/
    while((curPtr)&&(!finish))
    {
	    /*Get pointer to data in this node*/
	    retPtr = &curPtr->data;

	    /*If the two string numbers compare, then end the loop*/
	    if (!strcmp(retPtr->number, number))
	    {
		    /*Ends the loop*/
		    finish = TRUE;
	    }else
	    {
		    /*Otherwise increment the list pointer and previous pointer*/
		    if (!prvPtr)
			    prvPtr = *list_head;
		    else
			    prvPtr = curPtr;
		    curPtr = curPtr->next;
	    };
    };
    /*If a student was found*/
    if (finish)
    {
	    /*If the current pointer is at the head*/
	    if (curPtr == *list_head)
	    {
		    /*Reassign head pointer and delete old one*/
		    tmpPtr = *list_head;
		    curPtr = curPtr->next;
		    *list_head = curPtr;
		    free((void *)tmpPtr);
	    /*Otherwise, pointer is not at head*/
	    }else
	    {
		    /*Reassign previous pointer and delete student node*/
		    tmpPtr = curPtr;
		    curPtr = curPtr->next;
		    prvPtr->next = curPtr;
		    free((void *)tmpPtr);
	    };

	    /*Tell user of a successful delete*/
	    printf("Student(#%s) Successfully Deleted.\n", number);
    }else
    {
	    /*Error message to user*/
	    printf("Student not in list!\n");
    };
    printf("\n\n");
}

/********************************************************************
 *Function Name : ReadGradesLINKED
 *Input : File pointer to grades, student node list
 *Output : Nothing
 *Description : This function takes the grade information and
 *              puts it into the student structures in the linked list
 ********************************************************************/
void ReadGradesLINKED(FILE *grades, struct student_node *list_head)
{
    int line_status=0; /*Whether or not the line was valid*/
    char buffer[80]; /*File line holding buffer*/
    char temp_buffer[80]; /*Temp holding buffer*/
    int class_count=0; /*Counter for classes*/
    student_struct *t=NULL; /*Student structure pointer*/
    class_struct *c=NULL; /*Class structure pointer*/

    /*Reset grades file to the beginning*/
    rewind(grades);

    /*Get the first line of text from file*/
    line_status = GetLine(grades, buffer, 80);

    /*Loop through the grades.dat file*/
    do
    {
	    /*Get the student number from the buffer*/
	    GetDigitString(buffer, temp_buffer, STUDENT_NUMBER_WIDTH, 0);

	    /*Assign student pointer to student found in list*/
	    t = NULL;
	    t = FindStudent(list_head, temp_buffer);
	    if (t != NULL){
		    /*Loop through all classes assigned to this student number*/
		    do
		    {
			    /*Assign class pointer to (class_count)class*/
			    c = &t->classes[class_count];

			    /*Get the dept. name from the buffer*/
			    GetAlphaString(buffer, c->dept_name, COURSE_INFO_WIDTH, 6, 1);

			    /*Get the course number from the buffer*/
			    GetDigitString(buffer, c->course_number, COURSE_INFO_WIDTH, 11);

			    /*Get the grade from the buffer*/
			    GetAlphaString(buffer, c->grade, SMALL_INFO_WIDTH, 16, 0);

			    /*Set the class to active*/
			    c->active_class = TRUE;

			    /*Increment the class counter*/
			    class_count++;

			    /*Grab another line of text from file*/
			    line_status = GetLine(grades, buffer, 80);

			    /*If line was grabbed, put student number into the temp buffer*/
			    if (line_status)GetDigitString(buffer, temp_buffer, STUDENT_NUMBER_WIDTH, 0);

		    /*Loop while student numbers match and class count is less than max*/
		    }while( (!strcmp(t->number, temp_buffer)) && (class_count < MAX_CLASSES_STUDENT) && (line_status));
		    /*Set the student to active*/
		    t->active_student = TRUE;

		    /*Reset the class count*/
		    class_count=0;
	    }else
	    {
		    /*Grab a line of text*/
		    line_status = GetLine(grades, buffer, 80);
		    /*If line was grabbed, put student number into the temp buffer*/
		    if (line_status)GetDigitString(buffer, temp_buffer, STUDENT_NUMBER_WIDTH, 0);
	    };

    /*Loop while line_status is valid*/
    }while(line_status);
}

/********************************************************************
 *Function Name : ReadStudentsLINKED
 *Input : File pointer to students, student node linked list head
 *Output : Nothing
 *Description : This function takes the student file and assigns
 *              the data into the student node list.
 ********************************************************************/
void ReadStudentsLINKED(FILE *students, struct student_node **list_head)
{
    float gpa_temp=0.00; /*Temp holding gpa*/
    char buffer1[80], buffer2[80]; /*File grab buffers 1 and 2*/
    char temp_number[STUDENT_NUMBER_WIDTH+1]; /*Temp student number*/
    char temp_last[16], temp_first[11], temp_middle[16]; /*Temp name buffers*/
    char temp_buffer[20]; /*Temp buffer*/
    student_struct *s=NULL; /*Student pointer*/
    struct student_node *tempPtr=NULL; /*Temporary insert node pointer*/

    /*Reset students file*/
    rewind(students);

    /*Loop while the line grabs are both valid and count is less than max*/
    while((GetLine(students, buffer1, 80))&&
		(GetLine(students, buffer2, 80)) )
    {
	    /*Get node space*/
	    tempPtr = GetStudentNodeMemory();

	    /*Assign student pointer to student node->data*/
	    s = &tempPtr->data;

	    /*Reset the student data*/
	    ResetStudent(s);

	    /*Get temp student number from buffer1*/
	    GetDigitString(buffer1, s->number, STUDENT_NUMBER_WIDTH, 0);

	    /*Get the last name and put in temp_last*/
	    GetAlphaString(buffer1, temp_last, 15, 5, 0);
	    /*Make last name lowercase*/
	    MakeLowerName(temp_last);

	    /*Get the first name and put in temp_first*/
	    GetAlphaString(buffer1, temp_first, 10, 20, 0);
	    /*Make first name lowercase*/
	    MakeLowerName(temp_first);

	    /*Get the middle name and put in temp middle*/
	    GetAlphaString(buffer1, temp_middle, 15, 30, 1);
	    /*Fix spaces in middle string*/
	    FixString(temp_middle);
	    /*Make middle name lowercase*/
	    MakeLowerName(temp_middle);

	    /*Print all temp name buffers into student name string*/
	    sprintf(s->name, "%s, %s, %c", temp_last, temp_first, temp_middle[0]);

	    /*Get the year from the second buffer*/
	    GetDigitString(buffer2, s->year, SMALL_INFO_WIDTH, 33);

	    /*Get the major from the second buffer*/
	    GetAlphaString(buffer2, s->major, COURSE_INFO_WIDTH, 34, 1);

	    /*Get the hoursCom and gpa from second buffer*/
	    GetString(buffer2, temp_buffer, 20, 39);

	    /*Fix spaces in temp buffer*/
	    FixString(temp_buffer);

	    /*Scan the total hours and gpa_temp from temp_buffer*/
	    sscanf(temp_buffer, "%d %f", &s->hoursCom_total, &gpa_temp);

	    /*Assign gpa_temp to gpa_total*/
	    s->gpa_total = gpa_temp;

	    /*Insert node*/
	    InsertEnd(list_head, tempPtr);
    };
}

/********************************************************************
 *Function Name : CourseHours
 *Input : String->Course Number
 *Output : The numeric value of the course hours
 *Description : This function takes the value of the course number
 *              hours and returns it.
 ********************************************************************/
int CourseHours(char course_number[COURSE_INFO_WIDTH+1])
{
    int rt=-1; /*Return value*/
    char grab[SMALL_INFO_WIDTH+1]; /*Grab string*/

    /*Grab the course hours and assign null character at end*/
    grab[0] = course_number[1];
    grab[1] = '\0';

    /*Scan this string and grab the course hours*/
    sscanf(grab, "%d", &rt);

    /*Return the value*/
    return(rt);
}

/********************************************************************
 *Function Name : GradeAmount
 *Input : String->Grade
 *Output : The grade number based on alpha-grade character
 *Description : This function takes the grade string and returns
 *              the numeric grade value.
 ********************************************************************/
int GradeAmount(char grade[SMALL_INFO_WIDTH+1])
{
    int rt=-1; /*Return value*/
    char grab='0'; /*Grab character*/

    /*Grab the character from the string*/
    grab = grade[0];

    /*Do case statements and assign proper grade values*/
    switch(grab){
	 case 'A' : rt = 4;break;
	 case 'B' : rt = 3;break;
	 case 'C' : rt = 2;break;
	 case 'D' : rt = 1;break;
	 case 'F' : rt = 0;break;
    };

    /*Return the value*/
    return(rt);
}

/********************************************************************
 *Function Name : IsEnrolled
 *Input : Student structure pointer
 *Output : Whether or not the student has grades in his class structures
 *Description : This function tells whether or not this student is
 *              enrolled by seeing if his/her first class is active.
 ********************************************************************/
int IsEnrolled(student_struct *student){
    int rt=FALSE;  /*Return value*/
    class_struct *c=NULL; /*Class pointer*/

    /*Get first class of student*/
    c = &student->classes[0];

    /*If the class is active, the student has classes*/
    if (c->active_class)rt = TRUE;

    /*Return the value*/
    return(rt);
}

/********************************************************************
 *Function Name : CalculateInfoLINKED
 *Input : Student linked list
 *Output : Nothing
 *Description : This function calculates the gpa values, grade_points
 *              and total hours for each student.
 ********************************************************************/
void CalculateInfoLINKED(struct student_node *list_head)
{
    int class_count=0; /*Class counter*/
    int temp_grades_total=0; /*Temp grades holder*/
    struct student_node *curPtr=NULL; /*Temp list pointer*/
    student_struct *s=NULL; /*Student pointer*/
    class_struct *c=NULL; /*Class pointer*/

    /*Start at list head*/
    curPtr = list_head;

    /*Loop while student is active*/
    while (curPtr)
    {
	    /*Assign student pointer to student*/
	    s = &curPtr->data;

	    if (IsEnrolled(s))
	    {
		    /*Assign class pointer to first class*/
		    c = &s->classes[0];

		    /*Reset class count*/
		    class_count = 0;

		    /*Reset temp_grades_total*/
		    temp_grades_total = 0;

		    /*Loop while class is active*/
		    while(c->active_class)
		    {
			    /*If grade is not a W*/
			    if (strcmp(c->grade, "W"))
			    {
				    /*Get the course hours from course number*/
				    c->hours = CourseHours(c->course_number);

				    /*Get the grade points from hours * GradeAmount*/
				    c->grade_points = c->hours * GradeAmount(c->grade);

				    /*Add up the total current semester hours*/
				    s->hoursCom_current += c->hours;

				    /*Add up the total student hours*/
				    s->hoursCom_total += c->hours;

				    /*Add up the current student grade points*/
				    temp_grades_total += c->grade_points;

				    /*Increment class count*/
				    class_count++;
			    };
			    /*Increment class pointer*/
			    c++;
		    };
		    /*Calculate current gpa by dividing total grade points by
			*semester hours*/
		    s->gpa_current = (float)((float)temp_grades_total /
							    (float)s->hoursCom_current);

		    /*Calculate total gpa by adding two gpas together and
			*dividing by two*/
		    s->gpa_total = (s->gpa_current + s->gpa_total) / 2;

	    };
	    /*Increment the node pointer*/
	    curPtr = curPtr->next;
    };
}

/********************************************************************
 *Function Name : ReturnYear
 *Input : String->Year, Return Buffer
 *Output : Nothing
 *Description : This function returns the matching year string based on
 *              the year of the student.
 ********************************************************************/
void ReturnYear(char year[SMALL_INFO_WIDTH+1], char buffer[])
{
    char grab=year[0]; /*Character grab of year character*/

    /*Case statements of grab characters, returning proper strings*/
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
 *Function Name : PrintCommonData
 *Input : Student Structure Pointer
 *Output : Student number, name, year, and major to the screen
 *Description : This function formats the proper data for the
 *              student and puts it on the screen
 ********************************************************************/
void PrintCommonData(student_struct *s)
{
    char year[5]; /*Year buffer string*/

    /*Print student number*/
    printf("%5s ", s->number);

    /*Print student name*/
    printf("%30s  ", s->name);

    /*Get year buffer and print year*/
    ReturnYear(s->year, year);
    printf("%4s ", year);

    /*Print student major*/
    printf("%4s    ", s->major);
}

/********************************************************************
 *Function Name : PrintSummaryReportLINKED
 *Input : Number of students, student_node linked list
 *Output : The summary report to the screen
 *Description : This function formats all necessary student info.
 *              for a summary report and puts it on the screen.
 ********************************************************************/
void PrintSummaryReportLINKED(int number_students, struct student_node *list_head)
{
    int count=0; /*Student count*/
    char buffer[20]; /*Temp buffer*/
    struct student_node *curPtr=NULL; /*List pointer*/
    student_struct *s=NULL; /*Student pointer*/

    /*If there is only one student to print*/
    if (number_students == 1)
    {
	    /*Print single student intro*/
	    printf("Summary Report for 1 student: \n\n");
    /*Otherwise*/
    }else
    {
	    /*Print multiple student intro*/
	    printf("Summary Report for %d students: \n\n", number_students);
    };

    /*Print table headers*/
    sprintf(buffer, "Current      Overall");
    printf("%70s\n", buffer);
    sprintf(buffer, "Hours GPA    Hours GPA");
    printf("%71s\n", buffer);

    /*Start at list head*/
    curPtr = list_head;

    /*Loop through the number of students requested*/
    while((count < number_students)&&(curPtr))
    {
	    /*Assign student pointer to student_node list*/
	    s = &curPtr->data;

	    /*Print common data on screen*/
	    PrintCommonData(s);

	    /*Print the current semester info*/
	    printf("%d  ", s->hoursCom_current);
	    printf("%3.2f    ", s->gpa_current);

	    /*Print the total college career info*/
	    printf("%3d  ", s->hoursCom_total);
	    printf("%3.2f\n", s->gpa_total);

	    /*Increment counter*/
	    count++;

	    /*Increment list pointer*/
	    curPtr = curPtr->next;
    };
    printf("\n\n");
}

/********************************************************************
 *Function Name : PrintGradeReportLINKED
 *Input : The number of students and the student_node linked list
 *Output : The grade report to the screen
 *Description : This function takes all student info and formats
 *              it for output to the screen.
 ********************************************************************/
void PrintGradeReportLINKED(int number_students, struct student_node *list_head)
{
    int count=0; /*Student count*/
    char buffer[30]; /*Temp buffer*/
    student_struct *s=NULL; /*Student pointer*/
    class_struct *c=NULL; /*Class pointer*/
    struct student_node *curPtr=NULL; /*List pointer*/

    /*If there is only one student to print*/
    if (number_students == 1)
    {
	    /*Print single student intro*/
	    printf("Grade Report for 1 student: \n\n");
    /*Otherwise*/
    }else
    {
	    /*Print multiple student intro*/
	    printf("Grade Report for %d students: \n\n", number_students);
    };

    /*Start at list head*/
    curPtr = list_head;

    /*Loop through the number of students requested*/
    while((curPtr)&&(count < number_students))
    {
	    /*Assign student pointer to (count)student*/
	    s = &curPtr->data;

	    if (IsEnrolled(s))
	    {
		    /*Print common data on screen*/
		    PrintCommonData(s);
		    printf("\n\n");

		    /*Print grade header*/
		    sprintf(buffer, "   Course    Grade  GrPts\n");
		    printf("%20s", buffer);

		    /*Assign class pointer to start of classes*/
		    c = &s->classes[0];

		    /*Loop while class is active*/
		    while(c->active_class)
		    {
			    /*If class is not a W*/
			    if (strcmp(c->grade, "W"))
			    {
				    /*Print the department name*/
				    printf("   %4s ", c->dept_name);

				    /*Print the course number*/
				    printf("%4s   ", c->course_number);

				    /*Print the grade*/
				    printf("%1s      ", c->grade);

				    /*Print the grade points*/
				    printf("%2d\n", c->grade_points);
			    };
			    /*Increment class pointer*/
			    c++;
		    };
		    /*Print current semester info*/
		    printf("\nCurrent Semester:  %3d Hours, %3.2f GPA\n", s->hoursCom_current, s->gpa_current);

		    /*Print overall info*/
		    printf("Overall:           %3d Hours, %3.2f GPA\n", s->hoursCom_total, s->gpa_total);
		    printf("\n\n");

		    /*Increment student count*/
		    count++;
	    };
	    /*Advance position in list*/
	    curPtr = curPtr->next;
    };
    printf("\n\n");
}

/********************************************************************
 *Function Name : Swap
 *Input : Student structure 1 and 2
 *Output : Nothing
 *Description : This function swaps 2 student structures.
 ********************************************************************/
void Swap(student_struct *s1, student_struct *s2)
{
    student_struct temp; /*Temp student structure*/

    /*Assign first structure to temp*/
    temp = *s1;

    /*Assign second structure to the first*/
    *s1 = *s2;

    /*Assign temp to second structure*/
    *s2 = temp;
}

/********************************************************************
 *Function Name : SortStudentsNumericLINKED
 *Input : Student_node linked list
 *Output : Nothing
 *Description : This function numerically sorts the students based
 *              on student number.
 ********************************************************************/
void SortStudentsNumericLINKED(struct student_node *list_head)
{
    int number_students; /*Number of students*/
    student_struct *j, *k; /*Pointers to student structures*/
    struct student_node *jPtr=NULL, *kPtr=NULL; /*Pointers to the linked list*/

    /*Get list positions*/
    jPtr = list_head;

    /*Loop from start of list to one node before end of list*/
    while(jPtr->next)
    {
	    /*Loop from jPtr to end of list*/
	    kPtr = jPtr->next;
	    while(kPtr)
	    {
		    /*Get student structures from each node pointer*/
		    j = &jPtr->data;
		    k = &kPtr->data;

		    /*If student[k] number is less than student[j] number*/
		    if ( (atoi(k->number)) < (atoi(j->number)) )
		    {
			    /*Swap the structures*/
			    Swap(k, j);
		    };

		    /*Advance kPtr*/
		    kPtr = kPtr->next;
	    };

	    /*Advance jPtr*/
	    jPtr = jPtr->next;
    };
}

/********************************************************************
 *Function Name : SortStudentsAlphabeticLINKED
 *Input : Student_node linked list
 *Output : Nothing
 *Description : This function numerically sorts the students based
 *              on student name.
 ********************************************************************/
void SortStudentsAlphabeticLINKED(struct student_node *list_head)
{
    int number_students; /*Number of students*/
    student_struct *j, *k; /*Pointers to student structures*/
    struct student_node *jPtr=NULL, *kPtr=NULL; /*Pointers to the linked list*/

    /*Get list positions*/
    jPtr = list_head;

    /*Loop from start of list to one node before end of list*/
    while(jPtr->next)
    {
	    /*Loop from jPtr to end of list*/
	    kPtr = jPtr->next;
	    while(kPtr)
	    {
		    /*Get student structures from each node pointer*/
		    j = &jPtr->data;
		    k = &kPtr->data;

		    /*If student[k] name is less than student[j] name*/
		    if (strcmp(k->name, j->name) < 0)
		    {
			    /*Swap the structures*/
			    Swap(k, j);
		    };

		    /*Advance kPtr*/
		    kPtr = kPtr->next;
	    };

	    /*Advance jPtr*/
	    jPtr = jPtr->next;
    };
}

/********************************************************************
 *Function Name : WriteBinaryStudentsLINKED
 *Input : File pointer to binary file, student_structure array
 *Output : Nothing
 *Description : This function will write the entire active student
 *              node list to a binary file.
 ********************************************************************/
void WriteBinaryStudentsLINKED(struct student_node *list_head)
{
    FILE *out_binary_file=NULL; /*Output file*/
    struct student_node *curPtr=NULL; /*List pointer*/

    /*Start at the list head*/
    curPtr = list_head;

    /*Open the output file checking for errors*/
    if ((out_binary_file = fopen("stu_bin.dat", "wb"))==NULL)
    {
	    printf("Error opening stu_bin.dat!\n");
	    exit(1);
    }


    /*Write the students to a binary file*/
    while(curPtr)
    {
	    /*Write the current node data to the file*/
	    fwrite(&curPtr->data, sizeof(student_struct), 1, out_binary_file);

	    /*Advance the list pointer*/
	    curPtr = curPtr->next;
    };

    /*Close the binary file*/
    fclose(out_binary_file);
}

/********************************************************************
 *Function Name : ReadBinaryStudentsLINKED
 *Input : File pointer to binary file, student_node list, amount
 *        of students to read, starting offset of the read.
 *Output : Whether or not the read was successful
 *Description : This function will read a binary data file at a certain
 *              offset, into the student_node list with a given
 *              number
 ********************************************************************/
int ReadBinaryStudentsLINKED(struct student_node **list_head, int amount, int offset)
{
    FILE *in_binary_file=NULL; /*Input file pointer*/
    long seeker_amount=0; /*Offset file position for fseek*/
    struct student_node *InsertNode=NULL; /*Temp insert node*/
    student_struct s; /*Temp data holder*/
    int count=0; /*Counter for students read*/
    int rt=FALSE; /*Return value*/

    /*Release the student_node list*/
    Release(list_head);

    /*Get offset position for fseek*/
    seeker_amount = sizeof(student_struct) * offset;

    /*Open the input file checking for errors*/
    if ((in_binary_file = fopen("stu_bin.dat", "rb"))==NULL)
    {
	    printf("File doesn't exist->stu_bin.dat!\n");
	    rt = FALSE;
    }else
    {
	    /*Fseek the file position for the student read*/
	    fseek(in_binary_file, seeker_amount, 0);

	    /*Read the students from the binary file*/
	    while((fread(&s, sizeof(student_struct), 1, in_binary_file)==1)&&(count < amount))
	    {
		    /*Allocate node memory*/
		    InsertNode = GetStudentNodeMemory();

		    /*Assign read data to node data*/
		    InsertNode->data = s;

		    /*Insert the new node in the list*/
		    InsertEnd(list_head, InsertNode);

		    /*Increase the counter*/
		    count++;
	    };
	    /*Set valid return value*/
	    rt = TRUE;
    };
    /*Return the value*/
    return(rt);
}

/********************************************************************
 *Function Name : ValidChoice
 *Input : Choice variable, minimum and maximum values
 *Output : Whether or not the choice was valid
 *Description : This function determines the validity of the choice
 *              variable based on min and max.
 ********************************************************************/
int ValidChoice(int *choice, int min, int max)
{
    int rt=FALSE; /*Return value*/

    /*If choice is valid*/
    if ((*choice >= min) && (*choice <= max))
	    /*Set the return to TRUE*/
	    rt = TRUE;

    /*If choice is invalid, print warning statement*/
    if (!rt)printf("Invalid Choice!\n");

    /*Return the value*/
    return(rt);
}

/********************************************************************
 *Function Name : Main_Menu
 *Input : Nothing
 *Output : The user choice and the menu
 *Description : This function prints the menu for the user then
 *              prompts for the choice
 ********************************************************************/
int Main_Menu(void)
{
    int choice=-1; /*User choice*/

    /*Loop while choice is not valid*/
    do
    {
	 /*Print info*/
	 printf("1) Print a summary report\n");
	 printf("2) Print grade reports\n");
	 printf("3) Sort by name alphabetically\n");
	 printf("4) Sort by student number ascending\n");
	 printf("5) Write students to binary file\n");
	 printf("6) Read students from binary file\n");
	 printf("7) Delete a student from the list\n");
	 printf("8) Exit\n");
	 printf("\nYour Choice? ");

	 /*Flush input and get choice*/
	 fflush(stdin);
	 scanf("%d", &choice);
    }while (!ValidChoice(&choice, 1, EXIT_NUMBER));

    /*Return choice value*/
    return(choice);
}

/********************************************************************
 *Function Name : Handle_EventLINKED
 *Input : Event value, student_node linked list
 *Output : Nothing
 *Description : This function handles all user options.
 ********************************************************************/
void Handle_EventLINKED(int event, struct student_node **list_head)
{
    int number_students=-1; /*Number of students*/
    int offset=-1; /*Offset of read*/
    char student_number[STUDENT_NUMBER_WIDTH+1]; /*Hold string for number*/
    int finish=FALSE; /*Status value for loop condition*/


    /*Do case statements for each user option*/
    switch(event)
    {
	 /*Summary Report Case*/
	 case 1 :  /*Loop while number_students is not valid*/
			 do
			 {
				/*Prompt user and get amount*/
				printf("How many students(999 for all)? ");
				scanf("%d", &number_students);

			 }while(!ValidChoice(&number_students, 1, 999));

			 /*Print summary reports*/
			 PrintSummaryReportLINKED(number_students, *list_head);
			 break;

	 /*Grade Report Case*/
	 case 2 : /*Loop while number_students is not valid*/
			do
			{
				/*Prompt user and get amount*/
				printf("How many students(999 for all)? ");
				scanf("%d", &number_students);

			}while(!ValidChoice(&number_students, 1, 999));

			/*Print grade reports*/
			PrintGradeReportLINKED(number_students, *list_head);
			break;

	 /*Sort Alphabetic Case*/
	 case 3 : /*Sort students alphabetically*/
			SortStudentsAlphabeticLINKED(*list_head);
			printf("Alphabetic sort completed.\n\n");
			break;
	 /*Sort Numeric Case*/
	 case 4 : /*Sort students numerically*/
			SortStudentsNumericLINKED(*list_head);
			printf("Numeric sort completed.\n\n");
			break;

	 /*Write Binary Case*/
	 case 5 : /*Write students to a binary file*/
			WriteBinaryStudentsLINKED(*list_head);
			printf("Binary file created.\n");
			break;

	 /*Read Binary Case*/
	 case 6 : /*Loop while number students invalid*/
			do
			{
				/*Prompt user and get amount*/
				printf("How many students(999 for all)? ");
				scanf("%d", &number_students);

			}while(!ValidChoice(&number_students, 1, 999));

			/*Prompt user and get amount*/
			printf("Starting with what student? ");
			scanf("%d", &offset);

			/*Read binary file*/
			if (ReadBinaryStudentsLINKED(list_head, number_students, offset))
			    printf("Binary Read Complete.\n");
			break;

    /*Delete student case*/
    case 7 :   /*Loop until valid string read*/
			do
			{
			    /*Prompt user for student number*/
			    printf("Enter the student's number-> ");
			    scanf("%5s", student_number);

			    /*Check for valid string*/
			    if ( (atol(student_number) >= 10000) && (atol(student_number) <= 99999) )
				    finish = TRUE;
			    else
				    printf("Invalid student number!\n");
			}while(!finish);

			/*Delete student from list*/
			DeleteStudent(list_head, student_number);
			break;
    };
}






