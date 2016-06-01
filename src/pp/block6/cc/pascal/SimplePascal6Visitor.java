// Generated from SimplePascal6.g4 by ANTLR 4.4
package pp.block6.cc.pascal;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SimplePascal6Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SimplePascal6Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code assStat}
	 * labeled alternative in {@link SimplePascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssStat(@NotNull SimplePascal6Parser.AssStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStat}
	 * labeled alternative in {@link SimplePascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStat(@NotNull SimplePascal6Parser.BlockStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trueExpr}
	 * labeled alternative in {@link SimplePascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueExpr(@NotNull SimplePascal6Parser.TrueExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code outStat}
	 * labeled alternative in {@link SimplePascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutStat(@NotNull SimplePascal6Parser.OutStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimplePascal6Parser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(@NotNull SimplePascal6Parser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimplePascal6Parser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(@NotNull SimplePascal6Parser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link SimplePascal6Parser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(@NotNull SimplePascal6Parser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link SimplePascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpr(@NotNull SimplePascal6Parser.ParExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimplePascal6Parser#multOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultOp(@NotNull SimplePascal6Parser.MultOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link SimplePascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompExpr(@NotNull SimplePascal6Parser.CompExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimplePascal6Parser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(@NotNull SimplePascal6Parser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code falseExpr}
	 * labeled alternative in {@link SimplePascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseExpr(@NotNull SimplePascal6Parser.FalseExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimplePascal6Parser#plusOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusOp(@NotNull SimplePascal6Parser.PlusOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link SimplePascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStat(@NotNull SimplePascal6Parser.WhileStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link SimplePascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStat(@NotNull SimplePascal6Parser.IfStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimplePascal6Parser#boolOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolOp(@NotNull SimplePascal6Parser.BoolOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idTarget}
	 * labeled alternative in {@link SimplePascal6Parser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdTarget(@NotNull SimplePascal6Parser.IdTargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimplePascal6Parser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(@NotNull SimplePascal6Parser.VarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link SimplePascal6Parser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(@NotNull SimplePascal6Parser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link SimplePascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExpr(@NotNull SimplePascal6Parser.MultExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numExpr}
	 * labeled alternative in {@link SimplePascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumExpr(@NotNull SimplePascal6Parser.NumExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link SimplePascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusExpr(@NotNull SimplePascal6Parser.PlusExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimplePascal6Parser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompOp(@NotNull SimplePascal6Parser.CompOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prfExpr}
	 * labeled alternative in {@link SimplePascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrfExpr(@NotNull SimplePascal6Parser.PrfExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimplePascal6Parser#prfOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrfOp(@NotNull SimplePascal6Parser.PrfOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inStat}
	 * labeled alternative in {@link SimplePascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInStat(@NotNull SimplePascal6Parser.InStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link SimplePascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpr(@NotNull SimplePascal6Parser.BoolExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimplePascal6Parser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(@NotNull SimplePascal6Parser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link SimplePascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(@NotNull SimplePascal6Parser.IdExprContext ctx);
}