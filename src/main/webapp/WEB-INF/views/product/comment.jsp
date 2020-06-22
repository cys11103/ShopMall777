
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<!-- link rel="stylesheet" href="css/bootstrap.min.css"> -->

</head>

<body>

	<div class="container">

		<form id="commentForm" name="commentForm" method="post">

			<br> <br>

			<div>

				<div>

					<span><h3>상품평</h3></span> <span id="cCnt"></span>

				</div>

				<div>

					<table class="table">

						<tr>

							<td><textarea style="width: 650px" rows="3" cols="30"
									id="content" name="content" placeholder="댓글을 입력하세요"></textarea>

								<br>

								<div>

									<a href='#' onClick="save_comment('${productVO.pseq }')"
										class="btn pull-right btn-success">등록</a>

								</div></td>

						</tr>

					</table>

				</div>

			</div>

			<input type="hidden" id="pseq" name="pseq" value="${productVO.pseq }" />

		</form>

	</div>

	<div class="container">

		<form id="commentListForm" name="commentListForm" method="post">

			<div id="commentList"></div>

		</form>

	</div>

	<script>
	/*
	 * 초기 로딩시 상품평 목록 불러오기
	 */
	 $(function(){
		 getCommentList();
	 });
	
	
		/*
		 * 상품평 등록
		 */
		function save_comment(pseq) {

			$.ajax({
				type : 'POST',
				url : 'save_comment',
				data : $("#commentForm").serialize(), //form 내의 입력 데이터 전송
				success : function(data) {
					if (data = "success") { //상품평 등록 성공
						getCommentList(); //상품평 목록 요청함수 호출
						$("#content").val("");
					}
				},
				error : function(request, status, error) {
					alert("error:" + error);
				}
			});

		}

		/*
		 * 상품평 목록 불러오기
		 */
		function getCommentList() {
			$
					.ajex({
						type : 'GET',
						url : 'comment_list',
						dataType : "json",
						data : $("#commentForm").serialize(),
						contentType : "application/x-www-form-urlencoded; charset=UTF-8",
						success : function(data) {
							var html = "";
							var cCnt = data.length;

							if (data.length > 0) {
								for (i = 0; i < data.length; i++) {

									html += "<div>";
									html += "<div id=\"comment_item\"> <strong>작성자: " + data[i].writer + "</strong>"
									html += "<span id=\"write_date\">"+ displayTime(data[i].regDate)+ "</scan>&nbsp;&nbsp;&nbsp;"
									html += data[i].content + "<br></div>"
									html += "</div>";
								}
							}else{
								html += "<div>";
								html += "<div><h6>등록된 상품평이 없습니다.";
								html += "</div>"
							}
							
							$("#cCnt").html(cCnt); //상품평의 갯수 출력
							
							$("#commentList").html(html);
						},
						error: function(request, status, error){
							alert("상품평 목록을 조회하지 못했습니다.");
						}
					});
		}
		
		/*
		 * 작성일 출력 함수
		 */

		 function displayTime(timeValue) {
		     var today = new Date();

		     // 24시간 이내인지 계산하기 위함
		     var gap = today.getTime() - timeValue;

		     

		     var dateObj = new Date(timeValue);

		     var str = "";

		     

		     // 24시간 이내일 경우 시,분,초만 표시

		     if (gap < (1000 * 60 * 60 * 24)) {

		         var hh = dateObj.getHours();

		         var mi = dateObj.getMinutes();

		         var ss = dateObj.getSeconds();

		         

		         return [ (hh>9 ? '' : '0') + hh, ':', (mi>9 ? '' : '0') + mi, ':',

		                 (ss>9 ? '' : '0') + ss].join('');

		     } else {

		         var yy = dateObj.getFullYear();

		         var mm = dateObj.getMonth() + 1;

		         var dd = dateObj.getDate();

		         

		         return [ yy, '/', (mm>9 ? '':'0') + mm, '/', (dd>9 ? '':'0') + dd].join('');

		     }
		}


	</script>

</body>

</html>

