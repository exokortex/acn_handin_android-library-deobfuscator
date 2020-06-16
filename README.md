## Getting started

Run the following command to initialize the git submodules:

```
git submodule update --init --recursive
```

Then run the tests via

```
./run-tests.sh
```

The output can be found in the directory `results`.

## Running

use the following maven command to run the program:

```
mvn compile exec:exec -DappRoot=<appRoot> -DlibPath=<libPath>
```

Example:

```
mvn compile exec:exec -DappRoot=../app-debug/sources -DlibPath=../bc-java/core/src/main/java
```