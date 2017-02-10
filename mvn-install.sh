#!/usr/bin/env bash
#
# Install local jar files into Maven repository. The artifact name would be same
# as the filename minus the extension.
# :Author: Zemian Deng
# :Date: 2013/06/17
#
# Usage:
#   # Print as maven dependency used in pom file
#   mvn-install.sh mygroup 1.0.0 lib/*.jar
#
#   # Install jar files into local maven repo
#   RUN_TYPE=install mvn-install.sh mygroup 1.0.0 lib/*.jar
#
#   # Deploy jar files into remote maven repo
#   export REPO_URL=http://localhost/nexus/content/repositories/thirdparty
#   RUN_TYPE=deploy mvn-install.sh mygroup 1.0.0 lib/*.jar
#

# Capture command arguments and options
GROUP=$1
shift
VERSION=$1
shift
FILES="$@"
if [[ "$GROUP" == "" || "$VERSION" == "" || "$FILES" == "" ]]; then
 printf "ERROR: invalid arguments: GROUP VERSION FILES...\n"
 exit 1
fi

RUN_TYPE=${RUN_TYPE:="print"} # values: print|install|deploy
REPO_ID=${REPO_ID:="nexus-server"} # Id defined in user's settings.xml for authentication
REPO_URL=${REPO_URL:="http://localhost/nexus/content/repositories/thirdparty"}

# For each file, perform action based on run type.
for FILE in $FILES; do
 ARTIFACT=`basename $FILE '.jar'`
 if [[ "$RUN_TYPE" == "deploy" ]]; then
  printf "Deploying file=$FILE as artifact=$ARTIFACT to repo=$REPO_URL\n"
  mvn deploy:deploy-file \
   -DrepositoryId=$REPO_ID -Durl=$REPO_URL \
   -DgroupId=$GROUP -DartifactId=$ARTIFACT -Dversion=$VERSION -Dpackaging=jar \
   -Dfile=$FILE
 elif [[ "$RUN_TYPE" == "install" ]]; then
  printf "Installing file=$FILE as artifact=$ARTIFACT\n"
  mvn install:install-file \
   -DgroupId=$GROUP -DartifactId=$ARTIFACT -Dversion=$VERSION -Dpackaging=jar \
   -Dfile=$FILE
 elif [[ "$RUN_TYPE" == "print" ]]; then
  printf "        <dependency>\n"
  printf "            <groupId>$GROUP</groupId>\n"
  printf "            <artifactId>$ARTIFACT</artifactId>\n"
  printf "            <version>$VERSION</version>\n"
  printf "        </dependency>\n"
 fi
done
