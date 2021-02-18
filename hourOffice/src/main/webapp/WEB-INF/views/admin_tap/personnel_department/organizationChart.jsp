<%@page import="kr.or.houroffice.member.model.vo.Department"%>
<%@page import="kr.or.houroffice.member.model.vo.Member"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>
	<!-- 폰트어썸 -->
    <script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
    
    <!--jQuery CDN-->
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	
	<!-- 헤더 &네비 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
	<!-- 관리자 탭 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin_tap/adminTapCommon.css" />
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin_tap/organizationChart.css" />
    
	
</head>
<body>
	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp" %>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp" %>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">
					<div id="TitleName">
						<!--여기서 각자 id 만드시면 됩니다-->
						인사관리 조직도
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->
						<% ArrayList<Member> list = (ArrayList<Member>)request.getAttribute("list"); %>
						
						<div id="contWrapper">
                        <div id="btnDiv">
                            <button type="button" id="addBtn">+</button> <button type="button" class="delBtn">-</button><form action="/admin_tap_dropDepartment.ho" method="post" style="display:none"><input type="text" name="deptCode"/></form>
                        </div>
                        <div id="chart" class="float">
                            <ul id="treeMenu">
                                <li>
                                    <i class="fas fa-building i-icon"></i><a href="#menu-top" class="open">칠판 그룹</a>
                                    <ul id="menu-top">
                          			<% ArrayList<String> deptCodeList = new ArrayList<String>(); %>
									<% for(Member member : list){ %>
									<% if(!deptCodeList.contains(member.getDeptCode()) && member.getDeptCode()!=null){ %>
									<% deptCodeList.add(member.getDeptCode()); %>	
                                        <li>
                                            <a href="#subMenu<%=member.getDeptCode() %>" id="<%=member.getDeptCode() %>" class="open"><i class="fas fa-plus-square i-icon"></i> <span id="deptName-<%=member.getDeptCode() %>"><%=member.getDeptName() %></span></a>
                                            <ul id="subMenu<%=member.getDeptCode() %>">
                                	<% for(Member memInfo : list){ %>

                                	<% if(member.getDeptCode().equals(memInfo.getDeptCode()) && memInfo.getMemName()!=null){ %>
                                                <li><i class="fas fa-user-tie i-icon" style="cursor:default"></i> <a><%=memInfo.getMemName() %></a></li>
                                	<% } %>
                  
                                	<% } %>
                                            </ul>
                                        </li>
                                        
                                	<% }else if(member.getDeptCode()==null){ %>
                                		<li class="deptHaveno" style="margin: 0 5px;"><i class="fas fa-user-tie i-icon" style="cursor:default"></i> <a><%=member.getMemName() %></a></li>
									<% } %>
                                    <% } %>    
                                    </ul>
                                </li>
                            </ul>
                        </div>
                        
                        <div id="infor-div" class="float">
                            <div id="infor-list" class="float">
                            <p>부서명</p><br>
                            <p>부서코드</p><br>
                            </div>
                            <div>
                                <p class="dept-btn-div"><span id="deptName-InInfor">(없음)</span><input type="text" class="deptName"/><span id="modityDept-icon" class="dept-update"><i class="fas fa-pen i-icon iconClass"></i></span></p><br>
                                <p id="deptCode-InInfor">(없음)</p><br>
                            </div>
                            <p>부서원</p>
                            <form id="change-form" action="/admin_tap_changeDepartment.ho" method="post">
                                <table>    
                                    <tr>
                                        <td><input type="checkbox" name="memNo"/>
                                        <td>(사원명)</td>
                                        <td>(직위)</td>
                                    </tr>
                                </table>
                                <div id="positionChange">
                                   <select name="deptCode">
                                       <option value=""></option>
                                <% for(String deptData : deptCodeList){ %>
                                <% for(Member deptInfo : list){ %>
                                <% if(deptData.equals(deptInfo.getDeptCode())){ %>
                                       <option value="<%=deptInfo.getDeptCode() %>"><%=deptInfo.getDeptName() %></option>
                                <% break; } %>
                                <% } %>
                                <% } %>
                                   </select>
                                    <button type="button" id="change-btn">부서이동</button>
                                </div>
                            </form>
                        </div>
                    </div>
						
						
	<div id="modal">
        <div class="modal-content">
            <div class="exit-icon"><i class="far fa-times-circle i-icon"></i></div>

            <div id="modal-left" class="float">
                <p>부서명</p><br>
                <p>부서코드 <i class="fas fa-question-circle i-icon" title="예) D1 또는 P3, 중복비허용"></i></p>
            </div>
            <form action="/admin_tap_addDepartment.ho" method="post">
                <div id="modal-right">
                    <p><input type="text" name="deptName"/></p><br>
                    <p><input type="text" name="deptCode"/></p>
                </div>
                <button type="button" id="modal_close_btn">생성</button>
            </form>
        </div>
   
        <div class="modal-layer"></div>
    </div>
    
    <script>
        $(function(){
            // 조직도 셋팅
            var opener = $("a.open");
            var nested = $("a.open").parent().find("li");
            var nestedCont = $("li > ul > li").parent(); // ul
            var person = $('.fa-user-tie').parent(); // 사원
            nestedCont.hide();
            $('#menu-top').show(); // 칠판 그룹
            person.addClass('member'); // 사원 li 에 class="member" 추가
            $("li:last-child").addClass("end"); // 마지막 li에 class="end" 추가
            $('#infor-div').hide();
            $("a.open").each(function () {
                $(this).on( "click", treeControl );
            });
            
            var deptCode = ""; // 부서 코드 
            function treeControl (e) {
                deptCode = $(this).attr('id');
                
                var tblText = '<tr><td><input type="checkbox" name="memNo"/></td><td></td><td></td></tr>'; // 사원 정보 테이블
                
                if(deptCode!=null){
                	
                	
                <% deptCodeList = new ArrayList<String>(); %>
                <% for(Member member : list){ %>
                <% if(!deptCodeList.contains(member.getDeptCode()) && member.getDeptCode()!=null){ %>
                <% deptCodeList.add(member.getDeptCode()); // 부서코드 중복비허용을 위한 코드 %>
                
                	if(deptCode=='<%=member.getDeptCode() %>'){
                		
                		$('#infor-div #deptName-InInfor').text('<%=member.getDeptName() %>');
                		$('#infor-div #deptCode-InInfor').text('<%=member.getDeptCode() %>');
                <% for(Member memInfo : list){ %>
                <% 	if(member.getDeptCode().equals(memInfo.getDeptCode())){ %>
                		$('#infor-div #change-form > table').html(tblText);
                		tblText += '<tr><td><input type="checkbox" name="memNo"/></td><td></td><td></td></tr>';
                <% }// if %>
                <% }// for %>
               		 	tblText = '';
                	}// if - 추출한 부서코드가 member 부서코드랑 같을 때 - tr을 반복 추가하는 코드
                	
                <% }// if - 부서코드 중복비허용 코드%>
                <% }// for %>
                // 각 부서 정보 내의 사원 리스트 정보 추가하기
                var trNum = 1;
            	<% for(Member memInfor : list){ %>
            		if(deptCode=='<%=memInfor.getDeptCode() %>'){
            	<% if(memInfor.getMemName()!=null){ %>
	            		$('#infor-div #change-form tr:nth-child('+trNum+')').find('td:first-child>input').val('<%=memInfor.getMemNo() %>');
	        			$('#infor-div #change-form tr:nth-child('+trNum+')').find('td:nth-child(2)').text('<%=memInfor.getMemName() %>');
	        			$('#infor-div #change-form tr:nth-child('+trNum+')').find('td:last-child').text('<%=memInfor.getMemPosition() %>');
	        				
            		trNum++;
            	<% }else{ %>
		            	$('#infor-div #change-form tr:nth-child(1)').find('td:first-child>input').val('');
		    			$('#infor-div #change-form tr:nth-child(1)').find('td:nth-child(2)').text('없음');
		    			$('#infor-div #change-form tr:nth-child(1)').find('td:last-child').text('없음');
            	<% } %>            		
            		}
            	<% } %>
            	//---end
                	$('#infor-div #modityDept-icon').show();
                    $('#infor-div').show(); // 정보 div 열기
                }else{
                	$('#infor-div #deptName-InInfor').text('(없음)');
            		$('#infor-div #deptCode-InInfor').text('(없음)');
                <% for(Member member : list){ %>
                	if(<%=member.getDeptCode()%>==null){
                		$('#infor-div #change-form > table').html(tblText);
                		tblText += '<tr><td><input type="checkbox" name="memNo"/></td><td></td><td></td></tr>';
                	}
                <% } %>
                	tblText = '';
                
                var trNum = 1;
                <% for(Member memInfor : list){ %>
               	<% if(memInfor.getDeptCode()==null){ %>
		               	$('#infor-div #change-form tr:nth-child('+trNum+')').find('td:first-child>input').val('<%=memInfor.getMemNo() %>');
		    			$('#infor-div #change-form tr:nth-child('+trNum+')').find('td:nth-child(2)').text('<%=memInfor.getMemName() %>');
		    			$('#infor-div #change-form tr:nth-child('+trNum+')').find('td:last-child').text('<%=memInfor.getMemPosition() %>');
		    			trNum++;
                <% } %>
                <% } %>
                	
                	$('#infor-div #modityDept-icon').hide();
                	$('#infor-div').show(); // 정보 div 열기
                }
                
                $('a').css('background-color',''); // 모든 a 태그 배경 삭제
                $(this).css('background-color','#E7E0E8'); // 해당 a 태그 배경 색칠
                
                $(this).next().show();
                $(this).toggleClass("close");
                
                $(this).children('.i-icon').removeClass('fa-plus-square');
                $(this).children('.i-icon').addClass('fa-minus-square');

                if (! $(this).hasClass("close")) {
                    $(this).next().hide();
                    
                    $(this).children('.i-icon').removeClass('fa-minus-square');
                    $(this).children('.i-icon').addClass('fa-plus-square');
                }
                e.preventDefault();
           }
            
            // 부서 추가
            $('#addBtn').click(function(){
                $('#modal').show();
                
            });
            // 부서 추가
            $('#modal_close_btn').click(function(){
            	var $deptCode = $('#modal-right').find('input[name=deptCode]').val();
            	var count = 0;
            <% ArrayList<Department> deptListAll = (ArrayList<Department>)request.getAttribute("deptListAll"); %>
            <% for(Department deptAll : deptListAll){ %>
            	if($deptCode+" "=='<%=deptAll.getDeptCode()%>'){
            		alert('해당 코드는 사용하실 수 없습니다.(중복)');
            		count++;
            	}
            <% } %>
            	if(count==0){
            		$('#modal form').submit();
            	}
            });
            
            // 부서 삭제
            $('.delBtn').click(function(){
            	if(deptCode!=null){
	                if(confirm('해당 부서를 정말 삭제하시겠습니까?')){
	                	$(this).next().children().val(deptCode);
	                	$(this).next().submit();
	                }
            	}
            });
            
            // 부서 이름 변경
            $('.dept-update').click(function(){
                var newDeptName = $(this).prev().val();
                var deptCode = $(this).parent().next().next().text();
                if($(this).children('.i-icon').attr('class').indexOf('fa-pen')>-1){
                    $(this).children('.i-icon').removeClass('fa-pen').addClass('fa-check-circle');
                    $(this).prev().show('10000').focus().prev().hide();
                }else{
                    if(newDeptName!=""){
                    $.ajax({
                    	url: '/admin_tap_modifyDepartmentName.ho',
                    	data: {'deptName':newDeptName,'deptCode':deptCode},
                    	type: 'post',
                    	success: function(result){
                    		if(result) {
                    			$('#positionChange option').each(function(){
                    				if($(this).val()==deptCode){
                    					$(this).text(newDeptName); // 셀렉트 안에 있는 부서명 수정
                    				}
                    			});
                    			$('#deptName-InInfor').text(newDeptName); // 부서정보 안에 있는 부서명 수정
                    			$('#deptName-'+deptCode).text(newDeptName); // 메뉴트리 무서명 수정
                    			$('.dept-update').children('.i-icon').removeClass('fa-check-circle').addClass('fa-pen');
                                $('.dept-update').prev().hide('10000').val('').prev().show();
                    		}else{
                    			alert('부서명 변경을 실패했습니다. \n지속적인 오류시 관리자에 문의하세요.');
                    		}
                    	},
                    	error: function(){alert('부서명 변경을 실패했습니다. \n관리자에 문의바랍니다.');}
                    });
                    
                    }
                }
                
                
            });
            
            // 부서 이름 변경 취소
            $('body').click(function(e){
                
                var $place = $(e.target).hasClass('dept-btn-div'); // p 태그
                var $deptNameInInfor = $(e.target).is('path'); // i 태그 안의 path 태그
                var $deptName = $(e.target).hasClass('deptName'); // 부서명 span 태그
                var $modityDeptIcon = $(e.target).hasClass('dept-update'); // i 태그 감싼 span 태그
                var $iconClass = $(e.target).hasClass('iconClass'); // i 태그
                if($('.deptName').css('display') != 'none'){
                    if ( ((!$place && !$deptNameInInfor) && (!$deptName && !$modityDeptIcon)) && !$iconClass) {
                        $('.dept-update').children('.i-icon').removeClass('fa-check-circle').addClass('fa-pen');
                        $('.dept-update').prev().hide('10000').val('').prev().show();
                        
                    }
                }
                    e.stopImmediatePropagation(); // 버블링 방지
            });
            
            // 부서 이동 btn
            $('#change-btn').click(function(){
            	// checkbox 들 중에 checked가 존재 하면 실행
            	if($('input[name=memNo]').is(':checked')){ 
	                if(confirm('해당 사원의 부서를 이동하시겠습니까?')){
	                    $('#change-form').submit();
	                }	
            	}
            });
            
            // 모달 닫기
            $('.fa-times-circle').click(function(){
                $('#modal').hide();
                $('#modal input').val('');
            });
        
        
        
        
        })
        
            
       
    </script>  
    
    
    
    
						
						<!----------------------------------->
					</div>
				</div>
			</div>
		</div>
		
	<!-- 자바 스크립트    -->
    <script>
	$('#categoryAdmin').next().css('display','block');
	$('#categoryAdmin').next().css('height','75px');
	$('#categoryAdmin').children().last().children().attr('class','fas fa-chevron-left');
	
	$('#categoryAdmin').next().children().eq(2).children().css('font-weight','800');
	$('#categoryAdmin').next().children().eq(2).children().css('color','#ffcc29');
	</script>
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
	
	

	</div>
</body>
</html>