.include beta.uasm
.include intio.uasm
.options tty

CMOVE(pile, SP)
CALL(main)
HALT()

x: LONG(0)

main:
	PUSH(LP)
	PUSH(BP)
	MOVE(SP, BP)
	ALLOCATE(0)
	CMOVE(0, R0)
	PUSH(R0)
	POP(R0)
	ST(R0, x, R31)
loop_0:
	LD(x, R0)
	PUSH(R0)
	CMOVE(8, R0)
	PUSH(R0)
	POP(R2)
	POP(R1)
	CMPLT(R1, R2, R0)
	PUSH(R0)
	POP(R0)
	BF(R0, endloop_1)
	LD(x, R0)
	PUSH(R0)
	POP(R0)
	WRINT()
	LD(x, R0)
	PUSH(R0)
	CMOVE(1, R0)
	PUSH(R0)
	POP(R2)
	POP(R1)
	ADD(R1, R2, R0)
	PUSH(R0)
	POP(R0)
	ST(R0, x, R31)
	BR(loop_0)
endloop_1:
ret_main:
	MOVE(BP, SP)
	POP(BP)
	POP(LP)
	RTN()


pile: . = . + 0x1000
