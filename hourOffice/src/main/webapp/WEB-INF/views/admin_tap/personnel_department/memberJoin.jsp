<%@page import="kr.or.houroffice.member.model.vo.Department"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<!-- 폰트어썸 -->
    <script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
    
    <!--jQuery CDN-->
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	
	<!-- 주소찾기 다음 API -->
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	
	<!-- 헤더 & 네비 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
	<!-- 관리자 탭 공통 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin_tap/adminTapCommon.css" />
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/css/admin_tap/memberInfo.css" />

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
						인사관리 통합사원 <span id="subtitle"> 사원 생성</span>
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->
						
						
						<div id="contWrapper">
                    <form action="/memberSingUp.ho" method="post" enctype="multipart/form-data">
                        <div class="floatDiv">
                        	<div>
                                <div id="img-btn-div"><input type="file" name="memProfile" id="profileImg"/><label for="profileImg" id="profileImg-label">이미지 선택</label></div>
                            </div>
                            <div id="memProfile"><img src="/resources/images/profile/default_profile.png"/><br></div>
                            <div id="position_dept">
                                <div class="posi_deptDiv">직위<br>
                                    <select id="memPosition" name="memPosition" class="posi_deptSelect">
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
                                <div class="posi_deptDiv">부서<br>
                                    <select name="deptCode" class="posi_deptSelect">
                                        <option value=""></option>
                                <% ArrayList<Department> deptList = (ArrayList<Department>)request.getAttribute("deptList"); %>
                                <% for(Department dept : deptList){ %>
                                        <option value="<%=dept.getDeptCode() %>"><%=dept.getDeptName() %></option>
                                <% } %>
                                    </select>
                                </div>
                            </div>
                            
                        </div>
                        <div id="rightCont" class="floatDiv">
                           <div>이름<br>
                                <input type="text" name="memName" class="inputStyle"/>
                           </div>
                           <div class="infoRespective">
                               <div id="birthDiv">생년월일<br>
                                    <select name="memBirth1" id="birthYear" class="memBirthSelect">
                                    </select>
                                    <select name="memBirth2" id="birthMonth" class="memBirthSelect">
                                    </select>
                                    <select name="memBirth3" id="birthDay" class="memBirthSelect">
                                    </select>
                                </div>
                                <div id="genderDiv">성별<br>
                                    <input type="radio" name="memGender" class="genderInput" value="M" checked/> <span>남</span> <input type="radio" name="memGender" class="genderInput" value="F"/> <span>여</span>
                                </div>
                           </div>
                           <hr color="white">
                           <div class="infoRespective">현주소<br>
                               <input type="text" name="memAddress1" id="addrInput" readonly/> <button type="button" onclick="searchAddr()">검색</button><br>
                               <input type="text" name="memAddress2" class="inputStyle"/>
                           </div>
                           <div class="infoRespective">연락처<br>
                               <select name="memPhone1" id="phoneSelect">
                                   <option value=""></option>
                                   <option value="010">010</option>
                               </select> <input type="text" name="memPhone2" size="4" class="phoneInput"/> <input type="text" name="memPhone3" size="4" class="phoneInput"/>
                           </div>
                        </div>
                        
                        
                        
                        <div>
                            <div class="infoRespective">
                                학력<br>
                                <hr class="innerHr">
                                <div id="acaInfor" class="float">기간 <input type="date" name="acaEnrollDate" class="dateInput"/> ~ <input type="date" name="acaGradDate" class="dateInput"/> &nbsp;학교명 <input type="text" name="acaSchoolName" id="schoolNameInput"/> &nbsp;전공 <input type="text" name="acaMajorName" id="majorNameInput"/> &nbsp;졸업여부 
                                <select name="acaGrad" id="gradSelect">
                                    <option value="졸업">졸 업</option>
                                    <option value="졸업예정">졸업예정</option>
                                    <option value="휴학">휴 학</option>
                                    <option value="중퇴">중 퇴</option>
                                </select></div> <div class="float"><div class="plusBtn float" onclick="plusBtn('acaInfor')">+</div></div>
                            </div>
                            <hr class="outHr">
                            <div id="licenseDiv" class="infoRespective">
                                자격증 및 면허<br>
                                <hr class="innerHr">
                                <div id="licInfor" class="float">취득일자 <input type="date" name="licDate" class="dateInput"/> &nbsp;자격증명 <input type="text" name="licName" id="licNameInput"/> &nbsp;시행처 <input type="text" name="licOrigin" id="licOriginInput"/></div> <div class="float"><div class="plusBtn float" onclick="plusBtn('licInfor')">+</div></div>
                            </div>
                            <hr class="outHr">
                            <div id="careerDiv" class="infoRespective">
                                경력<br>
                                <hr class="innerHr">
                                <div id="carInfor" class="float">기간 <input type="date" name="carJoinDate" class="dateInput"/> ~ <input type="date" name="carResignDate" class="dateInput"/> &nbsp;근무처 <input type="text" name="carPlace" id="carPlaceInput"/> &nbsp;직위 <input type="text" name="carPosition" id="carPositionInput"/> &nbsp;업무내용 <input type="text" name="carContent" id="carContentInput"/></div> <div class="float"><div class="plusBtn float" onclick="plusBtn('carInfor')">+</div></div>
                            </div>
                            <hr class="outHr"><br>
                            <div id="militaryDiv" class="infoRespective">
                                병역<br>
                                <hr class="innerHr">
                                <div id="milInfor" class="float">복무여부 <select name="milServiceType" id="milTypeSelect">
                                    <option value="군필">군필</option>
                                    <option value="미필">미필</option>
                                    <option value="면제">면제</option>
                                </select> &nbsp;복무기간 <input type="date" name="milJoinDate" class="dateInput"/> ~ <input type="date" name="milLeaveDate" class="dateInput"/> &nbsp;사유 <input type="text" name="milReason" id="milReasonInput"/></div> <div class="float"></div>
                            </div>
                        </div>
                        
                        <div id="saveDiv"><button type="button">저장 후 추가 생성</button> <button id="submit-btn" type="button">저장</button> <a href=""><button type="button" class="delBtn">취소</button></a></div>
                        
                    </form>    
                    </div>
                        <script>
                        	
                        	
                        
                        
                            // + - 버튼 이름
                            var num =1;
                            // + div
                            function plusBtn(id){
                                var minusNum = 'minus'+num;
                                var infor = $('#'+id).html();
                                var addTag = $('#'+id).parent();
                                var plus = $('#'+id).next().html();
                                var minus = '<div id="'+minusNum+'" class="minusBtn float" onclick="minusBtn(\''+minusNum+'\')">-</div>';

                                var result = '<div id="'+id+'" class="float">'+infor+'</div> <div class="float">'+plus+minus+'</div>';
                                addTag.append(result);
                                num++;
                            }
                            // - div
                            function minusBtn(id){
                                $('#'+id).parent().prev().remove();
                                $('#'+id).parent().remove();
                            }
                        	
                            
                            // 프로필 사진 미리보기
							var sel_file; // 선택된 이미지
							$(function(){
								// on("change",function(){}) 은 해당 태그가 바뀔 때 마다 함수 실행한다는 의미
								$('#profileImg').on("change", handleImgFileSelect);
							});
                            function handleImgFileSelect(e){
                            	var files = e.target.files;
                            	var filesArr = Array.prototype.slice.call(files); // 파일리스트를 배열로 각각 넣어준다.
                            	
                            	filesArr.forEach(function(imgfile){ // 파일 배열의 길이만큼 반복
                            		if(!imgfile.type.match("image.*")){
                            			alert("확장자는 이미지 확장자만 가능합니다.");
                            			return;
                            		}
                            			
	                            	sel_file = imgfile;
	                            		
	                            	var reader = new FileReader();
	                            	reader.onload = function(e){
	                            		$('#memProfile>img').attr("src", e.target.result);
	                            	}
	                            	reader.readAsDataURL(imgfile);
                            	});
                            }
                            
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
                              for (var m = 1; m <= 12; m++) {
                                  if(m<10){
                                      $("#birthMonth").append("<option value='0" + m + "'>0" + m + " 월" + "</option>");
                                  }else{
                                      $("#birthMonth").append("<option value='" + m + "'>" + m + " 월" + "</option>");
                                  }
                              }

                              // 일 뿌려주기(1일부터 31일)
                              var day;
                              $("#birthDay").append("<option value=''>일</option>");
                              for (var d = 1; d <= 31; d++) {
                                  if(d<10){
                                      $("#birthDay").append("<option value='0" + d + "'>0" + d + " 일" + "</option>");
                                  }else{
                                      $("#birthDay").append("<option value='" + d + "'>" + d + " 일" + "</option>");
                                  }
                              }

                            }
                            // 성별 글자 눌러도 체크 되게
                            $('.genderInput').next().click(function(){
                            	$(this).prev().prop('checked',true);
                            });
                            
                            $('#submit-btn').click(function(){
                        		// submit 전에 거르기
                        		if($('select[name=memPosition]').val()==''){alert('직위를 선택해주세요');}
                        		else if($('#profileImg').val()==''){alert('프로필 사진을 선택해주세요');}
                        		else if($('input[name=memName]').val()==''){alert('이름을 입력해주세요');}
                        		else if($('select[name=memBirth1]').val()==''){alert('생년월일을 입력해주세요');}
                        		else if($('select[name=memBirth2]').val()==''){alert('생년월일을 입력해주세요');}
                        		else if($('select[name=memBirth3]').val()==''){alert('생년월일을 입력해주세요');}
                        		else if($('input[name=memAddress1]').val()==''){alert('주소를 입력해주세요');}
                        		else if($('input[name=memAddress2]').val()==''){alert('주소를 입력해주세요');}
                        		else if($('select[name=memPhone1]').val()==''){alert('연락처를 입력해주세요');}
                        		else if($('input[name=memPhone2]').val()==''){alert('연락처를 입력해주세요');}
                        		else if($('input[name=memPhone3]').val()==''){alert('연락처를 입력해주세요');}
                        		else{
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
                        		}
                        	});
                            // 취소 btn 클릭 이벤트
                            $('.delBtn').click(function(){
                            	history.go(-1);
                            });
                            
                            // 우편 API
                            function searchAddr(){
                            	new daum.Postcode({
    						        oncomplete: function(data) {
    						            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.

    						        	// 각 주소의 노출 규칙에 따라 주소를 조합한다.
    					                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
    					                var addr = ''; // 주소 변수
    					                var extraAddr = ''; // 참고항목 변수

    					                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
    					                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
    					                    addr = data.roadAddress;
    					                } else { // 사용자가 지번 주소를 선택했을 경우(J)
    					                    addr = data.jibunAddress;
    					                }

    					                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
    					                if(data.userSelectedType === 'R'){
    					                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
    					                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
    					                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
    					                        extraAddr += data.bname;
    					                    }
    					                    // 건물명이 있고, 공동주택일 경우 추가한다.
    					                    if(data.buildingName !== '' && data.apartment === 'Y'){
    					                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
    					                    }
    					                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
    					                    if(extraAddr !== ''){
    					                        extraAddr = ' (' + extraAddr + ')';
    					                    }
    					                    // 조합된 참고항목을 해당 필드에 넣는다.
    					                    //document.getElementById("sample6_extraAddress").value = extraAddr;
    					                
    					                } else {
    					                    //document.getElementById("sample6_extraAddress").value = '';
    					                }

    					                // 우편번호와 주소 정보를 해당 필드에 넣는다.
    					                //document.getElementById('addrInput').value = data.zonecode;
    					                $('#addrInput').val(data.zonecode);
    					                //document.getElementById("sample6_address").value = addr;
                            			$('#addrInput').siblings('input').val(addr+extraAddr);
    					                // 커서를 상세주소 필드로 이동한다.
    					                //document.getElementById("sample6_detailAddress").focus();
    						        }
    						    }).open();
                            }
						    
						</script>
						
						
						<!----------------------------------->
					</div>
				</div>
			</div>
		</div>

	<!-- 자바 스크립트    -->
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>

	</div>
</body>
</html>