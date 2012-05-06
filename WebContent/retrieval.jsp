<%@page import="com.zj.retrieval.master.Node"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.zj.retrieval.master.RetrievalResult"%>
<%@page import="com.zj.retrieval.master.dao.RetrievalDao"%>
<%@page import="com.zj.retrieval.master.Util"%>
<%@page import="com.zj.retrieval.master.dao.NodeDao"%>

<%@page language="java" 
		contentType="text/html; charset=utf-8"
    	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="common.css" type="text/css" rel="stylesheet" />
<link href="bootstrap-xtra/bootstrap.css" rel="stylesheet">

<style type="text/css">
      body {
        padding-top: 60px;
      }
</style>
<%
	String nodeId = request.getParameter("node_id");
	String selectedState = request.getParameter("selected_state");
	
	boolean hasResult = false;
	
	NodeDao nodeDao = Util.getNodeDao();
	Node startNode = nodeDao.getNodeById(nodeId);
	RetrievalDao retrievalDao = new RetrievalDao(startNode);
	String nodeName = startNode.getName();
	
	List<String> resultNodeIDs = null;
	String attrName = null;
	String attrNameEN = null;
	String attrDesc = null;
	List<String> attrImages = new ArrayList<String>();
	Map<String, String> attrUserFields = null;
	List<Node> rootChilds = null;
	
	if (nodeId != null && !nodeId.isEmpty()) {
		// 进入检索环节
		if (selectedState == null || selectedState.isEmpty()) 
			selectedState = "0"; // 要保证第一位必须有个数，随便是什么数字都可以，这一位以后可以用作其他用途的标志位
			RetrievalResult result = retrievalDao.retrieval(selectedState);
		
		hasResult = result.hasResult();
		if (result.hasResult()) {
			resultNodeIDs = result.getResult();
		} else {
			attrName = result.getNext().getName();
			attrNameEN = result.getNext().getEnglishName();
			attrDesc = result.getNext().getDesc();
			attrImages.add(result.getNext().getImage());
			attrUserFields = result.getNext().getUserFields();
		}
	}
%>
<title>当前节点: <%=nodeName %> 检索进度: <%=selectedState %></title>

<script type="text/javascript" src='jquery-1.7.1.js'></script>
<script src="bootstrap-xtra/js/bootstrap-dropdown.js"></script>
<script>$(function () { prettyPrint(); });</script>
<script src="bootstrap-xtra/js/bootstrap-modal.js"></script>
<script src="bootstrap-xtra/js/bootstrap-alerts.js"></script>
<script src="bootstrap-xtra/js/bootstrap-twipsy.js"></script>
<script src="bootstrap-xtra/js/bootstrap-popover.js"></script>
<script src="bootstrap-xtra/js/bootstrap-scrollspy.js"></script>
<script src="bootstrap-xtra/js/bootstrap-tabs.js"></script>
<script src="bootstrap-xtra/js/bootstrap-buttons.js"></script>
<script type="text/javascript" src='jquery-1.7.1.js'></script>
<!-- Add mousewheel plugin (this is optional) -->
<script type="text/javascript" src="fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>

<!-- Add fancyBox -->
<link rel="stylesheet" href="fancybox/source/jquery.fancybox.css?v=2.0.6" type="text/css" media="screen" />
<script type="text/javascript" src="fancybox/source/jquery.fancybox.pack.js?v=2.0.6"></script>

<!-- Optionally add helpers - button, thumbnail and/or media -->
<link rel="stylesheet" href="fancybox/source/helpers/jquery.fancybox-buttons.css?v=1.0.2" type="text/css" media="screen" />
<script type="text/javascript" src="fancybox/source/helpers/jquery.fancybox-buttons.js?v=1.0.2"></script>
<script type="text/javascript" src="fancybox/source/helpers/jquery.fancybox-media.js?v=1.0.0"></script>

<link rel="stylesheet" href="fancybox/source/helpers/jquery.fancybox-thumbs.css?v=2.0.6" type="text/css" media="screen" />
<script type="text/javascript" src="fancybox/source/helpers/jquery.fancybox-thumbs.js?v=2.0.6"></script>

<script type="text/javascript">
	var selected_state = '<%=selectedState%>';

	function answer(answer) {
		$('#selected_state_post').val(selected_state + answer);
		$('#retrieval_form').submit();
	}
	
	$(function() {
		
	});
</script>

