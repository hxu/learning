#!/bin/bash
for i in {10..16}
do
    curl http://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-005-elements-of-software-construction-fall-2011/lecture-notes/lec$i.zip -O
    unzip lec$i.zip -d lec$i
    rm lec$i.zip
done

