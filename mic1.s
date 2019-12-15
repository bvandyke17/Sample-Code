/* 2019-12-10 */
/* Tanenbaum Mic1 */

/* bring in external functions */
.extern fgetc
.extern fopen
.extern putchar
.extern printf 
.extern fclose

/* Assign register alias */
MMAR .req r2
MMDR .req r3
MPC .req r4
MMBR .req r5
MSP .req r6
MLV .req r7
MCPP .req r8
MTOS .req r9
MOPC .req r10
MH .req r11
MMBRU .req r12

/* Macro to read from memory */
.macro READ
   ldr r1, array_memory_addr
   add r1, r1, MMAR
   ldr MMDR, [r1]
.endm

/* Macro to write from memory */
.macro WRITE
   ldr r1, array_memory_addr
   add r1, r1, MMAR
   str MMDR, [r1]
.endm
 
/* Macro to fetch from stack */ 
.macro FETCH
   ldr r1, array_memory_addr
   add r1, r1, MPC
   ldrsb MMBR, [r1]
   ldrb MMBRU, [r1]
.endm

/* Macro to debug */
.macro DEBUG x
   push {r0, r1, r2, r3, r12, lr}
   ldr r0, =fmt
   ldr r1, =#\x
   bl printf
   pop {r0, r1, r2, r3, r12, lr}    
.endm

.data

/* Assign file pointer */
.balign 4
file: .word 0    

/* Assign read mode */
.balign 4
fileMode: .asciz "r"

/* Assign format for printing */
.balign 4
fmt: .asciz "%d\n"

/* Assign array to be used as memory */
.balign 4
array: .skip 16000

/* Assign index to increment memory array */
.balign 4
array_index : .word 0

.balign 4
.text

.global main

