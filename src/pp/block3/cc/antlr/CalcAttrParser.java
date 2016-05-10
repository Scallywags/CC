// Generated from CalcAttr.g4 by ANTLR 4.4
package pp.block3.cc.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CalcAttrParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		TIMES=1, PLUS=2, MINUS=3, LPAR=4, RPAR=5, NUMBER=6, WS=7;
	public static final String[] tokenNames = {
		"<INVALID>", "'*'", "'+'", "'-'", "'('", "')'", "NUMBER", "WS"
	};
	public static final int
		RULE_expr = 0;
	public static final String[] ruleNames = {
		"expr"
	};

	@Override
	public String getGrammarFileName() { return "CalcAttr.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    private int getValue(String text) {
	        return Integer.parseInt(text);
	    }

	public CalcAttrParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ExprContext extends ParserRuleContext {
		public int val;
		public ExprContext e0;
		public ExprContext e;
		public Token NUMBER;
		public ExprContext e1;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode TIMES() { return getToken(CalcAttrParser.TIMES, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MINUS() { return getToken(CalcAttrParser.MINUS, 0); }
		public TerminalNode PLUS() { return getToken(CalcAttrParser.PLUS, 0); }
		public TerminalNode LPAR() { return getToken(CalcAttrParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(CalcAttrParser.RPAR, 0); }
		public TerminalNode NUMBER() { return getToken(CalcAttrParser.NUMBER, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalcAttrListener ) ((CalcAttrListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalcAttrListener ) ((CalcAttrListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			switch (_input.LA(1)) {
			case MINUS:
				{
				 System.out.println("Evaluating MINUES expr"); 
				setState(4); match(MINUS);
				setState(5); ((ExprContext)_localctx).e = expr(3);
				 ((ExprContext)_localctx).val =  -((ExprContext)_localctx).e.val; 
				}
				break;
			case LPAR:
				{
				setState(8); match(LPAR);
				setState(9); ((ExprContext)_localctx).e = expr(0);
				setState(10); match(RPAR);
				 ((ExprContext)_localctx).val =  ((ExprContext)_localctx).e.val; 
				}
				break;
			case NUMBER:
				{
				 System.out.println("Evaluating NUMBER"); 
				setState(14); ((ExprContext)_localctx).NUMBER = match(NUMBER);
				 ((ExprContext)_localctx).val =  getValue((((ExprContext)_localctx).NUMBER!=null?((ExprContext)_localctx).NUMBER.getText():null)); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(30);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(28);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						_localctx.e0 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(18);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(19); match(TIMES);
						setState(20); ((ExprContext)_localctx).e1 = expr(6);
						 ((ExprContext)_localctx).val =  ((ExprContext)_localctx).e0.val * ((ExprContext)_localctx).e1.val; 
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						_localctx.e0 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(23);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(24); match(PLUS);
						setState(25); ((ExprContext)_localctx).e1 = expr(5);
						 ((ExprContext)_localctx).val =  ((ExprContext)_localctx).e0.val + ((ExprContext)_localctx).e1.val; 
						}
						break;
					}
					} 
				}
				setState(32);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 5);
		case 1: return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\t$\4\2\t\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2\23\n\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2\37\n\2\f\2\16\2\"\13\2\3\2\2\3\2"+
		"\3\2\2\2&\2\22\3\2\2\2\4\5\b\2\1\2\5\6\b\2\1\2\6\7\7\5\2\2\7\b\5\2\2\5"+
		"\b\t\b\2\1\2\t\23\3\2\2\2\n\13\7\6\2\2\13\f\5\2\2\2\f\r\7\7\2\2\r\16\b"+
		"\2\1\2\16\23\3\2\2\2\17\20\b\2\1\2\20\21\7\b\2\2\21\23\b\2\1\2\22\4\3"+
		"\2\2\2\22\n\3\2\2\2\22\17\3\2\2\2\23 \3\2\2\2\24\25\f\7\2\2\25\26\7\3"+
		"\2\2\26\27\5\2\2\b\27\30\b\2\1\2\30\37\3\2\2\2\31\32\f\6\2\2\32\33\7\4"+
		"\2\2\33\34\5\2\2\7\34\35\b\2\1\2\35\37\3\2\2\2\36\24\3\2\2\2\36\31\3\2"+
		"\2\2\37\"\3\2\2\2 \36\3\2\2\2 !\3\2\2\2!\3\3\2\2\2\" \3\2\2\2\5\22\36"+
		" ";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}