package board;

public class CellRef {

	public CellRef(int row, int col, int box) {
		this.row = row;
		this.col = col;
		this.box = box;
	}

	public final int row;
	public final int col;
	public final int box;
}
