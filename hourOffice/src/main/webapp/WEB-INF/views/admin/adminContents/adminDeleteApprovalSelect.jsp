<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

</head>
<body>
	<div id="wrap">
		<%@ include file ="../adminForm/header.jsp" %>
		
		<div id="contentsBox">
			<%@ include file ="../adminForm/sideNaviBar.jsp" %>
			
			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">
						삭제 조회
						<span>결재안 조회</span>
    				</div>
    				<div id="TitleContents">
    					<div class="searchStyle selectStyle">
    						<select class="optionStyle">
    							<option>문서번호</option>
    							<option>결재 양식</option>
    							<option>기안자</option>
    						</select>
							<input type="text" name="#"/>
							<button><i class="fas fa-search"></i></button>
						</div>
    				
						<table id="approvalList" class="tblStyle">
                                <tr>
                                    <th><input type="checkbox"/></th>
                                    <th>문서번호</th>
                                    <th>결재 양식</th>
                                    <th>제목</th>
                                    <th>기안자</th>
                                    <th>기안일</th>
                                </tr>
                                <tr>
                                    <td><input type="checkbox"/></td>
                                    <td>전산-2021-00004</td>
                                    <td>품의서</td>
                                    <td>문서 테스트</td>
                                    <td>다주빈</td>
                                    <td>2021-01-10</td>
                                </tr>
                    	</table>
                    	<center><span><< < 1 2 3 4 5 > >></span></center>
    					<div class="buttonSet buttonStyle">
                        	<button class="agreeButtonType">복원</button>
                            <button class="refuseButtonType">삭제</button>
                        </div>
    				</div>
				</div>
			</div> 
		</div>
	</div>
	
	<script type='text/javascript'>		
			var $deleteList = $('#deleteList');			
			var $deleteSelect = $('#deleteSelect');
			
            /* 삭제 관리일 때 */
            $deleteSelect.children().eq(2).children().attr('class','iArrow fas fa-angle-left');
            $deleteList.css('height','110px');
            
            $deleteSelect.removeClass('hoverColor').addClass('click');
			$deleteList.removeClass('accordion');
			$deleteList.addClass('click');
			$deleteList.children().eq(2).children().removeClass('hoverColor');	
		
		$(function(){
			// 결재안 조회 checkbox 전부 누르기		
			$('#approvalList').find('input').first().click(function(){
							
				if($(this).prop('checked')) {
					$('#approvalList input').each(function(){
						$(this).prop('checked',true);
					});
				} else {
					$('#approvalList input').each(function(){
						$(this).prop('checked',false);
					});
				}	
			});
		});
		
	</script>
</body>
</html>