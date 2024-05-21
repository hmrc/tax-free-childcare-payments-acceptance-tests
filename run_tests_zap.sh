#!/usr/bin/env bash
# Wrapper script for running DAST container locally
# See https://github.com/hmrc/dast-config-manager#running-zap-locally
# Ensure the runner is the same as the one for jenkins

set -o errexit  # Exit immediately if any command or pipeline of commands fails
set -o nounset  # Treat unset variables and parameters as an error
set -o pipefail # Exit when command before pipe fails
# set -o xtrace   # Debug mode expand everything and print it before execution

cd "$(dirname "$0")" # Always run from script location

# We assume you started all required services using 'sm2' and MongoDB if needed

export ZAP_FAIL_ON_SEVERITY=Low # Allowed values: High, Medium, Low, Informational
export ZAP_FORWARD_ENABLE="true"
ZAP_FORWARD_PORTS=$(sm2 -s | grep -E 'PASS|BOOT'| grep -v 'MONGO' | awk '{ print $8}' | tr "\n" " ")
export ZAP_FORWARD_PORTS

if [[ -f alert-filters.json ]]; then
    export ZAP_LOCAL_ALERT_FILTERS="${PWD}/alert-filters.json"
    echo "Using filters from ${ZAP_LOCAL_ALERT_FILTERS}"
fi

if ! [[ -d dast-config-manager ]]; then
    git clone git@github.com:hmrc/dast-config-manager.git
fi
(
    cd dast-config-manager
    git fetch --all
    # git reset --hard origin/main
    make local-zap-running
)

TEST_FAILED=false
# Modify following sbt command accordingly to your tests
sbt -Dzap.proxy=true -Denvironment=local clean 'testOnly uk.gov.hmrc.test.api.specs.*' || TEST_FAILED=true

(
    cd dast-config-manager
    make local-zap-stop
)

# display the results in a browser window
echo "Displaying results in browser..."
browse ./dast-config-manager/target/dast-reports/index.html

if [[ "${TEST_FAILED}" == "true" ]]; then
    echo "Your sbt tests failed, but you can still check partial ZAP report and session in dast-config-manager/target"
    exit 1
fi