package board;

public class BoardConstants {
	public static final int BOX_SIZE = 3;
	public static final int UNIT_SIZE = BOX_SIZE * BOX_SIZE;
	public static final int UNIT_COUNT = UNIT_SIZE * 3;
	public static final int GRID_SIZE = UNIT_SIZE * UNIT_SIZE;	
	public static final int BITMASK = ((1 << (UNIT_SIZE + 1)) - 2);
	
	private static CellRef[] m_cellReference;
	private static int[] m_groups;
	
	static {
		m_cellReference = new CellRef[GRID_SIZE];
		m_groups = new int[UNIT_SIZE * UNIT_COUNT];
		
		for(int i = 0; i < GRID_SIZE; i++)
		{
			int row = i / UNIT_SIZE;
			int col = i % UNIT_SIZE;
			int box = ((col / BOX_SIZE) * BOX_SIZE) + (row / BOX_SIZE);
			int boxIndex = (col % BOX_SIZE) + (row % BOX_SIZE) * BOX_SIZE;
			
			CellRef ref = new CellRef(row, col, box);

			m_cellReference[i] = ref;
			m_groups[(row + UNIT_SIZE*0) * UNIT_SIZE + col]  = i;
			m_groups[(col + UNIT_SIZE*1) * UNIT_SIZE + row]  = i;
			m_groups[(box + UNIT_SIZE*2) * UNIT_SIZE + boxIndex] = i;
		}
	}
	
	public static int iterate(int group, int cell) {
		return m_groups[group * UNIT_SIZE + cell];
	}
	
	public static CellRef getCellRef(int cell) {
		return m_cellReference[cell];
	}
}
