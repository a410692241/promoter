'use strict';
app.service('selectUserService', [function() {
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
        /*;*/
        if( data.userName==null || data.userName==""){
            throw new BusinessException("请输入账号名称！");
        }
        if( !/\w{6,}/.test( data.userName ) ){
            throw new BusinessException("账号至少6位（数字或字母或数字与字母组成）！");
        }
        if( data.password==null || data.password==""){
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

        /*if(data.realName==null || data.realName==""){
            throw new BusinessException("请输入真实姓名！");
        }*/

        if(data.mobile==null || data.mobile==""){
            throw new BusinessException("请输入联系电话！");

        }else if(data.mobile!="" && data.mobile!=null){
            var phoneFormat = /^(1[3|5|8])[\d]{9}$/;
            var isPhone = phoneFormat.test(data.mobile);
            if(!isPhone){
                throw new BusinessException("联系电话（手机号码）格式不正确！");
            }
        }


        options.data = {
            accountId: data.accountId,
            userName: data.userName,
            password: data.password,
           /* nickname: data.realName,*/
            mobile: data.mobile,

        };
        options.url = urls.ms+"/account/addUser.do";
        ajax( options );
    }

    function getById( options ){
        options.url = urls.ms+"/account/get.do";
        ajax( options );
    }

    function remove( options ){
        options.url = urls.ms+'/account/delUser.do';
        ajax( options );
    }

    function resetPWD( options ){
        options.url = urls.ms+'/account/setUserPWD.do';
        ajax( options );
    }

    function cancelFrozen( options ){
        options.url = urls.ms+'/account/cancelFrozen.do';
        ajax( options );
    }

    function fenpeiRole( options ){
        options.url = urls.ms + '/account/addUserRole.do?accountId='+options.data.accountId+'&roleId='+options.data.roleId;
        options.contentType = "application/json";
        ajax( options );
    }

    function listUserRolePrivilege( options ){
        options.url = urls.ms+'/account/getUserRoles.do';
        ajax( options );
    }
    function listUserPrivilege( options ){
        options.url = urls.ms+'/account/getUserPrivileges.do';
        ajax( options );
    }

    function addRole( options ){
        options.url = urls.ms+'/account/addRole.do';
        ajax( options );
    }

    function deleteRole( options ){
        options.url = urls.ms+'/account/delRole.do';
        ajax( options );
    }

    function getPrivilegesList( options ){
        options.url = urls.ms+'/account/getPrivilegesList.do';
        ajax( options );
    }

    function addUserPrivilege( options ){
        options.url = urls.ms+'/account/addUserPrivilege.do';
        //获取所有已选择的权限id
        var privilegeIdList = getCheckedPrivilageIdList( options.data.privilageList );
        options.data = {
            userId:options.data.userId,
            privilegeIdList:privilegeIdList.join(",")
        }
        ajax( options );
    }

    function getCheckedPrivilageIdList( privilageList ){
        var privilageIdList = new Array();
        for( var i = 0 ; i < privilageList.length; i++ ){
            var privilage = privilageList[i];
            if( privilage.checked ){
                privilageIdList.push( privilage.id );
            }
            if( privilage.children ){
                var childIdList = getCheckedPrivilageIdList( privilage.children );
                privilageIdList.push( childIdList );
            }

        }
        return privilageIdList;
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
        getPrivilegesList:getPrivilegesList,
        addUserPrivilege:addUserPrivilege,
    };
}]);