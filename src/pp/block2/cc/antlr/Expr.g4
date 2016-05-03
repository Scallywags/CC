grammar Expr;

NUMBER	: ('0'..'9')+	;

PLUS 	: '+'	;
MULT	: '*'	;
DIV		: '/'	;
MIN		: '-'	;
POW		: '^'	;

expr	: term PLUS term	#plusExpr
		| term MIN term		#minExpr
		| term				#termExpr
		;

term	: factor MULT factor	#multTerm
		| factor DIV factor		#divTerm
		| factor				#facTerm
		;

factor	: <assoc=right> factor POW factor		#powFac
		| NUMBER 								#numFac
		| '(' expr ')'							#exprFac
		| '-' factor							#negFac
		;

WS 		: [ \t\n\r] -> skip;
