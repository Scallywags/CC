// Generated from TAttr.g4 by ANTLR 4.4
package pp.block3.cc.antlr;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TAttrParser}.
 */
public interface TAttrListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TAttrParser#t}.
	 * @param ctx the parse tree
	 */
	void enterT(@NotNull TAttrParser.TContext ctx);
	/**
	 * Exit a parse tree produced by {@link TAttrParser#t}.
	 * @param ctx the parse tree
	 */
	void exitT(@NotNull TAttrParser.TContext ctx);
}