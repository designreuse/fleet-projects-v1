var googleMap;


function initializeMap() {

    var centerLatLng = assupg.Controls.GoogleMap.LatLng.fun_createLatLng(19.432965, -99.132621);//墨西哥城，19.432965，-99.132621
    //var centerLatLng = assupg.Controls.GoogleMap.LatLng.fun_createLatLng(23.113721, 113.311426);//公司，23.113721, 113.311426
    googleMap = new google.maps.Map(assupg.Controls.GoogleMap.mapDivElement_Obj, assupg.Controls.GoogleMap.Map.fun_mapOptions(centerLatLng));
    assupg.Controls.GoogleMap.Marker.fun_createMarker(googleMap, centerLatLng, 'comodin', '');

    
    var mc = assupg.Controls.GoogleMap.MarkerClusterer.fun_createMarkerClusterer(googleMap);
    var markerMap = new assupg.extend.Map();
    var image = 'resources/image/car/car_ic_map_icon_scar.png';
    window.setInterval(function () {

        var mapLatLngDriverBean = base.ItemBaseData.ajax_LatLngDriver_GetAllDriverCurrentLatLng();
        $.each(mapLatLngDriverBean, function (key, latLngDriverBean) {
            // console.info("key: " + key + " latLngDriverBean JSON:" + JSON.stringify(latLngDriverBean));
            var myLatLng = assupg.Controls.GoogleMap.LatLng.fun_createLatLng(latLngDriverBean.phoneLat, latLngDriverBean.phoneLng);


            var currentDriverUsername = latLngDriverBean.driverUsername;
            var title = 'driverUsername: ' + latLngDriverBean.driverUsername;
            if (markerMap.containsKey(currentDriverUsername)) {
                var oldMarker = markerMap.get(currentDriverUsername);
                //console.log(oldMarker.getTitle() + '  oldLatLng: ' + JSON.stringify(oldMarker.getPosition()) + 'newLatLng: ' + JSON.stringify(latLng));
                oldMarker.setPosition(myLatLng);
            } else {
                var newMarker = assupg.Controls.GoogleMap.Marker.fun_createMarker(googleMap, myLatLng, title, image);
                markerMap.put(currentDriverUsername, newMarker);
                //mc.addMarker(newMarker);
            }
        });

        mc.clearMarkers();
        console.info('markerMap.size: ' + markerMap.size() + '\t' + 'mc.getTotalMarkers:  ' + mc.getTotalMarkers() + '\t' + 'mc.getTotalClusters: ' + mc.getTotalClusters() + '\t');

        //mc.resetViewport();
        //mc.redraw();
        mc.addMarkers(markerMap.values());
        console.info('markerMap.size: ' + markerMap.size() + '\t' + 'mc.getTotalMarkers:  ' + mc.getTotalMarkers() + '\t' + 'mc.getTotalClusters: ' + mc.getTotalClusters() + '\t');
    }, 5000);

}

$(function () {
    assupg.Controls.GoogleMap.initializationControls.fun_initialization('map', 'initializeMap');
});
