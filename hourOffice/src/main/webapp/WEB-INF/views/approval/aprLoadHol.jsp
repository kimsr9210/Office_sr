<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList"%>
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
							<label for="docuType">양식 선택 :</label> <select name="docuType" id="docu-type" onchange="movePage(this.value);">
								<option value="/approvalForm.ho?docuType=H" selected>연차신청서</option>
								<option value="/approvalForm.ho?docuType=O">연장근무신청서</option>
								<option value="/approvalForm.ho?docuType=L">지각불참사유서</option>
								<option value="/approvalForm.ho?docuType=C">법인카드사용신청서</option>
							</select>
						</div>
                        <form action="/updateAprHol.ho" method="post">
                            <span class="opt-check"><input type="checkbox" name="lockYN" value="Y" <c:if test="${docu.lockYN == 'Y'.charAt(0) }">checked</c:if>><label for="lockYN">비공개</label></span>
                            <span class="opt-check"><input type="checkbox" name="urgencyYN" value="Y" <c:if test="${docu.urgencyYN == 'Y'.charAt(0) }">checked</c:if>><label for="urgencyYN">긴급문서</label></span>
                            <input type="submit" value="작성 완료">
                            <input type="reset" value="작성취소">
                            <div id="title-wrap">
                                <div>문서 제목</div><div><input type="text" name="title" value="${docu.title }"></div>
                            </div>
                            <fieldset id="form-wrap">
                                <div id="form-title">연차신청서</div>
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
                                    <tr><td colspan="3">결재선</td></tr>
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
                                        <td>휴가 구분</td>
                                        <td>
                                            <input type="radio" name="holType" id="holType_n" value="N"  required <c:if test="${docu.holType == 'N'.charAt(0) }">checked</c:if>><label for="holType_n"> 연차(일반)</label>
                                            <input type="radio" name="holType" id="holType_c" value="C" <c:if test="${docu.holType == 'C'.charAt(0) }">checked</c:if>><label for="holType_c"> 경조사</label>
                                            <input type="radio" name="holType" id="holType_p" value="P" <c:if test="${docu.holType == 'P'.charAt(0) }">checked</c:if>><label for="holType_p"> 공가</label>
                                            <input type="radio" name="holType" id="holType_s" value="S" <c:if test="${docu.holType == 'S'.charAt(0) }">checked</c:if>><label for="holType_s"> 병가</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>휴가 기간</td>
                                        <td>
                                            <input type="date" required name="startDate" value="<fmt:formatDate value="${docu.startDate }" pattern="yyyy-MM-dd"/>"> ~ <input type="date" required name="endDate" value="<fmt:formatDate value="${docu.endDate }" pattern="yyyy-MM-dd"/>"> <span class="space"></span>총 기간 <input type="number" readonly value="${docu.countDay }" name="countDay"> 일
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>반차 여부</td>
                                        <td>
                                        <input type="radio" name="afternoonOff" id="afternoonOff_a" value="A" required <c:if test="${docu.afternoonOff == 'A'.charAt(0) }">checked</c:if>><label for="afternoonOff_a"> 전일</label>
                                        <input type="radio" name="afternoonOff" id="afternoonOff_m" value="M" <c:if test="${docu.afternoonOff == 'N'.charAt(0) }">checked</c:if>><label for="afternoonOff_m"> 오전</label>
                                        <input type="radio" name="afternoonOff" id="afternoonOff_p" value="P" <c:if test="${docu.afternoonOff == 'P'.charAt(0) }">checked</c:if>><label for="afternoonOff_p"> 오후</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>잔여 연차</td>
                                        <td>현재 <input type="number" disabled value="${sessionScope.member.memHolidayCount }" name="myHolDay"> 일
                                            <span class="space"></span>사용 후 <input type="number" readonly value="${sessionScope.member.memHolidayCount - docu.countDay }" name="remaining"> 일
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>휴가 사유</td>
                                        <td>
                                            <textarea name="reasons" id="reasons" required>${docu.reasons }</textarea>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                            <fieldset id="apr-line-wrap">
                                <div id="line-header">
                                    <span class="line-left">결재선</span>
                                    <span class="line-mid">참조</span>
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
			
			
			//휴가일수 계산
			$('#con-info input[type=date]').change(function(){
				var selectedDate = new Date($(this).val());
				var today = new Date();
				if(selectedDate>=today){
					var startDate = $('input[name=startDate]').val();
					var endDate = $('input[name=endDate]').val();
					var day = 1000*60*60*24 //밀리초, 초, 분, 시간
					var countDay = (new Date(endDate)-new Date(startDate))/day+1; 
					if(countDay<=0){
						alert('휴가 기간이 0보다 작을 수 없습니다.');
						$('input[name=countDay]').val(0);
						$('input[name=remaining]').val(0);
					}else{
						if(countDay>1){
							$('input[name=countDay]').val(countDay);
							$('#afternoonOff_m').prop('disabled',true);
							$('#afternoonOff_p').prop('disabled',true);
						}else{
							$('input[name=countDay]').val(countDay);
							$('#afternoonOff_m').prop('disabled',false);
							$('#afternoonOff_p').prop('disabled',false);
						}
						
						//남은 연차 계산
						var remaining = Number($('input[name=myHolDay]').val());
						if(!isNaN(countDay) && remaining<countDay){
							alert('남은 연차보다 더 많은 연차를 사용할 수 없습니다.'); //0보다 작으면
							$('input[name=countDay]').val(0);
						}
						if(remaining>=countDay){
							$('input[name=remaining]').val(remaining-countDay);
						}
					}
					
				}else{
					alert('오늘보다 이전 날짜는 선택할 수 없습니다.');
					$(this).val('');
				}
			});
			//반차 계산
			$('input[name=afternoonOff]').change(function(){
				var offType =  $(this).val();
				var $countDay = $('input[name=countDay]');
				if(($countDay.val()==0.5 ||$countDay.val()==1) && (offType == 'M' || offType=='P')){
					$countDay.val(0.5);
				}else{
					var startDate = $('input[name=startDate]').val();
					var endDate = $('input[name=endDate]').val();
					var day = 1000*60*60*24 //밀리초, 초, 분, 시간
					var countDay = (new Date(endDate)-new Date(startDate))/day+1;
					$countDay.val(countDay);
					
					if(countDay<=0){
						alert('휴가 기간이 0보다 작을 수 없습니다.');
						$('input[name=countDay]').val(0);
						$('input[name=remaining]').val(0);
					}
				}
				//남은 연차 계산
				var cDay = Number($countDay.val());
				var remaining = Number($('input[name=myHolDay]').val());
				if(!isNaN(cDay) && remaining<cDay){
					alert('남은 연차보다 더 많은 연차를 사용할 수 없습니다.'); //0보다 작으면
					$('input[name=countDay]').val(0);
				}
				if(remaining>=cDay){
					$('input[name=remaining]').val(remaining-cDay);
				}
			});
			//submit전에 검사
			$('form').submit(function(){
                if($('input[name=aprLine]:checked').length==0){
                    alert('결재선을 1개 이상 선택해야 합니다.');
                    return false;
                }
                if($('input[name=countDay]').val()==0){
                	alert('사용할 연차가 없습니다.');
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