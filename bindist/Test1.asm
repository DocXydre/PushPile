.include beta.uasm
.include intio.uasm
.options tty

CMOVE(pile, SP)
CALL(main)
HALT()


main:
	PUSH(LP)
	PUSH(BP)
	MOVE(SP, BP)
	ALLOCATE(0)
ret_main:
	MOVE(BP, SP)
	POP(BP)
	POP(LP)
	RTN()


pile: . = . + 0x1000
