.include beta.uasm
.include intio.uasm
.options tty

CMOVE(pile, SP)
CALL(main)
HALT()

i: LONG(10)
j: LONG(20)
k: LONG(0)
l: LONG(0)

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
