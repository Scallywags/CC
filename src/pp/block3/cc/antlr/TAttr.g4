grammar TAttr;

import TVocab;

t returns [Type type]	:	t0=t HAT t1=t
							{$type = $t1.type == Type.NUM ? ($t0.type != Type.BOOL ? $t0.type : Type.ERR) : Type.ERR; }
						|	t0=t PLUS t1=t
							{$type = $t0.type == $t1.type ? $t0.type : Type.ERR; }
						|	t0=t EQ t1=t
							{$type = $t0.type == $t1.type ? Type.BOOL : Type.ERR ;}
						|	LPAR t0=t RPAR
							{$type = $t0.type; }
						|	NUM
							{$type = Type.NUM; }
						|	BOOL
							{$type = Type.BOOL; }
						|	STR
							{$type = Type.STR; }
						;