//push constant 10
//pop local 0
pop
//push constant 21
//push constant 22
//pop argument 2
pop
//pop argument 1
pop
//push constant 36
//pop this 6
pop
//push constant 42
//push constant 45
//pop that 5
pop
//pop that 2
pop
//push constant 510
//pop temp 6
pop
//push local 0
@0
D=A
@LCL
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//push that 5
@5
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
aritimetic
//push argument 1
@1
D=A
@ARG
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//sub
aritimetic
//push this 6
@6
D=A
@THIS
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//push this 6
@6
D=A
@THIS
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//add
aritimetic
//sub
aritimetic
//push temp 6
//add
aritimetic
