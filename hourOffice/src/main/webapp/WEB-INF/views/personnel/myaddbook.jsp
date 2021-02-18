<%@page import="kr.or.houroffice.personnel.model.vo.Contact"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.or.houroffice.member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 폰트어썸 -->
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<!--jQuery CDN-->
<script src="https://code.jquery.com/jquery-3.5.1.js"
	integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
	crossorigin="anonymous"></script>

<!-- jQuery lib -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

</head>

<style>
.sbbtn {
	background-color: #1D9F8E;
	color: white;
	border: 0;
	border-radius: 5px;
	width: 50px;
	height: 25px;
}

#create-user {
	background-color: #1D9F8E;
	color: white;
	border: 0;
	border-radius: 5px;
	width: 80px;
	height: 25px;
}

#update-user {
	background-color: #1D9F8E;
	color: white;
	border: 0;
	border-radius: 5px;
	width: 80px;
	height: 25px;
	margin-left: 5px;
}

#delbtn {
	background-color: #FF6363;
	color: white;
	border: 0;
	border-radius: 5px;
	width: 80px;
	height: 25px;
	margin-left: 5px;
}

tr {
	height: 30px;
}

select {
	height: 25px;
}

#search {
	height: 25px;
}

.addname {
	display: inline-block;
	width: 100px;
	text-align: center;
}

.textbox {
	width: 200px;
}

@font-face {
    font-family: 'GongGothicMedium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/GongGothicMedium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
@font-face {
    font-family: 'GongGothicLight';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/GongGothicLight.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

#title1{
font-family: GongGothicMedium;
font-size:1.5rem;
}

#title2{
font-family: GongGothicLight;
}

/*페이지 네비 css*/
#page-navi {
    height: 30px;
    margin-top: 50px;
    margin-bottom: 50px;
    display: flex;
    flex-direction: row;
    justify-content: center;
}

.page-list {
    width: 30px;
    height: 30px;
    border: 1px solid darkgray;
    text-align: center;
}
.page-link {
    width: 100%;
    height: 100%;
    line-height: 28px;
    display: inline-block;
}

.page-link:hover{
    background: #eaeaea;
}

</style>

<script>
	$(document).ready(function() {
		$('.ckall').click(function() {
			$('.ck').prop('checked', this.checked);
		});
	});

	$(function() {
		var dialog, form;
		function addUser() {

			// Class = addName
			//$(".addName")

			// Id = addName
			//$("#addName")
			var url = null;
			var type = null;

			var addName = $("#addName").val();
			var addCompany = $("#addCompany").val();
			var addPosition = $("#addPosition").val();
			var addTell = $("#addTell").val();
			var addPh = $("#addPh").val();
			var addEmail = $("#addEmail").val();

			var object = {
				"name" : addName,
				"company" : addCompany,
				"position" : addPosition,
				"tell" : addTell,
				"ph" : addPh,
				"email" : addEmail
			};

			if ($("#writeType").val() == "insert") {
				url = "/myaddbook.ho";
				type = "POST";
			} else {
				url = "/myaddbookUpdate.ho";
				type = "PUT";
				object.cntNo = $("input[type=checkbox]:checked").val();
			}

			// ajax 이벤트 추가
			$.ajax({
				url : url,
				data : JSON.stringify(object), //리스트(object)를 json 형식으로 저장하기 -> 키 벨류
				dataType : 'text',
				contentType : 'application/json; charset=utf-8',
				type : type,
				success : function(data) {
					if (data == 'success') {
						alert('등록이 정상적으로 처리되었습니다.');
						location.href = '/myaddbook.ho';
					} else {
						alert('등록이 정상적으로 처리되지않았습니다. \n 관리자에게 문의하세요.');
					}
				},
				error : function(e) {
					console.log("서버 호출을 정상적으로 처리하지 못하였습니다.!!");
				}

			});
		}

		dialog = $("#dialog-form").dialog({
			autoOpen : false,
			height : 400,
			width : 350,
			modal : true,
			buttons : {
				"연락처 등록" : addUser,
				"닫기" : function() {
					dialog.dialog("close");
				}
			},
			close : function() {
				form[0].reset();
			}
		});//

		//여기가 잘못된거같은데 모르겠네
		form = dialog.find("form").on("submit", function(event) {
			event.preventDefault();
			addUser();
		});

		$("#create-user").button().on("click", function() {
			$("#writeType").val("insert");
			dialog.dialog("open");
		});

		$("#update-user").button().on("click", function() {
			$("#writeType").val("update");
			var chk_idx = $("input[type=checkbox]:checked").val();
			$("#addName").val($(".name_" + chk_idx).text());
			$("#addCompany").val($(".company_" + chk_idx).text());

			$("#addPosition").val($(".appointment_" + chk_idx).text());
			$("#addTell").val($(".officeNumber_" + chk_idx).text());
			$("#addPh").val($(".ph_" + chk_idx).text());
			$("#addEmail").val($(".email_" + chk_idx).text());
			dialog.dialog("open");
		});

		//-- 삭제 -->
		$('#delbtn').click(function() {
			var result = window.confirm("연락처 삭제 하시겠습니까?");
			var ck = null;
			for(var i=0; i<$("input.ck[type=checkbox]:checked").length;i++){
				if(i == 0){
					ck = $("input.ck[type=checkbox]:checked")[i].value;
					continue;
				}
				ck += ","+$("input.ck[type=checkbox]:checked")[i].value;
			}

			if (result == true) {
				$.ajax({
					url : "/myaddbookDelete.ho?ck=" + ck,
					dataType : 'text',
					type : "DELETE",
					success : function(data) {
						if (data == 'success') {
							alert('삭제가 완료되었습니다.');
							location.href = '/myaddbook.ho';
						} else {
							alert('삭제가 정상적으로 처리되지않았습니다. \n 관리자에게 문의하세요.');
						}
					},
					error : function(e) {
						alert('오류가 발생하였습니다. \n 관리자에게 문의하세요.');
						console.log("서버 호출을 정상적으로 처리하지 못하였습니다.!!");
					}
				});
			}
		});

	});
