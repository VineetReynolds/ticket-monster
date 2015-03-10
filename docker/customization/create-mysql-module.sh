#!/bin/bash

# Usage: execute.sh [WildFly mode] [configuration file]
#
# The default mode is 'standalone' and default configuration is based on the
# mode. It can be 'standalone.xml' or 'domain.xml'.

JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE-ha.xml"}
IPADDR=$(ip addr list eth0|grep "inet "|cut -d' ' -f6|cut -d/ -f1)

function wait_for_server() {
  until `$JBOSS_CLI --controller=$IPADDR:9999 -c "ls /deployment" &> /dev/null`; do
    sleep 1
  done
}

echo "=> Starting JBoss EAP server"
$JBOSS_HOME/start.sh > /dev/null &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
DB_PORT_3306_TCP_ADDR=
DB_PORT_3306_TCP_PORT=
DB_ENV_MYSQL_ROOT_PASSWORD=
$JBOSS_CLI --controller=$IPADDR:9999 -c --file=`dirname "$0"`/mysql-module.cli

echo "=> Shutting down JBoss EAP"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI --controller=$IPADDR:9999 -c ":shutdown"
else
  $JBOSS_CLI --controller=$IPADDR:9999 -c "/host=*:shutdown"
fi