#!/bin/bash
mvn compile exec:exec -DappRoot=./examples/bouncydroid/jadx \
                      -DlibPath=./examples/bc-java/core/src/main/java \
                      -DoutPath=./results/bouncydroid-jadx \
                      -Dconfig=./examples/bouncydroid/config.txt \
                      -DtranslationOut=./results/bouncydroid-jadx_translation.txt

