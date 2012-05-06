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

    <script type="text/javascript" src="jquery-1.7.1.js"></script>
    <script src="bootstrap-xtra/js/bootstrap-dropdown.js"></script>
    <script>$(function () { prettyPrint(); });</script>
    <script src="bootstrap-xtra/js/bootstrap-modal.js"></script>
    <script src="bootstrap-xtra/js/bootstrap-alerts.js"></script>
    <script src="bootstrap-xtra/js/bootstrap-twipsy.js"></script>
    <script src="bootstrap-xtra/js/bootstrap-popover.js"></script>
    <script src="bootstrap-xtra/js/bootstrap-scrollspy.js"></script>
    <script src="bootstrap-xtra/js/bootstrap-tabs.js"></script>
    <script src="bootstrap-xtra/js/bootstrap-buttons.js"></script>
    
<title>Contact</title>
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
                <li><a href="query_node.jsp">Query</a></li>
                <li class="divider"></li>
                <li><a href="#">Another link</a></li>
              </ul>
            </li>
            <li><a href="#about">About</a></li>
            <li class="active"><a href="contact.jsp">Contact</a></li>
          </ul>
          <p class="pull-right">Logged in as <a href="#">username</a></p>
        </div>
      </div>
    </div>
   </div>
   
<div class="row">
  <div class="span6">
     <h1>Designed by:</h1><br />
     <address>
        <strong>Shanghai Ocean University</strong><br />
          999 Hucheng Huan Road, Nanhui District<br />
          Shanghai, China 201306<br />
     </address>
  </div>   
  <div class="span5">
     <h1>Email:</h1><br />
     <address>
       <a mailto="">qmyu@shou.edu.com</a>
     </address>
  </div>
  <div class="span5">
     <h1>Tel:</h1><br />
     <address>
        (021) 61900622
     </address>
  </div>
</div>


</body>
</html>