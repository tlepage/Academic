//**********************************************************************//
// CLOCK SOLITAIRE
//**********************************************************************//
// By Tom LePage
// December 2000 - January 2001
//***********************************************************************//

#include<iostream.h>
#include<stdio.h>
#include<stdlib.h>
#include<conio.h>
#include<string.h>
#include<time.h>

#define YES 1
#define NO 0
#define TERM_STRING  '\0'

class Card
{
   private:
	 int suit;
	 int rank;
	 bool active, visible;

   public:
	 Card();
	 ~Card();

	 int GET_SUIT(){return suit;};
	 int GET_RANK(){return rank;};
	 void CHANGE_SUIT(int s){if(s < 4)suit = s;};
	 void CHANGE_RANK(int r){if(r < 13)rank = r;};
	 bool CHECK_ACTIVE(){return active;};
      bool CHECK_VISIBLE(){return visible;};
	 void SET_ACTIVE(bool a){active = a;};
      void SET_VISIBLE(bool v){visible = v;};
};

class Deck
{
   private:
	 Card Full[52];
	 bool active;
   public:
	 Deck();
	 ~Deck();

	 void Init_Cards();
	 void Shuffle();

	 Card *Return_Card(int r){return &Full[r];};
	 void Print_Suit(int s);
	 void Print_Rank(int r);
	 void Print_Card(int t);
	 void Print_Deck();
	 void AcDe_Deck_CardRS(int rank, int suit, int ad);
	 void Set_Deck_Activity(bool a){ active = a;};
      bool IsActive(){return(active);};
};

class Clock
{
   private:

	 Deck Clock_Deck[13];

   public:

	 Clock();
	 ~Clock();

	 Deck *Return_Deck(int d){return(&Clock_Deck[d]);};
      bool Check_Visibility(int cldenum, int cardnum);
	 void Set_Visibility(int t, int cardnum, bool flag);
	 void Fill_Decks(Deck *d);
      void AcDe_Deck_CardNum(int decknum, int cardnum, bool ad, Card *c);
	 void Deactivate_Deck(int a);

};

class Game
{
   private:
	 Deck Global_Deck;
	 Clock Game_Clock;

   public:

	 Game();
	 ~Game();

	 void Init_Deck();
	 void Init_Clock();
	 void Init_Game();
	 void Print_Global_Deck();
	 void Print_Decks();
      void Pick_Card(int &deck_num);
	 void Run();

};

Card::Card()
{
   suit = -1;
   rank = -1;
   active = false;
   visible = false;
}

Card::~Card()
{
}

Deck::Deck()
{
   active = true;
}

Deck::~Deck()
{
}

Clock::Clock()
{
}

Clock::~Clock()
{
}

Game::Game()
{
}

Game::~Game()
{
}

//***********************************************************************//
// Intro()
//***********************************************************************//
void Intro()
{
   printf("\n\nThis is a version of solitaire in which the cards are set up\n");
   printf("like a clock with a 13th pile in the middle.  The object of\n");
   printf("the game is to get all the similar rank cards into their\n");
   printf("respective clock positions before you get all four kings, which\n");
   printf("is the thirteenth pile. Good Luck.");
   getch();

}

//**********************************************************************//
// MAIN
//**********************************************************************//

void main()
{
   clrscr();
   Game Play;
   cout << "*******************\n"
        << "**CLOCK SOLITAIRE**\n"
        << "*******************\n";
   Intro();
   Play.Run();
   getch();
}

//**********************************************************************//
// DECK FUNCTIONS
//**********************************************************************//

void Deck::Init_Cards()
{
   int t = 0, s = 0, r = 0;

   for(s = 0; s < 4; s++)
   {
	 for(r = 0; r < 13; r++)
	 {
		   t = (13 * s) + r;

		   Full[t].CHANGE_SUIT(s);
		   Full[t].CHANGE_RANK(r);
		   Full[t].SET_ACTIVE(true);
      }
   }
}

