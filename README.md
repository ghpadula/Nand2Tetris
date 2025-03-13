# Nand2Tetris Course - Readme

This repository contains the content of the **Nand2Tetris** course, which guides you through the process of building a computer from scratch using logic gates and other basic constructs. The course covers everything from creating logic gates to developing a fully functional computer, including its CPU and memory. Each week of the course focuses on a specific stage of the process.

## Table of Contents

- **Week 1:** Building basic logic gates (AND, OR, XOR, etc.)
- **Week 2:** Building the ALU (Arithmetic Logic Unit), Add16, Full and Half Adder, and Inc16
- **Week 3:** Building memory chips
- **Week 4:** Writing assembly code to familiarize with the language
- **Week 5:** Building the computer CPU and memory
- **Week 6:** Building the assembler in Java to translate assembly to binary
- **VMTranslator:** Translates VM (Virtual Machine) code to Assembly code

---

## Technologies Used

- **HDL (Hardware Description Language):** For describing and implementing logic gates, ALU, memory, and other hardware components.
- **Java:** Used for building the **Assembly** to **binary** and **VM bytecode** to **Assembly**.

---

## Week-by-Week Description

### **Week 1: Building Logic Gates**
In this week, we focused on implementing basic logic gates like AND, OR, XOR, NOT, and NAND using **HDL**. These gates form the foundation of the computer and will be used to build more complex circuits in the following weeks.

### **Week 2: Building the ALU and Arithmetic Components**
In Week 2, we constructed the **ALU** (Arithmetic Logic Unit) along with components such as **Add16**, **Full Adder**, **Half Adder**, and **Inc16**. These components are responsible for performing arithmetic operations such as addition and incrementing, which are essential for executing any program on the computer.

### **Week 3: Building Memory**
In Week 3, we built memory chips that allow the computer to store and retrieve data. These memory chips are the building blocks for the computer’s RAM.

### **Week 4: Writing in Assembly**
This week, we learned how to work with **Assembly**, a low-level language that the processor understands. The main task was to familiarize ourselves with the instructions in this language and how to write programs that interact with the hardware components built in the previous weeks.

### **Week 5: Building the CPU and Memory**
Week 5 focused on building the **CPU** (Central Processing Unit), which connects the ALU, memory, and other components. The CPU is the "brain" of the computer and is responsible for executing the program's instructions. We also worked on the memory, where data is stored and manipulated.

### **Week 6: Building the Assembler**
In Week 6, we developed the **Assembler** in **Java**, which translates programs written in Assembly into binary, a format that can be executed by the computer we built.

### **VMTranslator: Translating VM Code to Assembly**
The **VMTranslator** is a Java program that converts **VM (Virtual Machine)** code, a high-level language similar to Java bytecode, into **Assembly** code. VM code abstracts the machine's instruction set, making it easier to write and understand programs without dealing with the complexities of low-level Assembly.

The **VMTranslator** takes a `.vm` file (written in VM language) as input and outputs an Assembly file (`.asm`). This translation is necessary because the computer built in the Nand2Tetris course uses Assembly for execution.

---

## Repository Structure

This repository contains all the implementation files used throughout the course, organized by week:
## To run

### Assembler
```bash
javac Main.java
```
```bash
javac Main <asm-file>
```

### VMtranslator
He can compile multiple VM files in a dir.
```bash
javac Main.java
```
```bash
java Main <dir or vmfile>
```
