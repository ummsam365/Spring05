// Ajax 의 Response Type
// => html, xml, Json객체 

// ** JSON login 기능
// => loginForm : jslogin 버튼 추가 , MemberController : loginf, jslogin (추가) 
// => jslogin : 성공 -> 성공 Message 출력 하고, login 화면 clear
//              실패 -> 실패 Message 출력 하고, id 에 focus
$(function(){
	
	$('#jsloginf').click(function(){
		$.ajax({
			type:'Get',
			url:'loginf',
			success:function(result){
				$('#resultArea1').html(result);
			} // success
		});//ajax
	}); // click
	
	$('#jslogin').click(function(){
		$.ajax({
			type:'Post',
			url:'jslogin',
			data: {
				id:$('#id').val(),
				password:$('#password').val()	
			},
			success:function(data){
				if (data.fCode=='200' ) {
					// 성공
					// -> message 출력,
					// -> login화면 clear (현재 page새로고침 또는 resultArea clear)
					location.reload();
					//$('#resultArea1').html('');
					alert("Login 성공 ~~") ;
				}else {
					$('#id').focus();
					alert('~~ 로그인  실패  !!!\n 다시 하세요 ~~ ') ;
				} 
			} // success
		});//ajax
	}); // click
	
// Ajax Board List Test
// => result: axBoardList.jsp, BoardController: ablist		
// Test 1. 타이틀 클릭하면, 하단에 글 내용 출력하기  => => JS, aTag, axbDetail( , ) 
// Test 2. 타이틀 클릭하면, 글목록의 아랫쪽에 글 내용 출력하기 => JS, aTag, axbDetailJs( , , ) 
// Test 3. seq 에 마우스 오버시에 별도의 DIV에 글내용 표시 되도록 하기 => jQuery	
	
	$('#ablist').click(function() {
		$.ajax({
			type:'Get',
			url:'ablist',
			success:function(result){
				$('#resultArea1').html(result);
			}
		}); //ajax 
	}); // ablist_click
	
// Test 3. 글내용 읽어와 DIV 로 표시하기 : 	
// ~~.hover(function1(){...}, function2(){...});	
// => function1 : ajax 작성 , 글출력 (div-> display : block)
// => function2 : 글 지워줌 (div-> display : none).	
	
	$('.cseq').hover(function(e) {
		// 3.1) seq 를 id 속성의 값으로 이용해서  jquery 로 값을 전달
		// 		=> 글의 seq는 유일값 이므로
		//      => eachfor 문에서 인식 가능한 속성에 담아두었다가 jQuery 에서 꺼내어 사용 
		
		//var seq =$(this).attr('id'); 
		var seq =$(this).html(); 
		console.log('seq='+seq);
		
		// ** 마우스 위치 보관
		// => e.pageX, e.pageY
		// => e.clientX, e.clientY
		// ** view 
		// css => display: none->block
		var mleft=e.pageX;
		var mtop=e.pageY;
		console.log('e.pageX, e.pageY =>'+e.pageX+e.pageY);
		console.log('e.clientX, e.clientY =>'+e.clientX+e.clientY);
		// e.clientX, e.clientY => page scroll 시에 불편
		$.ajax({
			type:'Get',
			data: {
				seq:seq
				},
			url:'jsbdetail',
			success:function(data){
				$('.result').html('');
				$('#content').html(data.content)
				.css({
					display: 'Block',
					left:mleft,
					top:mtop
				});
				
			} // success
		}); // ajax	
	}, function() {
		$('.result').html('');
		$('#content').css({
			display:'None'
		}); // css
	});// hover
	
	// Test3.2) jQuery 메서드 => show() / hide() 
	// => this 와 html() 값 이용.
	$('.cseq2').hover(function(e) {
		var seq =$(this).html() ;
		var mleft=e.pageX;
		var mtop=e.pageY;
		$.ajax({
			type:'Get',
			data: {
				seq:seq
				},
			url:'jsbdetail',
			success:function(data){
				$('.result').html('');
				$('#content').html(data.content)
				.css({
					left:mleft,
					top:mtop
				}).show();
			} // success
		}); // ajax	
	}, function() {
		$('.result').html('');
		$('#content').hide();
	});// hover
	
}); // ready

//Test 1. 글번호 클릭하면, 하단에 글 내용 출력하기  
function axBDetail(seq,id) {
	console.log('axBDetail: seq='+seq+', id='+id) ;
	$.ajax({
		type:'Get',
		data: {
			seq:seq,
			id:id
			},
		url:'bdetail',
		success:function(result){
			$('#resultArea2').html(result);
		} // success
	}); // ajax	
} // axBDetail 

//Test 2. 타이틀 클릭하면, 글목록의 아랫쪽에 글 내용 출력하기 => JS, aTag, jsBDetail( , , ) 
function jsBDetail(e,seq,index) {
	alert('event Test e.type=>'+e.type);
	console.log('axBDetail: seq='+seq+', index='+index) ;
	
	// 출력된 상태에서 클릭하면 지워지도록 ...
	if ($('#result'+index).html()=='') {
		$.ajax({
			type:'Get',
			url:'jsbdetail',
			data: {
				seq:seq,
				},
			success:function(data){
				$('.result').html('');
				$('#result'+index).html(data.content);
			} // success
		}); // ajax	
	}else $('#result'+index).html('');
	
} // axBDetail 

// *** 
// Ajax 로  ~.jsp 를 출력하는 경우 주의 사항
// => jQuery 로 이벤트를 처리하기 위해 지금처럼 ready 이벤트를 사용하는 경우 
//    본 구문이 포함된 axTest03.js 를  부모창에도 추가하고,
//    결과로 불리워지는 loginForm.jsp 에도 포함 하게되면
//    마치 callBack 함수처럼 실행 할 때마다 이중으로 불리워지면서 2의 자승으로 늘어나게 됨.
// => 해결방법
//    1) ~.js 를 각각 분리한다. 
//			-> $('#jlogin').click( .....) 부분 axTest04.js 로 독립
//    2) JS의 함수 방식 으로 이벤트 처리
