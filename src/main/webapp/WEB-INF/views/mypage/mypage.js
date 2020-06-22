/**
 * 장바구니 삭제 처리
 */

//type="checkbox" name="cseq" 배열로 삭제
function go_cart_delete(){
	
	var count = 0;
	
	/*
	 * 삭제할 항목이 하나일 경우 배열로 체크가 안되므로 추가됨
	 */
	if(document.formm.cseq.length == undefined){
		if(document.formm.cseq.checked == true){
			count++;
		}
	}
	
	//삭제할 항목이 체크되어 있는지 개수 확인
	for(var i=0; i<document.formm.cseq.length; i++){
		if(document.formm.cseq[i].checked == true){
			count++;
		}
	}
	
	if(count == 0){
		alert("삭제할 항목을 선택해 주세요!");
	}else{
		document.formm.action = "cart_delete";
		document.formm.submit();
	}
}

function go_order_insert(){
	document.formm.action = "order_insert";
	document.formm.submit();
}