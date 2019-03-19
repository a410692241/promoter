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
<%--<script type="text/javascript" src="/resource/others/BeyondAdmin/lib/jquery/jqgrid/js/jquery-1.11.0.min.js"></script>--%>
<%--<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>--%>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
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

    function edit(){
        var form = new FormData(document.getElementById("add_goods"));
        $.ajax({
            url:"${pageContext.request.contextPath}/goods/goods/editGoodsAttribute.do",
            type:"post",
            data:form,
            processData:false,
            contentType:false,
            success:function(data){
                if(data.respCode == "S"){
                    debugger;
                    alert("修改成功！");
                    window .opener.document.getElementById("attrValues" ).value=data.data;
                    window.close();
                }else{
                    alert("添加失败！");
                }
            },
            error:function(){
                alert("错误！！");
            }

        });
    }

    function cancel() {
        window.close();
    }
</script>
<form id="add_goods"
      enctype="multipart/form-data">
    <input type="hidden" name="goodsSkuId" value="${goodsSkuId}" />
    <div class="form-addgoods">
        <div>
            <div id="list"></div>
        </div>
        <tr>
            <td colspan="3"><input type="button" onclick="edit();" value="修改">&nbsp;&nbsp;</td>
            <td colspan="3"><input type="button" onclick="cancel();" value="取消">&nbsp;&nbsp;</td>
        </tr>
    </div>
    <div id="ddd">
        <table id="form-addgoodsRight">
        </table>
    </div>
</form>
<script type="text/javascript">

    function attributes(){
        $.ajax({
            url: "${pageContext.request.contextPath}/goods/goods/getAttributeList.do",
            type:"post",
            dataType:"json",
            success:function (data) {
                if (data) {
                    for (var i = 0; i < data.length; i++) {
                        var $input = $("<tr><td class='form-addgoodsRightTd'><td/><td>" + "<input class='qqq" + (i + 1) + "' type='hidden' name='attribute' id='attribute" + (i + 1) + "'  readonly=readonly size='6'></td></tr>");
                        $input.appendTo("#form-addgoodsRight");
                    }
                }
            }
        });
    }

    attributes();

    var brandName = "${brandName}";
    var categoryName = "${categoryName}";
    $.ajax({
        type: "POST",//方法
        url: "${pageContext.request.contextPath}/goods/goods/specificationsFilter.do",//表单接收url
        data: {brandName: brandName,categoryName:categoryName},
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

    function bindAttrVal(index){
        $("#a" + index).each(function(){//给所有的input绑定事件
            var l=[]; //创建空数组l
            $("#a" + index + ":checked").each(function(i){l[i]=this.value});
            //将所有的选中的值存放l
            $("#attribute" + index).val(l);//将数据值联合字符串给显示对象附值 */
        });
    };

</script>

