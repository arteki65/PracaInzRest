$(document).ready(function(){
    $(".serviceman").click(function(){
        $.ajax({
            method: "GET",
            url: "/PracaInzRest/ftthIssue/" + $(this).text()
        })
            .done(function (msg) {
                var issueLocation;
                for(var i = 0; i < msg.length; i++) {
                    issueLocation = {lat: msg[0].latitude, lng: msg[0].longitude};
                     var marker = new google.maps.Marker({
                        position: issueLocation,
                        map: map
                    });
                }
                map.setZoom(15);
                map.setCenter(new google.maps.LatLng(issueLocation.lat, issueLocation.lng));
            });
    });
});