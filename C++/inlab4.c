/**********************************************************
 *Programmer : Thomas LePage                              *
 *Program : post04.c                                      *
 *Language : C                                            *
 *Description : A program that accepts an internet address*
 *              and stores it in a single variable.  The  *
 *               program then prompts the user for a menu *
 *              to perform several manipulations to the   *
 *              address.                                  *
 *********************************************************/

/*Includes*/
#include <stdio.h>

/*Defines for function returns*/
#define TRUE 1
#define FALSE 0

/*Shifting values for current bytes in the address*/
#define ZERO_BYTE 0
#define FIRST_BYTE 8
#define SECOND_BYTE 16
#define THIRD_BYTE 24

/*Exit number of the program*/
#define EXIT_NUMBER 5

/*Constant masks to return a certain byte*/
const unsigned int BYTE_3_MASK = 0xff000000;
const unsigned int BYTE_2_MASK = 0x00ff0000;
const unsigned int BYTE_1_MASK = 0x0000ff00;
const unsigned int BYTE_0_MASK = 0x000000ff;

/*Constant masks to zero out a certain byte*/
const unsigned int BYTE_3_MASK_0 = 0x00ffffff;
const unsigned int BYTE_2_MASK_0 = 0xff00ffff;
const unsigned int BYTE_1_MASK_0 = 0xffff00ff;
const unsigned int BYTE_0_MASK_0 = 0xffffff00;

/*Function Prototypes*/
short Get_Byte(unsigned int *, short);
void Print_Byte(unsigned int *, short);
short Valid_Number(short *, short, short);
short Add_Byte_Address(unsigned int *, short *, short);
unsigned int Get_Address(void);
void Print_Binary(short *);
void Print_Address_Binary(unsigned int *);
void Print_Address_Net(unsigned int *);
void Zero_Out_Byte(unsigned int *, short);
void Handle_SubNet_Change(unsigned int *);
short Main_Menu(void);
void Handle_Event(short, unsigned int *);

/********************MAIN PROGRAM*************************/
void main(void)
{
    short event = 0;  /*Holds the event value from the menu*/
    unsigned int address = 0;  /*Holds the address*/

    /*Print the welcome message*/
    printf("Welcome to Internet Address Storage\n");
    do
    {
         event = Main_Menu();
         Handle_Event(event, &address);
    }while(event != EXIT_NUMBER);
}
/*******************END MAIN PROGRAM**********************/


/**********************************************************
 *Function Name : Get_Byte                                *
 *Input : The address and the byte to retrieve            *
 *Output : The value of the sub_net from the byte         *
 *Description : Takes the address using masks and         *
 *              shifts to retrieve the specified byte.    *
 *              The function then returns that value.     *
 *********************************************************/
short Get_Byte(unsigned int *address, short byte)
{
    short sub_net = 0; /*sub_net value to return*/

    /*Starts case statements*/
    switch(byte)
    {
         /*Byte 0 Case-> Uses byte 0 mask and shifts to zero byte*/
         case 0 : sub_net = (short)((*address & BYTE_0_MASK)>>ZERO_BYTE);
            break;

         /*Byte 1 Case-> Uses byte 1 mask and shifts to first byte*/
         case 1 : sub_net = (short)((*address & BYTE_1_MASK)>>FIRST_BYTE);
            break;

         /*Byte 2 Case-> Uses byte 2 mask and shifts to second byte*/
         case 2 : sub_net = (short)((*address & BYTE_2_MASK)>>SECOND_BYTE);
            break;

         /*Byte 3 Case-> Uses byte 3 mask and shifts to third byte*/
         case 3 : sub_net = (short)((*address & BYTE_3_MASK)>>THIRD_BYTE);
            break;
    };

    /*Returns the value*/
    return(sub_net);
}

/**********************************************************
 *Function Name : Print_Byte                              *
 *Input : The address and the byte to retrieve            *
 *Output : Prints text to the screen                      *
 *Description : Takes the address and calls the Get_Byte  *
 *              function for a value to put on the screen *
 *********************************************************/
void Print_Byte(unsigned int *address, short byte)
{
    /*Print the result of Get_Byte*/
    printf("%hd\n", Get_Byte(address, byte));
}

/**********************************************************
 *Function Nme : Valid_Number                            *
 *Input : The number, the low range, and the high range   *
 *Output : Whether or not the number is in this range     *
 *Description : Takes a pointer to a number and checks to *
                see if that number is within the specified*
                range.                                    *
 *********************************************************/
short Valid_Number(short *number, short low, short high)
{
    short rt = FALSE;  /*Return value*/

    /*Checks the range of the number*/
    if ((*number >= low)&&(*number <= high))
         /*If number is valid, set return to TRUE*/
         rt = TRUE;

    /*Return the value*/
    return(rt);
}

