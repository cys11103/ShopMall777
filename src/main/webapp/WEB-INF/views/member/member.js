/**
 * 
 */
function go_next() {
	if (document.formm.okon1[0].checked == true) {
		document.formm.action = "join_form";	// 요청 문자열
		document.formm.submit();                // POST 방식 요청
	} else if (document.formm.okon1[1].checked == true) {
		alert("약관에 동의하셔야 합니다!");
	}
}

function idcheck() {
	if (document.formm.id.value == "") {
		alert("아이디를 입력해 주세요!");
		document.formm.id.focus();
		return;
	}
	
	// 중복된 아이디를 확인하는 윈도우를 띄운다.
	var url="id_check_form?id=" + document.formm.id.value;
	
	window.open(url, "_blank_1", 
			"toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=330, height=200");
}

function idok() {
	document.formm.action = "id_check_confirmed";	// 요청 문자열
	document.formm.submit();
}

/*
 * 동이름을 입력해서 우편번호 조회 창을 띄운다.
 */
function post_zip() {
	var url = "find_zip_num";
	window.open(url, "_blank_1", 
			"toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=330, height=330");
}

/*
 *  회원 정보 입력 확인 및 가입 처리
 */
function go_save() {
	
	if (document.formm.id.value=="") {
		alert("아이디를 입력해 주세요");
		document.formm.id.focus();
	} else if (document.formm.id.value != document.formm.reid.value) {
		alert("아이디 중복확인을 실행해주세요!");
		document.formm.id.focus();
	} else if (document.formm.pwd.value=="") {
		alert("비밀번호를 입력해 주세요");
		document.formm.pwd.focus();
	} else if (document.formm.pwd.value != document.formm.pwdCheck.value) {
		alert("비밀번호가 일치하지 않습니다.");
		document.formm.pwd.focus();
	} else if (document.formm.name.value=="") {
		alert("이름을 입력해 주세요!");
		document.formm.name.focus();
	} else if (document.formm.email.value="") {
		alert("이메일을 입력해 주세요!");
		document.formm.email.focus();
	} else {	// 모든 항목이 입력되었으면 회원가입을 요청
		document.formm.action = "join";
		document.formm.submit();
	}

}













