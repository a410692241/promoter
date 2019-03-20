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



</style>
<div class="specificationsClass">
    <form>
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
        <input type="submit" style="margin: 5px;" value="确认添加" onclick="tijiao()">&nbsp;&nbsp;
    </form>
</div>
<%--<div>
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
</div>--%>


<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script type="text/javascript">

    function tijiao() {
        debugger;
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
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/goods/goods/addSpecifications.do",
            data:{attributeId: attributeId,value: value},
            dataType:"json",
            success:function (data) {
                debugger;
                if(data.respCode == "S"){
                    self.opener.location.reload();
                    window.close();
                }else {
                    alert("新增失败!");
                }

            }
        })

    }


</script>