#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <conio.h>

#define MAX_STUDENTS 100
#define MAX_CLASSES_STUDENT 10
#define STUDENT_NUMBER_WIDTH 5
#define STUDENT_NAME_WIDTH 80
#define COURSE_INFO_WIDTH 4
#define SMALL_INFO_WIDTH 1

typedef struct{
int hours, grade_points;
char course_number[COURSE_INFO_WIDTH+1];
char dept_name[COURSE_INFO_WIDTH+1];
char grade[SMALL_INFO_WIDTH+1];
}class_struct;

typedef struct{
char student_number[STUDENT_NUMBER_WIDTH+1];
char student_name[STUDENT_NAME_WIDTH+1];
class_struct classes[MAX_CLASSES_STUDENT];
char year[SMALL_INFO_WIDTH+1];
char major[COURSE_INFO_WIDTH+1];
int hoursCom_current, hoursCom_total;
float gpa_current, gpa_total;
}student_struct;


int GetLine(FILE *, char [], int);
void AdvanceStringPointer(char **);
void FixString(char []);
void GetAlphaString(char [], char [], int, int, int);
void GetString(char [], char [], int, int);
void GetDigitString(char [], char [], int, int);
void ReadGrades(FILE *, char [][STUDENT_NUMBER_WIDTH+1],
		char [][COURSE_INFO_WIDTH+1], char [][SMALL_INFO_WIDTH+1]);

int HaveGrades(FILE *, char [STUDENT_NUMBER_WIDTH+1]);

void ReadStudents(FILE *, FILE *, int *, char [][STUDENT_NUMBER_WIDTH+1],
		  char [][STUDENT_NAME_WIDTH+1], char [][SMALL_INFO_WIDTH+1],
		  char [][COURSE_INFO_WIDTH+1],
		  int *, float *);
void CalculateInfo(char [][STUDENT_NUMBER_WIDTH+1], char[][COURSE_INFO_WIDTH+1],
		   char [][SMALL_INFO_WIDTH+1],
		   char [][STUDENT_NUMBER_WIDTH+1],
		   int *, float *,
		   int [][2], float *);

int Main_Menu(void);
void Handle_Event(int, FILE *, FILE *, int *,
		  char [][STUDENT_NUMBER_WIDTH+1],
		  char [][COURSE_INFO_WIDTH+1],
		  char [][SMALL_INFO_WIDTH+1],
		  char [][STUDENT_NUMBER_WIDTH+1],
		  char [][STUDENT_NAME_WIDTH+1],
		  char [][SMALL_INFO_WIDTH+1],
		  char [][COURSE_INFO_WIDTH+1],
		  int *, float *);

void main(void){
char stu_num[MAX_STUDENTS][STUDENT_NUMBER_WIDTH+1],
     course_num[MAX_STUDENTS][COURSE_INFO_WIDTH+1],
     grade[MAX_STUDENTS][SMALL_INFO_WIDTH+1];

char stuNum[MAX_STUDENTS][STUDENT_NUMBER_WIDTH+1],
     stuName[MAX_STUDENTS][STUDENT_NAME_WIDTH+1],
     year[MAX_STUDENTS][SMALL_INFO_WIDTH+1],
     major[MAX_STUDENTS][COURSE_INFO_WIDTH+1];
int hoursCom[MAX_STUDENTS];
float gpa[MAX_STUDENTS];

int student_info[MAX_STUDENTS][2];
float updated_gpas[MAX_STUDENTS];

int number_students=0;
int user_choice=0;

FILE *grades=NULL, *students=NULL;

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
clrscr();
ReadGrades(grades, stu_num, course_num, grade);
getch();
ReadStudents(students, grades, &number_students, stuNum, stuName, year, major, hoursCom, gpa);
getch();
/*do{
    user_choice = Main_Menu();
    Handle_Event(user_choice, students, grades, &number_students,
		 stu_num, course_num, grade,
		 stuNum, stuName, year, major, hoursCom, gpa);
}while(user_choice != 5);
*/
fclose(grades);
fclose(students);
}

int GetLine(FILE *fp, char *buffer, int max){
int rt=1;
if ((fgets(buffer, max, fp))==NULL){
    rt = 0;
};
if (rt){
//    printf("\nFile->%s", buffer);
//    getch();
};
return(rt);
}

void AdvanceStringPointer(char **ptr){
(*ptr)+=1;
}


void FixString(char buffer[]){
int j;
int length=strlen(buffer);
int space_mark=-1;

for (j = 0; j < length; j++){
    if ((space_mark == -1)&&(isspace(buffer[j])))space_mark = j;
    if (space_mark != -1){
	 if (!isspace(buffer[j])){
	      space_mark = -1;
	 };
    };
};
if (space_mark != -1)buffer[space_mark] = '\0';
}

