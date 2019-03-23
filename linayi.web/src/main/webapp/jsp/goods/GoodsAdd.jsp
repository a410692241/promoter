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
            <div class="form-header">
                <div class="form-category">
                    <input  name="category" type="text" id="makeInput" class="ww" onfocus="setDemo(this,event)" onkeyup="setContent(this,event);" placeholder="请选择或输入分类">
                    <select id="hh" class="form-selectName" onclick="choose(this)" onkeyup="getfocus(this,event)" size="10" style="display:none;">
                        <c:forEach items="${categorys}" var="category">
                            <option value="${category.name}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-category">
                    <input name="brand" type="text" id="makeInput1" class="ww" onfocus="setDemo1(this,event)" onkeyup="setContent1(this,event);" placeholder="请选择或输入品牌">
                    <select  id="hh1" class="form-selectName" onclick="choose1(this)" onkeyup="getfocus1(this,event)" size="10" style="display:none;">
                        <c:forEach items="${brands }" var="brand">
                            <option value="${brand.name }">${brand.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-button"><input type="button" value="显示关联规格" onclick="opens();"></div>
                <div><div id="list"></div></div>
                <div class="form-button"><input type="button" value="清除选择" id="cccc"></div>
                <div></div>
            </div>
            <table class="form-center">
                <tr>
                    <td style="width: 60px">商品名:</td>
                    <%--<td>
                        <input  name="name" type="text" id="goodsName" class="ww" onfocus="setDemo(this,event)" onkeyup="setContent(this,event);" placeholder="请选择或输入商品名称">
                        <select id="hh" class="form-selectName" onclick="choose(this)" onkeyup="getfocus(this,event)" size="10" style="display:none;">
                            <c:forEach items="${goodsNames}" var="goodsName">
                                <option value="${goodsName}">${goodsName}</option>
                            </c:forEach>
                        </select>
                    </td>--%>
                    <td><input type="text" id="goodsName" name="name" ></td>
                    <td><input type="button" id="specifications" value="从规格库选择" onclick="openwin()"></td>
                </tr>
                <tr>
                    <td>型号:</td>
                    <td><input type="text" id="gmarque" name="model"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>功能:</td>
                    <td><input type="text" id="goodsFunction" name="function"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>产地:</td>
                    <td><input type="text" id="placeOfOrigin" name="produceAddress"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>生产日期:</td>
                    <td><input type="date" id="dateOfManufacture" name="produceDate"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>有效期:</td>
                    <td><input type="date" id="termOfValidity" name="validDate"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>产家:</td>
                    <td><input type="text" id="producer" name="manufacturer"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>其他属性:</td>
                    <td><input type="text" id="otherAttributes" name="otherAttribute"></td>
                    <td></td>
                </tr>
                <tr style="height: 120px">
                    <td colspan="3"><div id="preview"></div></td>
                </tr>
                <tr>
                    <td style="width: 80px">图片:</td>
                    <td><input type="file" name="file" style="display: inline-block" id="file" value="上传图片" onchange="preview(this)"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td>条形码:</td>
                    <td><input type="text" id="barcode" name="barcode"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>超市:</td>
                    <td><select id="supermarket" style="width: 160px" name="supermarketId"></select></td>
                    <td></td>
                </tr>
                <tr>
                    <td>价格类型：</td>
                    <td>
                        <select id="priceType" style="width: 160px" name="priceType">
                            <option value="NORMAL" selected="selected">正常价</option>
                            <option value="PROMOTION">促销价</option>
                            <option value="DEAL">处理价</option>
                            <option value="MEMBER">会员价</option>
                        </select>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td>分享价格：</td>
                    <td><input type="text" id="price" name="price"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>分享开始自：</td>
                    <td><input type="date" id="startTime" name="startTime"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>分享结束于：</td>
                    <td><input type="date" id="endTime" name="endTime"></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="3"><input type="button" id="tj"  onclick="tijiao();" value="确认添加">&nbsp;&nbsp;</td>
                </tr>
            </table>
            <div>
            </div>
        </div>
        <div>
            <div id="ddd">
                <table id="form-addgoodsRight">
                </table>
            </div>
        </div>
    </div>

    <%-- <input type="hidden" name="token" value="${token}"> --%>

    <p><span id="pass" style="color: green;font-size: 50px">${pass}</span></p>
    <p><span id="pass1" style="color: red;font-size: 50px">${goodsRepeat}</span></p>
</form>
<script type="text/javascript">

    var Arr = []; //存储option
    var flag = "${flag}";
    if (flag == 1){
        alert("商品添加成功!");
    }
    if (flag == 2){
        alert("商品添加失败!");
    }
    /*先将数据存入数组*/
    $("#hh option").each(function(index, el) {
        Arr[index] = $(this).text();
    });
    $(document).bind('click', function(e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id == 'hh' || elem.id == "makeInput")) {
                return;
            }
            elem = elem.parentNode;
        }
        $('#hh').css('display', 'none');
    });

    var Arr1 = []; //存储option
    /*先将数据存入数组*/
    $("#hh1 option").each(function(index, el) {
        Arr1[index] = $(this).text();
    });
    $(document).bind('click', function(e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id == 'hh1' || elem.id == "makeInput1")) {
                return;
            }
            elem = elem.parentNode;
        }
        $('#hh1').css('display', 'none');
    });

    $(".ww").blur(function () {
        $(this).val("");
    });

    function openwin() {
        var classifyName = $("#makeInput").val();
        var brandName = $("#makeInput1").val();
        if(classifyName == null || classifyName == ""){
            alert("请选择或输入分类");
            return false;
        }
        if(brandName == null || brandName == ""){
            alert("请选择或输入品牌");
            return false;
        }
        window.open ("${pageContext.request.contextPath }/goods/goods/showSpecifications.do", "请选择规格", "height=800, width=1000,top=0,left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no")
    }

    function choose(a) {
        $(a).prev("input").val($(a).find("option:selected").text());
        $("#hh").css({
            "display": "none"
        });
    }

    function setDemo(a, event) {
        $("#makeInput").val("");
        $("#hh").css({
            "display": ""
        });

        var select = $("#hh");
        select.html("");
        setContent(a, event);
    }

    function setContent(a, event) {
        var select = $("#hh");
        select.html("");
        for (i = 0; i < Arr.length; i++) {
            //若找到包含txt的内容的，则添option
            if (Arr[i].indexOf(a.value) >= 0) {
                var option = $("<option value='" + i + "'></option>").text(Arr[i]);
                select.append(option);
            }
        }
        if (event.keyCode == 40) {
            //按向下的键之后跳转到列表中
            //焦点转移并且选中第一个值
            $("#hh").focus();
            $("#hh").find("option:first").attr("selected", "true");
            return false;
        }
    }

    function getfocus(this_, event) {
        if (event.keyCode == 13) { //回车键获取内容
            $(this_).prev("input").val($(this_).find("option:selected").text());
            $("#hh").css({
                "display": "none"
            });
        } else if (event.keyCode == 38) { //向上键回到输入框
            var option = $(this_).find("option:selected");
            if (option.prev().val() == undefined) {
                $('#makeInput').focus();
            }
        }
        return false;
    }

    function choose1(a) {
        $(a).prev("input").val($(a).find("option:selected").text());
        $("#hh1").css({
            "display": "none"
        });
    }

    function setDemo1(a, event) {
        $("#makeInput1").val("");
        $("#hh1").css({
            "display": ""
        });
        var select = $("#hh1");
        select.html("");
        setContent1(a, event);
    }

    function setContent1(a, event) {
        var select = $("#hh1");
        select.html("");
        for (i = 0; i < Arr1.length; i++) {
            //若找到包含txt的内容的，则添option
            if (Arr1[i].indexOf(a.value) >= 0) {
                var option = $("<option value='" + i + "'></option>").text(Arr1[i]);
                select.append(option);
            }
        }
        if (event.keyCode == 40) {
            //按向下的键之后跳转到列表中
            //焦点转移并且选中第一个值
            $("#hh1").focus();
            $("#hh1").find("option:first").attr("selected", "true");
            return false;
        }
    }

    function getfocus1(this_, event) {
        if (event.keyCode == 13) { //回车键获取内容
            $(this_).prev("input").val($(this_).find("option:selected").text());
            $("#hh1").css({
                "display": "none"
            });
        } else if (event.keyCode == 38) { //向上键回到输入框
            var option = $(this_).find("option:selected");
            if (option.prev().val() == undefined) {
                $('#makeInput1').focus();
            }
        }
        return false;
    }

    //处理筛选规格
    function opens(){
        var classifyN = $("#makeInput").val();
        var brandN = $("#makeInput1").val();
        if(classifyN == null || classifyN == ""){
            alert("请选择或输入分类");
            return false;
        }
        if(brandN == null || brandN == ""){
            alert("请选择或输入品牌");
            return false;
        }
        $.ajax({
            type: "POST",//方法
            url: "goods/goods/specificationsFilter.do",//表单接收url
            data: {categoryName: classifyN,brandName: brandN},
            dataType:"json",
            success: function (obj) {
               //清空
                $('#list').empty();
                if (obj != null) {
                    for (var i = 0; i < obj[0].length; i++) {
                        if(obj[0][i] != null && obj[0][i] > 0){
                            var attrValu = $(".qqq" + (i + 1) + ":first").val();
                            var ids = $('#list' + (i + 1)).attr("id");
                            if (!ids){
                                var $list = $('<div id="list' + (i + 1) + '"></div>');
                                $list.appendTo('#list');
                            }
                            ids = $('#list' + (i + 1)).attr("id");
                            for (var j = 0; j < obj[1].length; j++) {
                                var str = "";
                                if (attrValu == obj[1][j].value) {
                                    str = 'checked="true"';
                                }
                                if (obj[0][i] == obj[1][j].attributeId){
                                    var $li = $('<div class="radio" style="display: inline-block;margin-right: 5px;"><label><input  class="colored-blue" name="a' + (i + 1) + '" ' + str + ' id="a' + (i + 1) +'" type="radio" ' +
                                        'value="'+ obj[1][j].value +'" onclick="bindAttrVal('+ (i+1) +');" /><span class="text">' + obj[1][j].value + '</span></label></div>');
                                    $li.appendTo('#list' + (i + 1));
                                }
                            }
                        }
                    }
                }
            }
        });
    };


    function bindAttrVal(index){
        $("#a" + index).each(function(){//给所有的input绑定事件
            var l=[]; //创建空数组l
            $("#a" + index + ":checked").each(function(i){l[i]=this.value});
            //将所有的选中的值存放l
            $("#attribute" + index).val(l);//将数据值联合字符串给显示对象附值 */
        });
    };
    $("#cccc").click(function(){
        $("input[type='radio']").removeAttr('checked');
        $('#list').empty();
        $(".form-category input").each(function (index,ele) {
        $(ele).val("");
        });
    });

    function tijiao() {
        var classifyName = $("#makeInput").val();
        var brandName = $("#makeInput1").val();
        var goodsName = $("#goodsName").val();
        var barcode = $("#barcode").val();

        if(classifyName == null || classifyName == ""){
            alert("请选择或输入分类");
            return false;
        }
        if(brandName == null || brandName == ""){
            alert("请选择或输入品牌");
            return false;
        }
        if(goodsName == null || goodsName == ""){
            alert("请输入商品名！");
            return false;
        }
        if(barcode == null || barcode == ""){
            alert("请输入商品条形码！");
            return false;
        }
        if(barcode.length > 13){
            alert("商品条形码长度不能超过13位!");
            return false;
        }
        $('#tj').attr('disabled',"true");
        var form = new FormData(document.getElementById("add_goods"));
        $.ajax({
            url:"${pageContext.request.contextPath}/goods/goods/addGoodsForAdmin.do",
            type:"post",
            data:form,
            processData:false,
            contentType:false,
            success:function(data){
                $('#tj').removeAttr("disabled");
               if(data.respCode == "S"){
                   alert("添加成功！");
                   goodsSku = data.data;
                   goodsSkuId = goodsSku.goodsSkuId;
                   $("#add_goods").find("input").each(function (inedx,ele) {
                       var name = $(ele).attr("name");
                       if (name){
                           $(ele).val("");
                       }
                   });
                   $('#list').empty();
                   $("#preview").empty();
                   $("input[type='radio']").removeAttr('checked');
               }else if(data.respCode == "barcodeRepeat"){
                    alert("商品条形码已存在！");
                }else if(data.respCode == "nameRepeat"){
                   alert("商品名称已存在！");
               }else{
                   alert("添加失败！");
               }
            },
            error:function(){
                alert("错误！！");
            }
        });
    };

    function configPriceAfterAddGoods(id){
        var url = urls.ms + "/goods/goods/shareEdit.do?";
        if( id ){
            url = url + $.param( {goodsSkuId:id} );
        }
        templateform.open({
            title:"价格分享",
            url:url,
            scope:$scope,
            onOpen:function( $modalInstance, data ,$scope){

            }
        },function( $modalInstance,data, $scope ){
            saveShare( $modalInstance,data, $scope );
        });
    }

    $.ajax({
        url: "${pageContext.request.contextPath}/goods/goods/getAttributeList.do",
        type:"post",
        dataType:"json",
        success:function (data) {
            if (data) {
                for (var i = 0; i < data.length; i++) {
                    var $input = $("<tr><td class='form-addgoodsRightTd'>" +data[i].name + "<td><td>" + "<input class='qqq" + (i + 1) + "' type='text' name='attribute' id='attribute" + (i + 1) + "'  readonly=readonly size='6'></td></tr>");
                    $input.appendTo("#form-addgoodsRight");
                }
            }
        }
    });
    $.ajax({
        url: "${pageContext.request.contextPath}/goods/goods/listSupermarket.do",
        type: "post",
        dataType: "json",
        success:function (data) {
            var supermarketList = data.data;
            if (data) {
                for(var i = 0; i < supermarketList.length; i++){
                    var $supermarket_option = $("<option value='" + supermarketList[i].supermarketId + "'>" + supermarketList[i].name + "</option>");
                    $supermarket_option.appendTo("#supermarket")
                }
            }
        }
    })
</script>
<%-- <c:if test="${!empty attributes}"></c:if>--%>
<%--<c:forEach items="${attributes}" var="attribute" varStatus="statu">--%>
    <%--<tr>--%>
        <%--<td>${attribute.name}</td><td><input class="qqq${statu.num}" type=text name="attribute" id="attribute${statu.num}" readonly="readonly"  size="6"></td>--%>
    <%--</tr>--%>
<%--</c:forEach>--%>

