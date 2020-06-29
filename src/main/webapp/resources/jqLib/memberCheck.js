// ** member 오류 확인사항
// ID : 길이(4이상), 영문자,숫자 로만 구성
// password : 길이(4이상), 숫자와 특수문자는 반드시 1개 이상 포함할것
// Name : 길이(2이상), 영문 또는 한글로 만 입력
// Level : select 를 이용 (X)
// BirthDay : 입력 여부 확인  ( length == 10 )
// Point : 정수의 범위  ( 숫자이면서, '.'이 없어야함 )
// Weight: 구간 (30 ~ 200)
// 추천인 : 필수 사항이 아님 (X)

// ** 작성 규칙
// => JavaScript function 으로 정의 하고 
//    결과를 true or false 로 return

function idCheck() {
	var id = $('#id').val() ;
	if (id.length<4) {
		  $('#iMessage').html('ID 는 4 글자 이상 입력하세요 ~~');		
		  $('#id').focus();
		  return false;
	 	}else if (id.replace(/[a-z.0-9]/gi,'').length>0) {
	 		 $('#iMessage').html('ID 는 영문자 와 숫자 로만 입력하세요 ~~');
	 		$('#id').focus();
	 		 return false;
	 	}else {
	 		$('#iMessage').html('');
	 		return true;
	 	}
}; // idCheck() 

function pwCheck() {
	var password=$('#password').val();	
	var pLength=password.length ; 
	if (password.length<4) {
		$('#pMessage').html('Password 는 4 글자 이상 입력하세요 ~~');	
		$('#password').focus();
		return false;
	}else if (password.replace(/[!-*]/gi,'').length >= pLength ) {
		$('#pMessage').html('Password는 특수문자를 반드시 1개 이상 입력하세요 ~~');	
		$('#password').focus();
		return false;
	}else if (password.replace(/[0-9.!-*]/gi,'').length>0) {
		$('#pMessage').html('Password는 숫자와 특수문자 로만 입력하세요 ~~');	
		$('#password').focus();
		return false;
	}else {
		$('#pMessage').html('');	
		return true;
	} 
}; // pwCheck()

function nmCheck() {
	var name=$('#name').val();
	if (name.length<2) {
		$('#nMessage').html('Name 은 2 글자 이상 입력하세요 ~~');
		$('#name').focus();
		return false;
	}else if (name.replace(/[a-z.가-힇]/gi,'').length>0) {
		$('#nMessage').html('Name 은 한글 또는 영문으로만 입력하세요 ~~');
		$('#name').focus();
		return false;
	}else {
		$('#nMessage').html('');	
		return true;
	} 
}; // nCheck()

//birthDay Check
//=> 년.월.일 을 입력했는지..
// yyyy-mm-dd : length 10
function bdCheck() {
	var birthd=$('#birthd').val();
	if (birthd.length!=10) {
		$('#bMessage').html('생일을  YYYY-MM-DD 정확하게 입력하세요 ~~~');
		$('#birthd').focus();
		return false;
	}else {
		$('#bMessage').html('');	
		return true;
	}
} // bdCheck()

//point Check
//=> 정수 인지 확인
//		숫자확인 : isNumeric
//		"." 이 포함되면 안됨
function poCheck() {
	var point=$('#point').val();
	var poLength=point.length;
	// 주의 : var poLength=$('#point').length;
	//		=> 정수 부분의 길이만 return 
	console.log('poLength='+poLength) ;
	if ($.isNumeric(point)==false || point.replace('.','').length < poLength) {
		$('#oMessage').html('Point 를 정수로 정확하게 입력하세요 ~~~');
		$('#point').focus();
		return false;
	}else {
		$('#oMessage').html('');	
		return true;
	} 
} // poCheck()

//weight Check
//=> 실수 인지 확인
//		숫자확인 : isNumeric
//		구간 확인 (DB 의 제약조건과 동일)
function weCheck() {
	var weight=$('#weight').val();
	if (weight.length<2 || $.isNumeric(weight)==false) {
		$('#wMessage').html('weight를 정확하게 숫자로 입력하세요 ~~~');
		$('#weight').focus();
		return false;
	}else if (parseFloat(weight) < 20 || parseFloat(weight) > 200) {
			$('#wMessage').html('weight의 값이 범위를 벗어납니다.~~~');
			$('#weight').focus();
			return false;
	}else {
		$('#wMessage').html('');	
		return true;
	}   
} // wtCheck()

////////   Board inCheck_Title   ////////
function ttCheck() {
	var title=$('#title').val();
	if (title.length<1) {
		$('#tMessage').html('글 제목을  입력하세요 ~~~');
		$('#title').focus();
		return false;
	}else {
		$('#tMessage').html('');	
		return true;
	}   
} // ttCheck()
