lexer grammar TVocab;

HAT		:	'^'	;
PLUS	:	'+' ;
EQ		:	'=' ;
LPAR	: 	'('	;
RPAR	:	')'	;

NUM		:	[0-9]+				;
BOOL	:	'true' | 'false'	;
STR		:	'"' .*? '"'			;

WS : [ \t\n\r] -> skip;