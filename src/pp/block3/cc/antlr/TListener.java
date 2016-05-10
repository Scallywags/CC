// Generated from T.g4 by ANTLR 4.4
package pp.block3.cc.antlr;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TParser}.
 */
public interface TListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code par}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void enterPar(@NotNull TParser.ParContext ctx);
	/**
	 * Exit a parse tree produced by the {@code par}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void exitPar(@NotNull TParser.ParContext ctx);
	/**
	 * Enter a parse tree produced by the {@code str}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void enterStr(@NotNull TParser.StrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code str}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void exitStr(@NotNull TParser.StrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bool}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void enterBool(@NotNull TParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void exitBool(@NotNull TParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code num}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void enterNum(@NotNull TParser.NumContext ctx);
	/**
	 * Exit a parse tree produced by the {@code num}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void exitNum(@NotNull TParser.NumContext ctx);
	/**
	 * Enter a parse tree produced by the {@code hat}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void enterHat(@NotNull TParser.HatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code hat}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void exitHat(@NotNull TParser.HatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eq}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void enterEq(@NotNull TParser.EqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eq}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void exitEq(@NotNull TParser.EqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plus}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void enterPlus(@NotNull TParser.PlusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plus}
	 * labeled alternative in {@link TParser#t}.
	 * @param ctx the parse tree
	 */
	void exitPlus(@NotNull TParser.PlusContext ctx);
}