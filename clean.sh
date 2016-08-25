for d in */gradlew ; do
    cd ./$(dirname $d)
    ./$(basename $d) clean
    cd ../
done