void GetAlphaString(char buffer[], char alpha[], int amount, int offset, int space){
int j=0;
char *buffer_ptr=NULL;
char *alpha_ptr=NULL;
char holder='0';

buffer_ptr = &buffer[offset];
alpha_ptr = &alpha[0];

if ((offset+amount) > strlen(buffer))amount = strlen(buffer) - offset;

for (j = offset; j < (offset+amount); j++){
    switch(space){
	 case 0:
	 if (isalpha((*buffer_ptr))){
	      sscanf(buffer_ptr, "%c", &holder);
	      *alpha_ptr = holder;
	      alpha_ptr++;
	 };break;
	 case 1:
	 if ((isalpha((*buffer_ptr)))||(isspace((*buffer_ptr)))){
	      sscanf(buffer_ptr, "%c", &holder);
	      *alpha_ptr = holder;
	      alpha_ptr++;
	 };break;
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

if ((offset+amount) > strlen(buffer))amount = strlen(buffer) - offset;

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

void GetString(char buffer[], char string[], int amount, int offset){
int j=0;
char *buffer_ptr=NULL;
char *string_ptr=NULL;
char holder='0';

buffer_ptr = &buffer[offset];
string_ptr = &string[0];

if ((offset+amount) > strlen(buffer))amount = strlen(buffer) - offset;

for (j = offset; j < (offset+amount); j++){
    sscanf(buffer_ptr, "%c", &holder);
    *string_ptr = holder;
    string_ptr++;
    buffer_ptr++;
};
*string_ptr = '\0';
}



void ReadGrades(FILE *grades,
		char student_numbers[][STUDENT_NUMBER_WIDTH+1],
		char course_numbers[][COURSE_INFO_WIDTH+1],
		char grade[][SMALL_INFO_WIDTH+1]){
int count=0;
int line_status=0;
char buffer[80];
char temp_buffer[80];

rewind(grades);
do{
    line_status = GetLine(grades, buffer, 80);
    printf("Line Status->%d\n", line_status);
    if (line_status){
	 GetDigitString(buffer, student_numbers[count], STUDENT_NUMBER_WIDTH, 0);
	 printf("Student Number(#%d)->%s\n", count, student_numbers[count]);
	 GetAlphaString(buffer, temp_buffer, COURSE_INFO_WIDTH, 6, 1);
	 printf("Course Type(#%d)->%s\n", count, temp_buffer);
	 GetDigitString(buffer, course_numbers[count], 4, 11);
	 printf("Course Number(#%d)->%s\n", count, course_numbers[count]);
	 GetAlphaString(buffer, grade[count], 2, 16, 0);
	 printf("Grade (#%d)->%s\n\n", count, grade[count]);
//	 getch();
	 count++;
    };
}while((count < MAX_STUDENTS)&&(line_status));
}

int HaveGrades(FILE *grades, char student_number[STUDENT_NUMBER_WIDTH+1]){
int rt=0;
char buffer[80];
char temp_number[STUDENT_NUMBER_WIDTH+1];

rewind(grades);
while((GetLine(grades, buffer, 80))&&(!rt)){
    GetDigitString(buffer, temp_number, STUDENT_NUMBER_WIDTH, 0);
    if (!strcmp(temp_number, student_number)){
	 rt = 1;
    };
};
return(rt);
}

void ReadStudents(FILE *students, FILE *grades, int *number_students,
		  char student_numbers[][STUDENT_NUMBER_WIDTH+1],
		  char student_names[][STUDENT_NAME_WIDTH+1],
		  char student_years[][SMALL_INFO_WIDTH+1],
		  char student_majors[][COURSE_INFO_WIDTH+1],
		  int *student_hours, float *student_gpas){
int count=0;
char buffer1[80], buffer2[80];
char temp_number[STUDENT_NUMBER_WIDTH+1];
char temp_last[16], temp_first[11], temp_middle[16];
char temp_buffer[20];

rewind(students);
rewind(grades);

while((GetLine(students, buffer1, 80))&&
      (GetLine(students, buffer2, 80))&&
      (count < MAX_STUDENTS)){
    GetDigitString(buffer1, temp_number, 5, 0);
    if (HaveGrades(grades, temp_number)){
	 strcpy(student_numbers[count], temp_number);
	 printf("Student Number(#%d)->%s\n", count, student_numbers[count]);
	 GetAlphaString(buffer1, temp_last, 15, 5, 0);
	 printf("Student Last(#%d)->%s\n", count, temp_last);
	 GetAlphaString(buffer1, temp_first, 10, 20, 0);
	 printf("Student First(#%d)->%s\n", count, temp_first);
	 GetAlphaString(buffer1, temp_middle, 15, 30, 1);
	 FixString(temp_middle);
	 printf("Student Middle(#%d)->%s\n", count, temp_middle);
	 sprintf(student_names[count], "%s, %s, %s", temp_last, temp_first, temp_middle);
	 printf("Student Name(#%d)->%s\n", count, student_names[count]);
	 GetDigitString(buffer2, student_years[count], 2, 33);
	 printf("Student Years(#%d)->%s\n", count, student_years[count]);
	 GetAlphaString(buffer2, student_majors[count], 4, 34, 1);
	 printf("Student Majors(#%d)->%s\n", count, student_majors[count]);
	 GetString(buffer2, temp_buffer, 20, 39);
	 FixString(temp_buffer);
	 printf("Student Info(#%d)->%s\n", count, temp_buffer);
	 sscanf(temp_buffer, "%d %f", &student_hours[count], &student_gpas[count]);
	 printf("Student(#%d)->Hours->%d, GPA->%3.2f\n", count, student_hours[count], student_gpas[count]);
	 getch();
	 count++;
    };
};
*number_students = count;
}

void CalculateInfo(int *number_students,
		   char student_numbers[][STUDENT_NUMBER_WIDTH+1],
		   char course_numbers[][COURSE_INFO_WIDTH+1],
		   char grades[][SMALL_INFO_WIDTH+1],
		   char student_numbers2[][STUDENT_NUMBER_WIDTH+1],
		   int *hoursCom,
		   float *gpas,
		   int student_info[][2], float *update_gpas){
int count;






int Main_Menu(void);
void Handle_Event(int, FILE *, FILE *, int *,
		  char [][STUDENT_NUMBER_WIDTH+1],
		  char [][COURSE_INFO_WIDTH+1],
		  char [][SMALL_INFO_WIDTH+1],
		  char [][STUDENT_NUMBER_WIDTH+1],
		  char [][STUDENT_NAME_WIDTH+1],
		  char [][SMALL_INFO_WIDTH+1],
		  char [][COURSE_INFO_WIDTH+1],
		  int *, float *);
