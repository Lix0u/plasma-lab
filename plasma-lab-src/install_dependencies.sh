#!/bin/bash

REPO_DIR="repository"

# Function to install a dependency using its groupId, artifactId, version, and jar/pom files
install_dependency() {
    local groupId=$1
    local artifactId=$2
    local version=$3
    local jar_file=$4
    local pom_file=$5

    # Convert the groupId (with slashes) to Maven's dot notation
    local groupId_dot=$(echo "$groupId" | tr '/' '.')

    echo "Installing $groupId_dot:$artifactId:$version"

    echo "mvn install:install-file \
        -Dfile="$jar_file" \
        -DgroupId="$groupId_dot" \
        -DartifactId="$artifactId" \
        -Dversion="$version" \
        -Dpackaging=jar \
        -DpomFile="$pom_file""

    # Run the Maven install command
    mvn install:install-file \
        -Dfile="$jar_file" \
        -DgroupId="$groupId_dot" \
        -DartifactId="$artifactId" \
        -Dversion="$version" \
        -Dpackaging=jar \
        -DpomFile="$pom_file"

    if [ $? -eq 0 ]; then
        echo "$artifactId successfully installed."
    else
        echo "Failed to install $artifactId."
    fi
}

# Find and iterate over all JAR files in the repository directory
find "$REPO_DIR" -name "*.jar" | while read jar_file; do
    # Extract the path components for groupId, artifactId, and version
    relative_path=$(dirname "$jar_file")
    
    # extract groupId, artifactId, and version from the path
    artifactId=$(basename "$(dirname "$relative_path")")
    version=$(basename "$relative_path")
    #groupId=$(echo "$relative_path" | sed "s|$REPO_DIR/||" | awk -F'/' '{OFS=ex"/"; print $1,$2}')
    groupId=$(echo "$relative_path" | sed "s|$REPO_DIR/||" | awk -F"/$artifactId" '{print $1}')

    echo "groupID : "$groupId
    echo "artifactId : "$artifactId
    echo "version : "$version
    echo "jar_file : "$jar_file

    # Find the corresponding POM file (if it exists)
    #pom_file="${relative_path}/${artifactId}-${version}.pom"
    base_name=$(basename "$jar_file" .jar)
    dir_name=$(dirname "$jar_file")
    pom_file="$dir_name/$base_name.pom"
    echo "pom_file : "$pom_file
    #echo "pom_file2 : "$pom_file2

    if [ -f "$pom_file" ]; then
        install_dependency "$groupId" "$artifactId" "$version" "$jar_file" "$pom_file"
    #elif [ -f "$pom_file2" ]; then
     #   install_dependency "$groupId" "$artifactId" "$version" "$jar_file" "$pom_file2"
    else
        echo "POM file not found for $base_name.jar, skipping..."
    fi
done