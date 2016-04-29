lexer grammar LRQ;

L		: R A		| Q B A				;
R		: A B A S	| C A B A S			;
Q		: B P							;
P		: B C		| C					;
S		: B C S		| EMPTY				;

A		: 'a'							;
B		: 'b'							;
C		: 'c'							;
EMPTY	: ''							;

WS		: [' \r\n\t']	-> skip			;