#include <stdlib.h>
#include <stdio.h>
#include <conio.h>
#include <iostream.h>
#include <string.h>
#include <ctype.h>
#include <iomanip.h>
#include <fstream.h>

#define TERM_STRING '\0'

enum bool{ FALSE, TRUE };

class Teacher
{
   private:
	 char teacher_name[15];

   public:
	 Teacher();
	 ~Teacher();
	 void InitT(char *teachname);

   char *GET_T_NAME(){return teacher_name;};
};

class Student
{
   private:
	 char student_first_name[15], student_last_name[15];
	 unsigned int grade;

   public:
	 Student();
	 ~Student();
	 void InitST(char *fname, char *lname, unsigned int gr);

	 //Accessor functions
	 char *GetFIRST(){return(student_first_name);};
	 char *GetLAST(){return(student_last_name);};
	 unsigned int GetGRADE(){return(grade);};
};

class Classroom
{
   private:
   Student Cur_Set_Students[10];
   Teacher Cur_Teach;
   char class_subject[20];
   int studentcounter;
   bool teachercounter;
   bool subjectcounter;

   public:
   Classroom();
   ~Classroom();

   void Init_Student();
   void Enter_Subject();
   void Print_Student_Info(char firstname[15], char lastname[15]);

   void Init_Teacher();
   void Grab_Teacher_Info(char name[15], int &i);
   void Grab_Subject(char subject[15]);
   //Accessor functions
   void Change_Student_Counter(int num){studentcounter += num;};
   void Change_Subject_Counter(){subjectcounter = TRUE;};
   void Change_Teacher_Counter(){teachercounter = TRUE;};
   int GET_STUDENT_COUNTER(){return studentcounter;};
   bool GET_TEACHER_COUNTER(){return teachercounter;};
   bool GET_SUBJECT_COUNTER(){return subjectcounter;};
   unsigned int GET_GRADE(int r){return Cur_Set_Students[r].GetGRADE();};
   char *GET_SUBJECT(){return class_subject;};
   char *GET_STUDENT_FIRST(int r){return Cur_Set_Students[r].GetFIRST();};
   char *GET_STUDENT_LAST(int r){return Cur_Set_Students[r].GetLAST();};
   char *GET_TEACHER_NAME(){return Cur_Teach.GET_T_NAME();};
};

class School
{
   private:
   Classroom Cur_Room[5];

   public:
   School();
   ~School();
   int classcounter;
   void Init_Classroom();
   void Grab_Class_Info();

   int Grab_Student_Info(char firstname[15], char lastname[15], int &x, int &u);
   void Grab_Class_Info(int which);

   void Print_Info_Screen(int classnum);
   void Print_School_Info();
   void Save_File();
   void Load_File();
   void Open_UN_File_WR(FILE *fp, char filename[]);
   void Open_WR_File(FILE *fp);


};


Teacher::Teacher()
{
   teacher_name[0] = TERM_STRING;
}

Student::Student()
{
   grade = 0;
   student_first_name[0] = TERM_STRING;
   student_last_name[0] = TERM_STRING;
}

Classroom::Classroom()
{
   class_subject[0] = TERM_STRING;
   studentcounter = 0;
   teachercounter = FALSE;
   subjectcounter = FALSE;
}

School::School()
{
   classcounter = 0;
}

int Prompter();
void clear_kb(void);

// MAIN //////////////////////////////////////////////////////////////
void main()
{
   clrscr();
   int num = 0, midst = 0, sc = 0;
   char subject[15], choice, stfirst[15], stlast[15], teachname[15], firstchoose;

   FILE filepointer;
   int x, u, r, i;

   x = u = r = i = 0;

   School Paschal;


   };
//** END MAIN **//////////////////////////////////////////////////////


// FUNCTIONS /////////////////////////////////////////////////////////
int Prompter()
{
   int rt;
   char choice;

   cout << "Do you want to continue?";
   cin >> choice;

   if(choice == 'y')
   {
	 rt = 1;
   }else
   if(choice == 'n')
   {
	 rt = 1;
	 cout << "Are you sure?";
	 cin >> choice;

	 tolower(choice);

	 if(choice == 'y')rt = 0;
	 else rt = 1;

   }else{rt = 0;};

   return rt;
}

void Classroom::Init_Student()
{
   char studentfirst[15], studentlast[15];
   unsigned int grade;

	 if(studentcounter == 10)
	 {
	    cout << "Can't enter more students in this classroom!\n";
	 }
   else
   {              	 cout << "Enter first name of student- ";
	 gets(studentfirst);

	 cout << "Enter last name of student- ";
	 gets(studentlast);

	 endl;

	 cout << "Enter student grade- ";
	 cin >> grade;

	 Cur_Set_Students[studentcounter].InitST(studentfirst, studentlast, grade);

	 studentcounter++;
   }
}

