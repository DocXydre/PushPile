.include beta.uasm
.include intio.uasm
.options tty

CMOVE(pile, SP)
CALL(main)
HALT()

res: LONG(0)

main:
	PUSH(LP)
	PUSH(BP)
	MOVE(SP, BP)
	ALLOCATE(0)
	RDINT()
	PUSH(R0)
	CMOVE(2, R0)
	PUSH(R0)
	POP(R2)
	POP(R1)
	MUL(R1, R2, R0)
	PUSH(R0)
	RDINT()
	PUSH(R0)
	CMOVE(5, R0)
	PUSH(R0)
	POP(R2)
	POP(R1)
	SUB(R1, R2, R0)
	PUSH(R0)
	CMOVE(3, R0)
	PUSH(R0)
	POP(R2)
	POP(R1)
	DIV(R1, R2, R0)
	PUSH(R0)
	POP(R2)
	POP(R1)
	ADD(R1, R2, R0)
	PUSH(R0)
	POP(R0)
	ST(R0, res, R31)
	LD(res, R0)
	PUSH(R0)
	POP(R0)
	WRINT()
ret_main:
	MOVE(BP, SP)
	POP(BP)
	POP(LP)
	RTN()


pile: . = . + 0x1000
