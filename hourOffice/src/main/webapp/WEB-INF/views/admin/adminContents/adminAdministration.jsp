<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                                    <th><input type="checkbox" name="checkMem"/></th>
                                    <th>이름 (이메일)</th>
                                    <th>부서</th>
                                    <th>관리자 등록일</th>
                                </tr>                                
                                <c:forEach items="${list}" var="li" varStatus="status">  
                                	<c:if test="${li.memRightCode eq 'B'.charAt(0)}">	                                                                	  
	                                	<tr>
		                                    <td><input type="checkbox" name="checkMem" value="${admin.memNo}"/></td>
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
                                    <th><input type="checkbox" name="checkMem"/></th>
                                    <th>이름 (이메일)</th>
                                    <th>부서</th>
                                    <th>관리자 등록일</th>
                                </tr>
                                <c:forEach items="${list}" var="li" varStatus="status">  
                                	<c:if test="${li.memRightCode eq 'C'.charAt(0)}">	                                                                	  
	                                	<tr>
		                                    <td><input type="checkbox" name="checkMem" value="${admin.memNo}"/></td>
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
                                    <th><input type="checkbox" name="checkMem"/></th>
                                    <th>이름 (이메일)</th>
                                    <th>부서</th>
                                    <th>관리자 등록일</th>
                                </tr>
                                <c:forEach items="${list}" var="li" varStatus="status">  
                                	<c:if test="${li.memRightCode eq 'D'.charAt(0)}">	                                                                	  
	                                	<tr>
		                                    <td><input type="checkbox" name="checkMem" value="${admin.memNo}"/></td>
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
									<input type="text" id="keyword" name="keyword"/>
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
			
			// 전산관리자 checkbox 전부 누르기		
			$('#adminList').find('input').first().click(function(){
							
				if($(this).prop('checked')) { 
					$('#adminList input').each(function(){
						$(this).prop('checked',true);
					});
				} else {
					$('#adminList input').each(function(){
						$(this).prop('checked',false);
					});
				}	
			});
			
			// 인사관리자 checkbox 전부 누르기		
			$('#personnelList').find('input').first().click(function(){
							
				if($(this).prop('checked')) { 
					$('#personnelList input').each(function(){
						$(this).prop('checked',true);
					});
				} else {
					$('#personnelList input').each(function(){
						$(this).prop('checked',false);
					});
				}	
			});
			
			// 총무관리자 checkbox 전부 누르기		
			$('#generalList').find('input').first().click(function(){
							
				if($(this).prop('checked')) { 
					$('#generalList input').each(function(){
						$(this).prop('checked',true);
					});
				} else {
					$('#generalList input').each(function(){
						$(this).prop('checked',false);
					});
				}	
			});
			
			// 모달 열기
			$('.agreeButtonType').click(function(){
				<% ArrayList<Member> list = (ArrayList<Member>)request.getAttribute("list"); %>
				var modatop = $(this).offset().top;
				$('#tbody').html('');
			
				if($(this).parents('.adminSpace').children().first().text()=='전산관리자') {
					$('#modal').css('top',modatop);
					$('#keyword').next().children().click(function(){
						var keyword = $('#keyword').val();
						$.ajax({
							url : '/adminSearchModal.ho',
							data : {'keyword':keyword},
							type : 'post',
							success : function(data) {
								list = data;
								for(var i=0; i<list.length; i++){
									alert(list[i].memNo);
								}
								alert(list+"기");
							},
							error : function(){
								alert('안 나온다');
							}
						});
					});
										
					<% for(Member m : list){%>
							var deptCode = '<%=m.getDeptCode()%>';
						if(deptCode.indexOf('D3')>-1){
							var tr = $("<tr></tr>").appendTo("#tbody");
							$("<td><input type='checkbox'></td>").appendTo(tr);
							$("<td></td>").text('<%=m.getMemNo()%>').appendTo(tr);
							$("<td></td>").text('<%=m.getMemName()%>').appendTo(tr);
							$("<td></td>").text('<%=m.getMemPosition()%>').appendTo(tr);
						}
					<%}%>					
				} else if ($(this).parents('.adminSpace').children().first().text()=='인사관리자') {
					$('#modal').css('top',modatop);					
		           	<% for(Member m : list){%>
		           		 	var deptCode = '<%=m.getDeptCode()%>';
		           		if(deptCode.indexOf('D1')>-1){
		           			var tr = $("<tr></tr>").appendTo("#tbody");
							$("<td><input type='checkbox'></td>").appendTo(tr);
							$("<td></td>").text('<%=m.getMemNo()%>').appendTo(tr);
							$("<td></td>").text('<%=m.getMemName()%>').appendTo(tr);
							$("<td></td>").text('<%=m.getMemPosition() %>').appendTo(tr);
		           		}
		           	<% } %>
				} else if ($(this).parents('.adminSpace').children().first().text()=='총무관리자') {
					$('#modal').css('top',modatop);
					<% for(Member m : list){%>
		           		 	var deptCode = '<%=m.getDeptCode()%>';
		           		if(deptCode.indexOf('D2')>-1){
		           			var tr = $("<tr></tr>").appendTo("#tbody");
							$("<td><input type='checkbox'></td>").appendTo(tr);
							$("<td></td>").text('<%=m.getMemNo()%>').appendTo(tr);
							$("<td></td>").text('<%=m.getMemName()%>').appendTo(tr);
							$("<td></td>").text('<%=m.getMemPosition() %>').appendTo(tr);
		           		}
		           	<% } %>
				};
				$('#modal').show();				
			});
			
			// 모달 닫기
            $('#modal .exit-icon').click(function(){
                $('#modal').hide();
                $('#modal input').val('');
            });
			
		});
		
		$('#searchBtn').click(function(){
			$.ajax({
				url:"/adminSearchedList.ho",
				type:"get",
				data:{keyword:$("#keyword").val()},
				success:function(data){
					var len = $("input[name='checkMem']:checked").length;
					if(len>1){	
						$("input[name='checkMem']:checked").each(function(e){
							console.log($(this).val())
						})
					}
				},
				error: function(){
					alert('searchKeyword에서 에러 발생!');
				}
			})
		});

			$adminUpdate = $('#adminUpdate');
	        $adminUpdate.children().eq(2).children().addClass('fa-rotate-180');		
			$adminUpdate.removeClass('hoverColor').addClass('click');
	        $adminUpdate.children().eq(2).children().attr('class','iArrow fas fa-angle-left');		

	</script>
</body>
</html>