void Classroom::Init_Teacher()
{
   char tname[15];   cout << "Enter teacher name for the classroom- ";
   gets(tname);

   Cur_Teach.InitT(tname);
   teachercounter = TRUE;
}

void Student::InitST(char *fname, char *lname, unsigned int gr)
{
   strcpy(student_first_name, fname);
   strcpy(student_last_name, lname);
   grade = gr;
}

void Teacher::InitT(char *tname)
{
   strcpy(teacher_name, tname);
}

void Classroom::Enter_Subject()
{
   cout << "Enter subject- ";
   gets(class_subject);
}

void School::Init_Classroom()
{
   int t, howmany;

   if(classcounter == 5)
   {
	 cout << "School is full.";
   }else if(classcounter >= 0)
   {
	 cout << "Initializing classroom...\n";

	 Cur_Room[classcounter].Init_Teacher();

	 cout << "How many students would you like to input(up to 10) ->";
	 cin >> howmany;

	 if((Cur_Room[classcounter].GET_STUDENT_COUNTER() + howmany) <= 10)
	 {
	    for(t = 0;t < howmany;t++)
	    {
		  Cur_Room[classcounter].Init_Student();
	    }

	    }else if((Cur_Room[classcounter].GET_STUDENT_COUNTER() + howmany) > 10)
	    {
		  howmany = ((Cur_Room[classcounter].GET_STUDENT_COUNTER() + howmany) - 10);

		  for(t = 0;t < howmany;t++)
		  {
			Cur_Room[classcounter].Init_Student();
		  }
	    }

	    if((classcounter > 0) && (classcounter < 5))
	    {
		  if(Cur_Room[classcounter].GET_TEACHER_COUNTER() == TRUE)
		  {
			cout << "This room already has a teacher.\n";
		  }else
		  {
			Cur_Room[classcounter].Init_Teacher();
		  }
	    }

	    if(Cur_Room[classcounter].GET_SUBJECT_COUNTER() == TRUE)
	    {
		  cout << "This class already has a subject.\n";
	    }else
	    {
		  Cur_Room[classcounter].Enter_Subject();
	    }

	    classcounter++;
	 }
}

int School::Grab_Student_Info(char studentfirst[], char studentlast[],
						int &x, int &u)
{
   int t, r, countone = 0, counttwo = 0, rt = 0;

   for(t = 0;t < 5;t++)
   {
	 if(rt == 1)break;
	 for(r = 0;r < 10;r++)
	 {
	    if(!strcmp(studentfirst, Cur_Room[t].GET_STUDENT_FIRST(r)))
	    {
		  countone = 0;
	    }else countone = 1;

	    if(countone == 1)
	    {
		  cout << "No student by that name.\n";
		  break;
	    }

	    if(countone == 0)
	    {
		  if(!strcmp(studentlast, Cur_Room[t].GET_STUDENT_LAST(r)))
		  {
			counttwo = 0;
		  }else
		  {
			counttwo = 1;
		  }
			if(counttwo == 0)
			{
			   x = t;
			   u = r;

			   rt = 1;
			   cout << "Found student.\n\n";
			   cout << "Full Name: " << Cur_Room[t].GET_STUDENT_FIRST(r) << " " << Cur_Room[t].GET_STUDENT_LAST(r);
			   cout << "\nCurrent Subject: " <<  Cur_Room[t].GET_SUBJECT();
			   cout << "\nGrade: " << Cur_Room[t].GET_GRADE(r);

			   break;
			}else
			{
			   cout << "No student by that name.\n";
			   break;
			}
		  }
	    }
	 }

   return rt;
}

void Classroom::Grab_Teacher_Info(char teachname[], int &i)
{
   int t = 0, counter = 0;

	 for(t = 0;t < 5;t++)
	 {
	    if(!strcmp(teachname, GET_TEACHER_NAME()))
	    {
		  counter = 0;
	    }else counter = 1;

	    if(counter == 0)
	    {
		  i = t;
		  cout << "\nFound teacher.\n";
		  cout << "Name: " << GET_TEACHER_NAME();
		  cout << "\nSubject: " << GET_SUBJECT();
		  cout << "\nTeaches " << GET_STUDENT_COUNTER() << " students.\n";

		  break;
	    }else
	    {
		  cout << "\nNo teacher by that name.\n";
		  i = 6;
		  break;
	    }
	 }
}

