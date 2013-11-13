#include <stdio.h>
#include <graphics.h>
#include <stdlib.h>
#include <conio.h>
#include <iostream.h>
#include <alloc.h>
#include <dos.h>
#include <time.h>
#include <math.h>
#include <fcntl.h>
#include <memory.h>
#include <malloc.h>
#include <string.h>
#include <bios.h>

#include "black3.h"
#include "black4.h"

//typedefs
  typedef unsigned char byte;
  typedef unsigned int  word;

//video screen pointer
unsigned char *vga = (unsigned char *) MK_FP(0xA000, 0);


//virtual buffer pointer
unsigned char *buffer = NULL;
unsigned char *b_buffer = NULL;
//table for y multiplications
int y_table[200];
int xv;
//default graphics for C++ library
void Set_Graph(void){
/* request auto detection */
int gdriver = DETECT, gmode, errorcode;
int midx, midy;

/* initialize graphics and local variables */
initgraph(&gdriver, &gmode, "");

/* read result of initialization */
errorcode = graphresult();
if (errorcode != grOk)  /* an error occurred */
{
   printf("Graphics error: %s\n", grapherrormsg(errorcode));
   printf("Press any key to halt:");
   getch();
   exit(1); /* terminate with an error code */
}
}
/////////////////////////////////////////////////////////////////////////////
//                                                                         //
// SetMCGA() - This function gets you into 320x200x256 mode.               //
//                                                                         //
/////////////////////////////////////////////////////////////////////////////

void SetMCGA() {
  _AX = 0x0013;
  geninterrupt (0x10);
}

/////////////////////////////////////////////////////////////////////////////
//                                                                         //
// SetText() - This function gets you into text mode.                      //
//                                                                         //
/////////////////////////////////////////////////////////////////////////////

void SetText() {
  _AX = 0x0003;
  geninterrupt (0x10);
}



//inits y table for graphics functions
void Init_Y(void){
int j;
for (j = 0; j < 200; j++){
y_table[j] = j * 320;
};
}


//puts a pixel on the screen by setting memory points
void Putpixel(int x, int y, unsigned char col, unsigned char *b){
if ((x >= 0) && (x < 320) && (y >= 0) && (y < 200))
_fmemset(b+(x+(y_table[y])), col, 1);
}

//gets a pixel color from the screen by returning memory points
unsigned char Getpixel(int x, int y, unsigned char *b){
return(b[x+y_table[y]]);
}

/////////////////////////////////////////////////////////////////////////////
//                                                                         //
// sgn() - This checks the sign of an integer and returns a 1, -1, or 0.   //
//                                                                         //
/////////////////////////////////////////////////////////////////////////////

int sgn (int a) {

  if (a > 0)  return +1;
  if (a < 0)  return -1;
  return 0;
}



//copys memory in words
void _fwordcpy(void far *destination, void far *source,int num_words)
{
// this function is similar to fmemcpy except that is moves data in words
// it is about 25% faster than memcpy which uses bytes

_asm	{
	push ds              // need to save segment registers i.e. ds
	les di,destination   // point es:di to destination of memory move
	lds si,source        // point ds:si to source of memory move
	mov cx,num_words     // move into cx the number of words to be moved
	rep movsw            // let the processor do the memory move
	pop ds               // restore the ds segment register

   } // end inline asm

} // end fwordcpy


//creates double buffers by allocation memory
void Create_Buffer(void){
buffer = (unsigned char *)farcalloc(64000, 1);
b_buffer = (unsigned char *)farcalloc(64000, 1);
if (buffer == NULL){
       SetText();
       cout << "Not enough mem.";
       getch();
       exit(0);
       }
if (b_buffer == NULL){
       SetText();
       cout << "Not enough mem.";
       getch();
       exit(0);
       }
}

//clears the screen by setting all to black
void Clear(unsigned char col, unsigned char *b){
_fmemset(b, col, 64000);
}

