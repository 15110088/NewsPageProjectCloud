<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="//cdn.ckeditor.com/4.6.2/full/ckeditor.js"></script>
<link rel="stylesheet" 	type="text/css" href="/resources/css/app.css"/>
</head>
<body>
	<div class="container">
		<h1 class="centerize">Update Tin Tuc</h1>
		
		<form:form action="/editpage1" method="post" modelAttribute="nd">
			<div class="form-group">
				<label for="tieude">Tieu De:</label>
				<form:input type="text" path="TieuDe" class="form-control" id="tieude" value="${tt.getTieuDe()}"/> 
			</div>
			
			<div class="form-group">
				<label for="mota">Link1:</label>
				 <form:input type="text" path="Link1" class='form-control' id="mota" value="${tt.getLink1()}"/>
			</div> 
			<div class="form-group">
				<label for="mota">Link2:</label>
				 <form:input type="text" path="Link2" class='form-control' id="mota" value="${tt.getLink2()}"/>
			</div> 
			<div class="form-group">
				<label for="mota">Link3:</label>
				 <form:input type="text" path="Link3" class='form-control' id="mota" value="${tt.getLink3()}"/>
			</div> 
			<div class="form-group">
				<label for="mota">Link4:</label>
				 <form:input type="text" path="Link4" class='form-control' id="mota" value="${tt.getLink4()}"/>
			</div> 
			
			
			 
			

			<form:textarea cols="20" rows="20" id="editor" path="NoiDung"/>
			<div class="centerize">
				<input type="submit" value="Submit" class ="btn btn-primary"/> 
			</div>
			
		</form:form>
		
	</div>
		
	<script>
	
	function postAjax(fileName){
		var form = $('#fileUploadForm')[0];
	    var data = new FormData(form);
	 
	    $.ajax({
	        type: "POST",
	        enctype: 'multipart/form-data',
	        url: "/api/uploadfile",
	        data: data,
	        processData: false, 
	        contentType: false,
	        cache: false,
	        success: function (data) {
	        	console.log("success",data);
	        	var newDelete=$("<span></span>").css("cursor","pointer").html("<strong>X</strong>").on('click',function(){
	        		console.log("clicked");
	        		var _this=this;
	        		$.ajax({
						type:"GET",
						url:"/delete/att/"+filename,
						success: function(flag){
							console.log("click2",flag);
							if(flag){
								console.log(_this);
								console.log(_this.nodeName);
								console.log(_this.tagName);
								console.log($(_this).parent());
								$(_this).parent().remove();
								}
							}
						});
				});
	        	//var newli=$("<li></li>").text(filename+": ").append($("<a></a>").attr('href',data).text(data));
	        	var newli=$("<li></li>").text(fileName+" :").append($("<a></a>").attr("href",data).text(data+"  ")).append(newDelete);
	            $("#result").append(newli);
	        },
	        error: function (data) {
	        	console.log("error");
	        	var newli=$("<li></li>").text(fileName +":" +data);
	            $("#result").append(newli);
	        }
	    });
	}
	
	function getAreaContent(){
		var length = window.location.pathname.split("/").length;
		var id = window.location.pathname.split("/")[length-1];
		
		$.ajax({
			type:"GET",
			url:"/api/getContentPage/"+id,
			data:id,
			success: function(contentBack){
				console.log("content :" ,contentBack.noiDung);
				CKEDITOR.instances['editor'].setData(contentBack.noiDung);
					
				}
			});
	}
	function getAttachment(){
		var length = window.location.pathname.split("/").length;
		var id = window.location.pathname.split("/")[length-1];
		
		console.log("id: ",id);
		$.ajax({
			type:"GET",
			url:"/api/getAtt/"+id,
			data:id,
			success: function(attachmentBack){
				console.log("successfully get",attachmentBack);
				attachmentBack.forEach(function(one){
					
					if(one.hasOwnProperty("name")&&one.hasOwnProperty("link")){
						
						var newDelete=$("<span></span>").css("cursor","pointer").html("<strong>X</strong>").on('click',function(){
							console.log("clicked");
							var _this=this;
							$.ajax({
								type:"GET",
								url:"/delete/att/"+one.name,
								success: function(flag){
									console.log("click2",flag);
									console.log(_this);
									console.log(_this.nodeName);
									console.log(_this.tagName);
									console.log($(_this).prop('tagName'));
									if(flag){
										console.log($(_this).parent());
										console.log($(_this));
										$(_this).parent().remove();
									}
									}
								});
						});
						var newli=$("<li></li>").text(one.name+" :").append($("<a></a>").attr("href",one.link).text(one.link+"   ")).append(newDelete);
			            $("#result").append(newli);
			            
					}
						
						
					
				})
				
				
				
			}
		})
	}
	
	$(document).ready(function () {
		CKEDITOR.replace("editor");
		getAttachment();
		getAreaContent();
	    $("#btnSubmit").click(function (event) {
	        event.preventDefault();
	        var name = $("#uploadfile")[0].files[0].name;
	        console.log("filename",name);
	        postAjax(name);
	        return false;
	    });
	 
	});
	
	console.log("here1");
	</script>
</body>

</html>