</script>


<body>
	<%
		//jsp 페이지에서 사용하기 위하여 데이터를 꺼내는 작업
		//어레이리스트는 오브젝트 타입 , 다운캐스팅을 해줘야한다.   (ArrayList<Contact>)
		Member sessionMember = (Member) session.getAttribute("member");
	%>

	<div id="dialog-form" title="연락처등록">
		<br> <input type="hidden" id="writeType" value="insert" />
		<form>
			<div class="addname">이름</div>
			<input id="addName" class="textbox" type="text" class="text" /><br>
			<br> <br>
			<div class="addname">회사</div>
			<input id="addCompany" class="textbox" type="text" class="text" /><br>
			<br> <br>
			<div class="addname">직급</div>
			<input id="addPosition" class="textbox" type="text" class="text" /><br>
			<br> <br>
			<div class="addname">내선번호</div>
			<input id="addTell" class="textbox" type="text" class="text" /><br>
			<br> <br>
			<div class="addname">휴대폰</div>
			<input id="addPh" class="textbox" type="text" class="text" /><br>
			<br> <br>
			<div class="addname">이메일</div>
			<input id="addEmail" class="textbox" type="text" class="text" /><br>
			<br>
		</form>
	</div>

	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp"%>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">



					<div id="TitleName">
						<!--여기서 각자 id 만드시면 됩니다-->
                        <span id="title1">인사관리</span>
						<span id="title2">&nbsp;&nbsp; 개인 주소록</span>
                        
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->

						<table width="100%" style="margin: auto;">
							<tr>
								<td width="7%">
									<button id="create-user">등록</button>
								</td>

								<td width="7%">
									<button id="update-user">수정</button>
								</td>

								<td width="7%">
									<!-- <form action="/myaddbookDelete.ho" method="post">
										<input type="hidden" name="_method" value="delete" /> -->
									<button type="submit" id="delbtn">삭제</button> <!-- </form> -->
								</td>

								<td style="text-align: right;">
									<form action="/myaddbook.ho" method="get">
										<select name="selectBox">
											<option value="" selected>전체</option>
											<option value="name">이름</option>
											<option value="company">회사</option>
											<option value="appointment">직책</option>
										</select> <input type="text" id="search" name="searchText" /> <input
											type="submit" class="sbbtn" name="searchBtn" value="검색" />
									</form>
								</td>
							</tr>
						</table>
						<br> <br>


						<table border="1px" width="100%"
							style="text-align: center; margin: auto; border-collapse: collapse;">

							<tr style="background-color: #1D9F8E; color: white;">
								<th><input type="checkbox" class="ckall"></th>
								<th>이름</th>
								<th>회사</th>
								<th>직책</th>
								<th>내선번호</th>
								<th>휴대폰</th>
								<th>이메일</th>
							</tr>

							<c:forEach var="result" items="${requestScope.result.list}">
								<tr>
									<td><input type="checkbox" class="ck" value="${result.cntNo}"></td>
									<td class="name_${result.cntNo}">${result.name}</td>
									<td class="company_${result.cntNo}">${result.company}</td>
									<td class="appointment_${result.cntNo}">${result.appointment}</td>
									<td class="officeNumber_${result.cntNo}">${result.officeNumber}</td>
									<td class="ph_${result.cntNo}">${result.ph}</td>
									<td class="email_${result.cntNo}">${result.email}</td>
								</tr>
							</c:forEach>
						</table>
						<br>

						<%-- <%=pageNavi%> --%>
						<ul id="page-navi">${requestScope.result.pageNavi}</ul>
						<!----------------------------------->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 자바 스크립트    -->
	<script>
	$('#categoryMypage').next().css('display','block');
	$('#categoryMypage').next().css('height','150px');
	$('#categoryMypage').children().last().children().attr('class','fas fa-chevron-left');
	
	$('#categoryMypage').next().children().eq(5).children().css('font-weight','800');
	$('#categoryMypage').next().children().eq(5).children().css('color','#ffcc29');
	</script>
	<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>
</body>
</html>
