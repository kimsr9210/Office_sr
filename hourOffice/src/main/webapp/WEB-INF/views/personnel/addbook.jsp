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

<!-- CSS -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/header&sideNavi.css" />

</head>

<style>
.sbbtn{background-color:#1D9F8E; color:white; border: 0; width: 50px; height: 25px;}
               
tr{height: 30px;}
select{height: 25px;}
#search{height: 25px;}
</style>

<body>

	<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp"%>

			<div id="contents">
				<div id="contentsDetail" class="clearfix">



					<div id="TitleName">
						<!--여기서 각자 id 만드시면 됩니다-->
						사내주소록
						<!----------------------------------->
					</div>
					<div id="TitleContents">
						<!--여기서 각자 id 만드시면 됩니다-->

</div>
                    <div id="TitleContents">
                        <!--여기서 각자 id 만드시면 됩니다-->
                        
        <table width="100%" style="margin:auto;">
                            
        <tr style="text-align:right;">
            <td>
                <form>
                    <select>
                        <option>이름</option>
                        <option>부서</option>
                        <option>직책</option>
                    </select>
                    <input type="text" id="search" name="search" />
                    <input type="submit" class="sbbtn" value="검색" style="border-radius: 5px;"/>
                </form>
            </td>
        </tr>
    </table>
    <br><br>

    <table border="1px" width="100%" style="text-align:center; margin:auto; border-collapse:collapse;">
        <tr style="background-color:#1D9F8E; color:white;">
            <th>프로필</th>
            <th>이름</th>
            <th>소속부서</th>
            <th>직급</th>
            <th>내선번호</th>
            <th>휴대폰</th>
            <th>이메일</th>
        </tr>

        <!-- 반복문 for 작성 -->
        <tr>
            <td>사진</td>
            <td>김소련</td>
            <td>개발1팀</td>
            <td>팀장</td>
            <td>02)2049-3618</td>
            <td>010-8888-1111</td>
            <td>kim@kr.or.iei.kh</td>
        </tr>

        


    </table><br>
    
    <p style="text-align: center;"> 1 2 3 4 5 ></p>
                        


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