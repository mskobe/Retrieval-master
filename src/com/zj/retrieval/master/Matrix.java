package com.zj.retrieval.master;

import java.util.Arrays;

public class Matrix {
	private int[][] array;

	public Matrix(int[][] array) {
		this.array = array;
	}
	
	public Matrix() {
		array = new int[0][0];
	}
	
	public static Matrix create(int rowSize, int colSize) {
		int[][] array = new int[rowSize][colSize];
		return new Matrix(array);
	}
	
	public boolean isEmpty() {
		return array.length == 0;
	}

	public int[][] getArray() {
		return array;
	}

	public void setArray(int[][] data) {
		this.array = data;
	}

	public void addRow(int[] newRow, int start, int len) {
		
		if (newRow.length == 0)
			return;
		
		if (newRow.length < getColSize())
			throw new RuntimeException("新行元素个数小于矩阵中行的列数@Matrix.addRow()");
		
		int rowSize = getRowSize();
		int colSize = getColSize();
		int newColSize = newRow.length;
		
		int[][] newMatrix = new int[rowSize + 1][newColSize];
		
		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < newColSize; col++) {
				// 如果新行的元素少于原行的元素，则在新行中补零
				if (col < colSize) {
					// 说明在指针指在原始数据位置上
					newMatrix[row][col] = array[row][col];
				} else {
					// 说明指针指在原先没有数据的位置上，这些位置应该补零
					newMatrix[row][col] = 0;
				}
			}
		}

		System.arraycopy(newRow, 0, newMatrix[rowSize], 0, newColSize);
		array = newMatrix;
	}

	public void addCol(int[] newCol, int start, int len) {
		if (newCol.length != getRowSize() & getRowSize() != 0) {
			throw new RuntimeException("The length of new col wrong.");
		}
		
		// 如果当前矩阵是一个空的矩阵，则特殊处理
		if (getRowSize() == 0) {
			int[][] _data = new int[len][1];
			for(int i = 0; i < len; i++) {
				_data[i][0] = newCol[i];
			}
			this.array = _data;
			return;
		}
		
		int[][] _data = new int[getRowSize()][getColSize() + 1];
		for (int i = 0; i < getRowSize(); i++) {
			int[] row = getRow(i);
			System.arraycopy(row, 0, _data[i], 0, row.length);
			_data[i][row.length] = newCol[i];
		}
		this.array = _data;
	}

	public void setValue(int row, int col, int value) {
		this.array[row][col] = value;
	}

	public int[] getRow(int index) {
		return this.array[index];
	}

	public int[] getCol(int index) {
		int[] result = new int[getRowSize()];
		for (int row = 0; row < getRowSize(); row++) {
			result[row] = array[row][index];
		}
		return result;
	}

	public int getValue(int row, int col) {
		return array[row][col];
	}

	public int getRowSize() {
		return array.length;
	}

	public int getColSize() {
		return array.length == 0 ? 0 : array[0].length;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Matrix: \n");
		for (int[] row : array) {
			sb.append(Arrays.toString(row));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public void removeRow(int row) {
		Matrix m = new Matrix();
		for (int i = 0; i < getRowSize(); i++) {
			if (i == row)
				continue;
			else 
				m.addRow(array[i], 0, getColSize());
		}
		array = m.array;
	}

}
