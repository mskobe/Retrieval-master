package com.zj.retrieval.master.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.zj.retrieval.master.Attribute;
import com.zj.retrieval.master.Matrix;
import com.zj.retrieval.master.Node;
import com.zj.retrieval.master.RetrievalResult;

public class RetrievalDao {
	private Node retrievalNode;
	private List<Integer> mappingRows;
	private boolean hasInited = false;
	
	public RetrievalDao(Node retrievalNode) {
		this.retrievalNode = retrievalNode;
		this.mappingRows = new ArrayList<Integer>();
		initMappingRows();
	}
	
	private void initMappingRows() {
		// 初始化的时matchRows中的值就是matrix中所有的行号
		if (hasInited)
			return;
		if (mappingRows == null)
			mappingRows = new ArrayList<Integer>();
		// 因为NodeRetrieval是单例，????每次都要先清空状??
		mappingRows.clear();
		for (int i = 0; i < retrievalNode.getRetrievalDataSource().getChildNodes().size(); i++) {
			mappingRows.add(i);
		}
		hasInited = true;
	}
	
	public RetrievalResult retrieval(String selectState) {
		// selectState的第1个值代表当前节点的id，从下标1开始才是answer
		for (int i = 1; i < selectState.length(); i++) {
			int perAnswer = Integer.valueOf(selectState.substring(i, i + 1));
			// 如果回答unknown，轮空本次，这可能将导致最终答案出现多个
			if (perAnswer == Attribute.UNKNOW)
				continue;
			Matrix matrix = retrievalNode.getRetrievalDataSource().getMatrix();
			Iterator<Integer> iter = mappingRows.iterator();
			// i对应于selectState中对第i个特征的回答，同时对应于matrix中第i-1??
			int[] currComparedAttrCol = matrix.getCol(i - 1);
			while (iter.hasNext()) {
				if (currComparedAttrCol[iter.next()] == perAnswer)
					continue;
				else
					iter.remove();
			}
		}
		// 这里判断该返回什么结果
		RetrievalResult result = new RetrievalResult();
		if ((selectState.length() - 1) >= retrievalNode.getRetrievalDataSource().getAttributes().size()
				|| mappingRows.size() <= 1) {
			// 1.????自然结束的情况，即所有特征都已询问过
			// 2.可能还没有询问过全部的特征，但是已经确定没有匹配的子结点??
			// 3.匹配的子结点列表中只剩下????元素??
			result.hasResult(true);
			result.setResult(getMappedNode());
		} else {
			// ????还没有完成，返回下一????询问的特??
			result.hasResult(false);
			int nextAttributeId = selectState.length() - 1;
			result.setNext(retrievalNode.getRetrievalDataSource().getAttributes().get(nextAttributeId));
		}
		result.setLastState(selectState);
		return result;
	}
	
	public void setRetrievalNode(Node retrievalNode) {
		this.retrievalNode = retrievalNode;
		initMappingRows();
	}

	private List<String> getMappedNode() {
		List<String> mappedNodes = new ArrayList<String>();
		for (int mappedRow : mappingRows) {
			String mappedNodeId = retrievalNode.getRetrievalDataSource().getChildNodes().get(mappedRow);
			mappedNodes.add(mappedNodeId);
		}
		return mappedNodes;
	}
}
