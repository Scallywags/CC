// Generated from FuncPascal6.g4 by ANTLR 4.4
package pp.block6.cc.pascal;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FuncPascal6Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FuncPascal6Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code assStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssStat(@NotNull FuncPascal6Parser.AssStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStat(@NotNull FuncPascal6Parser.BlockStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trueExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueExpr(@NotNull FuncPascal6Parser.TrueExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code outStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutStat(@NotNull FuncPascal6Parser.OutStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(@NotNull FuncPascal6Parser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(@NotNull FuncPascal6Parser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link FuncPascal6Parser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(@NotNull FuncPascal6Parser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpr(@NotNull FuncPascal6Parser.ParExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#multOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultOp(@NotNull FuncPascal6Parser.MultOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompExpr(@NotNull FuncPascal6Parser.CompExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(@NotNull FuncPascal6Parser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpr(@NotNull FuncPascal6Parser.CallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code falseExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseExpr(@NotNull FuncPascal6Parser.FalseExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#plusOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusOp(@NotNull FuncPascal6Parser.PlusOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStat(@NotNull FuncPascal6Parser.WhileStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStat(@NotNull FuncPascal6Parser.IfStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#boolOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolOp(@NotNull FuncPascal6Parser.BoolOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idTarget}
	 * labeled alternative in {@link FuncPascal6Parser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdTarget(@NotNull FuncPascal6Parser.IdTargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(@NotNull FuncPascal6Parser.VarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link FuncPascal6Parser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(@NotNull FuncPascal6Parser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(@NotNull FuncPascal6Parser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExpr(@NotNull FuncPascal6Parser.MultExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumExpr(@NotNull FuncPascal6Parser.NumExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusExpr(@NotNull FuncPascal6Parser.PlusExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompOp(@NotNull FuncPascal6Parser.CompOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(@NotNull FuncPascal6Parser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#funcDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDecl(@NotNull FuncPascal6Parser.FuncDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prfExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrfExpr(@NotNull FuncPascal6Parser.PrfExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#prfOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrfOp(@NotNull FuncPascal6Parser.PrfOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inStat}
	 * labeled alternative in {@link FuncPascal6Parser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInStat(@NotNull FuncPascal6Parser.InStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpr(@NotNull FuncPascal6Parser.BoolExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FuncPascal6Parser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(@NotNull FuncPascal6Parser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link FuncPascal6Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(@NotNull FuncPascal6Parser.IdExprContext ctx);
}