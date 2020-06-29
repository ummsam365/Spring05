$(function(){
// ** Mouse Pointer 모양
	//	$('.textLink').hover(function() {..},function() {..});
	$('.textLink').hover(function() {
			$(this).css({
					color:'DeepSkyBlue',
					cursor: 'pointer'}); //css	
			},function() {
				$(this).css({
					color:'Black',
					cursor: 'default'}); //css				
		}); // hover
	
// ** AjaxLogin
// => loginForm, MemberController_url : loginf, login 사용	
	$('#aloginf').click(function(){
		$.ajax({
			type:'Get',
			url:'loginf',
			success:function(result){
				$('#resultArea').html(result);
				}
		}); // ajax 
	}); // alogin_click
	$('#alogin').click(function(){
		$.ajax({
			type:'Get',
			url:'login',
			data:{
				id:$('#id').val(),
				password:$('#password').val()
				},
			success:function(result){
				$('#resultArea').html(result);
				},
			error:function(){
				var eMessage ="<b> 오류 발생  다시 하세요 ~~ </b>";
				$('#resultArea').html(eMessage);
				}	
		}); // ajax 
	}); // alogin_click
	
// ** PopUpLogin
// => open 에 지정된 요청 url(서버에서)을 처리 하고 그 결과를 새창으로 open 해 줌. 
// => view: ajaxTest/ploginForm, MemberController_url : ploginf, plogin 사용		
	$('#ploginf').click(function(){
		open('ploginf','_blank',
		'toolbar=no,menubar=yes,scrollbars=yes,resizable=yes,width=500,height=300');
	}); // click
	
// ** AjaxJoin
// => joinForm, MemberController_url: joinf, join 
	$('#ajoinf').click(function(){
		$.ajax({
			type: "Post",
			url: "joinf",
			success:function(result){
				$('#resultArea1').html(result);
			},
			error:function(){
				$('#resultArea1').html("~~ 오류 발생 다시 하세요 ~~");
			}
		}); //ajax
	}); // ajoinf_click
	
// ** Data 처리방법
// 1) Form 의 serialize()
// => 직렬화 : multipart 타입은 제외 시켜야 함.	
// 2) 2) 객체화	
// => 특정 변수 (객체형) 에 담기
//     {...}
// 3) FormData() 객체 1 : append ...
// => FormData는 Ajax를 통해 이미지등이 업로드가 가능하도록 지원하는 폼 데이터 객체	
// 4) FormData() 객체 2 : all append ...
	
	
/*	{
		id:$('#id').val(),
		password:$('#password').val(),
		name:$('#name').val(),
		lev:$('#lev').val(),
		birthd:$('#birthd').val(),
		point:$('#point').val(),
		weight:$('#weight').val(),
		rid:$('#rid').val(),
		uploadfilef:$('#uploadfilef').val()				
		}
*/	
	$('#ajoin').click(function(){
		// input Data
		var id=$('#id').val();
		var password=$('#password').val();
		var name=$('#name').val();
		var lev=$('#lev').val();
		var birthd=$('#birthd').val();
		var point=$('#point').val();
		var weight=$('#weight').val();
		var rid=$('#rid').val();
		
	//	alert("id,password =>"+id+password) ;
		
	// 1) serialize()	
	//	var formData = $('#myForm').serialize();
    // => 특별한 자료형(fileType: UpLoad) 은 취급안됨.	
    /* 2) 객체화	
		var formData = {
			id:$('#id').val(),
			password:$('#password').val(),
			name:$('#name').val(),
			lev:$('#lev').val(),
			birthd:$('#birthd').val(),
			point:$('#point').val(),
			weight:$('#weight').val(),
			rid:$('#rid').val()	
		};  */
	/* 3) append  */
	// => 자료 특성에 맞게 적용 가능  : fileType (UpLoad) 가능 	
		var formData = new FormData();
		formData.append('id',id);
		formData.append('password',password);
		formData.append('name',name);
		formData.append('lev',lev);
		formData.append('birthd',birthd);
		formData.append('point',point);
		formData.append('weight',weight);
		formData.append('rid',rid); 
		
		// => Ajax 의  FormData 는  이미지를 선택하지 않으면 append시 오류 발생
		//    하기 때문에 이를 확인 후 append 하도록 함
		//    이때 append 를 하지 않으면  서버의 vo.uploadfilef 에는  null 값이 전달됨.
		if ($("#uploadfilef")[0].files[0]!=null)
			formData.append('uploadfilef',$("#uploadfilef")[0].files[0]);
		//formData.append('uploadfilef',$("input[name=uploadfilef]")[0].files[0]);
	// 4) all append
	// => 특별한 자료형(fileType: UpLoad) 은 취급안됨.		
	// 	var formData = new FormData(document.getElementById('myForm'));
		
	// ** 관련속성	
	// => enctype: 'multipart/form-data', // 생략 가능
	// => processData:false, // false로 선언 시 formData를 string으로 변환하지 않음
	// => contentType:false, // false로 선언 시 content-type 헤더가 multipart/form-data로 전송되게 함
		
		console.log('join Test =>'+formData);	
		alert("DataTest_append null Test !!! ~~");
		
		$.ajax({
			type:"Post",
			url:"join",
			data: formData,
 			enctype: 'multipart/form-data', // 생략 가능
			processData:false, // formData로 이미지를 처리하기 위해 false 로 지정 
			contentType:false, // formData로 이미지를 처리하기 위해 false 로 지정
 			success:function(result){
				$('#resultArea1').html(result);
			}
		}); //ajax
	}); // ajoin_click
	
}); // ready
/* 
 *** $.ajax 메서드   ******************
 *  
 * 1. 기본형식
 * $.ajax({옵션속성:값}); 
 * => $. ajax 함수는 XMLHttpRequest 객체를 반환함.
 * 
 * 2. 옵션속성 
 * => https://hsj0511.tistory.com/205 참고
 * 
 * url:문자열 - 요청url 설정

=> type:문자열 - GET/POST설정
   data:객체,문자열 - 요청 매개변수 설정 
   dataType: return Data Type - xml, html, json, jsonp, text, script
   success:함수
 		=> 성공시 호출할 함수 설정
 		=> 매개변수가 응답 결과를 받아줌 
   error:함수 - 실패시 호출할 함수 설정
   async:불리언 - 동기/비동기 설정 (True/False)
   beforeSend:HTTP 요청전에 발생하는 이벤트 핸들러
*/
