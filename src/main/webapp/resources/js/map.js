$(document).ready(function(){
    $(".serviceman").click(function(){
        $.ajax({
            method: "GET",
            url: "/PracaInzRest/ftthIssue/" + $(this).text()
        })
            .done(function (msg) {
                var issueLocation;
                for(var i = 0; i < msg.length; i++) {
                    issueLocation = {lat: msg[i].latitude, lng: msg[i].longitude};
                     var marker = new google.maps.Marker({
                        position: issueLocation,
                        map: map
                    });
                    for(var j = 0; j < msg[i].ftthJob.affectedEdges.length; j++) {
                        var affectedEdgeNodeA = msg[i].ftthJob.affectedEdges[j].nodeA;
                        var affectedEdgeNodeB = msg[i].ftthJob.affectedEdges[j].nodeB;
                        var nodeA = {lat: affectedEdgeNodeA.y, lng: affectedEdgeNodeA.x};
                        var nodeB = {lat: affectedEdgeNodeB.y, lng: affectedEdgeNodeB.x};
                        var markerNodeA = new google.maps.Marker({
                            position: nodeA,
                            map: map
                        });
                        var markerNodeB = new google.maps.Marker({
                            position: nodeB,
                            map: map
                        });
                    }
                }
                map.setZoom(15);
                map.setCenter(new google.maps.LatLng(issueLocation.lat, issueLocation.lng));
            });
    });
});