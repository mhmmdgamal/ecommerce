

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
                <title>${(title eq null) ? "Login" : title}</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/layout/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/layout/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/layout/css/jquery-ui.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/layout/css/jquery.selectBoxIt.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/layout/css/backend.css" />
	</head>
	<body>