package com.zj.retrieval.master.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import com.zj.retrieval.master.Attribute;
import com.zj.retrieval.master.AttributeSelector;
import com.zj.retrieval.master.DetailType;
import com.zj.retrieval.master.Matrix;
import com.zj.retrieval.master.Node;

public class NodeDao {
	
	private SimpleJdbcTemplate sqlclient;
	private static Log log = LogFactory.getLog(NodeDao.class);
	
	public List<Node> getAllNodeAsBrief() {
		String sql = "select `id`, `images` as imagesStr, `name`, `parent_id` as parentId, `detail_type` as detailType from `fish`";
		ParameterizedRowMapper<Node> rm = 
				ParameterizedBeanPropertyRowMapper.newInstance(Node.class);
		List<Node> queryResult = sqlclient.query(sql, rm);
		return queryResult;
	}
	
	public boolean deleteNode(Node nd) {
		try {
			// ԭ�ȵ�ndֻ��id��Ϣ�����ڸ���id�����ݿ���ȡ����node��������Ϣ
			nd = getNodeById(nd.getId());
			// �ҵ����ĸ��ڵ�
			Node parent = getNodeById(nd.getParentId());
			
			// ��nd���丸�ڵ��е���Ϣɾ�������Ӹ��ڵ���ӽ���б���ɾ��nd
			RetrievalDataSource dataSource = parent.getRetrievalDataSource();
			int row = dataSource.getChildNodes().indexOf(nd.getId());
			dataSource.getChildNodes().remove(nd.getId());
			// ɾ�����ڵ�����е������Ϣ
			dataSource.getMatrix().removeRow(row);
			// ���¸��ڵ��owl
			parent.setOwl(Node.getOwlFromNode(parent, sqlclient));
			// �����ڵ�д�����ݿ�
			String sqlUpdateParentNode = "UPDATE `fish` SET `owl`=:owl WHERE `id`=:id";
			SqlParameterSource paramUpdateParentNode = new BeanPropertySqlParameterSource(parent);
			if (sqlclient.update(sqlUpdateParentNode, paramUpdateParentNode) != 1) {
				throw new Exception("���¸���ʱʧ��@NodeService.delNode()"); // Rollback
			}
			
			// ��ʼ�����ݿ���ɾ��nd
			String sql = "DELETE FROM `fish` where id=?";
			if (sqlclient.update(sql, nd.getId()) != 1)
				throw new Exception(String.format("ɾ���ڵ�[id=%1$s]ʱʧ��", nd.getId()));
			else
				return true;
		} catch (Exception ex) {
			log.error("delNode()��������", ex);
			return false;
		}
	}

	public boolean updateNode(Node nd) throws Exception {
		try {
			nd.setOwl(Node.getOwlFromNode(nd, sqlclient));
			String sql = "update fish " +
					"set `uri_name` = :uriName, " +
					"`name` = :name, " +
					"`images` = :imagesStr, " +
					"`name_en` = :englishName, " +
					"`parent_id` = :parentId, " +
					"`owl` = :owl, " +
					"`uri` = :uri, " +
					"`detail_type` = :detailType " +
					"where `id` = :id";
			SqlParameterSource param = new BeanPropertySqlParameterSource(nd);
			if (sqlclient.update(sql, param) != 1) {
				throw new Exception("���½ڵ㷵�ؽ����Ϊ1"); // Rollback
			}
			return true;
		} catch (Exception ex) {
			log.error(String.format("��ѯ�ڵ�ʱ����[id=%1$s]", nd.getId()), ex);
			throw new Exception("���½ڵ�ʱ����", ex);
		}
	}
	
	public boolean updateRootNode(Node root) throws Exception {
		try {
			root.setOwl(Node.getOwlFromNode(root, sqlclient));
			String sql = "update `fish` set `uri_name`=:uriName, `name`=:name, `name_en`=:englishName, `images`=:imagesStr, " +
					"`owl`=:owl, `uri`=:uri where `id`=:id";
			SqlParameterSource param = new BeanPropertySqlParameterSource(root);
			int result = sqlclient.update(sql, param);
			
			if (result != 1) throw new Exception("����rootNodeʱ���ؽ��������1.");
			return true;
		} catch (Exception ex) {
			log.error(String.format("��ѯ�ڵ�ʱ����[id=%1$s]", root.getId()), ex);
			throw new Exception("���½ڵ�ʱ����", ex);
		}
		
	}
		