<script type="text/javascript">
		$(document).ready(function() {
			/*
			 *  Simple image gallery. Uses default settings
			 */

			$('.fancybox').fancybox();

			/*
			 *  Different effects
			 */

			// Change title type, overlay opening speed and opacity
			$(".fancybox-effects-a").fancybox({
				helpers: {
					title : {
						type : 'outside'
					},
					overlay : {
						speedIn : 500,
						opacity : 0.95
					}
				}
			});

			// Disable opening and closing animations, change title type
			$(".fancybox-effects-b").fancybox({
				openEffect  : 'none',
				closeEffect	: 'none',

				helpers : {
					title : {
						type : 'over'
					}
				}
			});

			// Set custom style, close if clicked, change title type and overlay color
			$(".fancybox-effects-c").fancybox({
				wrapCSS    : 'fancybox-custom',
				closeClick : true,

				helpers : {
					title : {
						type : 'inside'
					},
					overlay : {
						css : {
							'background-color' : '#eee'
						}
					}
				}
			});

			// Remove padding, set opening and closing animations, close if clicked and disable overlay
			$(".fancybox-effects-d").fancybox({
				padding: 0,

				openEffect : 'elastic',
				openSpeed  : 150,

				closeEffect : 'elastic',
				closeSpeed  : 150,

				closeClick : true,

				helpers : {
					overlay : null
				}
			});

			/*
			 *  Button helper. Disable animations, hide close button, change title type and content
			 */

			$('.fancybox-buttons').fancybox({
				openEffect  : 'none',
				closeEffect : 'none',

				prevEffect : 'none',
				nextEffect : 'none',

				closeBtn  : false,

				helpers : {
					title : {
						type : 'inside'
					},
					buttons	: {}
				},

				afterLoad : function() {
					this.title = 'Image ' + (this.index + 1) + ' of ' + this.group.length + (this.title ? ' - ' + this.title : '');
				}
			});


			/*
			 *  Thumbnail helper. Disable animations, hide close button, arrows and slide to next gallery item if clicked
			 */

			$('.fancybox-thumbs').fancybox({
				prevEffect : 'none',
				nextEffect : 'none',

				closeBtn  : false,
				arrows    : false,
				nextClick : true,

				helpers : {
					thumbs : {
						width  : 50,
						height : 50
					}
				}
			});

			/*
			 *  Media helper. Group items, disable animations, hide arrows, enable media and button helpers.
			*/
			$('.fancybox-media')
				.attr('rel', 'media-gallery')
				.fancybox({
					openEffect : 'none',
					closeEffect : 'none',
					prevEffect : 'none',
					nextEffect : 'none',

					arrows : false,
					helpers : {
						media : {},
						buttons : {}
					}
				});

			/*
			 *  Open manually
			 */

			$("#fancybox-manual-a").click(function() {
				$.fancybox.open('1_b.jpg');
			});

			$("#fancybox-manual-b").click(function() {
				$.fancybox.open({
					href : 'iframe.html',
					type : 'iframe',
					padding : 5
				});
			});

			$("#fancybox-manual-c").click(function() {
				$.fancybox.open([
					{
						href : '1_b.jpg',
						title : 'My title'
					}, {
						href : '2_b.jpg',
						title : '2nd title'
					}, {
						href : '3_b.jpg'
					}
				], {
					helpers : {
						thumbs : {
							width: 75,
							height: 50
						}
					}
				});
			});


		});
	</script>
	<style type="text/css">
		.fancybox-custom .fancybox-skin {
			box-shadow: 0 0 50px #222;
		}
	</style>
</head>
<body>
 <div class="topbar-wrapper" style="z-index: 5;">
    <div class="topbar" data-dropdown="dropdown">
      <div class="topbar-inner">
        <div class="container-fluid">
          <a class="brand" href="#">Fishery Retrieval</a>
          <ul class="nav">
            <li><a href="index.jsp">Home</a></li>
            <li class="active"><a href="retrieval_start.jsp">Common Search</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle">Management</a>
              <ul class="dropdown-menu">
                <li><a href="add_node.jsp">Add Node</a></li>
                <li><a href="query_node.jsp">Query Node</a></li>
                <li class="divider"></li>
                <li><a href="#">Another link</a></li>
              </ul>
            </li>
            <li><a href="#about">About</a></li>
            <li><a href="contact.jsp">Contact</a></li>
          </ul>
          <p class="pull-right">Logged in as <a href="#">username</a></p>
        </div>
      </div>
    </div>
   </div>
  
  <div class="container">  
	<% if (!hasResult) { %>
	<form id='retrieval_form' action="retrieval.jsp" method="post">
		<input id='selected_state_post' type="hidden" name='selected_state'/>
		<input id='node_id' type='hidden' name='node_id' value='<%=nodeId%>'/>
	  <div class="hero-unit">
	    <div class="row">
	      <div class="span-one-third">
	        <h2>Relevant Image:</h2>
	      </div>
	      <div class="span-one-third">
	       <ul class="media-grid">
		    
		     <% for (String imageURL : attrImages) { %>
		      <li>
		        <a class="fancybox-buttons" data-fancybox-group="button" href='<%="images/" + imageURL %>'><img height="230" width="320" align="middle" alt="" src='<%="images/" + imageURL %>'></a>
		      </li>  
		     <% } %>
		    
		   </ul>
		  </div>
		 </div>

	  </div>
		<div><h1><%=attrName%></h1><h3>( <%=attrNameEN %> )</h3></div>
		<table>
			<tr><td>DESCRIPTION:&nbsp;</td><td><%=attrDesc%></td></tr>
		</table>
		
		<div>
			<a><button class="btn success" href='#' onclick='answer(2);'>是</button></a>
			<a><button class="btn danger" href='#' onclick='answer(1);'>否</button></a>
			<a><button class="btn primary" href='#' onclick='answer(3);'>不知道</button></a>
		</div>
	</form>
	<% } %>
	
	<% if (hasResult) { 
			List<Node> nodeResults = new ArrayList<Node>();
			for (String id : resultNodeIDs) {
				nodeResults.add(nodeDao.getNodeById(id));
			}
	%>
			<div>结果（<%=nodeResults.size() %>个）：</div>
			<table>
				<% for (Node nd : nodeResults) { %>
				<tr>
					<td><%=nd.getName() %></td>
					<td><%=nd.getEnglishName() %></td>
					<td><a href='retrieval.jsp?node_id=<%=nd.getId()%>'>继续从这里检索</a></td>
				</tr>
				<% } %>
			</table>
	<% } %>
  </div>
</body>
</html>