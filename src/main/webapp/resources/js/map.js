var markers = [];
var circle;
var polylines = [];

$(document).ready(function () {
    var bounds = new google.maps.LatLngBounds();

    function clearMap() {
        for (var counter = 0; counter < markers.length; counter++) {
            markers[counter].setMap(null);
        }
        markers = [];

        if (circle != null) {
            circle.setMap(null);
        }

        for (var counter2 = 0; counter2 < polylines.length; counter2++) {
            polylines[counter2].setMap(null);
        }
        bounds = new google.maps.LatLngBounds();
    }

    $(".serviceman").click(function () {
        clearMap();

        $(".list-group").find(".serviceman").removeClass("active");
        $(this).addClass("active");
        $.ajax({
            method: "GET",
            url: "/PracaInzRest/ftthIssue/" + $(this).text()
        })
            .done(function (msg) {
                var issueLocation;
                for (var i = 0; i < msg.length; i++) {
                    issueLocation = {lat: msg[i].latitude, lng: msg[i].longitude};
                    var issueLocationMarker = new google.maps.Marker({
                        position: issueLocation,
                        map: map,
                        icon: '/PracaInzRest/resources/img/ic_error_black_24dp.png',
                        zIndex: 9999999
                    });
                    markers.push(issueLocationMarker);
                    bounds.extend(issueLocation);

                    circle = new google.maps.Circle({
                        strokeColor: '#FF0000',
                        strokeOpacity: 0.8,
                        strokeWeight: 2,
                        fillColor: '#FF0000',
                        fillOpacity: 0.35,
                        map: map,
                        center: issueLocation,
                        radius: 100
                    });

                    for (var j = 0; j < msg[i].ftthJob.affectedEdges.length; j++) {
                        var affectedEdgeNodeA = msg[i].ftthJob.affectedEdges[j].nodeA;
                        var affectedEdgeNodeB = msg[i].ftthJob.affectedEdges[j].nodeB;
                        var nodeA = {lat: affectedEdgeNodeA.y, lng: affectedEdgeNodeA.x};
                        var nodeB = {lat: affectedEdgeNodeB.y, lng: affectedEdgeNodeB.x};

                        var markerNodeA = new google.maps.Marker({
                            position: nodeA,
                            map: map,
                            icon: '/PracaInzRest/resources/img/blueMarker.png',
                            zIndex: 9999999
                        });
                        var markerNodeB = new google.maps.Marker({
                            position: nodeB,
                            map: map,
                            icon: '/PracaInzRest/resources/img/blueMarker.png',
                            zIndex: 9999999
                        });
                        markers.push(markerNodeA);
                        markers.push(markerNodeB);

                        var edgeCoordinates = [{lat: affectedEdgeNodeA.y, lng: affectedEdgeNodeA.x},
                            {lat: affectedEdgeNodeB.y, lng: affectedEdgeNodeB.x}];
                        var edge = new google.maps.Polyline({
                            path: edgeCoordinates,
                            geodesic: true,
                            strokeColor: '#0000FF',
                            strokeOpacity: 1.0,
                            strokeWeight: 2,
                            zIndex: 9999999
                        });
                        polylines.push(edge);

                        edge.setMap(map);
                    }
                }
                fitMap();
            });

        $.ajax({
            method: "GET",
            url: "/PracaInzRest/user/findUser/" + $(this).text()
        })
            .done(function (msg) {
                var servicemanLastPosition = {lat: msg.lastPosition.latitude, lng: msg.lastPosition.longitude};
                var servicemanLastPositionMarker = new google.maps.Marker({
                    position: servicemanLastPosition,
                    map: map,
                    icon: '/PracaInzRest/resources/img/ic_build_black_24dp.png',
                    zIndex: 9999999
                });
                markers.push(servicemanLastPositionMarker);
                bounds.extend(servicemanLastPosition);
                fitMap();
            });

        var functionsDone = 0;

        function fitMap() {
            functionsDone++;
            if (functionsDone == 2) {
                map.fitBounds(bounds);
                $.ajax({
                    method: "GET",
                    url: "/PracaInzRest/edge/findEdgesInArea?x1=" + map.getBounds().getSouthWest().lng() + "&y1=" +
                    map.getBounds().getSouthWest().lat() + "&x2=" + map.getBounds().getNorthEast().lng() + "&y2=" +
                    map.getBounds().getNorthEast().lat()
                })
                    .done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            var nodeA = {lat: msg[i].nodeA.y, lng: msg[i].nodeA.x};
                            var nodeB = {lat: msg[i].nodeB.y, lng: msg[i].nodeB.x};

                            var markerNodeA = new google.maps.Marker({
                                position: nodeA,
                                map: map,
                                icon: '/PracaInzRest/resources/img/redMarker.png'
                            });
                            var markerNodeB = new google.maps.Marker({
                                position: nodeB,
                                map: map,
                                icon: '/PracaInzRest/resources/img/redMarker.png'
                            });
                            markers.push(markerNodeA);
                            markers.push(markerNodeB);

                            var edgeCoordinates = [{lat: msg[i].nodeA.y, lng: msg[i].nodeA.x},
                                {lat: msg[i].nodeB.y, lng: msg[i].nodeB.x}];
                            var edge = new google.maps.Polyline({
                                path: edgeCoordinates,
                                geodesic: true,
                                strokeColor: '#000000',
                                strokeOpacity: 1.0,
                                strokeWeight: 2
                            });
                            polylines.push(edge);
                            edge.setMap(map);
                        }
                    });
            }
        }
    });

    $("#showAllServicemen").click(function () {
        clearMap();
    });
});