<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.zj.retrieval.master.actions.XMLUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.zj.retrieval.master.Attribute"%>
<%@page import="java.util.List"%>
<%@page import="com.zj.retrieval.master.DetailType"%>
<%@page import="com.zj.retrieval.master.Node"%>
<%@page import="com.zj.retrieval.master.Util"%>
<%@page import="com.zj.retrieval.master.dao.NodeDao"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="bootstrap-xtra/bootstrap.css" rel="stylesheet">
<style type="text/css">
      body {
        padding-top: 60px;
      }
</style>
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
<title>查看物种详细信息</title>
</head>
<body>
 <div class="topbar-wrapper" style="z-index: 5;">
    <div class="topbar" data-dropdown="dropdown">
      <div class="topbar-inner">
        <div class="container-fluid">
          <a class="brand" href="#">Fishery Retrieval</a>
          <ul class="nav">
            <li><a href="index.jsp">Home</a></li>
            <li><a href="retrieval_start.jsp">Common Search</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle">Management</a>
              <ul class="dropdown-menu">
                <li><a href="add_node.jsp">Add Node</a></li>
                <li><a href="query_node.jsp">Query Node</a></li>
                <li class="divider"></li>
                <li class="active"><a href="view_node_detail.jsp">View Detail</a></li>
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
<table width="95%" style='margin: 0 auto;'>
<%
	String name = "";
	String name_en = "";
	String parent_id = "";
	String desc = "";
	String owl = "";
	String label = "";
	String contact = "";
	String uri = "";
	String uri_name = "";
	List<String> images = new ArrayList<String>();
	List<String> child_nodes = new ArrayList<String>();
	List<Attribute> attrs = new ArrayList<Attribute>();
	String node_id = request.getParameter("node_id");
	Map<String, String> user_filed = new HashMap<String, String>();
	
	try {	
		NodeDao ns = Util.getNodeDao();
		
		Node nd = ns.getNodeById(node_id);
		name = nd.getName();
		name_en = nd.getEnglishName();
		parent_id = nd.getParentId();
		
		boolean isFull = (nd.getDetailType() == DetailType.FULL);
		if (isFull) {
			images = nd.getImages();
			uri = nd.getUri();
			uri_name = nd.getUriName();
			desc = nd.getDesc();
			owl = Util.html(XMLUtil.format(nd.getOwl(), 4));
			label = nd.getLabel();
			child_nodes = nd.getRetrievalDataSource().getChildNodes();
			attrs = nd.getRetrievalDataSource().getAttributes();
			user_filed = nd.getUserfields();
		} else {
			contact = nd.getContact();
		}
		
		
	} catch (Exception ex) {
		out.print(ex.getMessage());
	}
%>
<tr><td>ID: </td><td><%=node_id%></td></tr>
<tr><td>NAME: </td><td><%=name%></td></tr>
<tr><td>NAME_EN: </td><td><%=name_en%></td></tr>
<tr><td>DESC:</td><td><%=desc%></td></tr>
<tr><td>OWL: </td><td><%=owl%></td></tr>
<tr><td>URI: </td><td><%=uri%></td></tr>
<tr><td>URI_NAME: </td><td><%=uri_name%></td></tr>
<tr><td>PARENT_ID: </td><td><%=parent_id%></td></tr>
<tr><td>LABEL: </td><td><%=label%></td></tr>
<tr><td>CONTACT: </td><td><%=contact%></td></tr>
<tr><td colspan='2'>====== User Field ======</td></tr>
<%
	for (Entry<String, String> entry : user_filed.entrySet()) {
%>
<tr><td>KEY: <%=entry.getKey()%></td><td>VALUE: <%=entry.getValue()%></td></tr>
<%	}  %>
<tr><td colspan='2'>====== Images ======</td></tr>
<tr>
 <%
	for (String image_url : images) {
 %>
    <td>
		<a class="fancybox-buttons" data-fancybox-group="button" href='<%=image_url%>'><img height="230" width="320" align="middle" alt="" src='<%=image_url %>'></a>
    </td> 
 <%
	}
 %>
</tr>
<tr><td colspan='2'>====== Attributes ======</td></tr>
<% for (Attribute attr : attrs) { %>
<tr><td>attr_name</td><td><%=attr.getName() %></td></tr>
<tr><td>attr_name_en</td><td><%=attr.getEnglishName() %></td></tr>
<tr><td>attr_desc</td><td><%=attr.getDesc() %></td></tr>
<tr><td>attr_image</td><td><img src='<%="images/" + attr.getImage() %>'/></td></tr>
<tr><td colspan='2'>====== ====== ======</td></tr>
<% for (Entry<String, String> entry : attr.getUserFields().entrySet()) { %>
<tr><td>KEY: <%=entry.getKey() %></td><td>VALUE: <%=entry.getValue() %></td></tr>
<% } %>
<% } %>
<tr><td></td><td></td></tr>
<tr><td></td><td></td></tr>
<tr><td></td><td></td></tr>
<tr><td></td><td></td></tr>
</table>

</body>
</html>