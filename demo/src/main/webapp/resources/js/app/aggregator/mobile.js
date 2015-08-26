'use strict';

/**
 * The main module for the mobile application.
 * Pulls in other modules.
 */
define("aggregator", [
    'angular',
    'angularRoute',
    'angularResource',
    'angularTouch',
    'app/home/view',
    'app/events/eventsView',
    'app/venues/venuesView',
    'app/eventDetail/eventDetailView',
    'app/venueDetail/venueDetailView',
    'app/booking/view',
    'app/monitor/monitor'
],function (angular) {

    return angular.module('ticketMonster', ['ngRoute',
        'ngResource',
        'ngTouch',
        'ticketMonster.api',
        'ticketMonster.homeView',
        'ticketMonster.eventsView',
        'ticketMonster.venuesView',
        'ticketMonster.eventDetailView',
        'ticketMonster.venueDetailView',
        'ticketMonster.bookingView',
        'ticketMonster.monitorView'])
        .config(['$routeProvider', function($routeProvider) {
            $routeProvider
                .otherwise({redirectTo: '/'});
        }]);
});