#include <io.h>
#include <conio.h>
#include <stdio.h>
#include <stdlib.h>
#include <dos.h>
#include <bios.h>
#include <fcntl.h>
#include <memory.h>
#include <malloc.h>
#include <math.h>
#include <string.h>
#include <graphics.h>

#include <c:\tc\bin\tomlib.h>

// D E F I N E S ///////////////////////////////////////////////////////////

#define NUM_CRITTERS 200

// S T R U C T U R E S /////////////////////////////////////////////////////

typedef struct critter{

	float x, y, xv, yv;
	int back;

}critter, *critter_ptr;

// G L O B A L S ///////////////////////////////////////////////////////////

critter critters[NUM_CRITTERS];

void main(){
clrscr();

Set_Graph();

int index;

float speed, dx, dy, length, centroid_x, centroid_y;

for(index=0; index < NUM_CRITTERS; index++){

	critters[index].x = rand()%640;
	critters[index].y = rand()%480;
	critters[index].back = rand()%15;

}
   for (index=0; index < NUM_CRITTERS; index++){

	if (critters[index].back == GREEN){

			centroid_x = 10;
	centroid_y = 10;

		dx = centroid_x;
	dy = centroid_y;

length = sqrt(dx * dx+dy * dy);
	dx = dx / length;
	dy = dy / length;

speed = 2 + rand()%3;

	critters[index].xv = dx * speed;
	critters[index].yv = dy * speed;


if(critters[index].x == centroid_x) dx = 0;
if(critters[index].y == centroid_y) dy = 0;
	}
}
if (critters[index].back == RED){

	centroid_x = 20;
	centroid_y = 20;
	}

if (critters[index].back == BLUE){

	centroid_x = 30;
	centroid_y = 30;
	}

/*	//compute deltas
	dx = centroid_x - critters[index].x;
	dy = centroid_y - critters[index].y;

length = sqrt(dx * dx+dy * dy);
	dx = dx / length;
	dy = dy / length;

speed = 2 + rand()%3;

	critters[index].xv = dx * speed;
	critters[index].yv = dy * speed;

} */

while(!kbhit()){

	for (index=0; index < NUM_CRITTERS; index++){

		putpixel(critters[index].x, critters[index].y, BLACK);
		}
// BEGIN CONVERGENCE CODE //////////////////////////////////////////////////

	// move critters toward centroid if they are far enough away
	for (index=0; index < NUM_CRITTERS; index++){

		// test if critter is far enough away from centroid use
		// use manhattan distance

	    //	if ((abs(critters[index].x-centroid_x) + abs(critters[index].y-centroid_y)) > 20){

			critters[index].x += critters[index].xv;
			critters[index].y += critters[index].yv;
		    /*
	*/  //	} // end if critters is far enough
	} // end for index

// END CONVERGENCE CODE ////////////////////////////////////////////////////

	// draw critters
	for (index=0; index < NUM_CRITTERS; index++){
		putpixel((int)critters[index].x, critters[index].y, critters[index].back);
		}

	// Lock onto 9 frames per second max
	delay(200);
	}  // end while

closegraph();
getch();
}