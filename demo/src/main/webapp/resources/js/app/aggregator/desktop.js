'use strict';

/**
 * The main module for the desktop application.
 * Pulls in other modules.
 */
define("aggregator", [
    'angular',
    'angularRoute',
    'angularResource',
    'app/home/view',
    'app/events/eventsView',
    'app/eventDetail/eventDetailView',
    'app/venues/venuesView',
    'app/venueDetail/venueDetailView',
    'app/booking/view',
    'app/monitor/monitor'
],function (angular) {

    return angular.module('ticketMonster', ['ngRoute',
        'ngResource',
        'ticketMonster.api',
        'ticketMonster.homeView',
        'ticketMonster.eventsView',
        'ticketMonster.eventDetailView',
        'ticketMonster.venuesView',
        'ticketMonster.venueDetailView',
        'ticketMonster.bookingView',
        'ticketMonster.monitorView'])
        .config(['$routeProvider', function($routeProvider) {
            $routeProvider
                .otherwise({redirectTo: '/'});
        }]);
});