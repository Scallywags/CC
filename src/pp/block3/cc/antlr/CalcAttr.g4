grammar CalcAttr;

import CalcVocab;

@members {
    private int getValue(String text) {
        return Integer.parseInt(text);
    }
}

expr returns [ int val ]
     : //{ System.out.println("Evaluating expr TIMES expr"); }
       e0=expr TIMES e1=expr
       { $val = $e0.val * $e1.val; }
     | //{ System.out.println("Evaluating expr PLUS expr"); }
       e0=expr PLUS e1=expr
       { $val = $e0.val + $e1.val; }
     | { System.out.println("Evaluating MINUES expr"); }
       MINUS e=expr
     	{ $val = -$e.val; }
     | //{ System.out.println("Evaluating LPAR expr RPAR"); }
       LPAR e=expr RPAR
       { $val = $e.val; }
     | { System.out.println("Evaluating NUMBER"); }
       NUMBER
       { $val = getValue($NUMBER.text); }
     ;
