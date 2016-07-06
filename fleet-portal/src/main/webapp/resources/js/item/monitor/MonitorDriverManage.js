var googleMap = null;
var driverPolyLine = null;
var driverLatLngMVCArray = null;
var lastPhoneUploadTimestamp = null;
var windowTimerInterval = null;
var windowTimerIntervalTimeout = false; //启动及关闭按钮

function initializeMap() {

    var centerLatLng = assupg.Controls.GoogleMap.LatLng.fun_createLatLng(19.432965, -99.132621);//墨西哥城，19.432965，-99.132621
    //var centerLatLng = assupg.Controls.GoogleMap.LatLng.fun_createLatLng(23.113721, 113.311426);//公司，23.113721, 113.311426
    googleMap = new google.maps.Map(assupg.Controls.GoogleMap.mapDivElement_Obj, assupg.Controls.GoogleMap.Map.fun_mapOptions(centerLatLng));
    assupg.Controls.GoogleMap.Marker.fun_createMarker(googleMap, centerLatLng, 'comodin', '');

}

function removeLine() {
    //判断，线路若不为空；即代表之前有画过路线，将其在Map设置为空；
    if (driverPolyLine != null && typeof(driverPolyLine) != 'undefined') {
        driverPolyLine.setMap(null);
    }

    //判断，定时器若不为空；即代表之前使用过，将 定时器清除
    if (typeof(windowTimerInterval) != 'undefined') {
        //去掉定时器的方法
        windowTimerIntervalTimeout = true;
        window.clearInterval(windowTimerInterval);
    }

    var driverUsername = $('#modal_Search_Form_Element_driverUsername').val();
    var phoneUploadTimestampByDate = $('#modal_Search_Form_Element_phoneUploadTimestampByDate').val();
    if (driverUsername == '') {
        alert('driverUsername is null!');
        return;
    }
    //Ajax 获取当前输入的，司机登陆名 + 当前选择的日期 +
    var listLatLngDriverBean = base.ItemBaseData.ajax_LatLngDriver_ListByDriverUsername(driverUsername, phoneUploadTimestampByDate, null);
    if (listLatLngDriverBean.length < 1) {
        alert('The current date is not driver ' + driverUsername + ' data');
        return;
    }

    //将当前 司机线路的第一个经纬度信息，设置为 Map地址的中心位置，以便跟踪查看
    var latLngDriverBean = listLatLngDriverBean[listLatLngDriverBean.length - 1];
    var newCenterLatLng = assupg.Controls.GoogleMap.LatLng.fun_createLatLng(latLngDriverBean.phoneLat, latLngDriverBean.phoneLng);
    googleMap.setCenter(newCenterLatLng);
    googleMap.setZoom(16);

    //组装所有的，经纬度数据；再渲染路线图
    driverLatLngMVCArray = new google.maps.MVCArray();
    $.each(listLatLngDriverBean, function (i, latLngDriverBean) {
        // console.info('countSize: ' + (i + 1) + '\t' + JSON.stringify(latLngDriverBean));
        lastPhoneUploadTimestamp = latLngDriverBean.phoneUploadTimestamp;
        var myLatLng = assupg.Controls.GoogleMap.LatLng.fun_createLatLng(latLngDriverBean.phoneLat, latLngDriverBean.phoneLng);
        driverLatLngMVCArray.push(myLatLng);
    });
    driverPolyLine = assupg.Controls.GoogleMap.Polyline.fun_createPolyline(googleMap, driverLatLngMVCArray);


    //设置定时器：每隔3秒，获取司机的最新，经纬度数据，并追加到该司机已经存在的路线对象中去
    windowTimerIntervalTimeout = false;
    windowTimerInterval = window.setInterval(function () {

        if (windowTimerIntervalTimeout) {
            return;
        }

        listLatLngDriverBean = base.ItemBaseData.ajax_LatLngDriver_ListByDriverUsername(driverUsername, phoneUploadTimestampByDate, lastPhoneUploadTimestamp);
        var paths = driverPolyLine.getPath();
        $.each(listLatLngDriverBean, function (i, latLngDriverBean) {
            //console.info('countSize: ' + (i + 1) + '\t' + JSON.stringify(latLngDriverBean));
            lastPhoneUploadTimestamp = latLngDriverBean.phoneUploadTimestamp;
            var myLatLng = assupg.Controls.GoogleMap.LatLng.fun_createLatLng(latLngDriverBean.phoneLat, latLngDriverBean.phoneLng);
            paths.insertAt(paths.length, myLatLng);
        });

    }, 3000);

}

