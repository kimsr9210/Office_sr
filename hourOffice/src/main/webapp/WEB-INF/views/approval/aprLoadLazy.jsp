<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link rel="stylesheet" type="text/css" href="/resources/css/approval/apr_form.css" />

</head>
<body>
	<c:choose>
	<c:when test="${sessionScope.member.memNo == docu.memNo }">
	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp"%>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">전자결재 작성</div>
					<div id="TitleContents">
						<div id="docu-type-wrap">
							<label for="docuType">양식 선택 :</label> <select name="docuType"
								id="docu-type" onchange="movePage(this.value);">
								<option value="/approvalForm.ho?docuType=H">연차신청서</option>
								<option value="/approvalForm.ho?docuType=O">연장근무신청서</option>
								<option value="/approvalForm.ho?docuType=L" selected>지각불참사유서</option>
								<option value="/approvalForm.ho?docuType=C">법인카드사용신청서</option>
							</select>
						</div>
						<form action="/updateAprLazy.ho" method="post">
							<span class="opt-check"><input type="checkbox" name="lockYN" value="Y" <c:if test="${docu.lockYN == 'Y'.charAt(0) }">checked</c:if>><label for="lockYN">비공개</label></span>
                            <span class="opt-check"><input type="checkbox" name="urgencyYN" value="Y" <c:if test="${docu.urgencyYN == 'Y'.charAt(0) }">checked</c:if>><label for="urgencyYN">긴급문서</label></span>
							<input type="submit" value="작성 완료">
							<input type="reset" value="작성취소">
							<div id="title-wrap">
								<div>문서 제목</div><div><input type="text" name="title" value="${docu.title }"></div>
							</div>
							<fieldset id="form-wrap">
								<div id="form-title">지각불참사유서</div>
								<table id="docu-info">
									<tr>
										<td>기안자</td>
										<td>${docu.memName}</td>
									</tr>
									<tr>
										<td>기안부서</td>
										<td><c:choose>
                                        	<c:when test="${docu.deptCode != null }">${docu.deptName }</c:when>
                                        	<c:otherwise>부서없음</c:otherwise>
                                        </c:choose></td>
									</tr>
									<tr>
										<td>기안일</td>
										<td><fmt:formatDate value="${docu.docuDate }" pattern="yyyy-MM-dd"/></td>
									</tr>
									<tr>
										<td>문서번호</td>
										<td>${docu.docuNo }<input name="docuNo" type="hidden" value="${docu.docuNo }"/></td>
									</tr>
								</table>
								<table id="apr-line-info">
									<tr>
										<td colspan="3">결재선</td>
									</tr>
									<tr>
										<td><c:if test="${aprLine[0] != null }">${aprLine[0].memName } ${aprLine[0].memPosition }(${aprLine[0].deptName })</c:if></td>
                                        <td><c:if test="${aprLine[1] != null }">${aprLine[1].memName } ${aprLine[1].memPosition }(${aprLine[1].deptName })</c:if></td>
                                        <td><c:if test="${aprLine[2] != null }">${aprLine[2].memName } ${aprLine[2].memPosition }(${aprLine[2].deptName })</c:if></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</table>
								<table id="con-info">
									<tr>
										<td>부서</td>
										<td><select name="dept" id="dept_code" required>
												<option value="인사부" <c:if test="${docu.dept eq '인사부' }">selected</c:if>>인사부</option>
												<option value="총무부" <c:if test="${docu.dept eq '총무부' }">selected</c:if>>총무부</option>
												<option value="전산부" <c:if test="${docu.dept eq '전산부' }">selected</c:if>>전산부</option>
												<option value="개발부" <c:if test="${docu.dept eq '개발부' }">selected</c:if>>개발부</option>
												<option value="디자인부" <c:if test="${docu.dept eq '디자인부' }">selected</c:if>>디자인부</option>
										</select></td>
									</tr>
									<tr>
										<td>직위</td>
										<td><input type="text" required name="position" value="${docu.position }"></td>
									</tr>
									<tr>
										<td>성명</td>
										<td><input type="text" required name="mName" value="${docu.mName }"></td>
									</tr>
									<tr>
										<td>지각/불참일</td>
										<td><input type="date" required name="lazyDate" value="${docu.lazyDate }"></td>
									</tr>
									<tr>
										<td>지각/불참사유</td>
										<td><textarea name="reasons" id="reasons" required>${docu.reasons}</textarea>
										</td>
									</tr>
								</table>
							</fieldset>
							<fieldset id="apr-line-wrap">
								<div id="line-header">
									<span class="line-left">결재선</span> <span class="line-mid">참조</span>
									<span class="line-right">대상</span>
								</div>
								<c:if test="${!empty aprLineList }">
									<c:forEach var="line" items="${aprLineList }">
										<div class="line-list">
										<span class="line-left"><input type="checkbox" name="aprLine" value="${line.memNo }" <c:forEach var="ckLine" items="${aprLine }"><c:if test="${ckLine.memNo==line.memNo }">checked</c:if></c:forEach>></span>
										<span class="line-mid"><input type="checkbox" name="aprRef" value="${line.memNo }" <c:forEach var="ckRef" items="${aprRef }"><c:if test="${ckRef.memNo==line.memNo }">checked</c:if></c:forEach>></span>
										<span class="line-right">${line.memName } ${line.memPosition }(${line.deptName })</span>
									</div>
									</c:forEach>								
								</c:if>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 자바 스크립트    -->
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
	<script>
		$(function() {
			//결재선 선택 처리 //aprLine / apr-line-wrap 이름 유의
			$('input[name=aprLine]').click(function() {
					var $this = $(this);
					var aprLength = $('input[name=aprLine]:checked').length;
					var cidx = $('input[name=aprLine]:checked').index($this);
					var nidx = $('input[name=aprLine]').index($this);

					var $ref = $(this).parent().next().children();
					if ($this.prop('checked')) {
						if($ref.prop('checked')){
	                        alert("결재선과 참조는 동시에 선택할 수 없습니다.");
	                        return false;
	                    }
						
						if (aprLength < 4) {
							for (var i = 0; i < aprLength; i++) {
								$('#apr-line-info tr:nth-child(2) td').eq(i).html($('input[name=aprLine]:checked').eq(i).parent().next().next().html());
							}
						} else {
							alert('결재선은 3개까지만 선택 가능합니다.');
							return false;
						}
						
					} else {
						for (var i = 0; i < 3; i++) {
							$('#apr-line-info tr:nth-child(2) td').eq(i).html('');
						}
						for (var i = 0; i < aprLength; i++) {
							$('#apr-ine-info tr:nth-child(2) td').eq(i).html($('input[name=aprLine]:checked').eq(i).parent().next().next().html());
						}
					}
				});
			//참조와 결재선 동시 선택 불가
			$('input[name=aprRef]').click(function(){
                var $ref = $(this);
                var $line = $(this).parent().prev().children();
                if($ref.prop('checked')){
                    if($line.prop('checked')){
                        alert("결재선과 참조는 동시에 선택할 수 없습니다.");
                        return false;
                    }
                }
            });
			//submit전에 검사
			$('form').submit(function(){
                if($('input[name=aprLine]:checked').length==0){
                    alert('결재선을 1개 이상 선택해야 합니다.');
                    return false;
                }
            });
		});
		//페이지 호출 처리
		function movePage(url) {
			location.href = url;
		}
	</script>
	</c:when>
	<c:otherwise><script>alert("작성자 이외에는 접근할 수 없습니다."); history.back(-1);</script></c:otherwise>
</c:choose>
</body>
</html>