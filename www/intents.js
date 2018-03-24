/*global cordova, module*/


window.createEvent = function (title, location, startDate, endDate, successCallback, errorCallback) {
        var startDateMilli = new Date(startDate).getTime();
        var endDateMilli = new Date(endDate).getTime();        
        cordova.exec(successCallback, errorCallback, "Intents", "createEvent", [{
            "title" : title,
            "location" : location, 
            "startDateMilli" : startDateMilli,
            "endDateMilli" : endDateMilli
        }]);
    },
    
window.sendMail = function(address, infos, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Intents", "sendMail", [{
            "address" : address,
            "infos" : infos.cordova + "\n" + infos.model + "\n" + infos.platform + "\n" + infos.version
        }]);
    }

