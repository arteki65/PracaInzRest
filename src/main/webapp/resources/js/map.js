var markers = [];
var circle;
var polylines = [];
var infoWindows = [];
var networkMarkes = [];
var networkEdges = [];

$(document).ready(function () {
    var bounds = new google.maps.LatLngBounds();

    function clearMap() {
        for (var counter = 0; counter < markers.length; counter++) {
            markers[counter].setMap(null);
        }
        markers = [];

        for (var counter3 = 0; counter3 < infoWindows.length; counter3++) {
            infoWindows[counter3].close();
        }
        infoWindows = [];

        if (circle != null) {
            circle.setMap(null);
        }

        for (var counter2 = 0; counter2 < polylines.length; counter2++) {
            polylines[counter2].setMap(null);
        }
        polylines = [];
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
            '<p>Identyfikator: ' + issue.id + '</p>' +
            '<p>Opis: ' + issue.description + '</p>' +
            '<p>Status: ' + issue.ftthJob.jobStatus + '</p>'
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
            var affectedAccessPointNode = issue.ftthJob.affectedAccessPoints[j].node;
            var nodeA = {lat: affectedAccessPointNode.y, lng: affectedAccessPointNode.x};
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
                '<p>Szczegóły techniczne: ' + issue.ftthJob.affectedAccessPoints[j].description + '</p>' +
                '<p>Typ: ' + issue.ftthJob.affectedAccessPoints[j].type + '</p>'
            });
            accessPointInfoWindows.push(accessPointInfoWindow);
            infoWindows.push(accessPointInfoWindow);

            google.maps.event.addListener(accessPointsMarkers[j], 'click', function () {
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

                    var servicemanInfoWindow = new google.maps.InfoWindow({
                        content: '<h4>Serwisant:</h4>' +
                        '<p>Identyfikator: ' + msg.id + '</p>' +
                        '<p>Login: ' + msg.username + '</p>'
                    });
                    infoWindows.push(servicemanInfoWindow);

                    servicemanLastPositionMarker.addListener('click', function () {
                        servicemanInfoWindow.open(map, servicemanLastPositionMarker);
                    });

                    markers.push(servicemanLastPositionMarker);
                    bounds.extend(servicemanLastPosition);

                    // show distributionSite
                    $.ajax({
                        method: "GET",
                        url: "/PracaInzRest/hierarchy/findByAccessSiteLike/" + issue.ftthJob.affectedAccessPoints[0].node.name +
                        "_" + issue.ftthJob.affectedAccessPoints[0].description.substr(0, 3)
                    })
                        .done(function (msg) {
                            var distributionSite = {
                                lat: msg.distributionSiteNode.y,
                                lng: msg.distributionSiteNode.x
                            };

                            var distributionSiteMarker = new google.maps.Marker({
                                position: distributionSite,
                                map: map,
                                icon: '/PracaInzRest/resources/img/ic_room_black_24dp.png',
                                zIndex: 9999999
                            });
                            markers.push(distributionSiteMarker);
                            bounds.extend(distributionSite);

                            var distributionPointInfoWindow = new google.maps.InfoWindow({
                                content: '<h4>Punkt dystrybucji:</h4>' +
                                '<p>Szczegóły techniczne: ' + msg.distributionSiteDescription + '</p>'
                            });
                            infoWindows.push(distributionPointInfoWindow);

                            distributionSiteMarker.addListener('click', function () {
                                distributionPointInfoWindow.open(map, distributionSiteMarker);
                            });

                            var centralSite = {
                                lat: msg.centralSiteNode.y,
                                lng: msg.centralSiteNode.x
                            };

                            var centralSiteMarker = new google.maps.Marker({
                                position: centralSite,
                                map: map,
                                icon: '/PracaInzRest/resources/img/ic_room_black_24dp.png',
                                zIndex: 9999999
                            });
                            markers.push(centralSiteMarker);
                            bounds.extend(centralSite);

                            var centralSiteInfoWindow = new google.maps.InfoWindow({
                                content: '<h4>OLT:</h4>' +
                                '<p>Szczegóły techniczne: ' + msg.centralSiteDescription + '</p>'
                            });
                            infoWindows.push(centralSiteInfoWindow);

                            centralSiteMarker.addListener('click', function () {
                                centralSiteInfoWindow.open(map, centralSiteMarker);
                            });

                            map.fitBounds(bounds);

                            if ($("#showNetwork").prop('checked')) {
                                showNetwork(map);
                            }
                        });

                    $.ajax({
                        method: "GET",
                        url: "/PracaInzRest/path/findPathForIssue/" + issue.id
                    })
                        .done(function (msg) {
                            for (var i = 0; i < msg.length; i++) {
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
                }
            );
    }

    function showNetwork(map) {
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

                    networkMarkes.push(markerNodeA);
                    networkMarkes.push(markerNodeB);

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

                    networkEdges.push(edge);

                    edge.setMap(map);
                }
            });
    }

    $(".serviceman").click(function () {
        clearMap();

        var servicemanUsername = $(this).text();

        $(".list-group").find(".serviceman").removeClass("active");
        $(this).addClass("active");
        $.ajax({
            method: "GET",
            url: "/PracaInzRest/ftthIssue/" + servicemanUsername
        })
            .done(function (msg) {
                var issueLocation;
                var issueLocationMarkers = [];
                var numberOfIssues = msg.length;
                for (var i = 0; i < msg.length; i++) {
                    issueLocation = {lat: msg[i].latitude, lng: msg[i].longitude};

                    var issueLocationMarker = new google.maps.Marker({
                        position: issueLocation,
                        map: map,
                        icon: '/PracaInzRest/resources/img/ic_error_black_24dp.png',
                        zIndex: 9999999
                    });
                    issueLocationMarkers[i] = issueLocationMarker;
                    issueLocationMarkers[i].index = i;
                    issueLocationMarkers[i].issue = msg[i];

                    google.maps.event.addListener(issueLocationMarkers[i], 'click', function () {
                        goToIssue(this.issue);
                    });

                    markers.push(issueLocationMarker);
                    bounds.extend(issueLocation);

                }

                $.ajax({
                    method: "GET",
                    url: "/PracaInzRest/user/findUser/" + servicemanUsername
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

                        var servicemanInfoWindow = new google.maps.InfoWindow({
                            content: '<h4>Serwisant:</h4>' +
                            '<p>Identyfikator: ' + msg.id + '</p>' +
                            '<p>Login: ' + msg.username + '</p>'
                        });
                        infoWindows.push(servicemanInfoWindow);

                        servicemanLastPositionMarker.addListener('click', function () {
                            servicemanInfoWindow.open(map, servicemanLastPositionMarker);
                        });

                        if(numberOfIssues > 0) {
                            map.fitBounds(bounds);
                        } else {
                            map.setCenter(servicemanLastPosition);
                            map.setZoom(14);
                        }
                    });
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

    $("#showNetwork").click(function () {
        if ($(this).prop('checked')) {
            showNetwork(map);
        } else {
            for (var i = 0; i < networkMarkes.length; i++) {
                networkMarkes[i].setMap(null);
            }

            for (var counter2 = 0; counter2 < networkEdges.length; counter2++) {
                networkEdges[counter2].setMap(null);
            }

            networkMarkes = [];
            networkEdges = [];
        }
    });
})
;