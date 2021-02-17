<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>H:our Office</title>
<!-- 폰트어썸 -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.2/css/all.css" integrity="sha384-vSIIfh2YWi9wW0r9iZe7RJPrKwp6bG+s9QZMoITbCckVJqGCCRhc+ccxNcdpHuYu" crossorigin="anonymous">

<!--jQuery CDN-->
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="/resources/css/header&sideNavi.css" />
<link href="/resources/css/mail/mail_write.css" rel="stylesheet" type="text/css" />

<!--  스마트에디터 경로-->
<script type="text/javascript" src="/resources/api/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
</head>
<body>
<div id="wrap">
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<div id="contentsBox">
			<%@ include file="/WEB-INF/views/common/sideNavi.jsp"%>

			<div id="contents">
                <div id="contentsDetail" class="clearfix">
                    <div id="TitleName">메일 쓰기</div>
                    <div id="TitleContents">
                        <form action="/sendMail.ho" method="post" enctype="multipart/form-data">
                            <input type="submit" value="메일 전송">
                            <fieldset id="mail-info-wrap">
                                <div class="mail-info">
                                    <div>받는사람 <i class="fas fa-plus-circle addr_list"></i></div>
                                    <div id="receive-list" class="mem-list"></div>
                                </div>
                                <div class="mail-info">
                                    <div>제목</div><input type="text" name="title">
                                </div>
                                <div class="mail-info">
                                    <div>참조 <i class="fas fa-plus-circle addr_list" title="추가하려면 누르세요"></i></div>
                                    <div id="ref-list" class="mem-list">
                                    </div>
                                </div>
                                <div class="mail-info">
                                    <div>첨부파일</div><input type="file" name="mailFile" id="mail-file"><span></span><span>(1개만, 용량제한 10MB )</span>
                                </div>
                            </fieldset>
                            <fieldset id="text-wrap">
                                <textarea name="content" id="mail-content"></textarea>
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
        //smarteditor
        var oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: "mail-content",
            sSkinURI: "/resources/api/smarteditor2/SmartEditor2Skin2-1.html",
            fCreator: "createSEditor2",
            htParams: { fOnBeforeUnload : function(){}}
        });
       
        $(function() {
        	$('#categoryMail').next().css('display','block');
        	$('#categoryMail').next().css('height','150px');
        	$('#categoryMail').children().last().children().attr('class','fas fa-chevron-left');
            //주소록 창
            $(document).on('click', '.addr_list', function(){
                window.open('/address.ho', '_blank', 'width=801, height=652, top=150, left=350, resizable=no, location=no, titlebar=no');
            });
            //멤버 목록 삭제
            $(document).on('click', '.fa-times-circle', function() {
                $(this).parent().remove();
            });
            //
            $(document).on('change', 'input[type=file]', function(){
            	var size = this.files[0].size;
                var printSize;
                if(size/1024>1024){
                    printSize = Maht.round(size/1024/1024*10)/10 +"MB ";
                }else{
                    printSize = Math.round(size/1024*10)/10 +"KB ";
                }
           		$(this).next().text(printSize);
            });
            //제출시
            $('form').submit(function(){
                var inputContent = oEditors.getById["mail-content"].getIR();    
                //스마트에디터에 쓴 글을 가져오는 스크립트 코드
                $('#mail-content').val(inputContent);
                if($('#receive-list').html()==""){
                	alert('받는 사람이 1명 이상이어야 합니다.');
                	return false;
                }
                //파일용량 체크
                if(document.getElementById("mail-file").value!=""){
                    var fileSize = document.getElementById("mail-file").files[0].size;
                    var maxSize = 10 * 1024 * 1024;//2MB
                 
                    if(fileSize > maxSize){
                       alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다. ");
                       return false;
                    }
                }
            });
        });
    </script>
</body>
</html>