void Deck::Shuffle()
{
   int t, r1, r2, i;

   Card temp;
   time_t clock;

   srand((unsigned) time(&clock));

   t = 0;
   i = (2 + rand() % 300);

   while(t < i)
   {
	 r1 = rand() % 26;
	 r2 = rand() % 26;

	 temp = Full[r1];

	 Full[r1] = Full[51 - r2];

	 Full[51 - r2] = temp;

	 t++;
   }
}

void Deck::Print_Suit(int s)
{
   int a = 3, b = 4, x = 5, d = 6;

   switch(s)

	 {
	    case 0 :  printf("%c", d);break;
	    case 1 :  printf("%c", a);break;
	    case 2 :  printf("%c", b);break;
	    case 3 :  printf("%c", x);break;
	 }

};

void Deck::Print_Rank(int r)
{
   switch(r)
	 {
	    case 1  :  printf("A");break;

	    case 11 :  printf("J");break;

	    case 12 :  printf("Q");break;

	    case 0  :  printf("K");break;
	 }

   if((r > 1)&&(r < 11)) printf("%d", r);
}

void Deck::Print_Card(int t)
{
   int s = 0, r = 0;

   s = Full[t].GET_SUIT();
   r = Full[t].GET_RANK();

   Print_Rank(r);
   Print_Suit(s);

}

void Deck::Print_Deck()
{
   int t;
   bool c;

   for(t = 0;t < 52;t++)
   {
	 c = Return_Card(t)->CHECK_ACTIVE();

	 if(c == true)
	 {
		Print_Card(t);
		getch();
	 }
   }
}

void Deck::AcDe_Deck_CardRS(int rank, int suit, int ad)
{
   int t;

   if(ad == 1)
   {
	 for(t = 0;t < 52;t++)
	 {
	    if((Return_Card(t)->GET_SUIT() == suit)&&(Return_Card(t)->GET_RANK() == rank))
	    {
		  Return_Card(t)->SET_ACTIVE(true);
	    }
	 }
   }


   if(ad == 0)
   {
	 for(t = 0;t < 52;t++)
	 {
	    if((Return_Card(t)->GET_SUIT() == suit)&&(Return_Card(t)->GET_RANK() == rank))
	    {
		  Return_Card(t)->SET_ACTIVE(false);
	    }
	 }
   }
}

//**********************************************************************//
// CLOCK FUNCTIONS //
//**********************************************************************//

void Clock::Fill_Decks(Deck *d)
{
   int t, r, counter = 0, suit, rank;

   for(t = 0; t < 13; t++)
   {
	 for(r = 0; r < 4; r++)
	 {
	    suit = d->Return_Card(counter)->GET_SUIT();
	    Clock_Deck[t].Return_Card(counter)->CHANGE_SUIT(suit);

	    rank = d->Return_Card(counter)->GET_RANK();
	    Clock_Deck[t].Return_Card(counter)->CHANGE_RANK(rank);

	    Clock_Deck[t].Return_Card(counter)->SET_ACTIVE(true);

	    counter++;
	 }
   }

}

void Clock::Set_Visibility(int t, int cardnum, bool flag)
{
   if(flag == true)
   {
      Clock_Deck[t].Return_Card(cardnum)->SET_VISIBLE(true);
   }
   else if(flag == false)
   {
      Clock_Deck[t].Return_Card(cardnum)->SET_VISIBLE(false);
   }
}

bool Clock::Check_Visibility(int cldenum, int cardnum)
{
   bool rt;

   rt = Clock_Deck[cldenum].Return_Card(cardnum)->CHECK_VISIBLE();

   return rt;
}

void Clock::Deactivate_Deck(int a)
{
   Clock_Deck[a].Set_Deck_Activity(false);
}

void Clock::AcDe_Deck_CardNum(int decknum, int cardnum, bool ad, Card *src)
{
   Card *dst;

   if(ad == true)
   {
      dst = Clock_Deck[decknum].Return_Card(cardnum);
      dst->SET_ACTIVE(true);
      dst->CHANGE_SUIT(src->GET_SUIT());
      dst->CHANGE_RANK(src->GET_RANK());
   }else if(ad == false)
   {
      dst = Clock_Deck[decknum].Return_Card(cardnum);
      dst->SET_ACTIVE(false);
   }
}

