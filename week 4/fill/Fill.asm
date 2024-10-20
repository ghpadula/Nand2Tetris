// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

(SETVAR)
@8192
D=A 
@i
M=-1

@LOOP
0;JMP

(LOOP)
@i
M=M+1
D=M
@8192
D=D-A
@SETVAR
D;JGE
@KBD
D=M
@BLACKSCREEN
D;JNE
@WHITESCREEN
D;JEQ

(BLACKSCREEN)
@SCREEN		      
D=A
@i
A=D+M
M=-1
@LOOP
0;JMP

(WHITESCREEN)
@SCREEN		      
D=A
@i
A=D+M
M=0
@LOOP
0;JMP

