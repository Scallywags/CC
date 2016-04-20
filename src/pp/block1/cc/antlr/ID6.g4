lexer grammar ID6;

ID6						: LETTER ALPHANUMERIC ALPHANUMERIC ALPHANUMERIC ALPHANUMERIC ALPHANUMERIC;
WS    					: [ \t\r\n]+ -> skip ;

fragment ALPHANUMERIC	: DIGIT|LETTER	;
fragment DIGIT			: [0..9];
fragment LETTER			: ['a'..'z'|'A'..'Z'];

