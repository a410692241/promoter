'use strict';
app.service('feedbackService', [function() {
	function ajax( options ){
		$.ajax({
			url:options.url,
			data:options.data,
			async:options.async ? options.async : true,
			dataType: options.dataType ? options.dataType : "json",
			type: options.type ? options.type : "post",
			success: options.success,
	    	contentType : options.contentType ? options.contentType : "application/x-www-form-urlencoded",
			error: options.error ? options.error : function(){}
		}).then(function( data ){
			if( options.then ){
				options.then.call( this,data );
			}
		});
	}
	
	function save( options ){
		var data = options.data;
	    if( data.account==null || data.account==""){
			throw new BusinessException("请输入账号名称！");
		}
	    if( !/\w{6,}/.test( data.account ) ){
			throw new BusinessException("账号至少6位（数字或字母或数字与字母组成）！");
		}
	    if( data.accountId==null || data.accountId==""){
	    	if( data.password==null || data.password==""){
		 		throw new BusinessException("请输入密码！");
		 	}else if(data.password!=null && data.password!=""){
		 		var regexp_1 = /^([0-9]|[a-z]|[A-Z]){6,20}$/;
				var regexp_2 = /^([0-9]{6,20})$|^(([a-z]|[A-Z]){6,20}$)/;
			 	if(!regexp_1.test(data.password) || regexp_2.test(data.password)){
			 		throw new BusinessException("密码必须是6-20位字母与数字组合");
			 	}
		 	}
		 if(data.password != data.password1){
		 	throw new BusinessException("两次输入密码不一致！");
		 }
		}
	    
	    if(data.realName==null || data.realName==""){
		 	throw new BusinessException("请输入真实姓名！");
		}

	    if(data.userPhone==null || data.userPhone==""){
			throw new BusinessException("请输入联系电话！");
			
		 }else if(data.userPhone!="" && data.userPhone!=null){
		 	var phoneFormat = /^(1[3|5|8])[\d]{9}$/;
		 	var isPhone = phoneFormat.test(data.userPhone);
		 	if(!isPhone){
		 		throw new BusinessException("联系电话（手机号码）格式不正确！");
		 	}
		 }

	     if( data.accountId != '' && data.initRealName ==  data.realName && data.initUserPhone == data.userPhone){
			throw new BusinessException("未做任何修改！");
		 }
	     
		options.data = {
			accountId:data.accountId,
			realName:data.realName,
			account:data.account,
			userPhone:data.userPhone,
			password:data.password
		}
		options.url = urls.ms+"/backstageUserAction/addUser.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/user/user/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+'/backstageUserAction/delUser.do';
		ajax( options );
	}
	
	function resetPWD( options ){
		options.url = urls.ms+'/backstageUserAction/setUserPWD.do';
		ajax( options );
	}
	
	function cancelFrozen( options ){
		options.url = urls.ms+'/backstageUserAction/cancelFrozen.do';
		ajax( options );
	}
	
	function fenpeiRole( options ){
		options.url = urls.ms+'/roleAction/addUserRole.do?accountId='+options.data.accountId;
		options.contentType = "application/json";
		options.data=angular.toJson( options.data.roleIdList );
		ajax( options );
	}
	
	function listUserRolePrivilege( options ){
		options.url = urls.ms+'/backstageUserPrivilegeAction/getUserRoles.do';
		ajax( options );
	}
	function listUserPrivilege( options ){
		options.url = urls.ms+'/backstageUserPrivilegeAction/getUserPrivileges.do';
		ajax( options );
	}
	
	function addRole( options ){
		options.url = urls.ms+'/roleAction/addRole.do';
		ajax( options );
	}
	
	function deleteRole( options ){
		options.url = urls.ms+'/roleAction/delRole.do';
		ajax( options );
	}
	return {
		remove:remove,
		getById:getById,
		save:save,
		resetPWD:resetPWD,
		cancelFrozen:cancelFrozen,
		fenpeiRole:fenpeiRole,
		listUserRolePrivilege:listUserRolePrivilege,
		listUserPrivilege:listUserPrivilege,
		addRole:addRole,
		deleteRole:deleteRole,
	};
}]);