//**********************************************************************//
// GAME FUNCTIONS //
//**********************************************************************//

void Game::Init_Clock()
{
   Game_Clock.Fill_Decks(&Global_Deck);
}

void Game::Init_Deck()
{
   Global_Deck.Init_Cards();
   Global_Deck.Shuffle();
   Global_Deck.Shuffle();
}

void Game::Init_Game()
{
   Init_Deck();
   Init_Clock();
}

void Game::Print_Global_Deck()
{
   Global_Deck.Print_Deck();
}

void Game::Print_Decks()
{
   int t, r;
   bool checka, checkv, xflag;
   Deck *d=NULL;

   for(t = 0; t < 13; t++)
   {
      if(t == 0)cout << "\nDeck 13: ";
      else cout << endl << "Deck " << t << ": ";

      for(r = 0; r < 52; r++)
      {
          checka = Game_Clock.Return_Deck(t)->Return_Card(r)->CHECK_ACTIVE();

          if(checka == true)
          {
             checkv = Game_Clock.Check_Visibility(t, r);

             if(checkv)
             {
                xflag = false;

                d = Game_Clock.Return_Deck(t);
                d->Print_Card(r);
                cout << " ";
             }
             else
             {
                xflag = true;
                cout << "X ";
             }
         }
      }
   }
}

void Game::Pick_Card(int &deck_num)
{
   int t, r, checker = 0, rank, suit, totalvcheck = 0, user_rank = -1;
   bool checka = false, checkv = false, checktv = false;

   Card *c;

   for(t = 0; t < 52; t++)
   {
      checka = Game_Clock.Return_Deck(deck_num)->Return_Card(t)->CHECK_ACTIVE();
      checkv = Game_Clock.Check_Visibility(deck_num, t);

      if((checka == true) && (checkv == false))
      {
         checker = t;
         t = 52;
      }
   }

   cout << endl;

   if(deck_num == 0)
   cout << "\nNew Card from deck 13 is: ";
   else
   cout << "\nNew Card from deck " << deck_num << " is: ";

   Game_Clock.Return_Deck(deck_num)->Print_Card(checker);

   getch();
   cout << endl;

   c = Game_Clock.Return_Deck(deck_num)->Return_Card(checker);
   rank = c->GET_RANK();
   suit = c->GET_SUIT();

   while(user_rank != rank)
   {
      cout << "\nPut in what pile? ";
      cin >> user_rank;

      cout << endl;

      if(user_rank == 13)user_rank = 0;
   }

   Game_Clock.AcDe_Deck_CardNum(deck_num, checker, false, c);
   Game_Clock.AcDe_Deck_CardNum(rank, checker, true, c);

   Game_Clock.Set_Visibility(rank, checker, true);

   for(r = 0; r < 52; r++)
   {

      checktv = Game_Clock.Check_Visibility(rank, r);

      if(checktv == true)
      {
         totalvcheck++;
      }

      if(totalvcheck == 4)
      {
         Game_Clock.Deactivate_Deck(rank);
      }
   }

    deck_num = rank;

}

void Game::Run()
{
   int kcount = 0, t, wincount = 0, check = 0, rank = 0, flag = 0;
   bool checkdeck = false, checkking = false;

   Init_Game();

   getch();

   while(flag != 3)
   {
      Pick_Card(rank);

      check = 0;

      for(t = 0; t < 52; t++)
      {
          checkking = Game_Clock.Check_Visibility(0, t);
          if(checkking == true)check++;
      }

      wincount = 0;

      for(t = 0; t < 13; t++)
      {
            checkdeck = Game_Clock.Return_Deck(t)->IsActive();
            if(checkdeck == false)wincount++;

      }

      clrscr();

      Print_Decks();

      kcount = check;

      cout << "\nTotal Decks Deactivated = " << wincount;
      cout << "\nTotal King Count = " << kcount;

      if(wincount == 12)
      {
         flag = 3;

      }else if(kcount == 4)
      {
         flag = 3;
      }

   }

   if(wincount >= 12)cout << "\n\nCongrats!!";
   else if(kcount == 4)cout << "\nYou lose.";
}
//**********************************************************************//
