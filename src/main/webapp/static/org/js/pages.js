$(function () {
    var _n = $('.fy_part').parent().prev('.tabBox').find('table tr').length - 1;
    laypage({
        cont: $('.fy_part'), //容器。值支持id名、原生dom对象，jquery对象,
        pages: 10, //总页数
        skip: true, //是否开启跳页
        skin: '#365fb3',
        groups: 3 //连续显示分页数
        /*jump: function (obj) {
            $('#view').html('总数：共' + _n + '个，共有：' + obj.pages + '页');
        }*/
    });
    var _n0 = $('.fy_part0').parent().prev('.tabBox').find('table tr').length - 1;
    laypage({
        cont: $('.fy_part0'), //容器。值支持id名、原生dom对象，jquery对象,
        pages: 10, //总页数
        skip: true, //是否开启跳页
        skin: '#365fb3',
        groups: 3 //连续显示分页数
    });
    var _n1 = $('.fy_part1').parent().prev('.tabBox').find('table tr').length - 1;
    laypage({
        cont: $('.fy_part1'), //容器。值支持id名、原生dom对象，jquery对象,
        pages: 10, //总页数
        skip: true, //是否开启跳页
        skin: '#365fb3',
        groups: 3 //连续显示分页数
    });
});