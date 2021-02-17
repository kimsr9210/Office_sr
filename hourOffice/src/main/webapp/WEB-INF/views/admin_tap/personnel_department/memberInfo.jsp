<%@page import="kr.or.houroffice.member.model.vo.Department"%>
<%@page import="kr.or.houroffice.member.model.vo.Career"%>
<%@page import="kr.or.houroffice.member.model.vo.License"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.or.houroffice.member.model.vo.AcademicAbility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>
	<!-- 폰트어썸 -->
    <script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
    
    <!--jQuery CDN-->
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	
	<!-- 헤더 & 네비 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
	<!-- 관리자 탭 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin_tap/adminTapCommon.css" />
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin_tap/memberInfo.css" />
	
<script>
    // 생년월일 선택 목록 셋팅
  $(document).ready(function () {
    setDateBox();
  });
    
  // select box 연도 , 월 표시
  function setDateBox() {
    var dt = new Date();
    var year = "";
    var com_year = dt.getFullYear();

    // 발행 뿌려주기
    $("#birthYear").append("<option value=''>년도</option>");

    // 올해 기준으로 -50년부터 +1년을 보여준다.
    for (var y = (com_year - 60); y <= (com_year); y++) {
      $("#birthYear").append("<option value='" + y + "'>" + y + " 년" + "</option>");
    }

    // 월 뿌려주기(1월부터 12월)
    var month;
    $("#birthMonth").append("<option value=''>월</option>");
    for (var i = 1; i <= 12; i++) {
        if(i<10){
            $("#birthMonth").append("<option value='0" + i + "'>0" + i + " 월" + "</option>");
        }else{
            $("#birthMonth").append("<option value='" + i + "'>" + i + " 월" + "</option>");
        }
    }

    // 일 뿌려주기(1일부터 31일)
    var day;
    $("#birthDay").append("<option value=''>일</option>");
    for (var i = 1; i <= 31; i++) {
        if(i<10){
            $("#birthDay").append("<option value='0" + i + "'>0" + i + " 일" + "</option>");
        }else{
            $("#birthDay").append("<option value='" + i + "'>" + i + " 일" + "</option>");
        }
    }

  }

</script>


<style>
	/* img 태그 div */
        #TitleContents #memProfile{
            width: 177px; height: 236px;
            margin: 0 auto;
            margin-bottom: 50px;
        }
