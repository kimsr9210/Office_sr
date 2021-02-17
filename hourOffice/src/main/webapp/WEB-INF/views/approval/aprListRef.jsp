<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>
<!-- 폰트어썸 -->
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<!--jQuery CDN-->
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/approval/approval_list.css" />

</head>
<body>
<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp"%>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">
                        <span id="page-name">전자결재</span>
                        <span id="sub-name">참조결재함</span>
                        <form action="/approvalList.ho" method="get" id="apr-search-form">
                        <input name="listType" type="hidden" value="F"/>
                           <select name="searchType" id="search-option">
                               <option value="title">제목</option>
                               <option value="member">기안자</option>
                           </select>
                            <input type="text" id="approval-search" name="keyword"><button type="submit" id="apr-search-btn">
                                <i class="fas fa-search"></i>
                            </button>
                        </form>
                    </div>
                    <div id="TitleContents">
                       <a href="/approvalForm.ho?docuType=H" id="new-apr-btn">+ 새 결재문서</a>
                        <ul id="apr-list-filter">
                            <li><a href="/approvalList.ho?listType=F">전체목록</a></li>
                            <li><a href="/approvalList.ho?listType=F&aprStatus=W">대기</a></li>
                            <li><a href="/approvalList.ho?listType=F&aprStatus=I">진행중</a></li>
                            <li><a href="/approvalList.ho?listType=F&aprStatus=R">반려</a></li>
                            <li><a href="/approvalList.ho?listType=F&aprStatus=C">완료</a></li>
                        </ul>
                        
                        <table id="apr-docu-list">
                            <tr>
                                <th width="10%">문서번호</th>
                                <th width="10%">기안일</th>
                                <th width="20%">결재양식</th>
                                <th width="30%">제목</th>
                                <th width="10%">기안자</th>
                                <th width="10%">결재상태</th>
                                <th width="10%">긴급여부</th>
                            </tr>
                            <c:forEach var="apr" items="${requestScope.aprList.list }">
                            	<tr>
                                <td>${apr.docuNo}</td>
                                <td><fmt:formatDate value="${apr.docuDate }" pattern="yyyy-MM-dd"/></td>
                                <td><c:choose>
                                	<c:when test="${apr.docuType eq 'H'.charAt(0)}">연차신청서</c:when>
                                	<c:when test="${apr.docuType eq 'O'.charAt(0)}">연장근무신청서</c:when>
                                	<c:when test="${apr.docuType eq 'L'.charAt(0)}">지각불참사유서</c:when>
                                	<c:when test="${apr.docuType eq 'C'.charAt(0)}">법인카드사용신청서</c:when>
                                </c:choose></td>
                                <td><a href="/approval.ho?docuNo=${apr.docuNo}&docuType=${apr.docuType}">${apr.title }</a></td>
                                <td>${apr.memName }</td>
                                <td><c:choose>
                                <c:when test="${apr.aprType eq 'W'.charAt(0)}"><span class="apr-type type-ing">대기</span></c:when>
                                <c:when test="${apr.aprType eq 'I'.charAt(0)}"><span class="apr-type type-ing">진행중</span></c:when>
                                <c:when test="${apr.aprType eq 'R'.charAt(0)}"><span class="apr-type type-ref">반려</span></c:when>
                                <c:when test="${apr.aprType eq 'C'.charAt(0)}"><span class="apr-type type-done">완료</span></c:when>
                                </c:choose></td>
                                <td><c:if test="${apr.urgencyYN eq 'Y'.charAt(0)}"><span class="apr-type type-urg">긴급</span></c:if></td>
                            	</tr>
                            </c:forEach>
                        </table>
                        <ul id="page-navi">${requestScope.aprList.pageNavi}</ul>
                    </div>
				</div>
			</div>
		</div>
	</div>
	<script>
	$(function(){
		$('#categoryElecAppr').next().css('display', 'block');
		$('#categoryElecAppr').next().css('height', '150px');
		$('#categoryElecAppr').children().last().children().attr('class',
				'fas fa-chevron-left');

		$('#categoryElecAppr').next().children().eq(4).children().css('font-weight',
				'800');
		$('#categoryElecAppr').next().children().eq(4).children().css('color',
				'#ffcc29');

	});
	</script>
	<!-- 자바 스크립트    -->
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
</body>
</html>