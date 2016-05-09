grammar T;

import TVocab;

t	:	t HAT t			#hat
	|	t PLUS t		#plus
	|	t EQ t			#eq
	|	LPAR t RPAR		#par
	|	NUM				#num
	|	BOOL			#bool
	|	STR				#str
	;