void School::Grab_Class_Info(int which)
{
   char choice;
   int num, r, s, t = 0, x = 0;

   num = r = s = 0;

   char fname[15], lname[15], tname[15], csubject[15];

   if(classcounter == 0)
   {
	 cout << "No classes entered.\n";
	 x = 1;
   }

   if(which > classcounter)
   {
	 cout << "This class has not been initialized...\n";
	 cout << "Do you wish to initialize another?: ";
	 cin >> choice;

	 if(choice == 'y')
	 {
	    Init_Classroom();
	 }else
	 {
	    x = 1;
	 }
	 x = 1;
   }

   while(x == 0)
   {
	 cout << "Looking at the " << Cur_Room[which].GET_SUBJECT() << " room.\n";

	 cout << "What would you like to view in class(" << which << ")?\n";
	 cout << "1-View Student(s), 2-View Teacher, 3-View Subject\n";

	 cin >> num;

	 if(num == 1)
	 {
	    cout << "Give first name of student- ";
	    gets(fname);
	    cout << "Give last name of student- ";
	    gets(lname);
	    x = Grab_Student_Info(fname, lname, r, s);
	 }

	 if(num == 2)
	 {
	    cout << "Give teacher name- ";
	    gets(tname);
	    Cur_Room[which].Grab_Teacher_Info(tname, t);
	 }

	 if(num == 3)
	 {
	    cout << "Enter class subject- ";
	    gets(csubject);
	    Cur_Room[which].Grab_Subject(csubject);
	 }

	 cout << "\nDo you want view more in this class?";
	 cin >> choice;

	 getch();

	 if(choice == 'y')x = 0;
	 else x = 1;
	 }
}

void Classroom::Grab_Subject(char csubject[])
{
   int t = 0, check = 0;

   for(t = 0;t < 5;t++)
   {
	 if(!strcmp(csubject, GET_SUBJECT()))
	 {
	    check = 0;
	 }else check = 1;

	 if(check == 0)
	 {
	    cout << endl << csubject << " is taught in classroom( " << t << " ).";
	    cout << "\nThis class is taught by: " << GET_TEACHER_NAME();
	    cout << "\nIt contains " << GET_STUDENT_COUNTER() << " students.\n";
	    break;

	 }else cout << "No student by that name.\n";
	 break;
   }
}

void School::Print_Info_Screen(int classnumber)
{
   int t = 0;

   puts(Cur_Room[classnumber].GET_SUBJECT());
   cout << "---------------\n\n";
   cout << "Teacher: " << Cur_Room[classnumber].GET_TEACHER_NAME();
   cout << "\n\nStudents(" << Cur_Room[classnumber].GET_STUDENT_COUNTER() << ")\n";
   cout << "----------\n\n";

   for(t = 0;t < Cur_Room[classnumber].GET_STUDENT_COUNTER();t++)
   {
	 cout << Cur_Room[classnumber].GET_STUDENT_FIRST(t) << " "
		 << Cur_Room[classnumber].GET_STUDENT_LAST(t) << " "
		 << "    Grade: " << Cur_Room[classnumber].GET_GRADE(t) << "\n";
   }
}

void School::Open_UN_File_WR(FILE *fp, char filename[40])
{
   int t = 0, r = 0;

   clear_kb();

   if((fp = fopen(filename, "w")) == NULL)
   {
	 fprintf(stderr, "Error opening file %s.", filename);
	 exit(1);
   }

   fprintf(fp, "%d\n", classcounter);
   for(t = 0;t < classcounter;t++)
   {
	 fprintf(fp, "%d\n", Cur_Room[t].GET_STUDENT_COUNTER());
	 fprintf(fp, "%s\n", Cur_Room[t].GET_SUBJECT());
	 fprintf(fp, "%s\n", Cur_Room[t].GET_TEACHER_NAME());

	 for(r = 0;r < Cur_Room[t].GET_STUDENT_COUNTER();r++)
	 {
	    fprintf(fp, "%s %s %d\n", Cur_Room[t].GET_STUDENT_FIRST(r),
			  Cur_Room[t].GET_STUDENT_LAST(r), Cur_Room[t].GET_GRADE(r));
	 }
   }

   fclose(fp);
}

void clear_kb(void)
{
   char junk[80];
   gets(junk);
}

void School::Open_WR_File(FILE *fp)
{
   int ccount, scount, t, r;

   if((fp = fopen("test.txt", "r")) == NULL)
   {
	 fprintf(stderr, "Error opening file.");
	 exit(1);
   }

   fscanf(fp, "%d", &ccount);

   for(t = 0;t < ccount;t++)
   {
	 classcounter++;
	 fscanf(fp, "%d", &scount);
	 School();
	 Classroom();
	 Student();
	 Teacher();

	 Cur_Room[t].Change_Student_Counter(scount);
	 fscanf(fp, "%s", Cur_Room[t].GET_SUBJECT());
	 Cur_Room[t].Change_Subject_Counter();
	 fscanf(fp, "%s", Cur_Room[t].GET_TEACHER_NAME());
	 Cur_Room[t].Change_Teacher_Counter();

	 for(r = 0;r < scount;r++)
	 {
	    fscanf(fp, "%s %s %d", Cur_Room[t].GET_STUDENT_FIRST(r),
			 Cur_Room[t].GET_STUDENT_LAST(r), Cur_Room[t].GET_GRADE(r));
	 }
   }
   fclose(fp);
}





