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
        <input type="submit"  style="margin: 5px;" value="新增规格" onclick="openwin()">&nbsp;&nbsp;
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
    function openwin() {
        window.open ("${pageContext.request.contextPath }/goods/goods/toAddSpecifications.do", "新增规格", "height=800, width=1000,top=0,left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no")
    }

</script>