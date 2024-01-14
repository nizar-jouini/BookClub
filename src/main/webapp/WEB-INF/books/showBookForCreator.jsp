<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- New line below to use the JSP Standard Tag Library -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>Details Page For Creator</title>
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
				<form:form action="/books/${oneBook.id}/edit" method="post"
					modelAttribute="updatedBook">
					<input type="hidden" name="_method" value="put">
					<div class="mb-3">
						<form:errors class="text-danger" path="title" />
						<form:input type="text" path="title" value="${oneBook.title}"
							class="form-control fs-3" />
					</div>
					<h5>
						Added by:
						<c:out value="${oneBook.user.firstName}"></c:out>
						<br> Added on:
						<fmt:formatDate type="date" dateStyle="medium"
							value="${oneBook.createdAt}" />
						@
						<fmt:formatDate type="time" timeStyle="short"
							value="${oneBook.createdAt}" />
						<br> Last updated on:
						<fmt:formatDate type="date" dateStyle="medium"
							value="${oneBook.updatedAt}" />
						@
						<fmt:formatDate type="time" timeStyle="short"
							value="${oneBook.updatedAt}" />
						<div class="d-flex mx-3">
							<div class="w-25"></div>
							<div class="w-75">
								<form:errors path="description" class="text-danger" />
							</div>
						</div>
						<div class="d-flex align-items-start mb-3">
							<form:label path="description" class="form-label w-25 fs-5">Description:</form:label>
							<form:input path="description" value="${oneBook.description}"
								class="form-control w-75 text-start" style="height: 100px"></form:input>
						</div>
					</h5>
					<div class="mt-4 d-flex justify-content-end align-items-center">
						<button type="submit" class="btn btn-primary w-25 me-3"
							style="box-shadow: 5px 5px 5px black;">Update</button>
						<form action="/books/${oneBook.id}/delete" method="post">
							<input type="hidden" name="_method" value="delete">
							<button type="submit" class="btn btn-danger w-25"
								style="box-shadow: 5px 5px 5px black;">delete</button>
						</form>
					</div>
				</form:form>
			</div>
			<div class="d-flex flex-column justify-content-start"
				style="width: 40%;">
				<h3 class="mb-5">Users Who Like This Book</h3>
				<div>
					<c:forEach items="${oneBook.usersWhoFavor}" var="usersWhoFavor">
						<h5 class="mb-3">
							-
							<c:out
								value="${usersWhoFavor.firstName} ${usersWhoFavor.lastName}"></c:out>
							<span class="ms-2"> <c:if
									test="${usersWhoFavor.id == user.id}">
									<c:if test="${fn:contains(oneBook.usersWhoFavor, user)}">
										<a href="/books/favorites/remove/${oneBook.id}">Un-Favorite</a>
									</c:if>
								</c:if>
							</span> <br>
						</h5>
					</c:forEach>
					<h5>
						<c:if test="${not fn:contains(oneBook.usersWhoFavor, user)}">
							<a href="/books/favorites/add/${oneBook.id}">Add to Favorites</a>
						</c:if>
					</h5>
				</div>
			</div>
		</div>
	</div>
</body>
</html>