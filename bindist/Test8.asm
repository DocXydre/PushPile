.include beta.uasm
.include intio.uasm
.options tty

CMOVE(pile, SP)
CALL(main)
HALT()

a: LONG(1)
b: LONG(2)
x: LONG(0)

main:
	PUSH(LP)
	PUSH(BP)
	MOVE(SP, BP)
	ALLOCATE(0)
	LD(a, R0)
	PUSH(R0)
	LD(b, R0)
	PUSH(R0)
	POP(R2)
	POP(R1)
	CMPLT(R2, R1, R0)
	PUSH(R0)
	POP(R0)
	BF(R0, else_0)
	CMOVE(1000, R0)
	PUSH(R0)
	POP(R0)
	ST(R0, x, R31)
	BR(endif_1)
else_0:
	CMOVE(2000, R0)
	PUSH(R0)
	POP(R0)
	ST(R0, x, R31)
endif_1:
ret_main:
	MOVE(BP, SP)
	POP(BP)
	POP(LP)
	RTN()


pile: . = . + 0x1000
