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

class Player
{
   private:

	 Deck Player_Deck;

   public:

	 Player();
	 ~Player();

	 Deck *Return_Deck(){return(&Player_Deck);};
      bool Check_Visibility(int cardnum);
      void Set_Visibility(int cardnum, bool flag);

      void AcDe_Deck_CardNum(int cardnum, bool ad, Card *c);
      void Deactivate_Deck();

};

class Game
{
   private:
	 Deck Global_Deck;
	 Player Players[4];

   public:

	 Game();
	 ~Game();

	 void Init_Deck();
	 void Init_Players();
	 void Init_Game();
      void Print_Global_Deck();
      void Fill_Decks(Deck *d);
      void Print_Player_Deck(int t);
      void Pick_Card(int &deck_num);
	 void Run();

};

Card::Card()
{
   suit = -1;
   rank = -1;
   active = false;
   visible = true;
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

Player::Player()
{
}

Player::~Player()
{
}

Game::Game()
{
}

Game::~Game()
{
}

void main()
{
   //Game Spades;
   cout << "\nHello";
   //Spades.Init_Game();
   //Spades.Print_Player_Deck(0);
}

//**********************************************************************//
// DECK FUNCTIONS
//**********************************************************************//

void Deck::Init_Cards()
{
   cout << "\nReached Init_Cards()";
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
   cout << "\nReached end of Init_Cards()";
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
// PLAYER FUNCTIONS //
//**********************************************************************//

void Player::Set_Visibility(int cardnum, bool flag)
{
   if(flag == true)
   {
      Player_Deck.Return_Card(cardnum)->SET_VISIBLE(true);
   }
   else if(flag == false)
   {
      Player_Deck.Return_Card(cardnum)->SET_VISIBLE(false);
   }
}

bool Player::Check_Visibility(int cardnum)
{
   bool rt;

   rt = Player_Deck.Return_Card(cardnum)->CHECK_VISIBLE();

   return rt;
}

void Player::Deactivate_Deck()
{
   Player_Deck.Set_Deck_Activity(false);
}

void Player::AcDe_Deck_CardNum(int cardnum, bool ad, Card *src)
{
   Card *dst;

   if(ad == true)
   {
      dst = Player_Deck.Return_Card(cardnum);
      dst->SET_ACTIVE(true);
      dst->CHANGE_SUIT(src->GET_SUIT());
      dst->CHANGE_RANK(src->GET_RANK());
   }else if(ad == false)
   {
      dst = Player_Deck.Return_Card(cardnum);
      dst->SET_ACTIVE(false);
   }
}

//**********************************************************************//
// GAME FUNCTIONS //
//**********************************************************************//

void Game::Init_Players()
{
   Fill_Decks(&Global_Deck);
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
   Init_Players();
}

void Game::Print_Global_Deck()
{
   Global_Deck.Print_Deck();
}

void Game::Fill_Decks(Deck *d)
{
   int t, r, counter = 0, suit, rank;

   for(t = 0; t < 4; t++)
   {
      for(r = 0; r < 52; r++)
	 {
	    suit = d->Return_Card(counter)->GET_SUIT();
	    Players[t].Return_Deck()->Return_Card(counter)->CHANGE_SUIT(suit);

	    rank = d->Return_Card(counter)->GET_RANK();
	    Players[t].Return_Deck()->Return_Card(counter)->CHANGE_RANK(rank);

	    Players[t].Return_Deck()->Return_Card(counter)->SET_ACTIVE(true);

	    counter++;
	 }
   }
}

void Game::Print_Player_Deck(int t)
{
   int r;
   bool checka;
   Deck *d = NULL;

      for(r = 0; r < 52; r++)
      {
          checka = Players[t].Return_Deck()->Return_Card(r)->CHECK_ACTIVE();

          if(checka == true)
          {
                d = Players[t].Return_Deck();
                d->Print_Card(r);
                cout << " ";
           }
       }
}

/*void Game::Pick_Card(int &deck_num)
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

} */
