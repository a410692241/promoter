'use strict';
app.service('selectUService', [function() {
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
        if( data.roleName==null ||  data.roleName==""){
            throw new BusinessException("请不要留空！");
        }
        /*if( !/\w{6,}/.test( data.userName ) ){
            throw new BusinessException("账号至少6位（数字或字母或数字与字母组成）！");
        }*/
        /*if( data.password==null || data.password==""){
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
        }*/

        /*if(data.menuName==null || data.menuName==""){
            throw new BusinessException("请输入菜单名！");
        }*/

       /* if(data.mobile==null || data.mobile==""){
            throw new BusinessException("请输入联系电话！");

        }else if(data.mobile!="" && data.mobile!=null){
            var phoneFormat = /^(1[3|5|8])[\d]{9}$/;
            var isPhone = phoneFormat.test(data.mobile);
            if(!isPhone){
                throw new BusinessException("联系电话（手机号码）格式不正确！");
            }
        }*/


        options.data = {
            roleName: data.roleName,
            menuName: data.menuName,
            accountId: data.accountId,
            userName: data.userName,
            password: data.password,
            nickname: data.realName,
            mobile: data.mobile,

        };
        options.url = urls.ms+"/role/addRoleMenu.do";
        ajax( options );
    }

    function getById( options ){
        options.url = urls.ms+"/account/get.do";
        ajax( options );
    }

    function remove( options ){
        options.url = urls.ms+'/role/delRoleMenu.do';
        ajax( options );
    }

    function resetPWD( options ){
        options.url = urls.ms+'/role/setUserPWD.do';
        ajax( options );
    }

    function cancelFrozen( options ){
        options.url = urls.ms+'/role/cancelFrozen.do';
        ajax( options );
    }

    function fenpeiRole( options ){
        options.url = urls.ms+'/roleAction/addUserRole.do?accountId='+options.data.accountId;
        options.contentType = "application/json";
        options.data=angular.toJson( options.data.roleIdList );
        ajax( options );
    }

    function listUserRolePrivilege( options ){
        options.url = urls.ms+'/role/getMenu.do';
        ajax( options );
    }
    function listUserPrivilege( options ){
        options.url = urls.ms+'/backstageUserPrivilegeAction/getUserPrivileges.do';
        ajax( options );
    }

    function getPrivilegesList( options ){
        options.url = urls.ms+'/accountMenu/getModelMenus.do';
        ajax( options );
    }

    function addRole( options ){
        options.url = urls.ms+'/account/addRole.do';
        ajax( options );
    }

    function deleteRole( options ){
        options.url = urls.ms+'/roleAction/delRole.do';
        ajax( options );
    }


    function addUserPrivilege( options ){
        options.url = urls.ms+'/roleMenu/updateRolePrivilage.do';
        //获取所有已选择的权限id
        var privilegeIdList = getCheckedPrivilageIdList( options.data.privilageList );
        options.data = {
            roleId:options.data.roleId,
            privilegeIdList:privilegeIdList.join(",")
        }
        ajax( options );
    }

    function getCheckedPrivilageIdList( privilageList ){
        var privilageIdList = new Array();
        console.log(privilageList);
        for( var i = 0 ; i < privilageList.length; i++ ){
            var privilage = privilageList[i];
            /*if( privilage.checked ){
                privilageIdList.push( privilage.id );
            }*/
            if( privilage.children ){
                for( var j = 0 ; j < privilage.children.length; j++ ){
                    if( privilage.children[j].checked ) {
                        privilageIdList.push(privilage.children[j].id);
                    }
                }
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
        addRole:addRole,
        deleteRole:deleteRole,
        addUserPrivilege:addUserPrivilege,
        listUserRolePrivilege:listUserRolePrivilege,
        getPrivilegesList:getPrivilegesList,
        listUserPrivilege:listUserPrivilege,
    };
}]);