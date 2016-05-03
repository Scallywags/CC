grammar Expr;

expr	: expr op expr
		| number
		| '(' expr ')'
		;

op		: '+'
		| '-'
		| '*'
		| '/'
		| '--'
		| <assoc=righit> '^'
		;

number	: ('0'..'9')+
		;


