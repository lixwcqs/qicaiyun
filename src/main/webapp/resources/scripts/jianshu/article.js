/**
 * Created by cqs on 2017/5/31.
 */
// 加载文章列表
function _init() {
    $("#jqGrid").jqGrid({
        url: basePath + "/article/list",
        mtype: "GET",
        styleUI: 'Bootstrap',
        datatype: "json",
        caption: "文章列表",
        colModel: [
            {label: '主键', name: 'id', width: 500, hidden: false,
                formatter: function (value, options, row) {
                console.log(typeof value)
                console.log('主键：' + value)
                return value
            }},
            {label: '标题', name: 'title', width: 300},
            {
                label: '内容【摘要】', name: 'content', width: 300,
                //格式化列显示 只显示部分
                formatter: function (value, options, row) {
                    if (value != null && value.length > 10) {
                        return value.substr(0, 10) + '...';
                    }
                    return value
                }
            },
            {label: '作者', name: 'author', width: 150},
            {
                label: '发布日期',
                name: 'cTime',
                width: 200,

            },
            {
                label: '更新日期', name: 'uTime', width: 200
            },
            //虚拟列
            {
                label: '详情', width: 80,
                formatter: function (cellvalue, options, rawObject) {
                    console.log(rawObject)
                    var detail = '<input type="button" value="查看" onclick="displayArticle(\'' + rawObject.id + '\')">';
                    return detail;
                }
            }
        ],
        multiselect: true,
        rownumbers: true, // show row numbe
        hoverrows: true, // true by default, can be switched to false if highlight on hover is not needed
        //hiddengrid: false,//默认表格展开状态
        viewrecords: true,
        width: 780,
        height: 200,
        pager: "#jqGridPager"
    });


    // add first custom button
    $('#jqGrid').navButtonAdd('#jqGridPager',
        {
            buttonicon: "glyphicon glyphicon-plus",
            title: "发布文章",
            caption: "",
            position: "last",
            onClickButton: publish
        });
    // add first custom button
    $('#jqGrid').navButtonAdd('#jqGridPager',
        {
            buttonicon: "glyphicon glyphicon-edit",
            title: "编辑",
            caption: "",
            position: "last",
            onClickButton: publish
        });


    //浏览
    $('#jqGrid').navButtonAdd('#jqGridPager',
        {
            //buttonicon: "glyphicon-info-sign",
            title: "浏览",
            caption: "123",
            position: "last",
            onClickButton: display
        });
}


//关注
function follow(targetId,followerId){

}

//---demo function
function publish() {
    window.location.href = basePath + '/article/publish';
}

function display() {
    var selrow = $("#jqGrid").jqGrid('getGridParam', 'colModel');
    console.log(selrow)
}

function createArticle() {
    window.location.href = basePath + "/article/write";
}

function displayArticle(id) {
    console.log(id);
    window.location.href = basePath + '/article/p?id=' + id;
}

function comment(){

}

