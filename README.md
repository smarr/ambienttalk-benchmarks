AmbientTalk Benchmarks
======================

The repository contains a number of benchmarks to evaluate the performance
of [AmbientTalk][AT]. A good number of them are based on the [Computer 
Language Benchmarks Game][CLBG].

Since AmbientTalk is implemented in Java, we use the [Caliper benchmarking
framework][Caliper] for the benchmark execution. Caliper helps to ensure that
the results are more or less reliable and not totally off, because of
just-in-time compilation etc.

While not inherently necessary, we use [ReBench][ReBench] to record the
benchmarking results for statistic evaluation. Please see the `rebench.conf`
file for a list of benchmarks and the parameters we use.

Build and Run
-------------

The benchmarks can be build using `ant` and the `./caliper.sh` scripts assists
running them:

    $ ant compile
    $ ./caliper.sh bench.clbg.NbodyAT -i macro -l 30000s -p -v

This will compile the benchmarks and execute the N-Body benchmark as a macro
benchmark, for max. runtime of 30 000sec and verbose printouts, including an
overview of the used Caliper configuration.

License
-------

All code based on the [Computer Language Benchmarks Game][CLBG] underlies the
Benchmarks Game's license, i.e., [Revised BSD License][BSD] where necessary.
All other elements of this repository are licensed under the [MIT
License][MIT].

 [AT]:      https://code.google.com/p/ambienttalk/
 [CLBG]:    http://benchmarksgame.alioth.debian.org/
 [Caliper]: https://code.google.com/p/caliper/
 [ReBench]: https://github.com/smarr/ReBench
 [BSD]:     http://benchmarksgame.alioth.debian.org/license.php
 [MIT]:     http://opensource.org/licenses/mit-license.php