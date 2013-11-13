/********************************************************************
 *Description : This program allows the user to enter up to 20 digits
 *              in an array.  After this, the user is presented with a menu
 *              to choose what function(s) to carry out on the array.  These
 *              options include Sorting(Ascending/Descending), Printing,
 *              Adding/Deleting Values, and calculating statistics.
 ********************************************************************/

/*Includes*/
#include <stdio.h>
#include <math.h>

/*Defines for true/false*/
#define FALSE 0
#define TRUE 1

/*Defines for misc. program values*/
#define DESCENDING 0
#define ASCENDING 1
#define EXIT_NUMBER 7
#define EMPTY_VALUE -1

/*Defines for size of arrays*/
#define SIZE_INT_ARRAY 20
#define SIZE_FLOAT_ARRAY 3

/*Function Prototypes*/
int NumberInArray(int *);
void GetArray(int *);
void Swap(int *, int *);
void SortArray(int *, int);
float Mean(int *);
float Variance(int *);
float StdDeviation(float);
void CalcStatistics(int *, float *);
int AddValue(int *, int);
int SearchValue(int *, int);
int DeleteValue(int *, int);
void PrintArray(int *);
void PrintArrayStatistics(float *);
int Main_Menu(void);
void Handle_Event(int, int *, int *, float *);

/***************************MAIN PROGRAM****************************/
void main(void)
{
    int integer_array[SIZE_INT_ARRAY];  /*Integer storage array*/
    float stat_array[SIZE_FLOAT_ARRAY]; /*Statistics storage array*/
    int user_choice=0;  /*Choice entered by user*/
    int statistic_flag=FALSE;   /*Flag to print statistics*/

    /*Gets the array from the user*/
    GetArray(integer_array);

    /*Loop to run functions chosen by user*/
    do
    {
	 /*Get user choice from menu screen*/
	 user_choice = Main_Menu();

	 /*Handle user choice functions*/
	 Handle_Event(user_choice, &statistic_flag, integer_array, stat_array);
	 printf("\n\n");

    /*End loop when user enters exit number*/
    }while(user_choice != EXIT_NUMBER);
}

/********************************************************************
 *Function Name : NumberInArray
 *Input : Integer Array
 *Output : The number of valid entries in array
 *Description : This functions counts the number of valid entries
 *              in the current array.
 *******************************************************************/
int NumberInArray(int *array)
{
    int j=0, rt=0; /*For loop variable, return value*/

    /*Loop to run through all indices in array*/
    for (j = 0; j < SIZE_INT_ARRAY; j++)
	 /*If the array value is not an empty value, add up the value*/
	 if (array[j] != EMPTY_VALUE)rt++;

    /*Return the value*/
    return(rt);
}

/********************************************************************
 *Function Name : GetArray
 *Input : Integer Array
 *Output : Nothing
 *Description : This function gets the numbers to fill the array
		from the user.  When the user enters -1, it fills
		the rest of the array with -1's.
 *******************************************************************/
void GetArray(int *array)
{
    int j, count=0; /*For loop variable, array counter*/
    int *int_ptr=NULL; /*Integer array pointer*/

    /*Assign int_ptr to beginning of array*/
    int_ptr = &array[0];

    printf("Please enter up to 20 integers (%d to stop): \n", EMPTY_VALUE);
    /*Loop to fill array*/
    do{
	 /*Prompt user to enter number*/
	 printf("#%d> ", count+1);

	 /*Get number from user*/
	 scanf("%d", int_ptr);

	 /*If value is not negative*/
	 if ((*int_ptr) >= 0){
		/*Increment array counter*/
		count++;

		/*Increment array pointer*/
		int_ptr++;

	 /*Else if value is not -1*/
	 }else if ((*int_ptr) < EMPTY_VALUE){
		/*Tell user that they did not enter a valid number*/
		printf("Invalid Value!\n");
	 };

    /*End loop if value was -1 or if reached end of array*/
    }while(((*int_ptr) != EMPTY_VALUE)&&(count < SIZE_INT_ARRAY));

    /*Loop to fill rest of array with -1*/
    for (j = count; j < SIZE_INT_ARRAY; j++)
	 array[j] = EMPTY_VALUE;

    /*Skip two lines*/
    printf("\n\n");
}