	public Node getNodeById(String id) throws Exception {
		try {
			String sql = "select `id`, `uri_name` as uriName, `name`, `images` as imagesStr, " +
					"`name_en` as englishName, `parent_id` as parentId, " +
					"`owl`, `uri`, `detail_type` as detailType, `contact` from `fish` where `id`=?";
			ParameterizedRowMapper<Node> rowMapper = 
				ParameterizedBeanPropertyRowMapper.newInstance(Node.class);
			Node result = sqlclient.queryForObject(sql, rowMapper, id);
			if (result.getDetailType() == DetailType.FULL) {
				Node.parseNodeFromOWL(result);
			}
			return result;
		} catch (EmptyResultDataAccessException ex) {
			throw new Exception(String.format("IDΪ%1$s�Ľڵ㲻���ڣ�", id));
		} catch (Exception e) {
			log.error(String.format("��ѯ�ڵ�ʱ����[id=%1$s]", id), e);
			throw new Exception("��ѯ�ڵ�ʱ����@NodeServiceImpl.findNodeById()", e);
		}
	}

	public Node getNodeByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public AttributeSelector getAttributeSelector(Node nd) {
		List<Integer> resultData = new ArrayList<Integer>();
		List<Attribute> attrs = nd.getRetrievalDataSource().getAttributes();
		for (int i = 0; i < attrs.size(); i++) {
			resultData.add(i);
		}
		return new AttributeSelector(resultData);
	}

	public void addNode(Node newNode, Node parentNode, AttributeSelector as) {
		try {
			// ����parent��matrix����
			log.info("���¸��ڵ����������");
			Matrix matrix = parentNode.getRetrievalDataSource().getMatrix();
			//   ���޸��У���newNode��parentNode���������ԵĽ���ƥ��
			//   ���matrixΪ��[����������������]���������������������˵������Ӷ�����
			//   ������û����ӣ���Ϊû����֪����������ƥ��
			int[] newRow = new int[matrix.getColSize()];
			for(int i = 0; i < newRow.length; i++)
				newRow[i] = as.getAttributeMapping().get(i) ? Attribute.YES : Attribute.NO;
			matrix.addRow(newRow, 0, newRow.length);

			//   ���޸��У���parentNode��Ӵ���newNodeʱһ����ӵ�������
			//   ����������Ե�ͬʱ�������Լ���parentNode��attribute�б�
			List<Attribute> parentAttributes = parentNode.getRetrievalDataSource().getAttributes();
			for(Attribute attr : as.getNewAttributeMapping().keySet()) {
				// matrix����Ϊ�գ���վ������1����Ҫ�ĳ���ʼ����1
				int[] newCol = matrix.getRowSize() == 0 ? new int[1] : new int[matrix.getRowSize()];
				for(int j = 0; j < newCol.length; j++) {
					newCol[j] = (j != newCol.length - 1 ? 0 : 
						(as.getNewAttributeMapping().get(attr) ? Attribute.YES : Attribute.NO));
				}
				matrix.addCol(newCol, 0, newCol.length);
				// ͬʱ����parentNode��attribte�б�
				parentAttributes.add(attr);
			}
			
			// ����������newNode��owl�ַ���
			log.info("�����������½ڵ��owl�ַ���");
			newNode.setOwl(Node.getOwlFromNode(newNode, sqlclient));
			
			// �����ݿ��в���newNode���һ���Զ����ɵ�idֵ
			// ��idֵ���õ�newNode��
			log.info("���½ڵ�д�����ݿ�");
			if (newNode.getId().isEmpty() || newNode.getId() == null) {
				newNode.setId(UUID.randomUUID().toString());
			}
			String sqlInsertNewNode = "insert into fish(`id`, `uri_name`, `name`, `images`, " +
					"`name_en`, `parent_id`, `owl`, `uri`) values(:id, :uriName, :name, :imagesStr, " +
					":englishName, :parentId, :owl, :uri)";
			SqlParameterSource paramInsertNewNode = new BeanPropertySqlParameterSource(newNode);
			if (sqlclient.update(sqlInsertNewNode, paramInsertNewNode) != 1) {
				throw new Exception("����ڵ�ʱʧ��@NodeService.addNode()"); // Rollback
			}
	
			// ��newNode��idֵ����parentNode��childNodes�б���
			log.info("���¸��ڵ���ӽ���б�");
			parentNode.getRetrievalDataSource().getChildNodes().add(newNode.getId());
			// ����parentNode��owl�ַ���
			log.info("���¸��ڵ��owl�ַ���");
			parentNode.setOwl(Node.getOwlFromNode(parentNode, sqlclient));
					
			// ��parentNode����д�����ݿ�
			// ����ֻ�޸���parentNode��owl��Ϣ����������ֻ����owl�ֶ�
			log.info("�����ڵ�д�����ݿ�");
			String sqlUpdateParentNode = "update `fish` set `owl`=:owl where id=:id";
			SqlParameterSource paramUpdateParentNode = new BeanPropertySqlParameterSource(parentNode);
			if (sqlclient.update(sqlUpdateParentNode, paramUpdateParentNode) != 1) {
				throw new Exception("���¸���ʱʧ��@NodeService.addNode()"); // Rollback
			}
			
		} catch (Exception ex) {
			log.error("NodeService.addNode()������������", ex);
			throw new RuntimeException("NodeService.addNode()������������", ex);
		}
	}

