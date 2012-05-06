//package com.zj.retrieval.master.actions;
//
//import java.io.IOException;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import com.zj.retrieval.master.Node;
//import com.zj.retrieval.master.NodeRetrieval;
//import com.zj.retrieval.master.RetrievalResult;
//import com.zj.retrieval.master.UserField;
//import com.zj.retrieval.master.Util;
//import com.zj.retrieval.master.dao.NodeService;
//
//public class RetrievalNodeAction extends HttpServlet{
//
//	private static final long serialVersionUID = 5841303813965092690L;
//	private static Log log = LogFactory.getLog(RetrievalNodeAction.class);
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//	
//		resp.setContentType("text/plain;charset=utf8");
//		req.setCharacterEncoding("utf-8");
//		try {
//			String jSource = req.getParameter("json");
//			log.info("服务端接收到json数据：" + jSource);
//			JSONObject j = new JSONObject(jSource);
//			String startNodeId = j.getString("start_node_id");
//			String selectState = j.getString("select_state");
//			
//			NodeService ndService =  Util.getNodeService();
//			NodeRetrieval ndRetrieval = new NodeRetrieval();
//			
//			Node retrievalNode = ndService.queryNodeById(startNodeId);
//			ndRetrieval.setRetrievalNode(retrievalNode);
//			
//			RetrievalResult result = ndRetrieval.retrieval(selectState);
//			
//			JSONObject jResult = new JSONObject();
//			jResult.put("hasResult", result.hasResult());
//			jResult.put("lastState", result.getLastState());
//			if (result.hasResult()) {
//				// 如果已经有了结果，需要返回下一个节点的所有基本信息
//				JSONArray jResultNodeArray = new JSONArray();
//				for (String resultNodeId : result.getResult()) {
//					JSONObject jResultNode = new JSONObject();
//					Node resultNode = ndService.queryNodeById(resultNodeId);
//					jResultNode.put("nodeId", String.valueOf(resultNode.getId()));
//					jResultNode.put("name", resultNode.getName());
//					jResultNode.put("enName", resultNode.getEnglishName());
//					jResultNode.put("uriName", resultNode.getUri() + "#" + resultNode.getEnglishName());
//					jResultNode.put("uri", resultNode.getUri());
//					jResultNode.put("desc", resultNode.getDesc());
//					jResultNode.put("image", String.format("%1$s/%2$s", config.getOriginalfileFolderName(), resultNode.getImage()));
//					
//					Map<String, String> userfields = resultNode.getUserfields();
//					jResultNode.put("user_fields", UserField.parse(userfields));
//					
//					jResultNodeArray.put(jResultNode);
//				}
//				jResult.put("result", jResultNodeArray);
//			} else {
//				// 如果没有结果，返回下一个特征的基本信息
//				JSONObject jNextAttr = new JSONObject();
//				jNextAttr.put("name", result.getNext().getName());
//				jNextAttr.put("enName", result.getNext().getEnglishName());
//				jNextAttr.put("desc", result.getNext().getDesc());
//				jNextAttr.put("image", String.format("%1$s/%2$s", config.getOriginalfileFolderName(), result.getNext().getImage()));
//				jNextAttr.put("user_fields", UserField.parse(result.getNext().getUserFields()));
//				jResult.put("next", jNextAttr);
//			}
//			String resultString = jResult.toString();
//			log.info("返回数据: " + resultString);
//			resp.getWriter().write(resultString);
//		} catch (JSONException e) {
//			resp.getWriter().write("参数格式错误");
//			log.error("参数格式错误");
//		} catch (Exception ex) {
//			resp.getWriter().write(ex.getMessage());
//			log.error("检索过程出错", ex);
//		}
//	}
//}