main:
   /* open file */
   push {lr}                
   ldr r0, [r1, #4]        		
   ldr r1, =fileMode       
   bl fopen

   /* save file pointer */                
   ldr r2, =file     
   str r0, [r2]            
   
loop:
   /* load file pointer */
   ldr r0, =file
   ldr r0, [r0]

   /* get value and check if end of file */
   bl fgetc
   cmp r0, #-1
   beq done

   /* store value into next memory address */   
   ldr r1, array_memory_addr
   ldr r2, index_addr
   ldr r2, [r2]
   mov r3, #0
   add r3, r1, r2
   str r0, [r3]

   /* increment index */
   mov r3, #0
   add r3, r2, #1
   ldr r2, index_addr
   str r3, [r2]

   b loop    
             
done:
   /* close file */
   ldr r0, =file
   ldr r0, [r0]
   bl close
   
   /* set the LV */
   ldr r0, array_memory_addr
   ldr r2, index_addr
   ldr r2, [r2]
   mov r1, #0
   add r1, r0, r2
   mov MLV, r1
  
   /* assign PC */
   mov MPC, #2

   /* assign SP */
   ldr r0, array_memory_addr
   ldrb r0, [r0]
   mov r0, r0, asl #8
   ldr r1, array_memory_addr
   add r1, r1, #1
   ldrb r1, [r1]
   orr r0, r0, r1
   mov r0, r0, lsl #2
   add MSP, r0, MLV
 
   FETCH

   /* goto mic1 main */
   b Main1

Main1:
  	
   /* increment PC */
   add MPC, MPC, #1
   
   /* save MBRU value and fetch next */
   mov r0, MMBRU
   FETCH

   /* branch to corresponding opcode */
   cmp r0, #0x10		
   beq BIPUSH

   cmp r0, #0xA9
   beq RET

   cmp r0, #0x59
   beq DUP
 
   cmp r0, #0xA7		
   beq GOTO
 
   cmp r0, #0x60		
   beq IADD

   cmp r0, #0x7E		
   beq IAND

   cmp r0, #0x99		
   beq IFEQ

   cmp r0, #0x9B
   beq IFLT

   cmp r0, #0x84		
   beq IINC

   cmp r0, #0x15		
   beq ILOAD

   cmp r0, #0x80	
   beq IOR

   cmp r0, #0x36
   beq ISTORE

   cmp r0, #0x64 
   beq ISUB

   cmp r0, #0x00
   beq NOP

   cmp r0, #0xA8
   beq JSR

   cmp r0, #0x57
   beq POP

   cmp r0, #0x5F
   beq SWAP

   cmp r0, #0x68
   beq IMUL

   cmp r0, #0x6C
   beq IDIV
   
   cmp r0, #0x9F
   beq IF_ICMPEQ

/* Push byte onto stack */
BIPUSH:
   add MSP, MSP, #4
   mov MMAR, MSP
   mov MTOS, MMBR
   mov MMDR, MTOS
   WRITE
   add MPC, MPC, #1
   FETCH

   b Main1   

/* Copy top word on stack and push onto stack */
DUP:
   add MSP, MSP, #4
   mov MMAR, MSP
   mov MMDR, MTOS
   WRITE

   b Main1

/* Jump subroutine */
JSR:
   add MMBRU, MMBRU, #4
   add MSP, MSP, MMBRU
   mov MMDR, MCPP
   mov MCPP, MSP
   mov MMAR, MCPP
   WRITE
   add MMDR, MPC, #4
   add MSP, MSP, #4
   mov MMAR, MSP
   WRITE
   mov MMDR, MLV
   add MSP, MSP, #4
   mov MMAR, MSP
   WRITE
   sub MLV, MSP, #8
   sub MLV, MLV, MMBRU
   add MPC, MPC, #1
   FETCH

   sub MLV, MLV, MMBRU
   add MPC, MPC, #1
   FETCH

   mov MH, MMBR, lsl #8
   add MPC, MPC, #1
   FETCH

   sub MPC, MPC, #4
   orr MH, MH, MMBRU
   add MPC, MPC, MH
   FETCH

   b Main1

/* Return from method with integer value */
RET:
   cmp MCPP, #0
   beq exit
   mov MMAR, MCPP
   READ

   mov MCPP, MMDR
   add MMAR, MMAR, #4
   READ

   mov MPC, MMDR
   FETCH
   add MMAR, MMAR, #4
   READ
   mov MMAR, MLV
   mov MSP, MMAR
   mov MLV, MMDR
   mov MMDR, MTOS
   WRITE

   b Main1

/* Unconditional branch */
GOTO:
   sub MOPC, MPC, #1
   add MPC, MPC, #1
   mov MH, MMBR, asl #8
   FETCH
   orr MH, MMBRU, MH
   add MPC, MOPC, MH
   FETCH

   b Main1

/* Pop two words from stack and push their sum */
IADD:   
   sub MSP, MSP, #4
   mov MMAR, MSP
   READ
   mov MH, MTOS
   add MTOS, MMDR, MH
   mov MMDR, MTOS
   WRITE

   b Main1

/* Pop two words from stack and push their boolean AND */
IAND:
   sub MSP, MSP, #4
   mov MMAR, MSP
   READ
   mov MH, MTOS
   and MMDR, MMDR, MH
   mov MTOS, MMDR
   WRITE

   b Main1

/* Pop two words from stack and branch if zero */
IFEQ:
   sub MSP, MSP, #4
   mov MMAR, MSP
   READ
   mov MOPC, MTOS

   mov MTOS, MMDR
   mov MH, MOPC
   cmp MH,#0
   beq T
   b F

/* True comparison */
T:
   b GOTO

/* False comparison */
F:
   add MPC, MPC, #1
   add MPC, MPC, #1
   FETCH
   b Main1

/* Pop word from stack and branch if less than zero */
IFLT:
   sub MSP, MSP, #4		
   mov MMAR, MSP
   READ
   mov MOPC, MTOS
   mov MTOS, MMDR
   mov r0, MOPC
   cmp r0, #0
   blt T
   b F

/* Pop two words from stack and branch if equal */
IF_ICMPEQ:
   sub MSP, MSP, #4
   mov MMAR, MSP
   READ
   sub MSP, MSP, #4
   mov MMAR, MSP
   mov MH, MMDR
   READ
   mov MOPC, MTOS
   mov MTOS, MMDR
   sub r0, MOPC, MH
   cmp r0, #0
   beq T
   b F

/* Add a constant to a local variable */
IINC:
   mov MH, MLV
   add MMAR, MMBRU, MH
   READ
   add MPC, MPC, #1
   FETCH
   mov MH, MMDR
   add MMDR, MMBR, MH
   WRITE
   add MPC, MPC, #1
   FETCH

   b Main1

/* Push local variable onto stack */
ILOAD:
   mov MH, MLV
   add MMAR, MH, MMBRU, lsl #2
   READ
   add MSP, MSP, #4
   mov MMAR, MSP
   add MPC, MPC, #1
   FETCH
   WRITE
   mov MTOS, MMDR

   b Main1

/* Pop two words from stack and push their boolean OR value */
IOR:
   sub MSP, MSP, #4
   mov MMAR, MSP
   READ
   mov MH, MTOS
   orr MMDR, MMDR, MH
   mov MTOS, MMDR
   WRITE

   b Main1

/* Pop word from stack and store in local variable */
ISTORE:
   mov MH, MLV
   add MMAR, MH, MMBRU, lsl #2
   mov MMDR, MTOS
   WRITE
   sub MSP, MSP, #4
   mov MMAR, MSP
   add MPC, MPC, #1
   FETCH
   mov MTOS, MMDR

   b Main1

/* Pop two words from stack and push their difference */
ISUB:
   sub MSP, MSP, #4
   mov MMAR, MSP
   READ
   mov MH, MTOS
   sub MMDR, MMDR, MH
   mov MTOS, MMDR
   WRITE

   b Main1

/* Do nothing */
NOP:
   b Main1

/* Delete word on top of stack */
POP:
   sub MSP, MSP, #4
   mov MMAR, MSP
   READ

   mov MTOS, MMDR
   b Main1

/* Swap the two top words on stack */
SWAP:
   sub MMAR, MSP, #4
   READ
   mov MMAR, MSP
   mov MH, MMDR
   WRITE
   mov MMDR, MTOS
   sub MMAR, MSP, #4
   WRITE
   mov MTOS, MH

   b Main1

/* Pop two words from stack, multiply, push their value */
IMUL:
   sub MSP, MSP, #4
   mov MMAR, MSP
   READ
   mov MH, MTOS
   mul MTOS, MMDR, MH
   mov MMDR, MTOS
   WRITE

   b Main1

/* Pop two words from stack, divide, push their value */
IDIV:
   sub MSP, MSP, #4
   mov MMAR, MSP
   READ
   mov MH, MTOS
   mov r0, MMDR
   mov r1, MH
   push {r2, r12}
   bl __aeabi_idiv
   pop {r2, r12}
   mov MTOS, r0
   mov MMDR, MTOS
   WRITE

   b Main1

/* print top of stack and return */
exit:
   ldr r0, =fmt
   mov r1, MTOS
   bl printf
   mov r0, MTOS
   pop {lr}

   bx lr 

array_memory_addr : .word array
index_addr : .word array_index
