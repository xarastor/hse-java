#!/bin/bash

make cmpl_akari
make cmpl_akario

./tmp/akari-orig.c.out < example.ppm > tmp/odd_output-orig.ppm
echo $?
./tmp/akari-orig.c.out - - even < example.ppm > tmp/even_output.ppm
echo $?
./tmp/akari-orig.c.out example.ppm tmp/odd_output.ppm
echo $?

./tmp/akari.c.out < example.ppm > tmp/odd_output-orig.ppm
echo $?
./tmp/akari.c.out - - even < example.ppm > tmp/even_output-orig.ppm
echo $?
./tmp/akari.c.out example.ppm tmp/odd_output-orig.ppm
echo $?

diff tmp/odd_output.ppm tmp/odd_output-orig.ppm
diff tmp/even_output.ppm tmp/even_output-orig.ppm
