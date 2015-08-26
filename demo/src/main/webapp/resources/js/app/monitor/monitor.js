'use strict';
define([
    'angular',
    'underscore',
    'configuration',
    'bootstrap',
    'angularRoute',
    'angularResource',
    'app/api/services'
], function(angular, _, config) {
    angular.module('ticketMonster.monitorView', ['ngRoute', 'ngResource', 'ticketMonster.api'])
        .config(['$routeProvider', function($routeProvider) {
            $routeProvider.when('/monitor', {
                templateUrl: 'resources/js/app/monitor/monitor.html',
                controller: 'MonitorController'
            });
        }])
        .factory("Bot", ['$http', function($http) {
            /**
             * The Bot model class definition
             * Used perform operations on the Bot.
             */
            var bot = {
                statusUrl : config.baseUrl + 'rest/bot/status',
                messagesUrl : config.baseUrl + 'rest/bot/messages'
            };

            /*
             * Start the Bot by sending a request to the Bot resource
             * with the new status of the Bot set to "RUNNING".
             */
            bot.start = function() {
                $http({
                    method: "PUT",
                    url: this.statusUrl,
                    data: "\"RUNNING\"",
                    responseType: "application/json"
                });
            };

            /*
             * Stop the Bot by sending a request to the Bot resource
             * with the new status of the Bot set to "NOT_RUNNING".
             */
            bot.stop = function() {
                $http({
                    method: "PUT",
                    url: this.statusUrl,
                    data: "\"NOT_RUNNING\"",
                    responseType: "application/json"
                });
            };

            /*
             * Stop the Bot and delete all bookings by sending a request to the Bot resource
             * with the new status of the Bot set to "RESET".
             */
            bot.reset = function() {
                $http({
                    method: "PUT",
                    url: this.statusUrl,
                    data: "\"RESET\"",
                    responseType: "application/json"
                });
            };

            /*
             * Fetch the log messages of the Bot and invoke the callback.
             * The callback is provided with the log messages (an array of Strings).
             */
            bot.fetchMessages = function(callback) {
                $http.get(this.messagesUrl)
                    .then(function(data) {
                        if(callback) {
                            callback(data);
                        }
                    });
            };

            return bot;
        }])
        .controller('MonitorController', ['$scope', '$http', '$timeout', 'Bot', function($scope, $http, $timeout, Bot) {

            var fetchMetrics = function() {
                $http.get(config.baseUrl + "rest/metrics")
                    .then(function(response){
                        $scope.metrics = response.data;
                    });
            };

            var timer = null;
            var poll = function() {
                fetchMetrics();
                Bot.fetchMessages(function(response) {
                    $scope.messages = response.data.reverse().join("");
                });
                timer = $timeout(poll, 3000);
            };
            timer = $timeout(poll, 0);

            $scope.startBot = function () {
                Bot.start()
            };
            $scope.stopBot = function () {
                Bot.stop()
            };
            $scope.resetBot = function () {
                Bot.reset()
            };

            $scope.$on("$destroy", function() {
                if(timer) {
                    $timeout.cancel(timer);
                }
            });
        }]);
});