//copies one screen to another by memory copies
void Blit_Screen(unsigned char *src, unsigned char *dest){
//_fwordcpy(dest, src, 32000);
_asm	{
	push ds              // need to save segment registers i.e. ds
	les di,dest   // point es:di to destination of memory move
	lds si,src        // point ds:si to source of memory move
	mov cx,32000     // move into cx the number of words to be moved
	rep movsw            // let the processor do the memory move
	pop ds               // restore the ds segment register

   } // end inline asm
}

//frees the memory used by double buffer
void Release_Buffer(void){
farfree(buffer);
//farfree(b_buffer);
}

//vertical line on the screen
void vline(int x, int y1, int y2, unsigned char col, unsigned char *b){
int j;
for (j = y1; j < y2+1; j++){
_fmemset(b+(x+(y_table[j])), col, 1);
};
}

/////////////////////////////////////////////////////////////////////////////
//                                                                         //
// Pal() - This sets the Red, Green, and Blue values of a certain color.   //
//                                                                         //
/////////////////////////////////////////////////////////////////////////////

void Pal(unsigned char ColorNo, unsigned char R,
	 unsigned char G,       unsigned char B) {

  outp (0x03C8,ColorNo); // here is the pallette color I want to set
  outp (0x03C9,R);
  outp (0x03C9,G);
  outp (0x03C9,B);

}

//custom pallette for the screen
void Set_Pal(void){
Pal(0, 0, 0, 0);  //black
for (int j = 0; j < 64; j++){
Pal(j+192, 0, j, 0);  //blue to white

};
}
//horizontal line on the screen
void hline(word x1, word y, word x2, byte col, byte *b){
int temp = y_table[y];
int hold;
int length;
byte *addrs;
if (x1 > x2){
hold = x1;
x1 = x2;
x2 = hold;
};
if(x2 > 319)x2 = 319;
if (x1 < 0)x1 = 0;
addrs = b+(x1+(temp));
//_fmemset(b+(x1+(temp)), col, (x2-x1));//j, y, col, b);
_asm{
//       push ds
       les di, addrs
       mov al, [col]
       mov ah, al
       mov cx,[x2]
       sub cx,[x1]
       shr cx, 1
       jnc Start
       stosb
       }
       Start : asm{
       rep stosw
       }
}


void FillCircle(int x, int y, int r, int color, byte *b){
	int i, row, col_start, col_end, px, py;
	long int sum;

	py = r<<1;
	px = 0;
	sum = -(r<<1);
	while (px <= py)
	{
		if ( !(px & 1))
		{
			col_start = x - (px >> 1);
			col_end = x + (px >> 1);
			row = y + (py >> 1);
			if (row < 200)hline(col_start, row, col_end, color, b);
			col_start = x - (py >> 1);
			col_end = x + (py >> 1);
			row = y + (px >> 1);
			if (row < 200)hline(col_start, row, col_end, color, b);
			col_start = x - (px >> 1);
			col_end = x + (px >> 1);
			row = y - (py >> 1);
			if (row < 200)hline(col_start, row, col_end, color, b);
			col_start = x - (py >> 1);
			col_end = x + (py >> 1);
			row = y - (px >> 1);
			if (row < 200)hline(col_start, row, col_end, color, b);
		}
		sum += px++;
		if (sum >= 0)
		{
			sum -= py--;
		}
	}
}




void rect(word x1, word y1, word x2, word y2, byte col, byte *bmp){
int j, temp;
if (x1 > x2){
temp = x1;
x1 = x2;
x2 = temp;
};
if (y1 > y2){
temp = y1;
y1 = y2;
y2 = temp;
};

for (j = y1; j < y2+1; j++){
hline(x1, j, x2, col, bmp);
};
}

//shows pallette on the screen
void Show_Pal(void){
int count;
count = 32;
Clear(0, vga);
for (int j = 0; j < 200; j++){
if (j % 5 == 0)count += 1;
hline(0, j, 100, count, vga);
hline(100, j, 200, count+72, vga);
hline(200, j, 319, count+144, vga);
};
}

