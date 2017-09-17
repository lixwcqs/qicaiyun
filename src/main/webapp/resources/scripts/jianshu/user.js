function register() {
    register21();
}
function register21(){
    var param_json = $('#r_user').serializeJSON();
    $.ajax({
        url: getRootPath() + '/user/reg',
        data: param_json,
        type:'POST',
        dataType   : 'json',
        contentType: 'application/json',
        success: function (data) {
            if(data && data.success){
                // window.location.href = getRootPath()+'/article/list/p'
            }
        }
    });
}

function deleteUser(){
    $.ajax({
        url: getRootPath() + '/user/902680700132577281',
        type:'DELETE',
        dataType   : 'json',
        contentType: 'application/json',
        success: function (data) {
            if(data && data.success){
                // window.location.href = getRootPath()+'/article/list/p'
            }
        }
    });
}