.include beta.uasm
.include intio.uasm
.options tty

CMOVE(pile, SP)
CALL(main)
HALT()


f:
	PUSH(LP)
	PUSH(BP)
	MOVE(SP, BP)
	ALLOCATE(0)
	LD(BP, -12, R0)
	PUSH(R0)
	CMOVE(0, R0)
	PUSH(R0)
	POP(R2)
	POP(R1)
	CMPLE(R1, R2, R0)
	PUSH(R0)
	POP(R0)
	BF(R0, else_0)
	CMOVE(0, R0)
	PUSH(R0)
	POP(R0)
	BR(ret_f)
	BR(endif_1)
else_0:
	LD(BP, -12, R0)
	PUSH(R0)
	LD(BP, -12, R0)
	PUSH(R0)
	CMOVE(1, R0)
	PUSH(R0)
	POP(R2)
	POP(R1)
	SUB(R1, R2, R0)
	PUSH(R0)
	CALL(f)
	DEALLOCATE(1)
	PUSH(R0)
	POP(R2)
	POP(R1)
	ADD(R1, R2, R0)
	PUSH(R0)
	POP(R0)
	BR(ret_f)
endif_1:
ret_f:
	MOVE(BP, SP)
	POP(BP)
	POP(LP)
	RTN()

main:
	PUSH(LP)
	PUSH(BP)
	MOVE(SP, BP)
	ALLOCATE(0)
	CMOVE(6, R0)
	PUSH(R0)
	CALL(f)
	DEALLOCATE(1)
	PUSH(R0)
	POP(R0)
	WRINT()
ret_main:
	MOVE(BP, SP)
	POP(BP)
	POP(LP)
	RTN()


pile: . = . + 0x1000
