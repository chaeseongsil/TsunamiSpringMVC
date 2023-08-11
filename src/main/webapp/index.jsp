<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Spring MVC</title>
		<link rel="stylesheet"  href="/resources/css/main.css">
	</head>
	<body>
		<h1>환영합니다~๑•‿•๑</h1>
		<h2>Spring MVC 페이지입니다~٩(ˊᗜˋ*)و</h2>
		<c:if test="${sessionScope.memberId ne null }">
		
		. · . ʚ♡︎ɞ· .* .· ʚ♥︎ɞ• . ʚ♡︎ɞ. · • . ʚ♥︎ɞ*· . · .ʚ♡︎ɞ . · . <br>
		Δ~~~~Δ <br>
		ξ ･ェ･ ξ <br>
		ξ　~　ξ &nbsp;&nbsp;&nbsp;환영합니다~ ${memberName }님~ <br>
		ξ　　 ξ <br>
		ξ　　　“~～~～〇 <br>
		ξ　　　　　　 ξ <br>
		ξ　ξ　ξ~～~ξ　ξ <br>
		　ξ_ξξ_ξ　ξ_ξξ_ξ <br>
		. · . ʚ♡︎ɞ· .* .· ʚ♥︎ɞ• . ʚ♡︎ɞ. · • . ʚ♥︎ɞ*· . · .ʚ♡︎ɞ . · . <br>
		|￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣| <br>
		|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="/member/logout.do">로그아웃</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="/member/mypage.do?memberId=${memberId }">마이페이지</a>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  |<br>
		|＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿| <br>
		　　         ᕱ  ᕱ  &nbsp; ||<br>
		　         &nbsp;  ( ･ω･ &nbsp;||<br>
		  　      &nbsp;   /　&nbsp;つΦ<br>		
		</c:if>
		<c:if test="${sessionScope.memberId eq null }">
			<form action="/member/login.do" method="post">
				<label>ID</label>
				<input type="text" name="memberId"><br>
				<label>PW</label>
				<input type="password" name="memberPw"><br>
				<input type="submit" value="로그인">
				<input type="reset" value="취소">
				<input type="button" value="가입하기" onclick="goRegister();">
			</form>
		</c:if>
		<script>
			function goRegister(){
				location.href="/member/register.do"
			}
		</script>
	</body>
</html>