/********************************************************************
 *Function Name : Swap
 *Input : Pointers to values being swapped
 *Output : Nothing
 *Description : This function swaps the values of two numbers.
 *******************************************************************/
void Swap(int *x, int *y)
{
    int temp;  /*Temp holding variable*/

    /*Get x value*/
    temp = *x;

    /*Assign y value to x*/
    *x = *y;

    /*Assign old x value to y*/
    *y = temp;
}

/********************************************************************
 *Function Name : SortArray
 *Input : The integer array and the direction being sorted
 *Output : Nothing
 *Description : This function sorts the current array using a
		bubble sort and either in a descending or ascending
		order based on direction value.
 *******************************************************************/
void SortArray(int *array, int dir)
{
    int j=0, k=0;  /*For loop variables*/
    int num_array=0;  /*Number in the array*/

    /*Get amount in array*/
    num_array = NumberInArray(array);

    /*Loop to do bubble sort(From start to end-1)*/
    for (j = 0; j < num_array-1; j++)
    {
	 /*From j to end of the array*/
	 for (k = j; k < num_array; k++)
	 {
	      /*If direction is ascending*/
	      if (dir == ASCENDING)
	      {
		   /*If array value k is less than
		     array value j, swap them*/
		   if (array[k] < array[j])
		   {
			/*Swap the numbers*/
			Swap(&array[j], &array[k]);
		   };
	      /*If direction is descending*/
	      }else
	      {
		   /*If array value k is greater than
		     array value j, swap them*/
		   if (array[k] > array[j])
		   {
			/*Swap the numbers*/
			Swap(&array[j], &array[k]);
		   };
	      };
	 };
    };
}

/********************************************************************
 *Function Name : Mean
 *Input : The integer array
 *Output : The mean value
 *Description : This function finds the mean value from the array
 *******************************************************************/
float Mean(int *array)
{
    int total=0;  /*Sum number*/
    int j=0;  /*For loop variable*/
    int num_array=0;  /*Number in the array*/
    float avg=0.0;   /*The return variable*/

    /*Get the number of the array*/
    num_array = NumberInArray(array);

    /*For loop to add up the numbers in the array*/
    for (j = 0; j < num_array; j++)
    {
	 /*Sum is equal to sum plus array value*/
	 total += array[j];
    }

    /*Get average by dividing total by number in array*/
    avg = (float)(total / num_array);

    /*Return the value*/
    return(avg);
}

/********************************************************************
 *Function Name : Variance
 *Input : The integer array
 *Output : The variance value
 *Description : This function finds the variance value from the array
 *******************************************************************/
float Variance(int *array)
{
    float average=0.0;  /*The average value*/
    float total_sum=0.0;  /*The total sum from the sigma equation*/
    float var=0.0;  /*The variance value*/
    int num_array=0, j=0;  /*The number in the array, for loop variable*/

    /*Get the number in the array*/
    num_array = NumberInArray(array);

    /*Get the average*/
    average = Mean(array);

    /*For loop to add up the sigma equation*/
    for (j = 0; j < num_array; j++)
    {
	 /*Total is equal to total + (array value - average)^2*/
	 total_sum += (float)(pow((float)array[j] - average, 2));
    };

    /*Variance is equal to total divided by number in array*/
    var = total_sum / num_array;

    /*Return the value*/
    return(var);
}

/********************************************************************
 *Function Name : StdDeviation
 *Input : The variance value
 *Output : The standard deviation value
 *Description : This function finds the std. deviation from the
		variance value.
 *******************************************************************/
