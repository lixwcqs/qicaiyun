
function checkUserExists() {
    var exists = false;
    $.get(getRootPath() + '/user/check/' + $('#account').val(),
        function (data) {
            //用户存在
            exists = !data.success;
        }
    );
    return exists;
}

function _login(){
    var account = $('#account').val();
    var password = $('#inputPassword').val();
    $.post(getRootPath() + '/login',
        {
        account:account,
        password:password
    },function(data){
        if(data){
            
        }
    });
}

function register() {
    if(!checkUserExists()){
        register21();
    }
}
function register21() {
    var param_json = $('#r_user').serializeJSON();
    $.ajax({
        url: getRootPath() + '/user/reg',
        data: param_json,
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            if (data && data.success) {
                window.location.href = getRootPath() + '/fd/article/list/p'
            }else{
                alert('注册失败');
            }
        }
    });
}

function deleteUser() {
    $.ajax({
        url: getRootPath() + '/user/902680700132577281',
        type: 'DELETE',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            if (data && data.success) {
                // window.location.href = getRootPath()+'/article/list/p'
            }
        }
    });
}