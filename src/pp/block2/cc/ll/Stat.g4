lexer grammar Stat;

STAT			: ASSIGN		| IF EXPR THEN STAT ELSEPART	;
ELSEPART		: ELSE STAT		| EMPTY							;

ASSIGN			: 'assign'		;
IF				: 'if'			;
EXPR			: 'expr'		;
THEN			: 'then'		;
ELSE			: 'else'		;
EMPTY			: ''			;

// ignore whitespace
WS : [ \t\n\r] -> skip;