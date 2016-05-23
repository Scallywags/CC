// Generated from NumWord.g4 by ANTLR 4.4
package pp.block5.cc.antlr;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link NumWordParser}.
 */
public interface NumWordListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link NumWordParser#sentence}.
	 * @param ctx the parse tree
	 */
	void enterSentence(@NotNull NumWordParser.SentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link NumWordParser#sentence}.
	 * @param ctx the parse tree
	 */
	void exitSentence(@NotNull NumWordParser.SentenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link NumWordParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(@NotNull NumWordParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link NumWordParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(@NotNull NumWordParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link NumWordParser#word}.
	 * @param ctx the parse tree
	 */
	void enterWord(@NotNull NumWordParser.WordContext ctx);
	/**
	 * Exit a parse tree produced by {@link NumWordParser#word}.
	 * @param ctx the parse tree
	 */
	void exitWord(@NotNull NumWordParser.WordContext ctx);
}