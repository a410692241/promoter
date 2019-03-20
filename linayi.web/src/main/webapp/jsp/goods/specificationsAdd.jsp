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
    <input type="hidden" name="goodsSkuId" value="${goodsSkuId}" />

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

<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    function bindAttrVal(index,element){
        var value = $(element).val();
        $("#attr" + index).val(value);
    }


    var brandName = "${brandName}";
    var categoryName = "${categoryName}";
    var goodsSkuId = "${goodsSkuId}";

    function foo(){
        var attrArr = new Array();
        $(".qqq").each(function(index,ele){
            var val = $(ele).val();
            if(val == null || val == ""){
                attrArr[index] = "无";
            }else{
                attrArr[index] = val;
            }
        });
        var attrStr = attrArr.join(",");
        debugger;
        $.ajax({
            type: "POST",//方法
            url: "specificationsGoodsAdd.do",//表单接收url
            data: {categoryName:categoryName,brandName:brandName,attrStr:attrStr,goodsSkuId:goodsSkuId},
            dataType:"json",
            success: function (data){
                debugger;
                var s = data.data;
                if(data.data == "repate"){
                    alert("商品已存在");
                }else if(data.respCode == "S"){
                    window .opener.document.getElementById("attrValues").value=data.data.attrValues;
                    window .opener.document.getElementById("fullName").value=data.data.fullName;
                    window .close();
                }else if(data.respCode == "F") {
                    alert("新增失败!")
                }
            }
        })
    }
</script>