</style>
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
						인사관리 통합사원 <span id="subtitle"> 인사 정보</span>
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->
						
						
						<div id="contWrapper">
                    <form action="/admin_tap_modifyMemberInfo.ho" method="post">
                        <div class="floatDiv">
                            <div><input type="text" name="memNo" value="${member.memNo }" style="display:none"/>
                                사번 : ${member.memNo }<br><br>
                            </div>
                            <div id="memProfile"><img src="/resources/images/profile/${member.memProfile }"/><br></div>
                            <div id="position_dept">
                                <div class="posi_deptDiv">
                                    직위<br>
                                    <select name="memPosition" class="posi_deptSelect">
                                        <option value=""></option>
                                        <option value="사원">사 &nbsp;&nbsp;&nbsp;원</option>
                                        <option value="대리">대 &nbsp;&nbsp;&nbsp;리</option>
                                        <option value="과장">과 &nbsp;&nbsp;&nbsp;장</option>
                                        <option value="차장">차 &nbsp;&nbsp;&nbsp;장</option>
                                        <option value="부장">부 &nbsp;&nbsp;&nbsp;장</option>
                                        <option value="임원">임 &nbsp;&nbsp;&nbsp;원</option>
                                        <option value="이사">이 &nbsp;&nbsp;&nbsp;사</option>
                                        <option value="대표">대 &nbsp;&nbsp;&nbsp;표</option>
                                    </select>
                                </div>
                                <div class="posi_deptDiv">
                                    부서<br>
                                    <select name="deptCode" class="posi_deptSelect">
                                        <option value=""></option>
                            <% ArrayList<Department> deptList = (ArrayList<Department>)request.getAttribute("deptList"); %>
                 			<% for(Department dept : deptList){ %>
                                        <option value="<%=dept.getDeptCode() %>"><%=dept.getDeptName() %></option>
                            <% } %>
                                    </select>
                                    <!--<div class="select__arrow"></div>-->
                                </div>
                            </div>
                        </div>
                        <div id="rightCont" class="floatDiv">
                           <div>
                                이름<br>
                                <input type="text" name="memName" class="inputStyle" value="${member.memName }" readonly/>
                           </div>
                           <div class="infoRespective">
                               <div id="birthDiv">
                                    생년월일<br>
                                    <select name="memBirth1" id="birthYear" class="memBirthSelect">
                                    </select>
                                    <select name="memBirth2" id="birthMonth" class="memBirthSelect">
                                    </select>
                                    <select name="memBirth3" id="birthDay" class="memBirthSelect">
                                    </select>
                                </div>
                                <div id="genderDiv">
                                    성별<br>
                                    <input type="radio" name="memGender" class="genderInput" value="M" disabled/> 남 <input type="radio" name="memGender" class="genderInput" value="F" disabled/> 여
                                </div>
                           </div>
                           <hr color="white">
                           <div class="infoRespective">
                               현주소<br>
                           		<input type="text" name="memAddr2" class="inputStyle" value="${member.memAddress }" readonly/>
                           </div>
                           <div class="infoRespective">
                               연락처<br>
                           		<input type="text" name="memAddr2" class="inputStyle" value="${member.memPhone }" readonly/>
                           </div>
                        </div>
                        
                        
                        
                        <div>
                            <div class="infoRespective">
                                학력<br>
                                <hr class="innerHr">
                                 <div id="acaInforNullPlace" class="float" style="display:none;">기간 <input type="date" name="acaEnrollDate" class="dateInput" /> ~ <input type="date" name="acaGradDate" class="dateInput" /> &nbsp;학교명 <input type="text" name="acaSchoolName" id="schoolNameInput" /> &nbsp;전공 <input type="text" name="acaMajorName" id="majorNameInput" /> &nbsp;졸업여부 
                                <select name="acaGrad" id="gradSelect">
                                    <option value=""></option>
                                    <option value="졸업">졸 업</option>
                                    <option value="졸업예정">졸업예정</option>
                                    <option value="휴학">휴 학</option>
                                    <option value="중퇴">중 퇴</option>
                                </select></div>
                        <% int index = 1; %>
                        <% ArrayList<AcademicAbility> acaList = (ArrayList<AcademicAbility>)request.getAttribute("acaList"); %>
                        <% if(acaList.isEmpty()){ %>
                        		<div id="acaInfor" class="float">기간 <input type="date" name="acaEnrollDate" class="dateInput" /> ~ <input type="date" name="acaGradDate" class="dateInput" /> &nbsp;학교명 <input type="text" name="acaSchoolName" id="schoolNameInput" /> &nbsp;전공 <input type="text" name="acaMajorName" id="majorNameInput" /> &nbsp;졸업여부 
                                <select name="acaGrad" id="gradSelect">
                                    <option value=""></option>
                                    <option value="졸업">졸 업</option>
                                    <option value="졸업예정">졸업예정</option>
                                    <option value="휴학">휴 학</option>
                                    <option value="중퇴">중 퇴</option>
                                </select></div> 
                                <div class="float"><div class="plusBtn float" onclick="plusBtn('acaInfor')">+</div></div>
                        <% } %>
               			<% for(int acaN=0; acaN<acaList.size(); acaN++){ %>
                                <div id="acaInfor" class="float">
                                	기간 <input type="date" name="acaEnrollDate" class="dateInput" value="<%=acaList.get(acaN).getAcaEnrollDate()%>"/> 
                                	~ <input type="date" name="acaGradDate" class="dateInput" value="<%=acaList.get(acaN).getAcaGradDate()%>"/> 
                                	&nbsp;학교명 <input type="text" name="acaSchoolName" id="schoolNameInput" value="<%=acaList.get(acaN).getAcaSchoolName()%>"/> 
                                	&nbsp;전공 <input type="text" name="acaMajorName" id="majorNameInput" value="<%=acaList.get(acaN).getAcaMajorName()%>"/> 
                                	&nbsp;졸업여부 
	                                <select name="acaGrad" id="gradSelect">
	                                    <option value=""></option>
	                                    <option value="졸업">졸 업</option>
	                                    <option value="졸업예정">졸업예정</option>
	                                    <option value="휴학">휴 학</option>
	                                    <option value="중퇴">중 퇴</option>
	                                </select>
	                            </div> 
                                <div class="float">
                                	<div class="plusBtn float" onclick="plusBtn('acaInfor')">+</div>
               	<script>
               		$('#gradSelect option').each(function(){
               			if($(this).val()=='<%=acaList.get(acaN).getAcaGrad() %>') 
               				$(this).prop('selected',true);
               			
               		});
               	</script>
                    	<% if(acaN>0){ %>
                                	<div id="index<%=index %>" class="minusBtn float" onclick="minusBtn('index<%=index%>')">-</div>
                        <% } %> 
                        		</div>   
               			<% index++;} %>
                            </div>
                            <hr class="outHr">
                            <div id="licenseDiv" class="infoRespective">
                                자격증 및 면허<br>
                                <hr class="innerHr">
                                <div id="licInforNullPlace" class="float" style="display:none;"> 취득일자 <input type="date" name="licDate" class="dateInput" /> &nbsp;자격증명 <input type="text" name="licName" id="licNameInput" /> &nbsp;시행처 <input type="text" name="licOrigin" id="licOriginInput"/></div> 
                  		<% ArrayList<License> licList = (ArrayList<License>)request.getAttribute("licList"); %>
                  		<% if(licList.isEmpty()){ %>
                  				<div id="licInfor" class="float"> 취득일자 <input type="date" name="licDate" class="dateInput" /> &nbsp;자격증명 <input type="text" name="licName" id="licNameInput" /> &nbsp;시행처 <input type="text" name="licOrigin" id="licOriginInput"/></div> 
                  				<div class="float"><div class="plusBtn float" onclick="plusBtn('licInfor')">+</div></div>
                  		<% } %>
                  		<% for(int licN=0; licN<licList.size(); licN++){ %>
                                <div id="licInfor" class="float">
                                	취득일자 <input type="date" name="licDate" class="dateInput" value="<%=licList.get(licN).getLicDate() %>" /> 
                                	&nbsp;자격증명 <input type="text" name="licName" id="licNameInput" value="<%=licList.get(licN).getLicName() %>"/> 
                                	&nbsp;시행처 <input type="text" name="licOrigin" id="licOriginInput" value="<%=licList.get(licN).getLicOrigin()%>"/>
                                </div> 
                                <div class="float">
                                	<div class="plusBtn float" onclick="plusBtn('licInfor')">+</div>
                      	<% if(licN>0){ %>
                      				<div id="index<%=index %>" class="minusBtn float" onclick="minusBtn('index<%=index%>')">-</div>
                      	<% } %>
                                </div>
                   		<% } %>
                            </div>
                            <hr class="outHr">
                            <div id="careerDiv" class="infoRespective">
                                경력<br>
                                <hr class="innerHr">
                                <div id="carInforNullPlace" class="float" style="display:none;"> 기간 <input type="date" name="carJoinDate" class="dateInput" /> ~ <input type="date" name="carResignDate" class="dateInput" /> &nbsp;근무처 <input type="text" name="carPlace" id="carPlaceInput" /> &nbsp;직위 <input type="text" name="carPosition" id="carPositionInput"/> &nbsp;업무내용 <input type="text" name="carContent" id="carContentInput" /></div> 
                     	<% ArrayList<Career> carList = (ArrayList<Career>)request.getAttribute("carList"); %>
                     	<% if(carList.isEmpty()){ // 리스트가 비어져있다면 입력 공간 출력%>
                     		<div id="carInfor" class="float"> 기간 <input type="date" name="carJoinDate" class="dateInput" /> ~ <input type="date" name="carResignDate" class="dateInput" /> &nbsp;근무처 <input type="text" name="carPlace" id="carPlaceInput" /> &nbsp;직위 <input type="text" name="carPosition" id="carPositionInput" /> &nbsp;업무내용 <input type="text" name="carContent" id="carContentInput"/></div> <div class="float"><div class="plusBtn float" onclick="plusBtn('carInfor')">+</div></div>
                     	<% } %>
                     	<% for(int carN=0; carN<carList.size(); carN++){ %>
                                <div id="carInfor" class="float">
                                	기간 <input type="date" name="carJoinDate" class="dateInput" value="<%=carList.get(carN).getCarJoinDate() %>"/> 
                                	~ <input type="date" name="carResignDate" class="dateInput" value="<%=carList.get(carN).getCarResignDate() %>"/> 
                                	&nbsp;근무처 <input type="text" name="carPlace" id="carPlaceInput" value="<%=carList.get(carN).getCarPlace() %>"/> 
                                	&nbsp;직위 <input type="text" name="carPosition" id="carPositionInput" value="<%=carList.get(carN).getCarPosition() %>"/> 
                                	&nbsp;업무내용 <input type="text" name="carContent" id="carContentInput" value="<%=carList.get(carN).getCarContent() %>"/>
                                </div> 
                                <div class="float">
                                	<div class="plusBtn float" onclick="plusBtn('carInfor')">+</div>
                     	<% if(carN>0){ %>
                                	<div id="index<%=index %>" class="minusBtn float" onclick="minusBtn('index<%=index%>')">-</div>
                        <% } %>
                                </div>
                     	<% } %>
                            </div>
                            <hr class="outHr"><br>
                            <div id="militaryDiv" class="infoRespective">
                                병역<br>
                                <hr class="innerHr">
                                <div id="milInfor" class="float">복무여부 <select name="milServiceType" id="milTypeSelect">
                                    <option value=""></option>
                                    <option value="군필">군필</option>
                                    <option value="미필">미필</option>
                                    <option value="면제">면제</option>
                                </select> &nbsp;복무기간 <input type="date" name="milJoinDate" class="dateInput" value="${mil.milJoinDate }"/> ~ <input type="date" name="milLeaveDate" class="dateInput" value="${mil.milLeaveDate }"/> &nbsp;사유 <input type="text" name="milReason" id="milReasonInput" value="${mil.milReason }"/></div> <div class="float"></div>
                            </div>
                        </div>
                        
                        <div id="saveDiv"><button id="submit-btn">수정</button> <a href="/admin_tap_allListMember.ho"><button type="button" class="delBtn">취소</button></a></div>
                        
                    </form>    
                    </div>
                        <script>
                        
                            // + - 버튼 이름
                            var num =1;
                            // + div
                            function plusBtn(id){
                                var minusNum = 'minus'+num;
                                var infor = $('#'+id+'NullPlace').html();
                                var addTag = $('#'+id).parent();
                                var plus = '<div class="plusBtn float" onclick="plusBtn(\''+id+'\')">+</div>';
                                var minus = '<div id="'+minusNum+'" class="minusBtn float" onclick="minusBtn(\''+minusNum+'\')">-</div>';

                                var result = '<div id="'+id+'" class="float">'+infor+'</div> <div class="float">'+plus+minus+'</div>';
                                addTag.append(result);
                                num++;
                            }
                            // - div
                            function minusBtn(id){
                            	var $id = $('#'+id).parent().prev().attr('id');
                                $('#'+id).parent().prev().remove();
                                $('#'+id).parent().remove();
                            
                            }
                            $(function(){
                            	// 출력 값이 null 이면 빈 값으로 바꿈
                            	$('input[type=text]').each(function(){
                            		if($(this).val()=='null'){
                            			$(this).val('');
                            		}
                            	});
                                // 직위 & 부서 출력
                                $('.posi_deptSelect option').each(function(){
                                    if($(this).val()=='${member.memPosition}'){
                                        $(this).prop('selected',true);
                                    }
                                    if($(this).val()=='${member.deptCode}'){
                                        $(this).prop('selected',true);
                                    }
                                })
                                // 생년월일 출력
                                var birth = '${member.memBirth}'; // 생년월일 값 변수에 저장
                                var birthArr = birth.split('-'); // - 를 기준으로 문자열을 잘라 배열에 넣기
                                $('#birthYear option').each(function(){
                                  if($(this).val()==birthArr[0]){
                                      $(this).prop('selected',true);
                                  }
                                  $(this).not(":selected").attr("disabled", "disabled"); // 선택 막기
                                });
                                $('#birthMonth option').each(function(){
                                    if($(this).val()==birthArr[1]){
                                        $(this).prop('selected',true);
                                    }
                                    $(this).not(":selected").attr("disabled", "disabled"); // 선택 막기
                                });
                                $('#birthDay option').each(function(){
                                    if($(this).val()==birthArr[2]){
                                        $(this).prop('selected',true);
                                    }
                                    $(this).not(":selected").attr("disabled", "disabled"); // 선택 막기
                                });
                                // 성별 출력
                                $('#genderDiv .genderInput').each(function(){
                                	if($(this).val()=='${member.memGender}'){
                                		$(this).prop('checked',true);
                                	}
                                });
                                // 연락처 출력
                                var phone = '${member.memPhone}'.split('-');
                                $('#phoneSelect option').each(function(){
                                    if($(this).val()==phone[0]){
                                       $(this).prop('selected',true);
                                   } 
                                });
                                // 병역 복무여부 출력
                                $('#milInfor option').each(function(){
                                   if($(this).val()=='${mil.milServiceType}'){
                                       $(this).attr('selected',true);
                                   } 
                                });
                                
                                $('#submit-btn').click(function(){
                            		// submit 전에 거르기
                            		
    	                        	if($('input[name=acaEnrollDate]').val()==''){
    	                        		$('input[name=acaEnrollDate]').val('0001-01-01');
    	                        	}
    	                        	if($('input[name=acaGradDate]').val()==''){
    	                        		$('input[name=acaGradDate]').val('0001-01-01');
    	                        	}
    	                        	if($('input[name=licDate]').val()==''){
    	                        		$('input[name=licDate]').val('0001-01-01');
    	                        	}
    	                        	if($('input[name=carJoinDate]').val()==''){
    	                        		$('input[name=carJoinDate]').val('0001-01-01');
    	                        	}
    	                        	if($('input[name=carResignDate]').val()==''){
    	                        		$('input[name=carResignDate]').val('0001-01-01');
    	                        	}
    	                        	if($('input[name=milJoinDate]').val()==''){
    	                        		$('input[name=milJoinDate]').val('0001-01-01');
    	                        	}
    	                        	if($('input[name=milLeaveDate]').val()==''){
    	                        		$('input[name=milLeaveDate]').val('0001-01-01');
    	                        	}
    	                        	
    	                        	$('#contWrapper > form').submit();
                            		
                            	});
                                // 취소 btn 클릭 이벤트
                                $('.delBtn').click(function(){
                                	history.go(-1);
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
	$('#categoryAdmin').next().css('height','200px');
	$('#categoryAdmin').children().last().children().attr('class','fas fa-chevron-left');
	
	$('#categoryAdmin').next().children().eq(1).children().css('font-weight','800');
	$('#categoryAdmin').next().children().eq(1).children().css('color','#ffcc29');
	</script>
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>

	</div>
</body>
</html>