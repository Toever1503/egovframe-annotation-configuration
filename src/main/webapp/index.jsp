<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	index page

	<form method="post"
		action="/test-prj-annotation-configuration/upload/test">
		<button>test</button>
	</form>
	<br>

	<form action="/test-prj-annotation-configuration/upload/file"
		method="post" enctype="multipart/form-data">
		Select File: <input type="file" name="file" /> <input type="submit"
			value="Upload File" />
	</form>

</body>
</html>