///////////////////////////GLOBALS///////////////////////////////////////

pcx_picture image_pcx; // general PCX image used to load background and
		       // imagery

sprite worm,ant;       //the worm and the ant

//MAIN//////////////////////////////////////////////////////////////////

void main(int argc, char **argv)
{
int index; // loop variable

//set the graphics mode to mode 13h
Set_Graphics_Mode(GRAPHICS_MODE13);

//create the double buffer
Create_Double_Buffer(200);

//load the imagery for the worm
PCX_Init((pcx_picture_ptr)&image_pcx);
PCX_Load("worming.pcx", (pcx_picture_ptr)&image_pcx, 1);

//intitalize the worm sprite
Sprite_Init((sprite_ptr)&worm,160,100,38,20,0,0,0,0,0,0);

//extract the bitmaps for the worm, there are 4 animation cells
for (index=0; index<4; index++)
	PCX_Get_Sprite((pcx_picture_ptr)&image_pcx,
	(sprite_ptr)&worm,index,index,0);

//done with this PCX file so delete memory associated with it
PCX_Delete((pcx_picture_ptr)&image_pcx);

//load the imagery for the ant
PCX_Init((pcx_picture_ptr)&image_pcx);
PCX_Load("antimg.pcx", (pcx_picture_ptr)&image_pcx, 1);

//intitialize the ant sprite
Sprite_Init((sprite_ptr)&ant,160,180,12,6,0,0,0,0,0,0);

//extract the bitmaps for the ant, there are 3 animation cells
for (index=0; index<3; index++);
	PCX_Get_Sprite((pcx_picture_ptr)&image_pcx,
	(sprite_ptr)&ant,index,index,0);

//delete memory association
PCX_Delete((pcx_picture_ptr)&image_pcx);

//now load the background that the worm and ant will run around in
PCX_Init((pcx_picture_ptr)&image_pcx);
PCX_Load("mushroom.pcx",(pcx_picture_ptr)&image_pcx,1);

//copy PCX image to double buffer
PCX_Copy_To_Buffer((pcx_picture_ptr)&image_pcx, double_buffer);

PCX_Delete((pcx_picture_ptr)&image_pcx);

//scan worm and ant
Sprite_Under((sprite_ptr)&worm, double_buffer);
Sprite_Under_Clip((sprite_ptr)&worm, double_buffer);

//main event loop until kbhit
while(!kbhit())
	{
	//do animation cycle, erase, move draw...

	//erase all objects by replacing what was under them

	Sprite_Erase((sprite_ptr)&ant,double_buffer);
	Sprite_Erase_Clip((sprite_ptr)&worm, double_buffer);

	//move objects, test if they have run off the right edge of the screen
	if ((ant.x+=1) > 320 - 12)
		ant.x = 0;
	if ((worm.x+=6) > 320)
		worm.x = 0;

	// do animation for the objects

	if (++ant.curr_frame==3)	// if all frames have been displayed then
		ant.curr_frame = 0;	// reset back to frame 0

	if (++worm.curr_frame==4)
		worm.curr_frame = 0;

	//ready to draw objects, but first scan in the background
	Sprite_Under((sprite_ptr)&ant, double_buffer);
	Sprite_Under_Clip((sprite_ptr)&worm, double_buffer);

	Sprite_Draw((sprite_ptr)&ant, double_buffer,1);
	Sprite_Draw_Clip((sprite_ptr)&worm, double_buffer, 1);
	// display double buffer
	Display_Double_Buffer(double_buffer,0);
		// lock onto 18 frames a second max
		Time_Delay(1);

		} //end while
	//exit in a very cool way
	Screen_Transition(SCREEN_SWIPE_X);

	//free up all resources
	Sprite_Delete((sprite_ptr)&worm);
	Sprite_Delete((sprite_ptr)&ant);
	Delete_Double_Buffer();
	Set_Graphics_Mode(TEXT_MODE);

	} //end main
