/**
 * 
 */
function go_cart(){
	if(document.formm.quantity.value ==""){
		alert("수량을 입력해주세요");
		document.formm.quantity.focus();
	}
	document.formm.action = "cart_insert";
	document.formm.submit();
}