/**********************************************************
 *Function Name : Add_Byte_Address                        *
 *Input : The address, the number to add, and the byte    *
 *Output : Whether or not the number was added            *
 *Description : Using the (bitwise-OR) operator and shifts*
 *              adds the number to the address.           *
 *********************************************************/
short Add_Byte_Address(unsigned int *address, short *add_byte, short byte)
{
    short rt = FALSE; /*Return value*/

    /*If the sub_net number is 0-255*/
    if (Valid_Number(add_byte, 0, 255) == TRUE)
    {
         /*Set the return value to true*/
         rt = TRUE;

         /*Runs the case statements*/
          switch(byte)
         {
              /*Byte 0 case*/
              case 0 : *address |= *add_byte;
                 break;

              /*Byte 1 case*/
              case 1 : *address |= *add_byte << FIRST_BYTE;
                 break;

              /*Byte 2 case*/
              case 2 : *address |= *add_byte << SECOND_BYTE;
                 break;

              /*Byte 3 case*/
              case 3 : *address |= *add_byte << THIRD_BYTE;
                 break;
         };
    };

    /*Return value*/
    return(rt);
}

/*********************************************************
 *Function Name : Get_Address                         *
 *Input : Nothing                                        *
 *Output : The finished address                          *
 *Description : This function gets the four subnets from *
 *              the user and then sets the bytes in the  *
 *              address using Add_Byte_Address function  *
 *********************************************************/
unsigned int Get_Address(void)
{
    int j;   /*For loop variable*/
    short finish = FALSE, count = 0; /*Loop status variable, counting var.*/
    short sub_net[4];   /*Sub net array*/
    unsigned int address = 0;  /*Return value*/


    do{
         /*Reset the sub_net array*/
         for (j = 0; j < 4; j++) sub_net[j] = -1;

         /*Ask user for values and retrieve them with formatted scanf*/
         printf("What is the address?  ");
         fflush(stdin);
         scanf("%hd.%hd.%hd.%hd", &sub_net[3], &sub_net[2], &sub_net[1], &sub_net[0]);

         /*Reset count variable*/
         count = 0;

         /*Add bytes to address with result being added to count*/
         count += Add_Byte_Address(&address, &sub_net[3], 3);
         count += Add_Byte_Address(&address, &sub_net[2], 2);
         count += Add_Byte_Address(&address, &sub_net[1], 1);
         count += Add_Byte_Address(&address, &sub_net[0], 0);

         /*If four bytes were successfully added, stop while loop*/
         if (count == 4)
         {
              /*Stop loop by setting to TRUE*/
              finish = TRUE;
         }
         else
         {
              /*If not successful, address was invalid*/
              printf("Invalid address!\n");

              /*Reset the address*/
              address = 0;
         };

    /*End Loop*/
    }while(!finish);

    /*Return the address*/
    return(address);
}

/*********************************************************
 *Function Name : Print_Binary                           *
 *Input : The byte to be printed in binary               *
 *Output : Outputs text to the screen                    *
Description : This function takes the number, uses the *
 *              bitwise-and operator with shifts to get  *
 *              the binary values and puts them on the   *
 *              screen.                                  *
 *********************************************************/
void Print_Binary(short *number)
{
    int j=0; /*For loop var.*/

    /*Run loop through 8 bit positions*/
    for (j = 7; j > -1; j--)
    {
         /*If the result of 1 shifted j is positive*/
         if (*number & (1 << j))
         {
              /*Put a one on the screen*/
              printf("1");
         }
         else
         {
              /*Put a zero on the screen*/
              printf("0");
         };

         /*If have printed four numbers, put a space*/
         if (j == 4) printf(" ");
    /*End Loop*/
    };
}

/*********************************************************
 *Function Name : Print_Address_Binary                   *
 *Input : The address                                    *
 *Output : Puts text on the screen                       *
 *Description : This function takes the address and runs *
                through a loop that retrieves the address*
                bytes and prints them in binary          *
 *********************************************************/
void Print_Address_Binary(unsigned int *address)
{
    int j = 0;  /*For loop var.*/
    short sub_net = 0;  /*Sub Net value*/

    /*Print starting string*/
    printf("The bit pattern is: ");

    /*Run through all four bytes*/
    for (j = 3; j > -1; j--)
    {
         /*Get the byte*/
         sub_net = Get_Byte(address, j);

         /*Print the byte in binary*/
         Print_Binary(&sub_net);

         /*Print a space between bytes*/
         printf(" ");
    };

    /*Advance the line*/
    printf("\n");
}

