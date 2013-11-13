#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <iostream.h>
#include <math.h>

enum TicTacType{TicTacBlank, TicTacX, TicTacO};

#define PLAYER_X 0
#define PLAYER_O 1

typedef struct{
  TicTacType TicTacBoard[3][3];
  char player1[80];
  char player2[80];
  int status;
  int number_spots_taken;
}TicTacGame;

void InitTicTacGame(TicTacGame *g){
   int j, k;

   for (j = 0; j < 3; j++){
	 for (k = 0; k < 3; k++){
	    g->TicTacBoard[j][k] = TicTacBlank;
	 };
   };

   clrscr();
   cout << "Enter player one's name:";
   cin >> g->player1;
   cout << "Enter player two's name:";
   cin >> g->player2;

   //Reset status
   g->status = PLAYER_X;
   g->number_spots_taken = 0;
}

void DrawTicTacGame(TicTacGame *g){
   int j, k;

   for (j = 0; j < 3; j++){
	 printf("|");
	 for (k = 0; k < 3; k++){
	    switch(g->TicTacBoard[k][j]){
		  case TicTacBlank : printf(" |");break;
		  case TicTacX : printf("X|");break;
		  case TicTacO : printf("O|");break;
	    };
	 };
	 printf("\n");
   };
}

int PlaceXO(int x, int y, TicTacType T, TicTacGame *g){
	int rt=0;

	if (T != TicTacBlank){
	   if ((x < 3)&&(y < 3)){
		 if (g->TicTacBoard[x][y] == TicTacBlank){
		    g->TicTacBoard[x][y] = T;
		    rt = 1;
		    g->number_spots_taken++;
		 };
	   };
	};

	return(rt);
}

int CheckBoardDiagonal(int side, TicTacGame *g){
   TicTacType Start, S1, S2;
   int rt = 0;

   switch(side){
	 case 0 : Start = g->TicTacBoard[0][0];
			S1 = g->TicTacBoard[1][1];
			S2 = g->TicTacBoard[2][2];
			if ((Start == S1)&&(S1 == S2)&&(Start != TicTacBlank)){
			   rt = 1;
			};break;
	 case 1 : Start = g->TicTacBoard[2][0];
			S1 = g->TicTacBoard[1][1];
			S2 = g->TicTacBoard[0][2];
			if ((Start == S1)&&(S1 == S2)&&(Start != TicTacBlank)){
			   rt = 1;
			};break;
	 default:break;
   };

   return(rt);
}

int CheckBoardHorizontal(int row, TicTacGame *g){
	TicTacType Start, S1, S2;
	int rt=0;

   if (row < 3){
	  Start = g->TicTacBoard[0][row];
	  S1 = g->TicTacBoard[1][row];
	  S2 = g->TicTacBoard[2][row];
	  if ((Start == S1)&&(S1 == S2)&&(Start != TicTacBlank)){
		rt = 1;
	  };
   };

   return(rt);
}

int CheckBoardVertical(int column, TicTacGame *g){
	TicTacType Start, S1, S2;
	int rt=0;

   if (column < 3){
	  Start = g->TicTacBoard[column][0];
	  S1 = g->TicTacBoard[column][1];
	  S2 = g->TicTacBoard[column][2];
	  if ((Start == S1)&&(S1 == S2)&&(Start != TicTacBlank)){
		rt = 1;
	  };
   };

   return(rt);
}

void RunTicTacGame(TicTacGame *g){
   int rt, finish=0;
   int x, y;
   int win[8]={0,0,0,0,0,0,0,0};
   int tic_tac_toe=0, j;

   do{
	  clrscr();
	  DrawTicTacGame(g);
	  if (g->status == PLAYER_X){
		  cout << g->player1 << "\n";
		  printf("Enter X position(x, y):");
		  scanf("%d, %d", &x, &y);
		  rt = PlaceXO(x, y, TicTacX, g);
	  }else{
		  printf("%s:\n", g->player2);
		  printf("Enter O position(x, y):");
		  scanf("%d, %d", &x, &y);
		  rt = PlaceXO(x, y, TicTacO, g);
	  };

	  if (rt){
		  win[0] = CheckBoardDiagonal(0, g);
		  win[1] = CheckBoardDiagonal(1, g);
		  win[2] = CheckBoardHorizontal(0, g);
		  win[3] = CheckBoardHorizontal(1, g);
		  win[4] = CheckBoardHorizontal(2, g);
		  win[5] = CheckBoardVertical(0, g);
		  win[6] = CheckBoardVertical(1, g);
		  win[7] = CheckBoardVertical(2, g);

		  for (j = 0; j < 8; j++){
			if (win[j])tic_tac_toe = 1;
		  };
		  if ((g->number_spots_taken == 9)||(tic_tac_toe)){
			finish = 1;
		  }else{
			if (g->status == PLAYER_X)
			   g->status = PLAYER_O;
			else
			   g->status = PLAYER_X;
		  };
	  };

   }while(!finish);
   clrscr();

   if (tic_tac_toe){
	 if (g->status == PLAYER_X)
	    printf("Player X, Tic Tac Toe\n");
	 else
	    printf("Player O, Tic Tac Toe\n");
   }else{
	 printf("Tie\n");
   };
   getch();
}

void main(void){

TicTacGame game;

clrscr();
InitTicTacGame(&game);
RunTicTacGame(&game);
clrscr();
}
