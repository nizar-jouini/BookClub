<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- New line below to use the JSP Standard Tag Library -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
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
<title>Login and Registration</title>
</head>
<body>
	<div class="d-flex justify-content-around" style="height: 38.5rem">

		<!-- REGISTER A NEW USER  -->
		<!-- bring in the newUser empty Object -->
		<div class="col-4 p-4 bg-dark text-light">
			<h3 class="text-primary">Register:</h3>
			<form:form action="/register" method="post" modelAttribute="newUser">
				<div class="form-group mt-4">
					<label>First Name:</label>
					<form:input path="firstName" class="form-control" />
					<form:errors path="firstName" class="text-danger" />
				</div>
				<div class="form-group mt-4">
					<label>Last Name:</label>
					<form:input path="lastName" class="form-control" />
					<form:errors path="lastName" class="text-danger" />
				</div>
				<div class="form-group mt-4">
					<label>Email:</label>
					<form:input path="email" class="form-control" />
					<form:errors path="email" class="text-danger" />
				</div>
				<div class="form-group mt-4">
					<label>Password:</label>
					<form:password path="password" class="form-control" />
					<form:errors path="password" class="text-danger" />
				</div>
				<div class="form-group mt-4">
					<label>Confirm Password:</label>
					<form:password path="confirm" class="form-control" />
					<form:errors path="confirm" class="text-danger" />
				</div>
				<div class="d-flex justify-content-center mt-5">
					<input type="submit" value="Register" class="btn btn-primary w-50" />
				</div>
			</form:form>
		</div>

		<!-- LOGIN -->
		<!-- newLogin object will touch the model to validate only -->
		<div class="col-4 p-4 bg-dark text-light">
			<h3 class="text-success ">Login:</h3>
			<form:form action="/login" method="post" modelAttribute="newLogin">
				<div class="form-group mt-4">
					<label>Email:</label>
					<form:input path="email" class="form-control" />
					<form:errors path="email" class="text-danger" />
				</div>
				<div class="form-group mt-4">
					<label>Password:</label>
					<form:password path="password" class="form-control" />
					<form:errors path="password" class="text-danger" />
				</div>
				<div class="d-flex justify-content-center mt-5">
					<input type="submit" value="Login" class="btn btn-success w-50" />
				</div>
			</form:form>
		</div>

	</div>
</body>
</html>