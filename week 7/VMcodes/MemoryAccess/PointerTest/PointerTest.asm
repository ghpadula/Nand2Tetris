//push constant 3030
@3030
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop pointer 0
pop
//push constant 3040
@3040
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop pointer 1
pop
//push constant 32
@32
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop this 2
pop
//push constant 46
@46
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop that 6
pop
//push pointer 0
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
//push pointer 1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
//add
@SP
AM=M-1
D=M
A=A-1
M=D+M
//push this 2
@2
D=A
@THIS
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//sub
@SP
AM=M-1
D=M
A=A-1
M=D-M
//push that 6
@6
D=A
@THAT
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//add
@SP
AM=M-1
D=M
A=A-1
M=D+M
