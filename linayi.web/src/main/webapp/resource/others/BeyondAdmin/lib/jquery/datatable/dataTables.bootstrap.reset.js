//修改datatable默认配置和分页信息

/* Set the defaults for DataTables initialisation */
$.extend(true, $.fn.dataTable.defaults, {
	"bRetrieve": true,
    "paging": true,
    "ordering":false,
    "searching":false,
    "serverSide": true,
    "lengthMenu": [ 10, 25, 50, 75, 100 ],
    "sDom": "ft<'row DTTTFooter'<'col-sm-3'i><'col-sm-9'p<l>>>",
    "oTableTools": {
    	"aButtons": [
    	             
    	             ],
    	             "sSwfPath": "assets/swf/copy_csv_xls_pdf.swf"
    },
    "language": {
    	"search": "",
    	"sEmptyTable":"",
    	"sZeroRecords":"",
    	"sInfoEmpty":"",
    	"sLengthMenu": "_MENU_",
    	"oPaginate": {
    		"sFirst":"<span><i class='fa fa-chevron-left'></i><i class='fa fa-chevron-left'></i></span>",
    		"sLast":"<span><i class='fa fa-chevron-right'></i><i class='fa fa-chevron-right'></i></span>",
    		"sPrevious": "<span><i class='fa fa-chevron-left'></i></span>",
    		"sNext": "<span><i class='fa fa-chevron-right'></i></span>"
    	},
    	"loadingRecords": "数据加载中...",
    	"processing":"加载中...",
    	"info":"显示  _START_ 至 _END_ 条，总 _TOTAL_ 条",
    },
    "fnServerDataSuccess":null,
    "fnServerDataError":null,
    "ajaxContentType":null,
    "ajaxData":null,
    "initComplete": function(settings, json) {},
    "fnServerData": function(  sSource, aoData, fnCallback, oSettings ){
    	var _iDisplayStart = oSettings._iDisplayStart;
    	var _iDisplayLength = oSettings._iDisplayLength;
    	var postData={
    			"pageSize":_iDisplayLength,
    			"currentPage":(_iDisplayStart/_iDisplayLength)+1
    	}
    	var url = oSettings.ajax.url;
    	url = url + (url.indexOf("\\?") > 0 ? "" : "?");
    	url = url + (url.endsWith("\\&") ? "" : "&");
    	$.ajax({
    		url:url+"pageSize="+postData.pageSize+"&currentPage="+postData.currentPage,
    		data: oSettings.oInit.ajaxData ? oSettings.oInit.ajaxData : {},
    		dataType:"json",
    		contentType : oSettings.oInit.ajaxContentType ? oSettings.oInit.ajaxContentType :'application/x-www-form-urlencoded',
    		type:"post",
    		success:function( result ){
    			var flag = true;
    			if( oSettings.oInit.fnServerDataSuccess ){
    				flag = oSettings.oInit.fnServerDataSuccess.apply(this,[]);
    			}
    			if( flag === false ){
    				return;
    			}
    			var resultData={};
    			resultData.recordsTotal=result.total;
    			resultData.recordsFiltered=result.total;
    			resultData.data = result.rows;
    			resultData.draw = oSettings.iDraw
    			fnCallback.apply( this,[ resultData ] );
    		},
    		error:function(){
    			if( oSettings.oInit.fnServerDataError ){
    				oSettings.oInit.fnServerDataError.apply(this,[]);
    			}
    		}
    	})
    }
});



/* Bootstrap style pagination control */
$.extend($.fn.dataTableExt.oPagination, {
    "bootstrap": {
        "fnInit": function (oSettings, nPaging, fnDraw) {
            var oLang = oSettings.oLanguage.oPaginate;
            var fnClickHandler = function (e) {
                e.preventDefault();
                if (oSettings.oApi._fnPageChange(oSettings, e.data.action)) {
                    fnDraw(oSettings);
                }
            };

            $(nPaging).append(
				'<ul class="pagination">' +
					'<li class="first disabled"><a href="#">' + oLang.sFirst + '</a></li>' +
					'<li class="prev disabled"><a href="#">' + oLang.sPrevious + '</a></li>' +
					'<li class="next disabled"><a href="#">' + oLang.sNext + '</a></li>' +
					'<li class="last disabled"><a href="#">' + oLang.sLast + '</a></li>' +
				'</ul>'
			);
            var els = $('a', nPaging);
            $(els[0]).bind('click.DT', { action: "first" }, fnClickHandler);
            $(els[1]).bind('click.DT', { action: "previous" }, fnClickHandler);
            $(els[2]).bind('click.DT', { action: "next" }, fnClickHandler);
            $(els[3]).bind('click.DT', { action: "last" }, fnClickHandler);
        },

        "fnUpdate": function (oSettings, fnDraw) {
            var iListLength = 5;
            var oPaging = oSettings.oInstance.fnPagingInfo();
            var an = oSettings.aanFeatures.p;
            var i, ien, j, sClass, iStart, iEnd, iHalf = Math.floor(iListLength / 2);

            if (oPaging.iTotalPages < iListLength) {
                iStart = 1;
                iEnd = oPaging.iTotalPages;
            }
            else if (oPaging.iPage <= iHalf) {
                iStart = 1;
                iEnd = iListLength;
            } else if (oPaging.iPage >= (oPaging.iTotalPages - iHalf)) {
                iStart = oPaging.iTotalPages - iListLength + 1;
                iEnd = oPaging.iTotalPages;
            } else {
                iStart = oPaging.iPage - iHalf + 1;
                iEnd = iStart + iListLength - 1;
            }
            
            for (i = 0, ien = an.length ; i < ien ; i++) {
                // Remove the middle elements
                $('li:gt(1)', an[i]).not(".last,.next").remove();

                // Add the new list items and their event handlers
                for (j = iStart ; j <= iEnd ; j++) {
                    sClass = (j == oPaging.iPage + 1) ? 'class="active"' : '';
                    $('<li ' + sClass + '><a href="#">' + j + '</a></li>')
						.insertBefore($('li.next', an[i])[0])
						.bind('click', function (e) {
						    e.preventDefault();
						    oSettings._iDisplayStart = (parseInt($('a', this).text(), 10) - 1) * oPaging.iLength;
						    fnDraw(oSettings);
						});
                }

                // Add / remove disabled classes from the static elements
                if (oPaging.iPage === 0) {
                    $('li.first', an[i]).addClass('disabled');
                    $('li.prev', an[i]).addClass('disabled');
                } else {
                    $('li.first', an[i]).removeClass('disabled');
                    $('li.prev', an[i]).removeClass('disabled');
                }

                if (oPaging.iPage === oPaging.iTotalPages - 1 || oPaging.iTotalPages === 0) {
                    $('li.last', an[i]).addClass('disabled');
                    $('li.next', an[i]).addClass('disabled');
                } else {
                    $('li.last', an[i]).removeClass('disabled');
                    $('li.next', an[i]).removeClass('disabled');
                }
            }
        }
    }
});