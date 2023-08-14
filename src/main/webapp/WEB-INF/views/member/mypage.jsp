<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>마이페이지</title>
		<link rel="stylesheet" href="..../resources/css/main.css">
	</head>
	<body>
		<h1>마이페이지</h1>
		<fieldset>
			<legend>회원 정보</legend>
			<ul>
				<li>
					<label>아이디</label>						
					<span>${member.memberId }</span>
				</li>
				<li>
					<label>이름</label>						
					<span>${member.memberName }</span>
				</li>
				<li>
					<label>나이</label>						
					<span>${member.memberAge }</span>
				</li>
				<li>
					<label>성별</label>	
					<c:if test="${member.memberGender eq 'M' }"><span>남자</span></c:if>
					<c:if test="${member.memberGender eq 'F' }"><span>여자</span></c:if>
					<!-- 남 <input type="radio" name="memberGender" value="M">
					여 <input type="radio" name="memberGender" value="F"> -->
				</li>
				<li>
					<label>이메일</label>						
					<span>${member.memberEmail }</span>
				</li>
				<li>
					<label>전화번호</label>						
					<span>${member.memberPhone }</span>
				</li>
				<li>
					<label>주소</label>						
					<span>${member.memberAddr }</span>
				</li>
				<li>
					<label>취미</label>						
					<span>${member.memberHobby }</span>
				</li>
				<li>
					<label>가입일</label>						
					<span>${member.memberDate.toString().substring(0,11) }</span>
				</li>
			</ul>
		</fieldset>
		<div>
			<a href="/index.jsp">메인으로 이동</a>
			<a href="/member/update.do?memberId=${member.memberId }">수정하기</a>
			<a href="javascript:void(0)" onclick="checkDelete();">탈퇴하기</a>
		</div>
		<script>
			function checkDelete(){
				var memberId = '${member.memberId}';
				if(confirm("정말 탈퇴하시겠습니까?ㅠ")){
					location.href="/member/delete.do?memberId="+memberId;
				}
			}
		</script>
	</body>
</html>