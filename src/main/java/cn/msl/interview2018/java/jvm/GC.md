
# STW
`top-the-world` Stop-the-world会在任何一种GC算法中发生。
Stop-the-world意味着 JVM 因为要执行GC而停止了应用程序的执行。
当Stop-the-world发生时，除了GC所需的线程以外，所有线程都处于等待状态，直到GC任务完成。
GC优化很多时候就是指减少Stop-the-world发生的时间。