$(function () {
    $('#btn_Search').on('click', removeLine);
    ItemBaseData.InitializationControlsData.fun_Daterangepicker_SingleCalendar('modal_Search_Form_Element_phoneUploadTimestampByDate', null, new Date());
    assupg.Controls.GoogleMap.initializationControls.fun_initialization('map', 'initializeMap');
});

// function calculateAndDisplayRoute(directionsService, directionsDisplay, googleMaps) {
//     var origin = new google.maps.LatLng(23.114153, 113.319012);
//     var destination = new google.maps.LatLng(23.125344, 113.384813);//棠东
//     //var destination = new google.maps.LatLng(23.122534, 113.317151);//
//
//
//     var waypts = [];
//     // $.each(base.ItemBaseData.vehicleCurrentCoordinates, function (i, taskMonitorBean) {
//     //     var myLatLng = new google.maps.LatLng(parseFloat(taskMonitorBean.vehicleLatitude), parseFloat(taskMonitorBean.vehicleLongitude));
//     //     console.info(JSON.stringify(myLatLng));
//     //     waypts.push({
//     //         location: myLatLng,
//     //         stopover: true
//     //     });
//     //     if (i >= 8) {
//     //         return false;
//     //     }
//     // });
//
//     /**
//      * 呈现路线
//      *      如果使用 route() 方法向 DirectionsService 发起路线请求，则必须传递一个在该服务请求完成后执行的回调。此回调将在响应中返回 DirectionsResult 和 DirectionsStatus 代码。
//      *
//      *
//      * 路线查询状态
//      *      DirectionsStatus 可能会返回以下值：
//      *
//      *              OK：表示响应包含一个有效的 DirectionsResult
//      *              NOT_FOUND 表示请求的起点、目的地或路径点中指定的至少其中一个位置无法接受地理编码。
//      *              ZERO_RESULTS 表示在起点与目的地之间未找到路线。
//      *              MAX_WAYPOINTS_EXCEEDED：表示 DirectionsRequest 中提供的 DirectionsWaypoint 过多。允许的路径点数目上限为 8 个，另加起点和终点。Google Maps API for Work 客户可使用 23 个路径点，另加起点和终点。公交路线不支持路径点。
//      *              INVALID_REQUEST：表示提供的 DirectionsRequest 无效。出现该错误代码的最常见原因包括：请求中缺少起点或终点；或者公交请求中包括路径点
//      *              OVER_QUERY_LIMIT：表示网页在允许的时间段内发送的请求过多
//      *              REQUEST_DENIED：表示不允许网页使用路线服务
//      *              UNKNOWN_ERROR 表示由于服务器发生错误而无法处理路线请求。如果您重试一次，请求可能会成功
//      *          您应该在处理结果前检查此值，确保路线查询返回的结果有效。
//      *
//      *  显示 DirectionsResult
//      *      DirectionsResult 包含路线查询结果，您可以自行处理该结果，也可以将其传递到 DirectionsRenderer 对象，该对象会自动处理将该结果显示在地图上。
//      *      要使用 DirectionsRenderer 显示 DirectionsResult，您只需执行以下操作：
//      *
//      *              创建一个 DirectionsRenderer 对象
//      *              对呈现程序调用 setMap()，以将其绑定到传递的地图
//      *              对呈现程序调用 setDirections()，以向其传递上述 DirectionsResult。呈现程序是一个 MVCObject，它会自动检测其属性发生的任何变化，并在其关联路线更改时更新地图
//      *
//      *
//      *
//      *  DirectionsResult 对象
//      *      向 DirectionsService 发送路线请求后，您会收到一个包含状态代码和结果（即 DirectionsResult 对象）的响应。DirectionsResult 是一个带有以下字段的对象字面量：
//      *
//      *              geocoded_waypoints[]：包含一个 DirectionsGeocodedWaypoint 对象数组，其中每个对象均包含有关起点、终点和路径点的地理编码详情
//      *              routes[]：包含一个 DirectionsRoute 对象数组。每条路线均表示一种从 DirectionsRequest 中所提供的起点到终点的方式。
//      *                          通常，对于任何给定请求，均只会返回一条路线，除非请求的 provideRouteAlternatives 字段设置为 true（此情况下，可能会返回多条路线）
//      *
//      *
//      */
//     directionsService.route(basic.Controls.GoogleMap.route.fun_Initialization_options(origin, destination), function (directionsResult, directionsStatus) {
//
//         if (directionsStatus === google.maps.DirectionsStatus.OK) {
//
//             directionsDisplay.setDirections(directionsResult);
//
//             console.info(directionsResult);
//
//             /**
//              * 路线 DirectionsRoute 包含一个从指定起点到指定终点的结果。
//              *      该路线可包含一段或多段路程（类型为 DirectionsLeg），具体取决于是否指定了任何路径点。除路线信息外，该路线还包含必须向用户显示的版权和警告信息。
//              *
//              *
//              *      DirectionsRoute 是一个包含以下字段的对象字面量：
//              *              bounds：其中包含一个 LatLngBounds，用于表示沿着此给定路线的折线的边界
//              *              copyrights 包含需要为该路线显示的版权文本。 如果您不使用所提供的 DirectionsRenderer 对象，则必须自行处理和显示此信息
//              *
//              *              legs[]：其中包含一个 DirectionsLeg 对象数组，每个对象均包含关于路线的一段路程（介于给定路线中的两个位置之间）的信息。指定的每个路径点或目的地都有单独的段与之对应。（没有任何路径点的路线将仅包含一个 DirectionsLeg）。每段路程均由一系列 DirectionStep 组成
//              *              overview_polyline：其中包含单个 points 对象，用于储存以经过编码的折线表示的路线。此折线是所生成路线的近似（平滑处理）路径
//              *
//              *              warnings[]：其中包含要在显示这些路线时显示的一组警告。如果您不使用所提供的 DirectionsRenderer 对象，则必须自行处理和显示这些警告
//              *              waypoint_order 包含一个数组，用于指示所计算路线内所有路径点的顺序。如果已向 DirectionsRequest 传递 optimizeWaypoints: true，那么此数组可能会包含经过更改的顺序
//              *
//              *              overview_path：其中包含一个 LatLng 数组，用于表示所生成路线的近似（平滑处理）路径
//              *
//              *              fare：其中包含此路线的总交通费用（即总票价）。此属性只针对公交请求返回，且仅适用于所有公交路程均有票价信息的路线。这些信息包括：
//              *                      currency：ISO 4217 币种代码，表示票价所采用的币种
//              *                      value：总票价（以上面指定的币种为单位）
//              *
//              */
//             if (_.isArray(directionsResult.routes)) {
//
//                 var routes = directionsResult.routes;
//                 for (var i = 0, len = routes.length; i < len; i++) {
//
//                     var overviewPath = routes[i].overview_path;
//                     // console.info(JSON.stringify(overviewPath));
//
//
//                     var flightPath = new google.maps.Polyline({
//                         path: overviewPath,
//                         geodesic: true,
//                         //strokeColor: '#FFFFFF',     //strokeColor 指定 "#FFFFFF" 格式的十六进制 HTML 颜色。Polyline 类不支持命名颜色。
//                         //strokeOpacity: 1.0,         //strokeOpacity 指定一个介于 0.0 和 1.0 的数值，用于确定线颜色的不透明度。默认值为 1.0。
//                         strokeWeight: 4,            //指定线的宽度（单位：像素）。
//                         //editable: true,             //将形状设置为可编辑 可通过在形状的选项中将 editable 设置为 true，将任何形状（多段线、多边形、圆和矩形）设置为可由用户编辑。
//                         draggable: true              //将形状设置为可拖动 默认情况下，在地图上绘制的形状位置固定。如需允许用户将形状拖动到地图上的其他位置，请在形状的选项中将 draggable 设置为 true。
//                     });
//                     flightPath.setMap(googleMaps);
//
//                     // /**
//                     //  * [{"distance":{"text":"0.6 公里","value":598},"duration":{"text":"2分钟","value":109},"end_address":"中国广东省广州市越秀区春风路 邮政编码: 510620","end_location":{"lat":23.1148329,"lng":113.31325509999999},"start_address":"中国广东省广州市越秀区江月路17号 邮政编码: 510315","start_location":{"lat":23.1134038,"lng":113.31141600000001},"steps":[{"distance":{"text":"47 米","value":47},"duration":{"text":"1分钟","value":7},"end_location":{"lat":23.1133915,"lng":113.31187729999999},"polyline":{"points":"wialCkcrrT@{A"},"start_location":{"lat":23.1134038,"lng":113.31141600000001},"travel_mode":"DRIVING","encoded_lat_lngs":"wialCkcrrT@{A","path":[{"lat":23.113400000000002,"lng":113.31142000000001},{"lat":23.113390000000003,"lng":113.31188000000002}],"lat_lngs":[{"lat":23.113400000000002,"lng":113.31142000000001},{"lat":23.113390000000003,"lng":113.31188000000002}],"instructions":"从<b>江月路</b>向<b>东</b>行驶，到<b>明月二街</b>","maneuver":"","start_point":{"lat":23.1134038,"lng":113.31141600000001},"end_point":{"lat":23.1133915,"lng":113.31187729999999}},{"distance":{"text":"0.3 公里","value":280},"duration":{"text":"1分钟","value":52},"end_location":{"lat":23.1159051,"lng":113.31189070000005},"maneuver":"turn-left","polyline":{"points":"uialCgfrrT_@?aA?iA@w@@kBAiAAw@?IAE?U?"},"start_location":{"lat":23.1133915,"lng":113.31187729999999},"travel_mode":"DRIVING","encoded_lat_lngs":"uialCgfrrT_@?aA?iA@w@@kBAiAAw@?IAE?U?","path":[{"lat":23.113390000000003,"lng":113.31188000000002},{"lat":23.113550000000004,"lng":113.31188000000002},{"lat":23.11388,"lng":113.31188000000002},{"lat":23.114250000000002,"lng":113.31187000000001},{"lat":23.114530000000002,"lng":113.31186000000001},{"lat":23.115070000000003,"lng":113.31187000000001},{"lat":23.115440000000003,"lng":113.31188000000002},{"lat":23.115720000000003,"lng":113.31188000000002},{"lat":23.11577,"lng":113.31189},{"lat":23.1158,"lng":113.31189},{"lat":23.115910000000003,"lng":113.31189}],"lat_lngs":[{"lat":23.113390000000003,"lng":113.31188000000002},{"lat":23.113550000000004,"lng":113.31188000000002},{"lat":23.11388,"lng":113.31188000000002},{"lat":23.114250000000002,"lng":113.31187000000001},{"lat":23.114530000000002,"lng":113.31186000000001},{"lat":23.115070000000003,"lng":113.31187000000001},{"lat":23.115440000000003,"lng":113.31188000000002},{"lat":23.115720000000003,"lng":113.31188000000002},{"lat":23.11577,"lng":113.31189},{"lat":23.1158,"lng":113.31189},{"lat":23.115910000000003,"lng":113.31189}],"instructions":"向<b>左</b>转，进入<b>明月二街</b>","start_point":{"lat":23.1133915,"lng":113.31187729999999},"end_point":{"lat":23.1159051,"lng":113.31189070000005}},{"distance":{"text":"0.1 公里","value":149},"duration":{"text":"1分钟","value":27},"end_location":{"lat":23.115913,"lng":113.31334649999997},"maneuver":"turn-right","polyline":{"points":"myalCifrrT?W?_B?gA?cB"},"start_location":{"lat":23.1159051,"lng":113.31189070000005},"travel_mode":"DRIVING","encoded_lat_lngs":"myalCifrrT?W?_B?gA?cB","path":[{"lat":23.115910000000003,"lng":113.31189},{"lat":23.115910000000003,"lng":113.31201000000001},{"lat":23.115910000000003,"lng":113.31249000000001},{"lat":23.115910000000003,"lng":113.31285000000001},{"lat":23.115910000000003,"lng":113.31335000000001}],"lat_lngs":[{"lat":23.115910000000003,"lng":113.31189},{"lat":23.115910000000003,"lng":113.31201000000001},{"lat":23.115910000000003,"lng":113.31249000000001},{"lat":23.115910000000003,"lng":113.31285000000001},{"lat":23.115910000000003,"lng":113.31335000000001}],"instructions":"向<b>右</b>转，进入<b>明月二路</b>","start_point":{"lat":23.1159051,"lng":113.31189070000005},"end_point":{"lat":23.115913,"lng":113.31334649999997}},{"distance":{"text":"0.1 公里","value":122},"duration":{"text":"1分钟","value":23},"end_location":{"lat":23.1148329,"lng":113.31325509999999},"maneuver":"turn-right","polyline":{"points":"myalCmorrTzA@V?H@FDDBD@F@V?R?P?"},"start_location":{"lat":23.115913,"lng":113.31334649999997},"travel_mode":"DRIVING","encoded_lat_lngs":"myalCmorrTzA@V?H@FDDBD@F@V?R?P?","path":[{"lat":23.115910000000003,"lng":113.31335000000001},{"lat":23.115450000000003,"lng":113.31334000000001},{"lat":23.11533,"lng":113.31334000000001},{"lat":23.115280000000002,"lng":113.31333000000001},{"lat":23.115240000000004,"lng":113.31330000000001},{"lat":23.11521,"lng":113.31328},{"lat":23.115180000000002,"lng":113.31327},{"lat":23.11514,"lng":113.31326000000001},{"lat":23.11502,"lng":113.31326000000001},{"lat":23.11492,"lng":113.31326000000001},{"lat":23.11483,"lng":113.31326000000001}],"lat_lngs":[{"lat":23.115910000000003,"lng":113.31335000000001},{"lat":23.115450000000003,"lng":113.31334000000001},{"lat":23.11533,"lng":113.31334000000001},{"lat":23.115280000000002,"lng":113.31333000000001},{"lat":23.115240000000004,"lng":113.31330000000001},{"lat":23.11521,"lng":113.31328},{"lat":23.115180000000002,"lng":113.31327},{"lat":23.11514,"lng":113.31326000000001},{"lat":23.11502,"lng":113.31326000000001},{"lat":23.11492,"lng":113.31326000000001},{"lat":23.11483,"lng":113.31326000000001}],"instructions":"向<b>右</b>转，进入<b>春风路</b><div style=\"font-size:0.9em\">目的地在左侧</div>","start_point":{"lat":23.115913,"lng":113.31334649999997},"end_point":{"lat":23.1148329,"lng":113.31325509999999}}],"traffic_speed_entry":[],"via_waypoint":[],"via_waypoints":[]}]
//                     //  */
//                     // //console.info(JSON.stringify(routes[i].legs));
//                     // var legs = routes[i].legs;
//                     // for (var i = 0, len = legs.length; i < len; i++) {
//                     //
//                     //     var distance = legs[i].distance;    //距离
//                     //     console.info('text: ' + distance.text + '\t\t\tvalue: ' + distance.value);
//                     //     var duration = legs[i].duration;    //耗时
//                     //     console.info('text: ' + duration.text + '\t\t\tvalue: ' + duration.value);
//                     //
//                     //
//                     //     var start_location = legs[i].start_location;
//                     //     var start_address = legs[i].start_address;
//                     //     console.info('lat: ' + start_location.lat() + '\tlng: ' + start_location.lng() + '\taddress: ' + start_address);
//                     //
//                     //     var end_location = legs[i].end_location;
//                     //     var end_address = legs[i].end_address;
//                     //     console.info('lat: ' + end_location.lat() + '\tlng: ' + end_location.lng() + '\taddress: ' + end_address);
//                     //
//                     //     var steps = legs[i].steps;
//                     //     for (var i = 0, len = steps.length; i < len; i++) {
//                     //
//                     //     }
//                     // }
//
//                 }
//             }
//
//
//         } else {
//             window.alert('Directions request failed due to ' + status);
//         }
//     });
// }

