<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css"
	href="/resources/css/header&sideNavi.css" />
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

</head>

<style>
#sbbtn {
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

#delbtn {
	background-color: #FF6363;
	color: white;
	border: 0;
	border-radius: 5px;
	width: 80px;
	height: 25px;
	margin-left: 20px;
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

.addname{display: inline-block;
	width:100px;
	text-align:center;
	}
	
.textbox{width:200px;
}

</style>
<script>
	$(function() {
		var dialog, form;
		function addUser() {
			
			// Class = addName
			//$(".addName")
			
			// Id = addName
			//$("#addName")
			
			var addName = $("#addName").val();
			var addCompany = $("#addCompany").val();
			var addPosition = $("#addPosition").val();
			var addTell = $("#addTell").val();
			var addPh = $("#addPh").val();
			var addEmail = $("#addEmail").val();

			var object = {"name":addName,"company":addCompany,"position":addPosition,"tell":addTell,"ph":addPh,"email":addEmail};
			// ajax 이벤트 추가
			$.ajax({
				url : "/myaddbook.ho",
				dataType: 'json',
		        data: JSON.stringify(object), //리스트(object)를 json 형식으로 저장하기 -> 키 벨류
		        contentType:'application/json; charset=utf-8',
				type : "post",
				success : function(){
					console.log("서버 호출을 정상적으로 완료 하였습니다.");
				},
				error : function(){
					console.log("서버 호출을 정상적으로 처리하지 못하였습니다.!!");
				},
				complete : function(){
					dialog.dialog("close");
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
		});

		//여기가 잘못된거같은데 모르겠네
		form = dialog.find("form").on("submit", function(event) {
			event.preventDefault();
			addUser();
		});

		$("#create-user").button().on("click", function() {
			dialog.dialog("open");
		});
	});
</script>

<body>


	<div id="dialog-form" title="연락처등록"><br>
	<form>
			<div class="addname" >이름</div><input  id="addName" class="textbox" type="text" class="text" /><br><br><br>
			<div class="addname" >회사</div><input id="addCompany" class="textbox" type="text" class="text" /><br><br><br>
			<div class="addname" >직급</div><input id="addPosition" class="textbox" type="text" class="text" /><br><br><br>
			<div class="addname" >내선번호</div><input id="addTell" class="textbox" type="text" class="text" /><br><br><br>
			<div class="addname" >휴대폰</div><input id="addPh" class="textbox" type="text" class="text" /><br><br><br>
			<div class="addname" >이메일</div><input id="addEmail" class="textbox" type="text" class="text" /><br><br>
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
						개인주소록
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
									<form>
										<button type="submit" id="delbtn">삭제</button>
									</form>
								</td>


								<script>
									$(function() {
										$('#delbtn').click(function() {
										var result = window.confirm("연락처 삭제 하시겠습니까?");
										if (result == true) {

										}
									});
									});
									
								</script>

								<!-- 체크박스 모두선택하기 -->
								<script>
								$(document).ready(
								function() {
								$('.ckall').click(function() {
								$('.ck').prop('checked',this.checked);
									});
								});
								</script>

								<td style="text-align: right;">
									<form>
										<select>
											<option>이름</option>
											<option>회사</option>
											<option>직급</option>
										</select> <input type="text" id="search" name="search" /> <input
											type="submit" id="sbbtn" value="검색" />
									</form>
								</td>
							</tr>
						</table>
						<br>
						<br>


						<table border="1px" width="100%"
							style="text-align: center; margin: auto; border-collapse: collapse;">

							<tr style="background-color: #1D9F8E; color: white;">
								<th><input type="checkbox" class="ckall"></th>
								<th>이름</th>
								<th>회사</th>
								<th>직위</th>
								<th>내선번호</th>
								<th>휴대폰</th>
								<th>이메일</th>
							</tr>

							<!-- 반복문 for 작성 -->
							<tr>
								<td><input type="checkbox" class="ck"></td>
								<td>김소련</td>
								<td>삼성</td>
								<td>팀장</td>
								<td>02)2049-3618</td>
								<td>010-1111-2222</td>
								<td>kim@kr.or.iei.kh</td>
							</tr>

						</table>
						<br>

						<p style="text-align: center;">1 2 3 4 5 ></p>

						<!----------------------------------->
					</div>

				</div>
			</div>
		</div>
		
		

		<!-- 자바 스크립트    -->
		<script type="text/javascript" src="/resources/js/header&sideNavi.js"></script>

	</div>

	</div>
	
	
</body>
</html>


</body>
</html>