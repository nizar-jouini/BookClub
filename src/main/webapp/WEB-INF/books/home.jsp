<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- New line below to use the JSP Standard Tag Library -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script type="text/javascript" src="/js/app.js"></script>
<!-- change to match your file/naming structure -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
	crossorigin="anonymous"></script>
<title>Book Club</title>
</head>
<body>
	<div class="container">
		<div
			class="d-flex justify-content-between align-items-center mt-3 mb-5">
			<h1 style="color: #36659E;">Welcome, ${user.firstName}!</h1>
			<a href="/logout" class="me-3 fs-4">Logout</a>
		</div>
		<hr />
		<div class="d-flex justify-content-between align-items-start">
			<div class="d-flex flex-column justify-content-start"
				style="width: 49%;">
				<h3 class="mb-5">Add a New Book</h3>
				<form:form action="/books/new" method="post"
					modelAttribute="newBook">
					<div class="d-flex mx-3">
						<div class="w-25"></div>
						<div class="w-75">
							<form:errors path="title" class="text-danger" />
						</div>
					</div>
					<div class="d-flex align-items-baseline mb-3">
						<form:label path="title" class="form-label w-25 fs-4"
							style="top: -15px;">Title</form:label>
						<form:input path="title" type="text" class="form-control w-75" />
					</div>
					<div class="d-flex mx-3">
						<div class="w-25"></div>
						<div class="w-75">
							<form:errors path="description" class="text-danger" />
						</div>
					</div>
					<div class="d-flex align-items-baseline mb-3">
						<form:label path="description" class="form-label w-25 fs-4">Description</form:label>
						<form:textarea path="description" class="form-control w-75"
							style="height: 100px"></form:textarea>
					</div>
					<div class="mt-4 d-flex justify-content-center align-items-center">
						<button type="submit" class="btn btn-primary w-50"
							style="box-shadow: 5px 5px 5px black;">Add</button>
					</div>
				</form:form>
			</div>
			<div class="d-flex flex-column justify-content-start"
				style="width: 40%;">
				<h3 class="mb-5">All Books</h3>
				<div>
					<c:forEach items="${books}" var="book">
						<h5 class="mb-3">
							<a href="/books/${book.id}"><c:out value="${book.title}"></c:out></a><br />
							(added by
							<c:out value="${book.user.firstName} ${book.user.lastName}"></c:out>
							) <br>
							<c:if test="${fn:contains(book.usersWhoFavor, user)}">
								this is one of your favorites
							</c:if>
							<c:if test="${not fn:contains(book.usersWhoFavor, user)}">
								<a href="/books/favorites/${book.id}">Add to Favorites</a>
							</c:if>
						</h5>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>





