/*********************************************************
 *Function Name : Print_Address_Net                      *
 *Input : The address                                  *
 *Output : Puts text on the screen                       *
 *Description : This function takes the address and runs *
                through a loop that retrieves the address*
                bytes and prints them in normal form     *
 *********************************************************/
void Print_Address_Net(unsigned int *address)
{
      /*Prints the string on the screen*/
      printf("The current address is %hd.%hd.%hd.%hd\n",
                                      Get_Byte(address, 3), /*Byte 3*/
                                      Get_Byte(address, 2), /*Byte 2*/
                                      Get_Byte(address, 1), /*Byte 1*/
                                      Get_Byte(address, 0));/*Byte 0*/
}

/*********************************************************
 *Function Name : Zero_Out_Byte                          *
 *Input : The address and the byte                       *
 *Output : The address with specified byte zeroed out    *
 *Description : Takes the address using bitwise-and and  *
                masks to zero out a specific byte        *
 *********************************************************/
void Zero_Out_Byte(unsigned int *address, short byte)
{
      /*Runs the case statements*/
      switch(byte)
      {
            /*Byte 0*/
            case 0 : *address &= BYTE_0_MASK_0;
              break;
            /*Byte 1*/
            case 1 : *address &= BYTE_1_MASK_0;
              break;
            /*Byte 2*/
            case 2 : *address &= BYTE_2_MASK_0;
              break;
            /*Byte 3*/
            case 3 : *address &= BYTE_3_MASK_0;
              break;
      };
}

/*********************************************************
 *Function Name : Handle_SubNet_Change                   *
 *Input : The address                                    *
 *Output : The changed address                           *
 *Description : This function handles all necessary      *
 *              tasks to change a specific subnet in the *
 *              address.                                 *
 *******************************************************/
void Handle_SubNet_Change(unsigned int *address)
{
      short byte_choice = 0;   /*User byte choice*/
      short byte_to_change = 0;  /*Reverse of the byte choice*/
      short sub_net_address = -1; /*User sub net choice*/

      /*Loop to get the byte choice*/
      do
      {
            /*Prompt user for byte choice and get it*/
            printf("Which part to change (1-4)? ");
            fflush(stdin);
            scanf("%hd", &byte_choice);

      /*Make sure the byte choice is valid before continuing*/
      }while(Valid_Number(&byte_choice, 1, 4) != TRUE);

      /*Must reverse the byte choice to work in this program*/
      /*In this program the bytes are ordered like this:
           3.2.1.0
        This reversal makes the byte choice correspond to the
        proper byte, stored in byte_to_change*/
      byte_choice -= 4;
      byte_to_change = -byte_choice;

      /*Loop to get proper subnet value*/
      do
      {
            /*Prompt user for subnet choice and get it*/
            printf("Change it from %hd to what? ", Get_Byte(address, byte_to_change));
            fflush(stdin);
            scanf("%hd", &sub_net_address);

      /*Make sure the subnet value is valid before continuing*/
      }while(Valid_Number(&sub_net_address, 0, 255) != TRUE);

      /*Zero out the byte to change*/
      Zero_Out_Byte(address, byte_to_change);

      /*Add the byte to the address*/
      Add_Byte_Address(address, &sub_net_address, byte_to_change);
}

/*********************************************************
 *Function Name : Main_Menu                              *
 *Input : Nothing                                        *
 *Output : Returns the user choice                       *
 *Description : Displays the menu and prompts user for   *
 *              their choice (Invalid value are handled  *
 *              through the switch statement in the      *
 *              Handle Event function).                 
 *********************************************************/
short Main_Menu(void)
{
      short rt = 0;  /*Return value*/

      /*Display Menu*/
      printf("1) Enter an address\n");
      printf("2) Print the current address\n");
      printf("3) Print current address as bits\n");
      printf("4) Change a subnet\n");
      printf("5) Exit\n");

      /*Get user choice*/
      printf("Your Choice? ");
      fflush(stdin);
      scanf("%hd", &rt);

      /*Return the value*/
      return(rt);
}

/*********************************************************
 *Function Name : Handle_Event                           *
 *Input : The event and the address                      *
 *Output : Nothing                                       *
 *Description : Runs the specified function based on     *
                event value (If value is invalid, it is  *
                not used).                               *
 *********************************************************/
void Handle_Event(short event, unsigned int *address)
{
      /*Run case statements*/
      switch(event)
      {
            /*Gets the address->Menu Option 1*/
            case 1 : *address = Get_Address();
              break;
            /*Prints the address in net form->Menu Option 2*/
            case 2 : Print_Address_Net(address);
              break;
            /*Prints the address in binary form->Menu Option 3*/
            case 3 : Print_Address_Binary(address);
              break;
            /*Runs subnet change function->Menu Option 4*/
            case 4 : Handle_SubNet_Change(address);
              break;
      };
}



