#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <conio.h>

#define MAX_STUDENTS 100

void AdvanceStringPointer(char **);
void GetAlphaString(char [], char [], int, int);
void GetDigitString(char [], char [], int, int);

void main(void){
FILE *grades=NULL, *students=NULL;
char buffer[80];
char alpha_string[5];
char digit_string[6];

if ((grades = fopen("grades.dat", "r"))==NULL){
    printf("Error opening grades.dat!\n");
    getch();
    exit(1);
}

if ((students = fopen("students.dat", "r"))==NULL){
    printf("Error opening students.dat!\n");
    getch();
    exit(1);
};

if (GetLine(grades, buffer, 80)){
    GetDigitString(buffer, digit_string, 5, 0);
    printf("Digit String->%s", digit_string);
    GetAlphaString(buffer, alpha_string, 4, 6);
    printf("Alpha String->%s", alpha_string);
    getch();
};


fclose(grades);
fclose(students);
}

int GetLine(FILE *fp, char *buffer, int max){
int rt=1;
if ((fgets(buffer, max, fp))==NULL){
    rt = 0;
};
if (rt){
    printf("\nFile->%s", buffer);
    getch();
};
return(rt);
}

void AdvanceStringPointer(char **ptr){
(*ptr)+=1;
}

void GetAlphaString(char buffer[], char alpha[], int amount, int offset){
int j=0;
char *buffer_ptr=NULL;
char *alpha_ptr=NULL;
char holder='0';

buffer_ptr = &buffer[offset];
alpha_ptr = &alpha[0];
for (j = offset; j < (offset+amount); j++){
    if (isalpha((*buffer_ptr))){
	 sscanf(buffer_ptr, "%c", &holder);
	 *alpha_ptr = holder;
	 alpha_ptr++;
    };
    buffer_ptr++;
};
*alpha_ptr = '\0';
}

void GetDigitString(char buffer[], char digit[], int amount, int offset){
int j=0;
char *buffer_ptr=NULL;
char *digit_ptr=NULL;
char holder='0';

buffer_ptr = &buffer[offset];
digit_ptr = &digit[0];
for (j = offset; j < (offset+amount); j++){
    if (isdigit((*buffer_ptr))){
	 sscanf(buffer_ptr, "%c", &holder);
	 *digit_ptr = holder;
	 digit_ptr++;
    };
    buffer_ptr++;
};
*digit_ptr = '\0';
}


