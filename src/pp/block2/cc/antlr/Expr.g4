grammar Expr;

//TODO FIX!!!

expr	: expr op expr	#compExpr
		| NUMBER		#numExpr
		| '(' expr ')'	#brackExpr
		;

op		: <assoc=right> POW		#powOp 
		| MULT					#multOp
		| DIV					#divOp 	
		| PLUS					#plusOp
		| MIN					#minOp
		;

NUMBER	: ('0'..'9')+
		| MIN NUMBER
		;


PLUS 	: '+'	;
MULT	: '*'	;
DIV		: '/'	;
MIN		: '-'	;
POW		: '^'	;