// function calculateAndDisplayRoute(directionsService, directionsDisplay, googleMaps) {
//     var origin = new google.maps.LatLng(23.114153, 113.319012);
//     var destination = new google.maps.LatLng(23.125344, 113.384813);//棠东
//     //var destination = new google.maps.LatLng(23.122534, 113.317151);//
//
//
//     base.ItemBaseData.ajax_GetAllVehicleCurrentCoordinates();
//     var waypts = [];
//     // $.each(base.ItemBaseData.vehicleCurrentCoordinates, function (i, taskMonitorBean) {
//     //     var myLatLng = new google.maps.LatLng(parseFloat(taskMonitorBean.vehicleLatitude), parseFloat(taskMonitorBean.vehicleLongitude));
//     //     console.info(JSON.stringify(myLatLng));
//     //     waypts.push({
//     //         location: myLatLng,
//     //         stopover: true
//     //     });
//     //     if (i >= 8) {
//     //         return false;
//     //     }
//     // });
//
//     /**
//      * 呈现路线
//      *      如果使用 route() 方法向 DirectionsService 发起路线请求，则必须传递一个在该服务请求完成后执行的回调。此回调将在响应中返回 DirectionsResult 和 DirectionsStatus 代码。
//      *
//      *
//      * 路线查询状态
//      *      DirectionsStatus 可能会返回以下值：
//      *
//      *              OK：表示响应包含一个有效的 DirectionsResult
//      *              NOT_FOUND 表示请求的起点、目的地或路径点中指定的至少其中一个位置无法接受地理编码。
//      *              ZERO_RESULTS 表示在起点与目的地之间未找到路线。
//      *              MAX_WAYPOINTS_EXCEEDED：表示 DirectionsRequest 中提供的 DirectionsWaypoint 过多。允许的路径点数目上限为 8 个，另加起点和终点。Google Maps API for Work 客户可使用 23 个路径点，另加起点和终点。公交路线不支持路径点。
//      *              INVALID_REQUEST：表示提供的 DirectionsRequest 无效。出现该错误代码的最常见原因包括：请求中缺少起点或终点；或者公交请求中包括路径点
//      *              OVER_QUERY_LIMIT：表示网页在允许的时间段内发送的请求过多
//      *              REQUEST_DENIED：表示不允许网页使用路线服务
//      *              UNKNOWN_ERROR 表示由于服务器发生错误而无法处理路线请求。如果您重试一次，请求可能会成功
//      *          您应该在处理结果前检查此值，确保路线查询返回的结果有效。
//      *
//      *  显示 DirectionsResult
//      *      DirectionsResult 包含路线查询结果，您可以自行处理该结果，也可以将其传递到 DirectionsRenderer 对象，该对象会自动处理将该结果显示在地图上。
//      *      要使用 DirectionsRenderer 显示 DirectionsResult，您只需执行以下操作：
//      *
//      *              创建一个 DirectionsRenderer 对象
//      *              对呈现程序调用 setMap()，以将其绑定到传递的地图
//      *              对呈现程序调用 setDirections()，以向其传递上述 DirectionsResult。呈现程序是一个 MVCObject，它会自动检测其属性发生的任何变化，并在其关联路线更改时更新地图
//      *
//      *
//      *
//      *  DirectionsResult 对象
//      *      向 DirectionsService 发送路线请求后，您会收到一个包含状态代码和结果（即 DirectionsResult 对象）的响应。DirectionsResult 是一个带有以下字段的对象字面量：
//      *
//      *              geocoded_waypoints[]：包含一个 DirectionsGeocodedWaypoint 对象数组，其中每个对象均包含有关起点、终点和路径点的地理编码详情
//      *              routes[]：包含一个 DirectionsRoute 对象数组。每条路线均表示一种从 DirectionsRequest 中所提供的起点到终点的方式。
//      *                          通常，对于任何给定请求，均只会返回一条路线，除非请求的 provideRouteAlternatives 字段设置为 true（此情况下，可能会返回多条路线）
//      *
//      *
//      */
//     directionsService.route(basic.Controls.GoogleMap.route.fun_Initialization_options(origin, destination), function (directionsResult, directionsStatus) {
//
//         if (directionsStatus === google.maps.DirectionsStatus.OK) {
//
//             directionsDisplay.setDirections(directionsResult);
//
//             console.info(directionsResult);
//
//             /**
//              * 路线 DirectionsRoute 包含一个从指定起点到指定终点的结果。
//              *      该路线可包含一段或多段路程（类型为 DirectionsLeg），具体取决于是否指定了任何路径点。除路线信息外，该路线还包含必须向用户显示的版权和警告信息。
//              *
//              *
//              *      DirectionsRoute 是一个包含以下字段的对象字面量：
//              *              bounds：其中包含一个 LatLngBounds，用于表示沿着此给定路线的折线的边界
//              *              copyrights 包含需要为该路线显示的版权文本。 如果您不使用所提供的 DirectionsRenderer 对象，则必须自行处理和显示此信息
//              *
//              *              legs[]：其中包含一个 DirectionsLeg 对象数组，每个对象均包含关于路线的一段路程（介于给定路线中的两个位置之间）的信息。指定的每个路径点或目的地都有单独的段与之对应。（没有任何路径点的路线将仅包含一个 DirectionsLeg）。每段路程均由一系列 DirectionStep 组成
//              *              overview_polyline：其中包含单个 points 对象，用于储存以经过编码的折线表示的路线。此折线是所生成路线的近似（平滑处理）路径
//              *
//              *              warnings[]：其中包含要在显示这些路线时显示的一组警告。如果您不使用所提供的 DirectionsRenderer 对象，则必须自行处理和显示这些警告
//              *              waypoint_order 包含一个数组，用于指示所计算路线内所有路径点的顺序。如果已向 DirectionsRequest 传递 optimizeWaypoints: true，那么此数组可能会包含经过更改的顺序
//              *
//              *              overview_path：其中包含一个 LatLng 数组，用于表示所生成路线的近似（平滑处理）路径
//              *
//              *              fare：其中包含此路线的总交通费用（即总票价）。此属性只针对公交请求返回，且仅适用于所有公交路程均有票价信息的路线。这些信息包括：
//              *                      currency：ISO 4217 币种代码，表示票价所采用的币种
//              *                      value：总票价（以上面指定的币种为单位）
//              *
//              */
//             if (_.isArray(directionsResult.routes)) {
//
//                 var routes = directionsResult.routes;
//                 for (var i = 0, len = routes.length; i < len; i++) {
//
//                     var overviewPath = routes[i].overview_path;
//                     // console.info(JSON.stringify(overviewPath));
//
//
//                     var flightPath = new google.maps.Polyline({
//                         path: overviewPath,
//                         geodesic: true,
//                         //strokeColor: '#FFFFFF',     //strokeColor 指定 "#FFFFFF" 格式的十六进制 HTML 颜色。Polyline 类不支持命名颜色。
//                         //strokeOpacity: 1.0,         //strokeOpacity 指定一个介于 0.0 和 1.0 的数值，用于确定线颜色的不透明度。默认值为 1.0。
//                         strokeWeight: 4,            //指定线的宽度（单位：像素）。
//                         //editable: true,             //将形状设置为可编辑 可通过在形状的选项中将 editable 设置为 true，将任何形状（多段线、多边形、圆和矩形）设置为可由用户编辑。
//                         draggable: true              //将形状设置为可拖动 默认情况下，在地图上绘制的形状位置固定。如需允许用户将形状拖动到地图上的其他位置，请在形状的选项中将 draggable 设置为 true。
//                     });
//                     flightPath.setMap(googleMaps);
//
//                     // /**
//                     //  * [{"distance":{"text":"0.6 公里","value":598},"duration":{"text":"2分钟","value":109},"end_address":"中国广东省广州市越秀区春风路 邮政编码: 510620","end_location":{"lat":23.1148329,"lng":113.31325509999999},"start_address":"中国广东省广州市越秀区江月路17号 邮政编码: 510315","start_location":{"lat":23.1134038,"lng":113.31141600000001},"steps":[{"distance":{"text":"47 米","value":47},"duration":{"text":"1分钟","value":7},"end_location":{"lat":23.1133915,"lng":113.31187729999999},"polyline":{"points":"wialCkcrrT@{A"},"start_location":{"lat":23.1134038,"lng":113.31141600000001},"travel_mode":"DRIVING","encoded_lat_lngs":"wialCkcrrT@{A","path":[{"lat":23.113400000000002,"lng":113.31142000000001},{"lat":23.113390000000003,"lng":113.31188000000002}],"lat_lngs":[{"lat":23.113400000000002,"lng":113.31142000000001},{"lat":23.113390000000003,"lng":113.31188000000002}],"instructions":"从<b>江月路</b>向<b>东</b>行驶，到<b>明月二街</b>","maneuver":"","start_point":{"lat":23.1134038,"lng":113.31141600000001},"end_point":{"lat":23.1133915,"lng":113.31187729999999}},{"distance":{"text":"0.3 公里","value":280},"duration":{"text":"1分钟","value":52},"end_location":{"lat":23.1159051,"lng":113.31189070000005},"maneuver":"turn-left","polyline":{"points":"uialCgfrrT_@?aA?iA@w@@kBAiAAw@?IAE?U?"},"start_location":{"lat":23.1133915,"lng":113.31187729999999},"travel_mode":"DRIVING","encoded_lat_lngs":"uialCgfrrT_@?aA?iA@w@@kBAiAAw@?IAE?U?","path":[{"lat":23.113390000000003,"lng":113.31188000000002},{"lat":23.113550000000004,"lng":113.31188000000002},{"lat":23.11388,"lng":113.31188000000002},{"lat":23.114250000000002,"lng":113.31187000000001},{"lat":23.114530000000002,"lng":113.31186000000001},{"lat":23.115070000000003,"lng":113.31187000000001},{"lat":23.115440000000003,"lng":113.31188000000002},{"lat":23.115720000000003,"lng":113.31188000000002},{"lat":23.11577,"lng":113.31189},{"lat":23.1158,"lng":113.31189},{"lat":23.115910000000003,"lng":113.31189}],"lat_lngs":[{"lat":23.113390000000003,"lng":113.31188000000002},{"lat":23.113550000000004,"lng":113.31188000000002},{"lat":23.11388,"lng":113.31188000000002},{"lat":23.114250000000002,"lng":113.31187000000001},{"lat":23.114530000000002,"lng":113.31186000000001},{"lat":23.115070000000003,"lng":113.31187000000001},{"lat":23.115440000000003,"lng":113.31188000000002},{"lat":23.115720000000003,"lng":113.31188000000002},{"lat":23.11577,"lng":113.31189},{"lat":23.1158,"lng":113.31189},{"lat":23.115910000000003,"lng":113.31189}],"instructions":"向<b>左</b>转，进入<b>明月二街</b>","start_point":{"lat":23.1133915,"lng":113.31187729999999},"end_point":{"lat":23.1159051,"lng":113.31189070000005}},{"distance":{"text":"0.1 公里","value":149},"duration":{"text":"1分钟","value":27},"end_location":{"lat":23.115913,"lng":113.31334649999997},"maneuver":"turn-right","polyline":{"points":"myalCifrrT?W?_B?gA?cB"},"start_location":{"lat":23.1159051,"lng":113.31189070000005},"travel_mode":"DRIVING","encoded_lat_lngs":"myalCifrrT?W?_B?gA?cB","path":[{"lat":23.115910000000003,"lng":113.31189},{"lat":23.115910000000003,"lng":113.31201000000001},{"lat":23.115910000000003,"lng":113.31249000000001},{"lat":23.115910000000003,"lng":113.31285000000001},{"lat":23.115910000000003,"lng":113.31335000000001}],"lat_lngs":[{"lat":23.115910000000003,"lng":113.31189},{"lat":23.115910000000003,"lng":113.31201000000001},{"lat":23.115910000000003,"lng":113.31249000000001},{"lat":23.115910000000003,"lng":113.31285000000001},{"lat":23.115910000000003,"lng":113.31335000000001}],"instructions":"向<b>右</b>转，进入<b>明月二路</b>","start_point":{"lat":23.1159051,"lng":113.31189070000005},"end_point":{"lat":23.115913,"lng":113.31334649999997}},{"distance":{"text":"0.1 公里","value":122},"duration":{"text":"1分钟","value":23},"end_location":{"lat":23.1148329,"lng":113.31325509999999},"maneuver":"turn-right","polyline":{"points":"myalCmorrTzA@V?H@FDDBD@F@V?R?P?"},"start_location":{"lat":23.115913,"lng":113.31334649999997},"travel_mode":"DRIVING","encoded_lat_lngs":"myalCmorrTzA@V?H@FDDBD@F@V?R?P?","path":[{"lat":23.115910000000003,"lng":113.31335000000001},{"lat":23.115450000000003,"lng":113.31334000000001},{"lat":23.11533,"lng":113.31334000000001},{"lat":23.115280000000002,"lng":113.31333000000001},{"lat":23.115240000000004,"lng":113.31330000000001},{"lat":23.11521,"lng":113.31328},{"lat":23.115180000000002,"lng":113.31327},{"lat":23.11514,"lng":113.31326000000001},{"lat":23.11502,"lng":113.31326000000001},{"lat":23.11492,"lng":113.31326000000001},{"lat":23.11483,"lng":113.31326000000001}],"lat_lngs":[{"lat":23.115910000000003,"lng":113.31335000000001},{"lat":23.115450000000003,"lng":113.31334000000001},{"lat":23.11533,"lng":113.31334000000001},{"lat":23.115280000000002,"lng":113.31333000000001},{"lat":23.115240000000004,"lng":113.31330000000001},{"lat":23.11521,"lng":113.31328},{"lat":23.115180000000002,"lng":113.31327},{"lat":23.11514,"lng":113.31326000000001},{"lat":23.11502,"lng":113.31326000000001},{"lat":23.11492,"lng":113.31326000000001},{"lat":23.11483,"lng":113.31326000000001}],"instructions":"向<b>右</b>转，进入<b>春风路</b><div style=\"font-size:0.9em\">目的地在左侧</div>","start_point":{"lat":23.115913,"lng":113.31334649999997},"end_point":{"lat":23.1148329,"lng":113.31325509999999}}],"traffic_speed_entry":[],"via_waypoint":[],"via_waypoints":[]}]
//                     //  */
//                     // //console.info(JSON.stringify(routes[i].legs));
//                     // var legs = routes[i].legs;
//                     // for (var i = 0, len = legs.length; i < len; i++) {
//                     //
//                     //     var distance = legs[i].distance;    //距离
//                     //     console.info('text: ' + distance.text + '\t\t\tvalue: ' + distance.value);
//                     //     var duration = legs[i].duration;    //耗时
//                     //     console.info('text: ' + duration.text + '\t\t\tvalue: ' + duration.value);
//                     //
//                     //
//                     //     var start_location = legs[i].start_location;
//                     //     var start_address = legs[i].start_address;
//                     //     console.info('lat: ' + start_location.lat() + '\tlng: ' + start_location.lng() + '\taddress: ' + start_address);
//                     //
//                     //     var end_location = legs[i].end_location;
//                     //     var end_address = legs[i].end_address;
//                     //     console.info('lat: ' + end_location.lat() + '\tlng: ' + end_location.lng() + '\taddress: ' + end_address);
//                     //
//                     //     var steps = legs[i].steps;
//                     //     for (var i = 0, len = steps.length; i < len; i++) {
//                     //
//                     //     }
//                     // }
//
//                 }
//             }
//
//
//         } else {
//             window.alert('Directions request failed due to ' + status);
//         }
//     });
// }