float StdDeviation(float variance)
{
    float std_dev=0.0;  /*The std. deviation value*/

    /*Standard deviation is equal to square root of variance*/
    std_dev = sqrt(variance);

    /*Return the value*/
    return(std_dev);
}

/********************************************************************
 *Function Name : CalcStatistics
 *Input : The integer array and the floating point array
 *Output : Nothing
 *Description : This function calls all necessary statistical
		functions and stores their values in the
		stat array using pointer notation.
 *******************************************************************/
void CalcStatistics(int *array, float *stats)
{
    float *stat_ptr=NULL; /*Pointer to floating point array*/

    /*Assign pointer to first value*/
    stat_ptr = &stats[0];

    /*Get the average*/
    *stat_ptr = Mean(array);

    /*Increment pointer*/
    stat_ptr++;

    /*Get the variance*/
    *stat_ptr = Variance(array);

    /*Increment pointer*/
    stat_ptr++;

    /*Get the standard deviation accessing the previous pointer
      value for the variance*/
    *stat_ptr = StdDeviation(*(stat_ptr-1));
}

/********************************************************************
 *Function Name : AddValue
 *Input : The integer array and the value to add
 *Output : Whether or not the value was added
 *Description : This function adds a value to the end of the array
 *******************************************************************/
int AddValue(int *array, int value)
{
    int num_array=0, rt=FALSE;  /*Number in the array, return value*/

    /*Get the number in the array*/
    num_array = NumberInArray(array);

    /*Check to see if there is room in the array*/
    if (num_array < SIZE_INT_ARRAY)
    {
	/*Assign the value at the end of the array*/
	array[num_array] = value;

	/*The adding of the value was successful*/
	rt = TRUE;
    };

    /*Return the value*/
    return(rt);
}

/********************************************************************
 *Function Name : SearchValue
 *Input : The integer array and the value to find
 *Output : The index of the value in the array
 *Description : This function finds a value in the current array
 *******************************************************************/
int SearchValue(int *array, int value)
{
    int j, rt=-1;  /*For loop variable, the array index*/
    int num_array=0; /*Number in the array*/

    /*Get the number in the array*/
    num_array = NumberInArray(array);

    /*For loop through the array*/
    for (j = 0; j < num_array; j++)
	 /*If the value is at this point in the array,
	   assign return value to the array index*/
	 if (array[j] == value)rt = j;

    /*Return the value*/
    return(rt);
}

/********************************************************************
 *Function Name : DeleteValue
 *Input : The integer array and the value to delete
 *Output : Whether or not the value was deleted
 *Description : This function deletes a value currently in the array,
		returning 1 if it was deleted and false if it could
		not be found.
 *******************************************************************/
int DeleteValue(int *array, int value)
{
    int num_array=0, rt=FALSE;  /*Number in the array, the return value*/
    int array_index=-1;  /*Array index value*/
    int j=0; /*For loop variable*/

    /*Get the number in the array*/
    num_array = NumberInArray(array);

    /*Get the array index of the number*/
    array_index = SearchValue(array, value);

    /*If the array index is valid*/
    if (array_index != -1)
    {
	 /*Make that value empty(-1)*/
	 array[array_index] = EMPTY_VALUE;

	 /*The deletion was successful*/
	 rt = TRUE;

	 /*For loop through rest of the array to flush
	   the -1 to the end of the array*/
	 for (j = array_index; j < num_array-1; j++)
	      Swap(&array[j], &array[j+1]);
    };

    /*Return the value*/
    return(rt);
}

/********************************************************************
 *Function Name : PrintArray
 *Input : The integer array
 *Output : Nothing
 *Description : This function prints out the array on the screen
 *******************************************************************/
void PrintArray(int *array)
{
    int j=0, num_array=0; /*For loop variable, the number in the array*/

    /*Get the number in the array*/
    num_array = NumberInArray(array);

    printf("\n");

    /*For loop through the array*/
    for (j = 0; j < num_array; j++)
	 /*Print the value*/
	 printf("%d ", array[j]);

    printf("\n");
}

