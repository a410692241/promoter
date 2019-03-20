<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
   /* .specificationsClass{
        border: 1px solid;
        padding:5px;
        position: absolute;
        left:600px;
        top:10px;
    }*/
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
<div class="specificationsClass">
    <form action="${pageContext.request.contextPath}/goods/goods/toAhowSpecifications.do" method="post" onsubmit="return check();">
        <span style="color: red">没有的规格先在此添加规格</span><br>
        <select name="attributeId" id="attributeId" style="width: 100px;height: 35px;">
            <option value="0">请选择规格</option>
            <c:forEach items="${attributes}" var="attribute">
                <option value="${attribute.attributeId}">${attribute.name}</option>
            </c:forEach>
        </select>
        <input type="text" style="width: 100px;height: 35px;" id="value" placeholder="输入规格" name="value" size="6">
        <div>
            <span style="color: red">${error}</span>
            <span style="color: green">${pass}</span>
        </div>
        <input type="submit" style="margin: 5px;" value="确认添加">&nbsp;&nbsp;
    </form>
</div>
<div>
    <c:if test="${!empty map}">
        <c:forEach items="${map}" var="attr" varStatus="statu">
            <fieldset>
                <legend>${attr.key}</legend>
                <c:if test="${ not empty attr.value}">
                    <c:forEach items="${attr.value}" var="attribute">
                        <td> <label><input class="attribute${statu.count}" onclick="bindAttrVal(${statu.count},this)" name="attributes${statu.count}" type="radio"  value="${attribute}" />${attribute}</label>&nbsp;&nbsp;&nbsp;</td>
                    </c:forEach>
                </c:if>
            </fieldset>
        </c:forEach>
    </c:if>

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
       /* $.ajax({
            type:"POST",
            url:"/goods/goods/addShowSpecifications.do",
            data:{attributeId: attributeId,value: value},
            dataType:"json",
            success:function () {
                alert("刷新");
                window.location.reload();
            }
        })*/

    }


</script>