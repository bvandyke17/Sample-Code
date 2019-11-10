/* intStack */
/* 2019-11-07 */

.data

.balign 4
/* reserve space for an array of 1024 words */
array: 
 .skip 4096		

.balign 4
/* declare a counter starting at 0 for elements on stack */
top_of_stack: 
 .word 0

.text

/* function determining if the stack is empty */
.type isEmpty %function
.global isEmpty
isEmpty:
	/* load return register with "true" value */
	mov r0, #1
	/* load value of stack counter */
	ldr r1, top_addr
	ldr r1, [r1]
	/* return 1 if true by determining if the stack count is 0 */
	cmp r1, #0
	/* load register with false value 0 and return */
	bne return_false
	bx lr

return_false:
	mov r0, #0
	bx lr
	
/* function determining if the stack is full (1024 words) */
.type isFull %function
.global isFull
isFull:
	/* load return register with "false"value */
	mov r0, #0
	/* load value of stack counter */
	ldr r1, top_addr
	ldr r1, [r1]
	/* compare amount of elements in stack with total reserved stack size */
	/* return 0 if false, branch and assign true value of 1 if otherwise */
	cmp r1, #1024
	bge true_return
	bx lr

true_return:
	mov r0, #1
	bx lr

/* function to push integers onto stack with a size of 1024 words */
.type push %function
.global push
push:
	/* load array address */
	ldr r1, array_addr
	/* load stack pointer value */
	ldr r2, top_addr
	ldr r2, [r2]
	/* add (stack counter * 4) to array address to get current top of stack */
	add r3, r1, r2, lsl #2
	/* store parameter value of function into top of stack address */
	str r0, [r3]

	/* increment stack counter value and store value into stack counter address */
	ldr r1, top_addr
	ldr r2, [r1]
	add r2, r2, #1
	str r2, [r1]
	bx lr

/* function to pop integers off of stack  */
.type pop %function
.global pop
pop:
	/* decrement stack counter value and store value into stack counter address */
	ldr r1, top_addr
	ldr r2, [r1]
	sub r2, r2, #1
	str r2, [r1]

	/* load array address */
	ldr r1, array_addr
	/* load stack pointer value*/
	ldr r2, top_addr
	ldr r2, [r2]
	/* add (stack counter * 4) to array address to get current top of stack */
	add r3, r1, r2, lsl #2
	/* load top of stack value into return register */
	ldr r0, [r3]
	bx lr

/* function to peak at the top of the stack */
.type top %function
.global top
top:
	/* get top of stack index */
	ldr r1, top_addr
	ldr r2, [r1]
	sub r2, r2, #1

	/* add (stack counter * 4) to array address to get current top of stack */
	ldr r1, array_addr
	add r3, r1, r2, lsl #2
	/* load top of stack value into return register */
	ldr r0, [r3]
	bx lr

/* function to determine how many elements are in the stack */
.type size %function
.global size
size:
	/* return stack counter value */
	ldr r0, top_addr
	ldr r0, [r0]
	bx lr

array_addr : .word array
top_addr : .word top_of_stack
