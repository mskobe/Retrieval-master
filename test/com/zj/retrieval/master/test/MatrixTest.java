package com.zj.retrieval.master.test;


import org.junit.Before;
import org.junit.Test;

import com.zj.retrieval.master.Matrix;

public class MatrixTest {

	@Test
	public void addRowTest() {
		Matrix mx = new Matrix();
		
		System.out.println(mx);
//		mx.addRow(new int[] {7, 8, 9}, 0, 3);
//		mx.addRow(new int[] {1, 2, 3}, 0, 3);
//		mx.addRow(new int[] {9, 8, 7}, 0, 3);
//		mx.addRow(new int[] {0}, 0, 1);
		mx.addCol(new int[] {1}, 0, 1);
		mx.addCol(new int[] {4}, 0, 1);
		mx.addRow(new int[] {7, 2}, 0, 2);
		System.out.println(mx);
	}
	
	@Test
	public void compositeTest() {
		int[][] data = new int[][] { { 1, 2 }, { 3, 4 } };
		Matrix mx = new Matrix(data);
		System.out.println(mx);

		int[] newRow = new int[] { 5, 6 };
		mx.addRow(newRow, 0, newRow.length);
		System.out.println(mx);

		int[] newCol = new int[] { 8, 8, 8 };
		mx.addCol(newCol, 0, newCol.length);
		System.out.println(mx);
	}

}
