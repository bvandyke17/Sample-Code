/* printNum.s */
/* 2019-10-31 */

.data

.balign 4
/* create i value */
i: 
 .word 12

.balign 4
/* create j value */
j: 
 .word 23

.balign 4
/* create string format */
string: 
 .asciz "%d\n"

.text

.balign 4

.global main

main: 

    /* push link register onto stack */
    push {lr}

    /* load address of i to register */
    ldr r4, i_addr
    /* load value of de-referenced i into register */
    ldr r4, [r4]

    /* load address of j to register */
    ldr r5, j_addr
    /* load value of de-referenced j into register */
    ldr r5, [r5]

    /* add 8 to the value of j */
    add r5, r5, #8
    /* multiply the value of (j + 8) by the value of i */
    mul r1, r4, r5

    /* pass format to printf call */
    ldr r0, format

    bl printf

    /* pop link register from stack */   
    pop {lr}

    /* assign value to return */
    mov r0, #0

    /* exit main and return r0 */
    bx lr

i_addr : .word i
j_addr : .word j
format : .word string

