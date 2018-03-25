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
		<h1 class="centerize">Quan Ly Tin Tuc</h1>
		
		<form:form action="/edit" method="post" modelAttribute="nd">
			<div class="form-group">
				<label for="tieude">Tieu De:</label>
				<form:input type="text" path="TieuDe" class="form-control" id="tieude"/> 
			</div>
			
			<div class="form-group">
				<label for="mota">Mo ta:</label>
				 <form:input type="text" path="MoTa" class='form-control' id="mota"/>
			</div> 
			
			<div class="form-group">
				<label for="tacgia">Tac Gia:</label>
				 <form:input type="text" path="TacGia" class="form-control" id="tacgia"/> 
			</div>
			

			<form:textarea cols="20" rows="20" id="editor" path="NoiDung"></form:textarea>
			<div class="centerize">
				<input type="submit" value="Submit" class ="btn btn-primary"/> 
			</div>
			
		</form:form>
		<hr>
		
		<h1 class='centerize'>Attach File</h1>
		<form class="form-horizontal" method="POST" enctype="multipart/form-data" id="fileUploadForm">
			<div class="form-group">
				<label class="control-label col-sm-2" for="uploadfile">Upload File:</label>
				<div class="col-sm-5">
					<input type="file" class="form-control" name="uploadfile" placeholder="Upload File" id="uploadfile"></input>
				</div>
			</div>
			<div class="form-group"> 
			    <div class="col-sm-offset-2 col-sm-10">
			      <button type="submit" class="btn btn-default" id="btnSubmit">Upload</button>
			    </div>
			</div>
		</form>
		<div class="col-sm-offset-2" >
			<ul id="result">
				
			</ul>
		</div>
		
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
			url:"/api/getContent/"+id,
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