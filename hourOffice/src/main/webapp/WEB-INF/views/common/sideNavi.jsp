<%@page import="kr.or.houroffice.member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="sideNavi">
		<div class="sideNaviClick">
			<div class="sideNaviIcon">
				&nbsp;&nbsp;<i class="fas fa-home"></i>
			</div>
			<div class="sideNaviBox">홈</div>
			<div class="arrow">
				<i class="fas fa-chevron-right"></i>
			</div>
		</div>
		<ui class="categoryBox"> </ui>

		<div id="categoryMail" class="sideNaviClick">
			<div class="sideNaviIcon">
				&nbsp;&nbsp;<i class="fas fa-envelope"></i>
			</div>
			<div class="sideNaviBox">메일</div>
			<div class="arrow">
				<i class="fas fa-chevron-right"></i>
			</div>
		</div>

		<ui class="categoryBox">
		<li class="categoryName">메일함</li>
		<li class="categoryList"><a href="/mailList.ho?listType=R">받은 메일함</a></li>
		<li class="categoryList"><a href="/mailList.ho?listType=S">보낸 메일함</a></li>
		<li class="categoryList"><a href="/mailList.ho?listType=K">보관 메일함</a></li>
		<li class="categoryList"><a href="/mailList.ho?listType=F">참조 메일함</a></li>
		<li class="categoryList"><a href="/mailList.ho?listType=D">삭제 메일함</a></li>
		</ui>

		<div id="categoryMypage" class="sideNaviClick">
			<div class="sideNaviIcon">
				&nbsp;&nbsp;<i class="fas fa-user-cog"></i>
			</div>
			<div class="sideNaviBox">인사관리</div>
			<div class="arrow">
				<i class="fas fa-chevron-right"></i>
			</div>
		</div>

		<ui class="categoryBox">
		<li class="categoryName">내 정보</li>
		<li class="categoryList"><a href="/mypage.ho">내 개인정보</a></li>
		<li class="categoryList"><a href="/information.ho">내 인사정보</a></li>
		<li class="categoryName">주소록</li>
		<li class="categoryList"><a href="/addbook.ho">사내 주소록</a></li>
		<li class="categoryList"><a href="/myaddbook.ho">개인 주소록</a></li>
		</ui>

		<div id="categoryWork" class="sideNaviClick">
			<div class="sideNaviIcon">
				&nbsp;&nbsp;<i class="fas fa-user-clock"></i>
			</div>
			<div class="sideNaviBox">근태관리</div>
			<div class="arrow">
				<i class="fas fa-chevron-right"></i>
			</div>
		</div>
		<ui class="categoryBox">
		<li class="categoryName">근태관리</li>
		<li class="categoryList"><a href="/holiday.ho">내 연차내역</a></li>
		<li class="categoryList"><a href="/work.ho">내 근태현황</a></li>
		</ui>

		<div id="categoryElecAppr" class="sideNaviClick">
			<div class="sideNaviIcon">
				&nbsp;&nbsp;<i class="far fa-newspaper"></i>
			</div>
			<div class="sideNaviBox">전자결재</div>
			<div class="arrow">
				<i class="fas fa-chevron-right"></i>
			</div>
		</div>
		<ui class="categoryBox">
		<li class="categoryName">결재하기</li>
		<li class="categoryList"><a href="/approvalList.ho?listType=R">결재 요청함</a></li>
		<li class="categoryList"><a href="/approvalList.ho?listType=W">결재 대기함</a></li>
		<li class="categoryName">문서함</li>
		<li class="categoryList"><a href="/approvalList.ho?listType=F">참조 문서함</a></li>
		<li class="categoryList"><a href="/approvalList.ho?listType=D">부서 문서함</a></li>
		</ui>

		<div id="categoryBoard" class="sideNaviClick">
			<div class="sideNaviIcon">
				&nbsp;&nbsp;<i class="fas fa-chalkboard"></i>
			</div>
			<div class="sideNaviBox">게시판</div>
			<div class="arrow">
				<i class="fas fa-chevron-right"></i>
			</div>
		</div>
		<ui class="categoryBox">
		<li class="categoryName">전사 게시판</li>
		<li class="categoryList"><a href="/allCompanyRulePage.ho">사내규정 게시판</a></li>
		<li class="categoryList"><a href="/allNoticePage.ho">공지사항 게시판</a></li>
		<li class="categoryList"><a href="#">문의사항 게시판</a></li>
		<li class="categoryName">부서 게시판</li>
		<li class="categoryList"><a href="/allPartBoardPage.ho">부서별 게시판</a></li>
		</ui>

		<div id="categoryProject" class="sideNaviClick">
			<div class="sideNaviIcon">
				&nbsp;&nbsp;<i class="fas fa-archive"></i>
			</div>
			<div class="sideNaviBox">프로젝트</div>
			<div class="arrow">
				<i class="fas fa-chevron-right"></i>
			</div>
		</div>

		<ui class="categoryBox">
		<li class="categoryName">프로젝트 목록</li>
		<li class="categoryList"><a href="/projectAllList.ho">프로젝트 전체목록</a></li>
		<li class="categoryList"><a href="/projectProgressList.ho">프로젝트 진행목록</a></li>
		<li class="categoryList"><a href="/projectComplateList.ho">프로젝트 완료목록</a></li>
		<li class="categoryList"><a href="/projectLikeList.ho">프로젝트 즐겨찾기</a></li>
		</ui>

		<div id="categoryResource" class="sideNaviClick">
			<div class="sideNaviIcon">
				&nbsp;&nbsp;<i class="fas fa-save"></i>
			</div>
			<div class="sideNaviBox">자료실</div>
			<div class="arrow">
				<i class="fas fa-chevron-right"></i>
			</div>
		</div>
		<ui class="categoryBox"> </ui>
		
		
		<c:if test="${sessionScope.member.memRightCode=='A'.charAt(0) || sessionScope.member.memRightCode=='B'.charAt(0) || sessionScope.member.memRightCode=='C'.charAt(0) || sessionScope.member.memRightCode=='D'.charAt(0)}">

		<div id="categoryAdmin" class="sideNaviClick">
			<div class="sideNaviIcon">
				&nbsp;&nbsp;<i class="fas fa-cog"></i>
			</div>
			<div class="sideNaviBox">관리</div>
			<div class="arrow">
				<i class="fas fa-chevron-right"></i>
			</div>
		</div>
		<ui class="categoryBox">
		<c:if test="${sessionScope.member.memRightCode=='A'.charAt(0) || sessionScope.member.memRightCode=='C'.charAt(0) }">
		<li class="categoryName">인사 관리자</li>
		<li class="categoryList"><a href="/admin_tap_allListMember.ho">통합사원 관리</a></li>
		<li class="categoryList"><a href="/admin_tap_organizationChart.ho">조직도 관리</a></li>
		</c:if>
		<c:if test="${sessionScope.member.memRightCode=='A'.charAt(0) || sessionScope.member.memRightCode=='D'.charAt(0) }">
		<li class="categoryName">총무 관리자</li>
		<li class="categoryList"><a href="/admin_tap_allNoticePage.ho">공지사항 관리</a></li>
		<li class="categoryList"><a href="/admin_tap_allCompanyRulePage.ho">사내규정 관리</a></li>
		</c:if>
		<c:if test="${sessionScope.member.memRightCode=='A'.charAt(0) || sessionScope.member.memRightCode=='B'.charAt(0) }">
		<li class="categoryName">전산 관리자</li>
		<li class="categoryList"><a href="/adminMainPage.ho">관리자 페이지</a></li>
		</c:if>
		</ui>
		</c:if>
	</div>

</body>
<script>
$(function(){
	$('.fa-home').parents('.sideNaviClick').click(function(){
		var todayMon = new Date().getMonth()+1;
		location.replace('/main.ho?todayMon='+todayMon);
	});
	
	$('#categoryResource').click(function(){
		location.replace('/resourceCenter.ho?resourceType=all');
	});
})
	
</script>
</html>