<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="kr.or.houroffice.member.model.vo.Member" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>

	<!-- 폰트 어썸 CDN -->
	<script src="https://use.fontawesome.com/releases/v5.8.2/js/all.js"></script>
	<!-- 제이쿼리 CDN -->
	<script src="https://code.jquery.com/jquery-3.5.1.js"integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="crossorigin="anonymous"></script>

	<!-- 관리자 페이지 공통 css -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin/style.css" />
	
	<!-- 관리자 페이지 관리자 관리 css -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin/administration.css" />

</head>
<body>
	<!--JSTL core Tag 사용 선언  -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<!-- JSTL format Tag 사용 선언 -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	
	<div id="wrap">
		<%@ include file ="../adminForm/header.jsp" %>
		
		<div id="contentsBox">
			<%@ include file ="../adminForm/sideNaviBar.jsp" %>
			
			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">
						관리자 관리
    				</div>
    				<div id="TitleContents">
    					<div class="adminSpace">
                            <span class="adminName">전산관리자</span>
                            <hr class="adminLine"/>
                            <span class="adminTitle">기본 관리</span>
                            <span class="adminText">관리자 페이지 관리, 메뉴 관리 등을 관리합니다.</span>
                            
                            <div class="buttonSet">
                                <button class="agreeButtonType">추가</button>
                                <button class="refuseButtonType">삭제</button>
                            </div>
                            <table id="adminList" class="tblStyle">
                                <tr>
                                    <th></th>
                                    <th>이름 (이메일)</th>
                                    <th>부서</th>
                                    <th>관리자 등록일</th>
                                </tr>                                
                                <c:forEach items="${list}" var="li" varStatus="status">  
                                	<c:if test="${li.memRightCode eq 'B'.charAt(0)}">	                                                                	  
	                                	<tr>
		                                    <td><input type="checkbox" name="adCheckMem" value="${li.memNo}"/></td>
		                                    <td>${li.memName} (${li.memEmail})</td>
		                                    <td>${li.deptName}</td>
		                                    <td><fmt:formatDate value="${li.memRightDate}" type="both" pattern="yyyy년 MM월 dd일"/></td>
		                            	</tr>
		                            </c:if>	
		                       	</c:forEach>           	 								                             
                            </table>
                        </div>                     
                        <div class="adminSpace">
                            <span class="adminName">인사관리자</span>
                            <hr class="adminLine"/>
                            <span class="adminTitle">조직 관리</span>
                            <span class="adminText">멤버, 부서, 직위 등 조직을 구성/관리합니다.</span>
                            <div class="buttonSet">
                                <button class="agreeButtonType">추가</button>
                                <button class="refuseButtonType">삭제</button>
                            </div>
                            <table id="personnelList" class="tblStyle">
                                <tr>
                                    <th></th>
                                    <th>이름 (이메일)</th>
                                    <th>부서</th>
                                    <th>관리자 등록일</th>
                                </tr>
                                <c:forEach items="${list}" var="li" varStatus="status">  
                                	<c:if test="${li.memRightCode eq 'C'.charAt(0)}">	                                                                	  
	                                	<tr>
		                                    <td><input type="checkbox" name="peCheckMem" value="${li.memNo}"/></td>
		                                    <td>${li.memName} (${li.memEmail})</td>
		                                    <td>${li.deptName}</td>
		                                    <td><fmt:formatDate value="${li.memRightDate}" type="both" pattern="yyyy년 MM월 dd일"/></td>
		                            	</tr>
		                            </c:if>	
		                       	</c:forEach>
                            </table>
                        </div>                       
                       <div class="adminSpace">
                            <span class="adminName">총무관리자</span>
                            <hr class="adminLine"/>
                            <span class="adminTitle">문서 관리</span> 	
                            <span class="adminText">사내 규정, 문서 등을 관리합니다.</span>
                            <div class="buttonSet">
                                <button class="agreeButtonType">추가</button>
                                <button class="refuseButtonType">삭제</button>
                            </div>
                            <table id="generalList" class="tblStyle">
                                <tr>
                                    <th></th>
                                    <th>이름 (이메일)</th>
                                    <th>부서</th>
                                    <th>관리자 등록일</th>
                                </tr>
                                <c:forEach items="${list}" var="li" varStatus="status">  
                                	<c:if test="${li.memRightCode eq 'D'.charAt(0)}">	                                                                	  
	                                	<tr>
		                                    <td><input type="checkbox" name="geCheckMem" value="${li.memNo}"/></td>
		                                    <td>${li.memName} (${li.memEmail})</td>
		                                    <td>${li.deptName}</td>
		                                    <td><fmt:formatDate value="${li.memRightDate}" type="both" pattern="yyyy년 MM월 dd일"/></td>
		                            	</tr>
		                            </c:if>	
		                       	</c:forEach>
                            </table>
                        </div>                       
                        <div id="modal">
							<div class="modal-content">
								<center><span class="exit-icon"><i class="fas fa-times"></i></span></center><br/>
								<div class="searchStyle selectStyle formStyle">
									<input placeholder="검색어를 입력하세요" type="text" id="keyword" name="keyword" style="padding-left:5px;"/>
									<button><i class="fas fa-search"></i></button>
								</div><br/><br/>
								<table class="modal-table">
									<thead>
										<tr>
											<th></th>
											<th>사번</th>
											<th>이름</th>
											<th>직위</th>
										</tr>
									</thead>		
									<tbody id="tbody">
									</tbody>     
								</table><br/>
								<button class="agreeButtonType">저장</button>
							</div>
							<div class="modal-layer"></div>
						</div>						                                               
    				</div>
				</div>
			</div>
		</div>
	</div>
		
	<script type='text/javascript'>	
		$(function(){
			
			// 전산관리자 checkbox 누르기
			var adCheckMem = [];//체크한 사번 넣을 곳
			$('#adminList tr td input[name=adCheckMem]').click(function(){				
				if($(this).is(':checked')){
					adCheckMem.push($(this).val());
				} else {
					adCheckMem.splice(adCheckMem.indexOf($(this).val()),1);	
				}
			});
			
			// 인사관리자 checkbox 누르기
			var peCheckMem = [];//체크한 사번 넣을 곳
			$('#personnelList tr td input[name=peCheckMem]').click(function(){				
				if($(this).is(':checked')){
					peCheckMem.push($(this).val());
				} else {
					peCheckMem.splice(peCheckMem.indexOf($(this).val()),1);	
				}
			});
			
			// 총무관리자 checkbox 누르기
			var geCheckMem = [];//체크한 사번 넣을 곳
			$('#generalList tr td input[name=geCheckMem]').click(function(){				
				if($(this).is(':checked')){
					geCheckMem.push($(this).val());
				} else {
					geCheckMem.splice(geCheckMem.indexOf($(this).val()),1);	
				}
			});
			
			$('#tbody').on('click','.mCheck',function(){
				checkboxClick($(this));	
			});
			
			// 모달 열기 
			var rightData = "";
			$('.agreeButtonType').click(function(){
				rightData += $(this).parents('.adminSpace').children().first().text();//부서관리자 구별 넣기
				var modatop = $(this).offset().top;
				<% ArrayList<Member> list = (ArrayList<Member>)request.getAttribute("list"); %>
				
				if($(this).parents('.adminSpace').children().first().text()=='전산관리자') {
					mCheck = [];
					$('#modal').css('top',modatop);
					$("#tbody *").remove();
						<% for(Member m : list){%>
							var deptCode = '<%=m.getDeptCode()%>';
							var memRightCode = '<%=m.getMemRightCode()%>';
							if((deptCode.indexOf('D3')>-1)&&(memRightCode!='B')){
								var tr = $("<tr></tr>").appendTo("#tbody");
								$("<td><input type='checkbox' class='mCheck' value='<%=m.getMemNo()%>'/></td>").appendTo(tr);
								$("<td></td>").text('<%=m.getMemNo()%>').appendTo(tr);
								$("<td></td>").text('<%=m.getMemName()%>').appendTo(tr);
								$("<td></td>").text('<%=m.getMemPosition()%>').appendTo(tr);
							}
						<%}%>
						
					$('#keyword').next().children().click(function(){
						mCheck = [];
						var keyword = $('#keyword').val();
						$.ajax({
							url : '/adminSearchModal.ho',
							data : {'keyword':keyword},
							type : 'post',
							success : function(data) {
								 		$("#tbody *").remove();
										for(var i=0; i<data.length; i++) {
											var deptCode = data[i].deptCode;
											var memRightCode = data[i].memRightCode;
											if((deptCode.indexOf('D3')>-1)&&(memRightCode!='B')){
												var tr = $("<tr></tr>").appendTo("#tbody");
												$("<td><input type='checkbox' class='mCheck' value=" + data[i].memNo + "></td>").appendTo(tr);
												$("<td></td>").text(data[i].memNo).appendTo(tr);
												$("<td></td>").text(data[i].memName).appendTo(tr);
												$("<td></td>").text(data[i].memPosition).appendTo(tr);
											}
										}
									},
							error : function(){
								alert('오류가 발생하였습니다.');
							}		
						});
					});
				} else if ($(this).parents('.adminSpace').children().first().text()=='인사관리자') {
					mCheck = [];
					$('#modal').css('top',modatop);				
					$("#tbody *").remove();
					<% for(Member m : list){%>
						var deptCode = '<%=m.getDeptCode()%>';
						var memRightCode = '<%=m.getMemRightCode()%>';
						if((deptCode.indexOf('D1')>-1)&&(memRightCode!='C')){
							var tr = $("<tr></tr>").appendTo("#tbody");
							$("<td><input type='checkbox' class='mCheck' value='<%=m.getMemNo()%>'/></td>").appendTo(tr);
							$("<td></td>").text('<%=m.getMemNo()%>').appendTo(tr);
							$("<td></td>").text('<%=m.getMemName()%>').appendTo(tr);
							$("<td></td>").text('<%=m.getMemPosition()%>').appendTo(tr);
						}
					<%}%>
					
					$('#keyword').next().children().click(function(){
						var keyword = $('#keyword').val();
						$.ajax({
							url : '/adminSearchModal.ho',
							data : {'keyword':keyword},
							type : 'post',
							success : function(data) {
								 		$("#tbody *").remove();
										for(var i=0; i<data.length; i++) {
											var deptCode = data[i].deptCode;
											var memRightCode = data[i].memRightCode;
											if((deptCode.indexOf('D1')>-1)&&(memRightCode!='C')){
												var tr = $("<tr></tr>").appendTo("#tbody");
												$("<td><input type='checkbox' class='mCheck' value=" + data[i].memNo + "></td>").appendTo(tr);
												$("<td></td>").text(data[i].memNo).appendTo(tr);
												$("<td></td>").text(data[i].memName).appendTo(tr);
												$("<td></td>").text(data[i].memPosition).appendTo(tr);
											}
										}
									},
							error : function(){
								alert('오류가 발생하였습니다.');
							}
						});
					});
				} else if ($(this).parents('.adminSpace').children().first().text()=='총무관리자') {
					mCheck = [];
					$('#modal').css('top',modatop);
					$("#tbody *").remove();
					<% for(Member m : list){%>
						var deptCode = '<%=m.getDeptCode()%>';
						var memRightCode = '<%=m.getMemRightCode()%>';
						if((deptCode.indexOf('D2')>-1)&&(memRightCode!='D')){
							var tr = $("<tr></tr>").appendTo("#tbody");
							$("<td><input type='checkbox' class='mCheck' value='<%=m.getMemNo()%>'/></td>").appendTo(tr);
							$("<td></td>").text('<%=m.getMemNo()%>').appendTo(tr);
							$("<td></td>").text('<%=m.getMemName()%>').appendTo(tr);
							$("<td></td>").text('<%=m.getMemPosition()%>').appendTo(tr);
						}
					<%}%>

					$('#keyword').next().children().click(function(){
						var keyword = $('#keyword').val();
						$.ajax({
							url : '/adminSearchModal.ho',
							data : {'keyword':keyword},
							type : 'post',
							success : function(data) {
								 		$("#tbody *").remove();
										for(var i=0; i<data.length; i++) {
											var deptCode = data[i].deptCode;
											var memRightCode = data[i].memRightCode;
											if((deptCode.indexOf('D2')>-1)&&(memRightCode!='D')){
												var tr = $("<tr></tr>").appendTo("#tbody");
												$("<td><input type='checkbox' class='mCheck' value=" + data[i].memNo + "></td>").appendTo(tr);
												$("<td></td>").text(data[i].memNo).appendTo(tr);
												$("<td></td>").text(data[i].memName).appendTo(tr);
												$("<td></td>").text(data[i].memPosition).appendTo(tr);
											}
										}

									},
							error : function(){
								alert('오류가 발생하였습니다.');
							}
						});
					});
				}
				$('#modal').show();
			});
			
			// 모달 닫기
            $('#modal .exit-icon').click(function(){
            	rightData = "";
                $('#modal').hide();
                $('#modal input').val('');
                $("#tbody *").remove();
            });
			
			//모달 checkbox
			function checkboxClick(data){
				if(data.prop('checked')){					
					mCheck.push(data.val());
				} else {
					mCheck.splice(mCheck.indexOf(data.val()),1);
				}
			}		
		
			//모달 권한 부여
			$('#modal').children().children().last().click(function(){
				if(mCheck.length==0){
					alert('관리자 권한을 부여할 사원을 선택해주세요.');
				} else {
					if(rightData=='전산관리자'){
						if(confirm("전산관리자 권한을 부여하시겠습니까?")){
							$.ajax({
								url: '/adminUpdateAdRight.ho',
								data : {'memNoList':mCheck},
								type : 'post',
								success : function(result){
									alert("전산관리자 권한 부여를 성공하였습니다.");
									history.go(0);
								},
								error : function(){
									alert("전산관리자 권한 부여를 실패하였습니다.");
								}
							});
						}
					} else if(rightData=='인사관리자'){
						if(confirm("인사관리자 권한을 부여하시겠습니까?")){
							$.ajax({
								url: '/adminUpdatePeRight.ho',
								data : {'memNoList':mCheck},
								type : 'post',
								success : function(result){
									alert("인사관리자 권한 부여를 성공하였습니다.");
									history.go(0);
								},
								error : function(){
									alert("인사관리자 권한 부여를 실패하였습니다.");
								}
							});
						}
					} else if(rightData=='총무관리자'){
						if(confirm("총무관리자 권한을 부여하시겠습니까?")){
							$.ajax({
								url: 'adminUpdateGeRight.ho',
								data : {'memNoList':mCheck},
								type : 'post',
								success : function(result){
									alert("총무관리자 권한 부여를 성공하였습니다.");
									history.go(0);
								},
								error : function(){
									alert("총무관리자 권한 부여를 실패하였습니다.");
								}
							});
						}
					}
					
				}
			});
		
		//관리자 권한 삭제
		$('.refuseButtonType').click(function(){
			if($(this).parents('.adminSpace').children().first().text()=='전산관리자') {
				if(adCheckMem.length==0){
					alert('전산관리자 권한을 삭제할 사원을 선택해주세요.');
				} else {
					if(confirm("전산관리자 권한을 삭제하시겠습니까?")){
						$.ajax({
							url: '/adminDeleteRight.ho',
							data : {'memNoList':adCheckMem},
							type : 'post',
							success : function(result){
								alert("전산관리자 권한을 삭제하였습니다.");
								history.go(0);
							},
							error : function(){
								alert("전산관리자 권한 삭제를 실패하였습니다.");
							}
						});
					}
				}
			} else if ($(this).parents('.adminSpace').children().first().text()=='인사관리자') {
				if(peCheckMem.length==0){
					alert('인사관리자 권한을 삭제할 사원을 선택해주세요.');
				} else {
					if(confirm("인사관리자 권한을 삭제하시겠습니까?")){
						$.ajax({
							url: '/adminDeleteRight.ho',
							data : {'memNoList':peCheckMem},
							type : 'post',
							success : function(result){
								alert("인사관리자 권한을 삭제하였습니다.");
								history.go(0);
							},
							error : function(){
								alert("인사관리자 권한 삭제를 실패하였습니다.");
							}
						});
					}
				}
			} else if ($(this).parents('.adminSpace').children().first().text()=='총무관리자') {
				if(geCheckMem.length==0){
					alert('총무관리자 권한을 삭제할 사원을 선택해주세요.');
				} else {
					if(confirm("총무관리자 권한을 삭제하시겠습니까?")){
						$.ajax({
							url: '/adminDeleteRight.ho',
							data : {'memNoList':geCheckMem},
							type : 'post',
							success : function(result){
								alert("총무관리자 권한을 삭제하였습니다.");
								history.go(0);
							},
							error : function(){
								alert("총무관리자 권한 삭제 실패하였습니다.");
							}
						});
					}
				}
			}		
		});
	});
			$adminUpdate = $('#adminUpdate');
	        $adminUpdate.children().eq(2).children().addClass('fa-rotate-180');		
			$adminUpdate.removeClass('hoverColor').addClass('click');
	        $adminUpdate.children().eq(2).children().attr('class','iArrow fas fa-angle-left');		

	</script>
</body>
</html>