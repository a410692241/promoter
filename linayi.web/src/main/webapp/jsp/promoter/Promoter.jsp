<%@ page contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<style type="text/css">
    .ww {
        width:167px;
        outline:none;
        line-height:30px;
        left:8px;
        border:1px solid #999;
    }
    .selectName {
        position:absolute;
        width:169px;
        margin-top:42px;
        margin-left:15px;
        z-index:999999;
        outline:none;
    }
    .search select option:hover {
        color:white;
        background-color:rgb(12,172,249);
    }

    body{
        text-align:center;
        margin-left:auto;
        margin-right:auto;
        margin-top:50px;
    }

    #preview,.img,img {
        width:100px;
        height:100px;
    }
    #preview {
        text-align:center;
        margin-left:auto;
        margin-right:auto;
        border:1px solid #000;
    }

    .qqq{

        border:none;
    }
    .form-addgoods {
        display: grid;
        grid-template-columns: auto 60%;
        margin-left: 100px;
    }
    .form-header{
        display: grid;
        grid-template-columns: 180px 180px 100px 100px 70px auto;
    }
    .form-category {
        position: relative;
    }
    .form-selectName {
        position:absolute;
        left: 6px;
        top: 35px;
        width:169px;
        z-index:999999;
        outline:none;
    }
    .form-button {
        margin-top: 4px;
    }
    .form-center {
        margin-top: 50px;
    }
    .form-center tr {
        height: 40px;
    }
    #form-addgoodsRight tr {
        height: 40px;
    }
    .form-addgoodsRightTd {
        width: 60px;
    }

</style>

<script>
    var _URL = window.URL || window.webkitURL;
    function preview(file) {
        var prevDiv = document.getElementById('preview');
        if (file.files && file.files[0]) {
            var reader = new FileReader();
            reader.onload = function(evt) {
                prevDiv.innerHTML = '<img id="imgnode" src="' + evt.target.result + '" />';
                console.log(evt.target.result)
            }
            reader.readAsDataURL(file.files[0]);
        } else {
            prevDiv.innerHTML = '<div class="img" id="imgnode"  style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
        }
    }
</script>
<form id="add_goods"
      enctype="multipart/form-data">
    <div class="form-addgoods">
        <div>
            <table class="form-center">
                <tr>
                    <td style="width: 100px">推广商ID:</td>
                    <td><input type="text" id="promoterId" name="promoterId" ></td>
                </tr>
                <tr>
                    <td style="width: 60px">下单员ID:</td>
                    <td><input type="text" id="orderManId" name="orderManId" ></td>
                </tr>
                <tr>
                    <td style="width: 60px">下单员身份:</td>
                    <td><input type="text" id="identity" name="identity" ></td>
                </tr>
                <tr>
                    <td style="width: 60px">下单员级别:</td>
                    <td><input type="text" id="orderManLevel" name="orderManLevel" ></td>
                </tr>
                <tr>
                    <td style="width: 60px">下单员类型:</td>
                    <td><input type="text" id="parentType" name="parentType" ></td>
                </tr>
                <tr>
                    <td colspan="3"><input type="button" id="tj"  onclick="tijiao();" value="确认添加">&nbsp;&nbsp;</td>
                </tr>
            </table>
            <div>
            </div>
        </div>
    </div>

    <%-- <input type="hidden" name="token" value="${token}"> --%>
</form>
<script type="text/javascript">
    function tijiao() {
        var promoterId = $("#promoterId").val();
        var orderManId = $("#orderManId").val();
        var identity = $("#identity").val();
        var orderManLevel = $("#orderManLevel").val();
        var parentType=$("#parentType").val();
        if(promoterId == null || promoterId == ""){
            alert("请输入推广商ID");
            return false;
        }
        if(orderManId == null || orderManId == ""){
            alert("请输入开通下单员ID");
            return false;
        }
        if(identity == null || identity == ""){
            alert("请输入开通下单员身份！");
            return false;
        }
        if(orderManLevel == null || orderManLevel == ""){
            alert("请输入开通下单员级别！");
            return false;
        }
        if(parentType == null || parentType == ""){
            alert("请输入开通下单员类型！");
            return false;
        }
        $('#tj').attr('disabled',"true");
        var form = new FormData(document.getElementById("add_goods"));
        $.ajax({
            url:"${pageContext.request.contextPath}/promoter/promoter/add.do",
            type:"post",
            data:form,
            processData:false,
            contentType:false,
            success:function(data){
                $('#tj').removeAttr("disabled");
                if(data.respCode == "S"){
                    alert("添加成功！");
                }
            },
            error:function(){
                alert("错误！！");
            }
        });
    };

</script>
<%-- <c:if test="${!empty attributes}"></c:if>--%>
<%--<c:forEach items="${attributes}" var="attribute" varStatus="statu">--%>
<%--<tr>--%>
<%--<td>${attribute.name}</td><td><input class="qqq${statu.num}" type=text name="attribute" id="attribute${statu.num}" readonly="readonly"  size="6"></td>--%>
<%--</tr>--%>
<%--</c:forEach>--%>

