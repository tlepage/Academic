#include<iostream.h>
#include<stdio.h>
#include<stdlib.h>
#include<conio.h>
#include<string.h>
#include<time.h>

#define YES 1
#define NO 0
#define TERM_STRING  '\0'

enum bool{ FALSE, TRUE };

class Card
{
   private:
	 int suit;
	 int rank;
	 bool active;

   public:
	 Card();
	 ~Card();

	 int GET_SUIT(){return suit;};
	 int GET_RANK(){return rank;};
	 void CHANGE_SUIT(int s){if(s < 4)suit = s;};
	 void CHANGE_RANK(int r){if(r < 13)rank = r;};
	 bool CHECK_ACTIVE(){return active;};
	 void SET_ACTIVE(bool a){active = a;};

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
};

class Clock
{
   private:

	 Deck Clock_Deck[13];

   public:

	 Clock();
	 ~Clock();

	 Deck *Return_Deck(int d){return &Clock_Deck[d];};
	 void Fill_Decks(Deck *d);
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
	 void Print_Decks(int n);
};

Card::Card()
{
   suit = -1;
   rank = -1;
   active = FALSE;
}

Card::~Card()
{
}

Deck::Deck()
{
   active = TRUE;
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
//**********************************************************************//
// MAIN
//**********************************************************************//
void main()
{
   clrscr();
   Clock Thirteen;
   Game Play;
   int t;

   Play.Init_Game();
  //Play.Print_Global_Deck();
   for(t = 0; t < 13; t++)
  {
	Play.Print_Decks(t);
  }
  getch();
}

//**********************************************************************//
// DECK FUNCTIONS
//**********************************************************************//
void Deck::Init_Cards()
{
   cout << "Called from Deck::Init_Cards()\n";
   int t = 0, s = 0, r = 0;

   for(s = 0; s < 4; s++)
   {
	 for(r = 0; r < 13; r++)
	 {
		   t = (13 * s) + r;

		   Full[t].CHANGE_SUIT(s);
		   Full[t].CHANGE_RANK(r);
		   Full[t].SET_ACTIVE(TRUE);
		   cout << "Setting card(" << t << ") = suit(" << s
			   << "), rank(" << r << ")\n";
	    }
	    getch();
	 }

}

void Deck::Shuffle()
{
   cout << "Called from Deck::Shuffle()\n";
   randomize();
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
	    case 1  :  cout << "A";break;

	    case 11 :  cout << "J";break;

	    case 12 :  cout << "Q";break;

	    case 0  :  cout << "K";break;
	 }

   if((r > 1)&&(r < 11)) cout << r;
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
   //cout << "Called from Deck::Print_Deck()\n";
   int t;
   bool c;

   for(t = 0;t < 52;t++)
   {
	 c = Return_Card(t)->CHECK_ACTIVE();

	 if(c == TRUE)
	 {
		Print_Card(t);
		getch();
	 }
//	 cout << " " << t << endl;
//
	 //cout << " ";
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
		  Return_Card(t)->SET_ACTIVE(TRUE);
	    }
	 }
   }


   if(ad == 0)
   {
	 for(t = 0;t < 52;t++)
	 {
	    if((Return_Card(t)->GET_SUIT() == suit)&&(Return_Card(t)->GET_RANK() == rank))
	    {
		  Return_Card(t)->SET_ACTIVE(FALSE);
	    }
	 }
   }
}

//**********************************************************************//
// CLOCK FUNCTIONS //
//**********************************************************************//
void Clock::Fill_Decks(Deck *d)
{
   //cout << "Called Fill_Decks Check 1\n";
   int t, r, counter = 0, suit, rank;

/*   for(t = 0; t < 13; t++)
   {
	 for(r = 0; r < 4; r++)
	 {
	    suit = d->Return_Card(counter)->GET_SUIT();
	    Clock_Deck[t].Return_Card(counter)->CHANGE_SUIT(suit);

	    rank = d->Return_Card(counter)->GET_RANK();
	    Clock_Deck[t].Return_Card(counter)->CHANGE_RANK(rank);

	    Clock_Deck[t].Return_Card(counter)->SET_ACTIVE(TRUE);

	   // Clock_Deck[t].Print_Card(counter);
	   // cout << " ";
	   // d->Print_Card(counter);
	   // cout << endl;
	   // getch();
	    counter++;
	 }
   }*/

   while(counter < 52)
   {
	suit = d->Return_Card(counter)->GET_SUIT();
	rank = d->Return_Card(counter)->GET_RANK();

	Clock_Deck[t].Return_Card(counter)->CHANGE_SUIT(suit);
	Clock_Deck[t].Return_Card(counter)->CHANGE_RANK(rank);
	Clock_Deck[t].Return_Card(counter)->SET_ACTIVE(TRUE);
	t++;
	if (t > 12)
	{
		t = 0;
	}
	counter++;
   }
}

void Clock::Deactivate_Deck(int a)
{
   Clock_Deck[a].Set_Deck_Activity(FALSE);
}

//**********************************************************************//
// GAME FUNCTIONS //
//**********************************************************************//
void Game::Init_Clock()
{
   //cout << "Called Init_Clock() check 1\n";
   Game_Clock.Fill_Decks(&Global_Deck);
}

void Game::Init_Deck()
{
   Global_Deck.Init_Cards();
//   Global_Deck.Shuffle();
//   Global_Deck.Shuffle();
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
void Game::Print_Decks(int n)
{
   cout << "\nDeck #" << (n + 1) << ": ";
   Game_Clock.Return_Deck(n)->Print_Deck();
}