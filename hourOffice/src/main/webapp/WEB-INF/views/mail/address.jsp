<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>H:our Office 주소록</title>
       <!-- fontawesome-->
    <link href="/resources/css/mail/mail_addr.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.2/css/all.css" integrity="sha384-vSIIfh2YWi9wW0r9iZe7RJPrKwp6bG+s9QZMoITbCckVJqGCCRhc+ccxNcdpHuYu" crossorigin="anonymous">
    <!--jquery cdn-->
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
</head>
<body>
   <div id="wrapper">
       <div id="title">주소록</div>
       <div id="con-wrap">
           <div id="dept-list">
           	<span deptcode=""><i class="far fa-folder-open"></i> 전체</span>
           	<c:forEach var="dept" items="${deptList }">
           		<span deptcode="${dept.deptCode }"><i class="far fa-folder"></i> ${dept.deptName }</span>
           	</c:forEach>
           </div>
           <div id="mem-list">
           	<c:forEach var="mem" items="${memList }">
           		<span><input type="checkbox" name="mail-addr"> <span memno="${mem.memNo }">${mem.memName } ${mem.memPosition }</span></span>
           	</c:forEach>
           </div>
           <div id="selected-area">
               <div class="cont-btn"><span id="receiver-add"><i class="fas fa-plus-square"></i></span><br><span id="receiver-sub"><i class="fas fa-minus-square"></i></span></div>
               <div class="list-title">받는 사람</div>
               <div class="selected-list" id="receiver-list"></div>
               <div class="cont-btn"><span id="ref-add"><i class="fas fa-plus-square"></i></span><br><span id="ref-sub"><i class="fas fa-minus-square"></i></span></div>
               <div class="list-title">참조</div>
               <div class="selected-list" id="ref-list"></div>
           </div>
       </div>
       <div id="btn-wrap">
           <button id="reset-btn" onclick="closeAddr();">닫기</button>
            <button id="add-btn" onclick="returnList();">등록</button>
       </div>
   </div>
    </body>
    
    <script>
        $(function(){
        	$('#categoryMail').next().css('display','block');
        	$('#categoryMail').next().css('height','150px');
        	$('#categoryMail').children().last().children().attr('class','fas fa-chevron-left');
        	
            //부서 클릭시 리스트 출력
            $('#dept-list>span').click(function(){
            	var $this = $(this);
                var deptCode = $this.attr('deptcode');
                $.ajax({
                	url : "/deptAddress.ho",
                	data : {"deptCode" : deptCode},
                	type : "get",
                	success : function(data){
                		if(data!=null){
                			var str = "";
                			for(var i=0; i<data.length; i++){
                				str += '<span><input type="checkbox" name="mail-addr"> <span memno="'+data[i].memNo+'">'+data[i].memName+' '+data[i].memPosition + '</span></span>';
                			}
                			$('#mem-list').html(str);
                			for(var i=0;i<$this.length; i++){
                                $('#dept-list>span>i').removeClass('fa-folder-open').addClass('fa-folder');
                             } $this.children('i').removeClass('fa-folder').addClass('fa-folder-open');
                		}else{
                			alert('데이터 없음');
                		}
                	},
                	error :function(){
                		alert('로딩 실패');
                	}
                });
               
            });
            //리스트에서 선택시 받는 사람, 참조에 추가
            
            $('#receiver-add').click(function(){
                var selectedList = $('#mem-list>span>input:checked');
                for(var i=0; i<selectedList.length; i++){
                    $(selectedList[i]).prop('checked',false);
                   var tmp = $(selectedList[i]).parent().clone(); 
                    $('#receiver-list').append(tmp);
                }
                
            });
            $('#receiver-sub').click(function(){
                var selectedList = $('#receiver-list>span>input:checked');
                for(var i=0; i<selectedList.length; i++){
                    $(selectedList[i]).parent().remove();
                }
            });
            $('#ref-add').click(function(){
                var selectedList = $('#mem-list>span>input:checked');
                for(var i=0; i<selectedList.length; i++){
                    $(selectedList[i]).prop('checked',false);
                   var tmp = $(selectedList[i]).parent().clone(); 
                    
                    $('#ref-list').append(tmp);
                }
            });
            $('#ref-sub').click(function(){
                var selectedList = $('#ref-list>span>input:checked');
                for(var i=0; i<selectedList.length; i++){
                    $(selectedList[i]).parent().remove();
                }
            });
           
        });
         $(document).ready(function(){ //부모창에서 데이터 가져오기 
            var recList = $(opener.document).find('#receive-list').children();
            var refList = $(opener.document).find('#ref-list').children();
             var addRec = "";
             var addRef = "";
             for(var i=0; i<recList.length; i++){
                 addRec += '<span><input type="checkbox" name="mail-addr"> <span memno="'+$(recList[i]).children('input').val()+'">'+$(recList[i]).text()+'</span></span>';
             }
            for(var i=0; i<refList.length; i++){
                 addRef += '<span><input type="checkbox" name="mail-addr"> <span memno="'+$(refList[i]).children('input').val()+'">'+$(refList[i]).text()+'</span></span>';
             }
             $('#receiver-list').html(addRec);
             $('#ref-list').html(addRef);
        });
         //opener
           function returnList(){
            var recList = $('#receiver-list').children();
            var refList = $('#ref-list').children();
               var addRec = "";
               var addRef = "";
               for(var i=0; i<recList.length; i++){
                   addRec += '<span class="rec-name"><i class="fas fa-times-circle"></i> '+ $(recList[i]).children('span').text()+'<input type="hidden" name="receiver" value="'+$(recList[i]).children('span').attr('memno')+'"/></span>';
               }
               for(var i=0; i<refList.length; i++){
                   addRef += '<span class="rec-name"><i class="fas fa-times-circle"></i> '+ $(refList[i]).children('span').text()+'<input type="hidden" name="memRef" value="'+$(refList[i]).children('span').attr('memno')+'"/></span>';
               }  
               $("#receive-list", opener.document).html(addRec);
               $("#ref-list", opener.document).html(addRef);
             
               window.close();
           } 
            function closeAddr(){
                window.close();
            }
    </script>
</html>