/********************************************************************
 *Function Name : PrintArrayStatistics
 *Input : The floating point array
 *Output : Nothing
 *Description : This function prints out the statistical array on
		the screen using pointer notation.
 *******************************************************************/
void PrintArrayStatistics(float *stats)
{
    float *stat_ptr=NULL;  /*The floating point pointer*/

    /*Assign pointer to first value in array*/
    stat_ptr = &stats[0];

    /*Print out the mean*/
    printf("  Mean :     %5.2f\n", *stat_ptr);

    /*Increment pointer two to get third value*/
    stat_ptr+=2;

    /*Print out the std. deviation*/
    printf("  Std Dev :  %5.2f\n", *stat_ptr);

    /*Decrement pointer to get second value*/
    stat_ptr--;

    /*Print out the variance*/
    printf("  Variance:  %5.2f\n", *stat_ptr);
}

/********************************************************************
 *Function Name : Main_Menu
 *Input : Nothing
 *Output : The user choice
 *Description : This function prints out the menu, prompts the user
		for a choice, and gets it.
 *******************************************************************/
int Main_Menu(void)
{
    int choice=0; /*User choice*/

    /*Display the menu*/
    printf("1) Sort/Descending\n");
    printf("2) Sort/Ascending\n");
    printf("3) Calculate Statistics\n");
    printf("4) Add a Value\n");
    printf("5) Delete a Value\n");
    printf("6) Print\n");
    printf("7) Exit\n");

    /*Prompt user for choice*/
    printf("Enter choice: ");

    /*Get the number from the user*/
    scanf("%d", &choice);

    /*Return the choice value*/
    return(choice);
}

/********************************************************************
 *Function Name : Handle_Event
 *Input : The event, the stat flag, the integer array, and stat. array
 *Output : Nothing
 *Description : This function handles all user functions based on
		input event.
 *******************************************************************/
void Handle_Event(int event, int *stat_flag, int *array, float *stats)
{
    int choice=0;  /*The choice value*/

    /*If menu option 1*/
    if (event == 1)
	 /*Sort array in descending order*/
	 SortArray(array, DESCENDING);

    /*Else if menu option 2*/
    else if (event == 2)

	 /*Sort array in ascending order*/
	 SortArray(array, ASCENDING);

    /*Else if menu option 3*/
    else if (event == 3)
    {
	 /*Calculate statistics*/
	 CalcStatistics(array, stats);

	 /*Set flag for printing statistics*/
	 *stat_flag = TRUE;

    /*Else if menu option 4*/
    }else if (event == 4)
    {
	 /*Prompt and get value to add*/
	 printf("Value to add> ");
	 scanf("%d", &choice);

	 /*If add value was not successful*/
	 if (!AddValue(array, choice))
	      /*Tell the user there was an error*/
	      printf("\nError : No more room in array!\n");
	 /*Otherwise*/
	 else
	      /*Set flag for not printing statistics*/
	      *stat_flag = FALSE;

    /*Else if menu option 5*/
    }else if (event == 5)
    {
	 /*Prompt and get value to delete*/
	 printf("Value to delete> ");
	 scanf("%d", &choice);

	 /*If delete value was not successful*/
	 if (!DeleteValue(array, choice))
	      /*Tell the user there was an error*/
	      printf("\nError : %d is not in list.", choice);
	 /*Otherwise*/
	 else
	      /*Set flag for not printing statistics*/
	      *stat_flag = FALSE;
    /*Else if menu option 6*/
    }else if (event == 6)
    {
	 /*Print the array out on the screen*/
	 PrintArray(array);

	 /*If the statistics can are current, print them out*/
	 if (*stat_flag == TRUE)PrintArrayStatistics(stats);

    /*Else if exit value*/
    }else{
	 /*Print the array out on the screen*/
	 PrintArray(array);

	 /*Print the statistics array on the screen*/
	 PrintArrayStatistics(stats);
    };
}