	public void setDataSource(DataSource dataSource) {
		sqlclient = new SimpleJdbcTemplate(dataSource);
	}

	public void addRootNode(Node rootNode) {
		try {

			// 1.���rootNode
			log.info("��ʼ����rootNode...");
			if (rootNode.getId().isEmpty() || rootNode.getId() == null) {
				rootNode.setId(UUID.randomUUID().toString());
			}
			rootNode.setOwl(Node.getOwlFromNode(rootNode, sqlclient));
			String sql = "insert into `fish`(`id`, `uri_name`, `name`, `name_en`, `images`, " +
					"`parent_id`, `owl`, `uri`) values(:id, :uriName, :name, :englishName, :imagesStr, :parentId, :owl, :uri)";
			SqlParameterSource param = new BeanPropertySqlParameterSource(rootNode);
			int result = sqlclient.update(sql, param);
			
			if (result != 1) throw new Exception("����rootNodeʱ���ؽ��������1.");
			
			// 2.����abstractRootNode[id="virtual_node"]�ڵ��childNodes����
			log.info("��ʼ����abstractRootNode���ӽڵ��б�...");
			String vtrlNodeQuerySQL = "select `owl` from `fish` where `id`=?";
			List<Node> vrtlNodeQueryResult = sqlclient.query(vtrlNodeQuerySQL, new ParameterizedRowMapper<Node>() {

				public Node mapRow(ResultSet rs, int rowNum) throws SQLException {
					Node result = null;
					try {
						String owl = rs.getString("owl");
						if(owl == null || owl.isEmpty()) {
							log.error("VirtualNode���ڣ�����owl��ϢΪ�գ�����ô���ܣ�");
							return null;
						}
						result = Node.parseVirtualNodeFromOWL(owl);
					} catch (Exception e) {
						log.error("��������ڵ��е�owlʱ����");
						return null;
					}
					return result;
				}
			}, Node.VIRTUAL_NODE_NAME);
			
			Node virtual_node = null;
			if (vrtlNodeQueryResult == null) {
				log.error("��ѯ����ڵ�ʱ����");
				throw new Exception("��ѯ����ڵ�ʱ����");
			}
			if (vrtlNodeQueryResult.size() == 0) {
				// ��˵��id="VirtualNode"�ļ�¼������
				// �������ͨ��ֻ�����ڳ����һ������ʱ
				// ����һ���µ�����ڵ㣬����id="VirtualNode"����д�����ݿ�
				log.info("��Ϊvirtual_node��¼�����ڣ����½�һ��virtual_node");
				virtual_node = new Node();
				int insert_vrtl_node_result = sqlclient.getJdbcOperations().update(
						"INSERT INTO `fish`(" +
						"`id`, `uri_name`, `name`, `name_en`, `parent_id`, `owl`, `uri`) " +
						"VALUES('virtual_node','','','',-1,'','');"
				);
				if (insert_vrtl_node_result != 1) {
					log.error("����VirtualNodeʧ�ܡ�");
					throw new Exception("����VirtualNodeʧ�ܡ�");
				}
				log.info("�½���VirtualNode�������ݿ�ɹ�");
			} else {
				virtual_node = vrtlNodeQueryResult.get(0);
			}
			
			virtual_node.getRetrievalDataSource().getChildNodes().add(rootNode.getId());
	
			// �ؽ�VirtualNode��owl�ֶ�
			virtual_node.setOwl(Node.getOwlFromNode(virtual_node, sqlclient));
			log.info("�����µ�owl�ַ�����" + virtual_node.getOwl());
			
			// ��VirtualNodeд�����ݿ�
			log.info("��ʼ��VirtualNodeд������...");
			String update_vrtl_node_sql = "update `fish` set `owl`=:owl where `id`='virtual_node'";
			SqlParameterSource vrtl_node_param = new BeanPropertySqlParameterSource(virtual_node);
			sqlclient.update(update_vrtl_node_sql, vrtl_node_param);
			log.info("VirtualNodeд�����ݿ�ɹ�");
			
		} catch (Exception ex) {
			log.error("���rootNodeʱ�����������񽫻ع�", ex);
			throw new RuntimeException("���rootNodeʱ��������", ex); // �ع�
		}
	}

