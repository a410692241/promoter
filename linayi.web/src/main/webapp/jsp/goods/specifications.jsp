<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
	.specificationsClass{
		border: 1px solid;
		padding:5px;
		position: absolute;
		left:600px;
		top:10px;
	}
	.showSpecifications{
		width:150px;
		height:80px;
		position: fixed;
		left:830px;
		top:10px;
	}
	/* 	.qqq{

        border:none;
        } */



</style>

<div>
	<c:if test="${!empty map}">
		<c:forEach items="${map}" var="attributes" varStatus="statu">
			${attributes.key}:<input class="qqq" type=text name="attr${statu.count}"  id="attr${statu.count}" readonly="readonly"  size="3">
			<c:if test="${statu.count % 6 ==0}">
				<br/><br/>
			</c:if>
		</c:forEach>
	</c:if>
	<%-- 容量:<input class="qqq" type=text name="capacity" id="capacity" readonly="readonly"  size="3">
     重量:<input class="qqq" type=text name="weight" id="weight" readonly="readonly" size="3">
     规格:<input class="qqq" type=text name="specification" id="specification" readonly="readonly" size="3">
     大小:<input class="qqq" type=text name="size" id="size" readonly="readonly" size="3">
     颜色:<input class="qqq" type=text name="color" id="color" readonly="readonly" size="3">
     克重:<input class="qqq" type=text name="gram" id="gram" readonly="readonly" size="3">
     袋装:<input class="qqq" type=text name="Bagged" id="Bagged" readonly="readonly" size="3"><br><br>
     瓶装:<input class="qqq" type=text name="bottled" id="bottled" readonly="readonly" size="3">
     --%>
	<input type="button" id="bu" value="确定" onclick="foo();" class="showSpecifications"><br><br>
	<c:if test="${!empty map}">
		<c:forEach items="${map}" var="attr" varStatus="statu">
			<fieldset>
				<legend>${attr.key}</legend>
				<c:if test="${ not empty attr.value}">
					<c:forEach items="${attr.value}" var="attribute">
						<td> <label><input class="attribute${statu.count}" onclick="bindAttrVal(${statu.count},this)" name="attributes${statu.count}" type="radio"  value="${attribute}" />${attribute}</label> </td>
					</c:forEach>
				</c:if>
			</fieldset>
		</c:forEach>
	</c:if>

</div>
<div class="specificationsClass">
	<form action="${pageContext.request.contextPath}/goods/goods/showSpecifications.do" method="post" onsubmit="return check();">
		<span style="color: red">没有的规格先在此添加规格</span><br>
		<select name="attributeId" id="attributeId">
			<option value="0">请选择规格</option>
			<c:forEach items="${attributes}" var="attribute">
				<option value="${attribute.attributeId}">${attribute.name}</option>
			</c:forEach>
		</select>
		<input type="text" id="value" placeholder="输入规格" name="value" size="6">
		<div>
			<span style="color: red">${error}</span>
			<span style="color: green">${pass}</span>
		</div>
		<input type="submit" value="确认添加">&nbsp;&nbsp;
		<%--<button onclick="location.reload();">单击刷新页面</button>--%>

	</form>
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	function bindAttrVal(index,element){
		var value = $(element).val();
		$("#attr" + index).val(value);
	}



	function foo(){
		var attrArr = new Array();
		$(".qqq").each(function(index,ele){
			var val = $(ele).val();
			if(val == null || val == ""){
				attrArr[index] = "无";
				window .opener.document.getElementById("attribute" + (index + 1)).value="";
			}else{
				attrArr[index] = val;
				window .opener.document.getElementById("attribute" + (index + 1)).value=val;
			}
		});
		var attrStr = attrArr.join(",");
		window .close();
		var categoryName = window .opener.document.getElementById("makeInput").value;
		var brandName = window .opener.document.getElementById("makeInput1").value;
		/*var packing = document.getElementById("packing").value;
		var color = document.getElementById("color").value;
		var capacity = document.getElementById("capacity").value;
		var weight = document.getElementById("weight").value;
		var taste = document.getElementById("taste").value;
		var material = document.getElementById("material").value;
		var pumpingNum = document.getElementById("pumpingNum").value;
		var alcoholicStrength = document.getElementById("alcoholicStrength").value;
		var salesAttributes = document.getElementById("salesAttributes").value;
		var productAttributes = document.getElementById("productAttributes").value;

		window .opener.document.getElementById("color").value=color;
		window .opener.document.getElementById("capacity").value=capacity;
		window .opener.document.getElementById("weight").value=weight;
		window .opener.document.getElementById("taste").value=taste;
		window .opener.document.getElementById("material").value=material;
		window .opener.document.getElementById("pumpingNum").value=pumpingNum;
		window .opener.document.getElementById("alcoholicStrength").value=alcoholicStrength;
		window .opener.document.getElementById("salesAttributes").value=salesAttributes;
		window .opener.document.getElementById("productAttributes").value=productAttributes;*/

		$.ajax({
			type: "POST",//方法
			url: "specificationsAdd.do",//表单接收url
			data: {categoryName:categoryName,brandName:brandName,attrStr:attrStr},
			dataType:"json",
			success: function (obj){
			}
		})
	}

	function check() {
		var attributeId = $("#attributeId").val();
		var value = $("#value").val();
		if (attributeId == null || attributeId == ""){
			alert("请选择规格！");
			return false;
		}
		if (value == null || value == ""){
			alert("请填写规格值！");
			return false;
		}
		/*$.ajax({
			type:"POST",
			url:"showSpecifications.do",
			data:{attributeId: attributeId,value: value},
			dataType:"json",
			success:function () {
				alert("刷新");
				window.location.reload();
			}
		})*/

	}


</script>