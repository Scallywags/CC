// Generated from Building.g4 by ANTLR 4.4
package pp.block5.cc.antlr;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BuildingParser}.
 */
public interface BuildingListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BuildingParser#floor}.
	 * @param ctx the parse tree
	 */
	void enterFloor(@NotNull BuildingParser.FloorContext ctx);
	/**
	 * Exit a parse tree produced by {@link BuildingParser#floor}.
	 * @param ctx the parse tree
	 */
	void exitFloor(@NotNull BuildingParser.FloorContext ctx);
	/**
	 * Enter a parse tree produced by {@link BuildingParser#building}.
	 * @param ctx the parse tree
	 */
	void enterBuilding(@NotNull BuildingParser.BuildingContext ctx);
	/**
	 * Exit a parse tree produced by {@link BuildingParser#building}.
	 * @param ctx the parse tree
	 */
	void exitBuilding(@NotNull BuildingParser.BuildingContext ctx);
	/**
	 * Enter a parse tree produced by {@link BuildingParser#room}.
	 * @param ctx the parse tree
	 */
	void enterRoom(@NotNull BuildingParser.RoomContext ctx);
	/**
	 * Exit a parse tree produced by {@link BuildingParser#room}.
	 * @param ctx the parse tree
	 */
	void exitRoom(@NotNull BuildingParser.RoomContext ctx);
}