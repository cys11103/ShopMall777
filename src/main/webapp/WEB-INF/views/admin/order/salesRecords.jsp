<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nonage Admin</title>
<link rel="stylesheet" href="admin/css/admin.css">
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="admin/product/product.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				$.ajax({
					type : 'GET',
					headers : {
						Accept : "application/json; charset=utf-8",
						"Content-type" : "application/json; charset=utf-8"
					},
					url : 'sales_record_chart',
					success : function(result) {
						google.charts.load('current', {
							'packages' : [ 'corechart' ]
						});

						google.charts.setOnLoadCallback(function() {

							drawChart(result);

						})

					},

					error : function() {

						alert("Chart drawing error!");

					}

				});

				function drawChart(result) {

					var data = new google.visualization.DataTable();

					data.addColumn('string', 'pname');

					data.addColumn('number', 'quantity');

					var dataArray = [];

					$.each(result, function(i, obj) {

						dataArray.push([ obj.pname, obj.quantity ]);

					});

					data.addRows(dataArray);

					// 파이 차트 그리기

					var piechart_options = {

						title : '제품별 판매 실적',

						width : 400,

						height : 300

					};

					var piechart = new google.visualization.PieChart(document
							.getElementById('piechart_div'));

					piechart.draw(data, piechart_options);

					// 바차트 그리기

					var barchart_options = {

						title : 'BarChart: 제품별 판매 실적',

						width : 400,

						height : 300,

						legend : 'none'

					};

					var barchart = new google.visualization.BarChart(document
							.getElementById('barchart_div'));

					barchart.draw(data, barchart_options);

				}

			});
</script>

</head>


<body>

	<%@ include file="../sub_menu.jsp"%>


	<div id="wrap">

		<header>

			<div id="logo">

				<a href="admin_login_form"> <img style="width: 800px"
					src="admin/images/bar_01.gif"> <img
					src="admin/images/text.gif">

				</a>

			</div>

			<input class="btn" type="button" value="logout" style="float: right;"
				onClick="location.href='admin_logout'">

		</header>

		<div class="clear"></div>


		<div align="center">

			<h1>제품별 판매 실적</h1>

			<table>

				<tr>

					<td><div id="piechart_div" style="border: 1px solid #ccc"></div></td>

					<td><div id="barchart_div" style="border: 1px solid #ccc"></div></td>

				</tr>

			</table>

		</div>



		<div class="clear"></div>



		<footer>

			<hr>

			<div id="copy">

				All contents Copyright 2013 Nonage Inc. all rights reserved<br>

				Contact mail : Nonage@Nonage.com Tel: +82 64 123 4315 Fax +82 64 123
				4321

			</div>

		</footer>

	</div>

</body>

</html>

