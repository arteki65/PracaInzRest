var markers = [];
var circle;
var polylines = [];
var infoWindows = [];

$(document).ready(function () {
    var bounds = new google.maps.LatLngBounds();

    function clearMap() {
        for (var counter = 0; counter < markers.length; counter++) {
            markers[counter].setMap(null);
        }
        markers = [];

        for(var counter3 = 0; counter3 < infoWindows.length; counter3++) {
            infoWindows[counter3].close();
        }
        infoWindows = [];

        if (circle != null) {
            circle.setMap(null);
        }

        for (var counter2 = 0; counter2 < polylines.length; counter2++) {
            polylines[counter2].setMap(null);
        }
        bounds = new google.maps.LatLngBounds();
    }

    function goToIssue(issue) {
        clearMap();

        var issueLocation = {lat: issue.latitude, lng: issue.longitude};
        bounds.extend(issueLocation);

        var issueLocationMarker = new google.maps.Marker({
            position: issueLocation,
            map: map,
            icon: '/PracaInzRest/resources/img/ic_error_black_24dp.png',
            zIndex: 9999999
        });
        markers.push(issueLocationMarker);

        var issueLocationInfoWindow = new google.maps.InfoWindow({
            content: '<h4>Szczegóły zgłoszenia:</h4>' +
            '<p>Długość geograficzna: ' + issue.longitude + '</p>' +
            '<p>Szerokość geograficzna: ' + issue.latitude + '</p>' +
            '<p>Opis: ' + issue.description + '</p>'
        });
        infoWindows.push(issueLocationInfoWindow);

        issueLocationMarker.addListener('click', function () {
            issueLocationInfoWindow.open(map, issueLocationMarker);
        });

        circle = new google.maps.Circle({
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#FF0000',
            fillOpacity: 0.35,
            map: map,
            center: issueLocation,
            radius: 30
        });

        var accessPointsMarkers = [];
        var accessPointInfoWindows = [];
        for (var j = 0; j < issue.ftthJob.affectedAccessPoints.length; j++) {
            var affectedAccessPoint = issue.ftthJob.affectedAccessPoints[j].node;
            var nodeA = {lat: affectedAccessPoint.y, lng: affectedAccessPoint.x};
            bounds.extend(nodeA);

            var markerAccessPoint = new google.maps.Marker({
                position: nodeA,
                map: map,
                icon: '/PracaInzRest/resources/img/blueMarker.png',
                zIndex: 9999999
            });
            accessPointsMarkers[j] = markerAccessPoint;
            accessPointsMarkers[j].index = j;

            var accessPointInfoWindow = new google.maps.InfoWindow({
                content: '<h4>Punkt dostępowy:</h4>' +
                '<p>Długość geograficzna: ' + affectedAccessPoint.x + '</p>' +
                '<p>Szerokość geograficzna: ' + affectedAccessPoint.y + '</p>'
            });
            accessPointInfoWindows.push(accessPointInfoWindow);
            infoWindows.push(accessPointInfoWindow);

            google.maps.event.addListener(accessPointsMarkers[j], 'click', function() {
                accessPointInfoWindows[this.index].open(map, accessPointsMarkers[this.index]);
            });

            markers.push(markerAccessPoint);
        }

        // show sesrviceman
        $.ajax({
            method: "GET",
            url: "/PracaInzRest/user/findUser/" + issue.ftthJob.servicemanUsername
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

                // show distributionSite
                $.ajax({
                    method: "GET",
                    url: "/PracaInzRest/hierarchy/findByAccessSiteLike/" + issue.ftthJob.affectedAccessPoints[0].node.name
                })
                    .done(function (msg) {
                        var distributionSite = {lat: msg.distributionSiteNode.y,
                            lng: msg.distributionSiteNode.x};
                        var distributionSiteMarker = new google.maps.Marker({
                            position: distributionSite,
                            map: map,
                            icon: '/PracaInzRest/resources/img/ic_share_black_24dp.png',
                            zIndex: 9999999
                        });
                        markers.push(distributionSiteMarker);
                        bounds.extend(distributionSite);

                        map.fitBounds(bounds);

                        // TODO: add checkbox for showing all network
                        /*$.ajax({
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
                                        strokeWeight: 1
                                    });
                                    polylines.push(edge);
                                    edge.setMap(map);
                                }
                            });*/
                    });

                $.ajax({
                    method: "GET",
                    url: "/PracaInzRest/path/findPathForIssue/" + issue.id
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
                            markers.push(markerNodeA);

                            if(i != (msg.length - 1)) {
                                var markerNodeB = new google.maps.Marker({
                                    position: nodeB,
                                    map: map,
                                    icon: '/PracaInzRest/resources/img/redMarker.png'
                                });
                                markers.push(markerNodeB);
                            } else {
                                console.log("last");
                            }

                            var edgeCoordinates = [{lat: msg[i].nodeA.y, lng: msg[i].nodeA.x},
                                {lat: msg[i].nodeB.y, lng: msg[i].nodeB.x}];
                            var edge = new google.maps.Polyline({
                                path: edgeCoordinates,
                                geodesic: true,
                                strokeColor: '#0000FF',
                                strokeOpacity: 1.0,
                                strokeWeight: 4,
                                zIndex: 9999999
                            });
                            polylines.push(edge);
                            edge.setMap(map);
                        }
                    });
            });
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
                var issueLocationMarkers = [];
                var issueLocationInfoWindows = [];
                for (var i = 0; i < msg.length; i++) {
                    issueLocation = {lat: msg[i].latitude, lng: msg[i].longitude};

                    issueLocationInfoWindows[i] = new google.maps.InfoWindow({
                        content: '<h4>Szczegóły zgłoszenia:</h4>' +
                        '<p>Długość geograficzna: ' + msg[i].longitude + '</p>' +
                        '<p>Szerokość geograficzna: ' + msg[i].latitude + '</p>' +
                        '<p>Opis: ' + msg[i].description + '</p>'
                    });

                    var issueLocationMarker = new google.maps.Marker({
                        position: issueLocation,
                        map: map,
                        icon: '/PracaInzRest/resources/img/ic_error_black_24dp.png',
                        zIndex: 9999999
                    });
                    issueLocationMarkers[i] = issueLocationMarker;
                    issueLocationMarkers[i].index = i;
                    issueLocationMarkers[i].issue = msg[i];

                    google.maps.event.addListener(issueLocationMarkers[i], 'click', function() {
                        goToIssue(this.issue);
                    });

                    markers.push(issueLocationMarker);
                    bounds.extend(issueLocation);

                }
                map.fitBounds(bounds);
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
            });
    });

    $("#showAllServicemen").click(function () {
        clearMap();

        $.ajax({
            method: "GET",
            url: "/PracaInzRest/accessPoint//findAccessPointsInArea?x1=" + map.getBounds().getSouthWest().lng() + "&y1=" +
            map.getBounds().getSouthWest().lat() + "&x2=" + map.getBounds().getNorthEast().lng() + "&y2=" +
            map.getBounds().getNorthEast().lat()
        })
            .done(function (msg) {
                for (var i = 0; i < msg.length; i++) {
                    var node = {lat: msg[i].node.y, lng: msg[i].node.x};

                    var markerNode = new google.maps.Marker({
                        position: node,
                        map: map,
                        icon: '/PracaInzRest/resources/img/redMarker.png'
                    });
                    markers.push(markerNode);
                    bounds.extend(node);
                }

                map.fitBounds(bounds);
            });
    });
});