	// ����ֻ����������Ϣ�Ľڵ�
	public void addNodeBrief(Node newNode, Node parentNode, AttributeSelector as) {
		try {
			// ����parent��matrix����
			log.info("���¸��ڵ����������");
			Matrix matrix = parentNode.getRetrievalDataSource().getMatrix();
			//   ���޸��У���newNode��parentNode���������ԵĽ���ƥ��
			//   ���matrixΪ��[����������������]���������������������˵������Ӷ�����
			//   ������û����ӣ���Ϊû����֪����������ƥ��
			int[] newRow = new int[matrix.getColSize()];
			for(int i = 0; i < newRow.length; i++)
				newRow[i] = as.getAttributeMapping().get(i) ? Attribute.YES : Attribute.NO;
			matrix.addRow(newRow, 0, newRow.length);

			//   ���޸��У���parentNode��Ӵ���newNodeʱһ����ӵ�������
			//   ����������Ե�ͬʱ�������Լ���parentNode��attribute�б�
			List<Attribute> parentAttributes = parentNode.getRetrievalDataSource().getAttributes();
			for(Attribute attr : as.getNewAttributeMapping().keySet()) {
				// matrix����Ϊ�գ���վ������1����Ҫ�ĳ���ʼ����1
				int[] newCol = matrix.getRowSize() == 0 ? new int[1] : new int[matrix.getRowSize()];
				for(int j = 0; j < newCol.length; j++) {
					newCol[j] = (j != newCol.length - 1 ? 0 : 
						(as.getNewAttributeMapping().get(attr) ? Attribute.YES : Attribute.NO));
				}
				matrix.addCol(newCol, 0, newCol.length);
				// ͬʱ����parentNode��attribte�б�
				parentAttributes.add(attr);
			}
			
			// ����������newNode��owl�ַ���
//			log.info("�����������½ڵ��owl�ַ���");
//			newNode.setOwl(Node.getOwlFromNode(newNode, sqlclient));
			
			log.info("���½ڵ�д�����ݿ�");
			// ע�⣬brief�ڵ��IDֵ���ɿͻ����ṩ�ģ�����
			// Ϊ�½ڵ�detailType����ֵ: brief
			newNode.setDetailType(DetailType.BRIEF);
			String sqlInsertNewNode = "insert into fish(`id`, `name`, `name_en`, `parent_id`, `contact`, `detail_type`) values(" +
					                                    ":id, :name, :englishName, :parentId, :contact, :detailType)";
			SqlParameterSource paramInsertNewNode = new BeanPropertySqlParameterSource(newNode) ;
			if (sqlclient.update(sqlInsertNewNode, paramInsertNewNode) != 1) {
				throw new Exception("����ڵ�ʱʧ��@NodeService.addNode()"); // Rollback
			}
	
			// ��newNode��idֵ����parentNode��childNodes�б���
			log.info("���¸��ڵ���ӽ���б�");
			parentNode.getRetrievalDataSource().getChildNodes().add(newNode.getId());
			// ����parentNode��owl�ַ���
			log.info("���¸��ڵ��owl�ַ���");
			parentNode.setOwl(Node.getOwlFromNode(parentNode, sqlclient));
					
			// ��parentNode����д�����ݿ�
			// ����ֻ�޸���parentNode��owl��Ϣ����������ֻ����owl�ֶ�
			log.info("�����ڵ�д�����ݿ�");
			String sqlUpdateParentNode = "update `fish` set `owl`=:owl where id=:id";
			SqlParameterSource paramUpdateParentNode = new BeanPropertySqlParameterSource(parentNode);
			if (sqlclient.update(sqlUpdateParentNode, paramUpdateParentNode) != 1) {
				throw new Exception("���¸���ʱʧ��@NodeService.addNode()"); // Rollback
			}
			
		} catch (Exception ex) {
			log.error("NodeService.addNode()������������", ex);
			throw new RuntimeException("NodeService.addNode()������������", ex);
		}
	}


}
