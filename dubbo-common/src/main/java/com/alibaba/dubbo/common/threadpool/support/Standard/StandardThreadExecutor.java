package com.alibaba.dubbo.common.threadpool.support.Standard;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * 
 * 代码和思路主要来自于 taskThreadPool(也是我从tocmat源码里拿过来的)
 * ，进行再次改造，比较适合于业务处理需要远程资源的场景，Jdk的线程池更适合cpu密集型，内存操作型
 * </pre>
 * 
 * @author smartlv
 * @date 20170420
 */
public class StandardThreadExecutor extends ThreadPoolExecutor
{

    public static final int DEFAULT_MIN_THREADS = 20;
    public static final int DEFAULT_MAX_THREADS = 200;
    public static final int DEFAULT_MAX_IDLE_TIME = 60 * 1000; // 1 minutes

    protected AtomicInteger submittedTasksCount; // 正在处理的任务数
    private int maxSubmittedTaskCount; // 最大允许同时处理的任务数

    public StandardThreadExecutor()
    {
        this(DEFAULT_MIN_THREADS, DEFAULT_MAX_THREADS);
    }

    public StandardThreadExecutor(int coreThread, int maxThreads)
    {
        this(coreThread, maxThreads, maxThreads);
    }

    public StandardThreadExecutor(int coreThread, int maxThreads, long keepAliveTime, TimeUnit unit)
    {
        this(coreThread, maxThreads, keepAliveTime, unit, maxThreads);
    }

    public StandardThreadExecutor(int coreThreads, int maxThreads, int queueCapacity)
    {
        this(coreThreads, maxThreads, queueCapacity, Executors.defaultThreadFactory());
    }

    public StandardThreadExecutor(int coreThreads, int maxThreads, int queueCapacity, ThreadFactory threadFactory)
    {
        this(coreThreads, maxThreads, DEFAULT_MAX_IDLE_TIME, TimeUnit.MILLISECONDS, queueCapacity, threadFactory);
    }

    public StandardThreadExecutor(int coreThreads, int maxThreads, long keepAliveTime, TimeUnit unit, int queueCapacity)
    {
        this(coreThreads, maxThreads, keepAliveTime, unit, queueCapacity, Executors.defaultThreadFactory());
    }

    public StandardThreadExecutor(int coreThreads, int maxThreads, long keepAliveTime, TimeUnit unit, int queueCapacity,
            ThreadFactory threadFactory)
    {
        this(coreThreads, maxThreads, keepAliveTime, unit, queueCapacity, threadFactory, new AbortPolicy());
    }

    public StandardThreadExecutor(int coreThreads, int maxThreads, long keepAliveTime, TimeUnit unit, int queueCapacity,
            ThreadFactory threadFactory, RejectedExecutionHandler handler)
    {
        super(coreThreads, maxThreads, keepAliveTime, unit, new ExecutorQueue(), threadFactory, handler);
        ((ExecutorQueue) getQueue()).setStandardThreadExecutor(this);

        submittedTasksCount = new AtomicInteger(0);

        // 最大并发任务限制： 队列buffer数 + 最大线程数
        maxSubmittedTaskCount = queueCapacity + maxThreads;
    }

    public void execute(Runnable command)
    {
        int count = submittedTasksCount.incrementAndGet();

        // 超过最大的并发任务限制，进行 reject
        // 依赖的LinkedTransferQueue没有长度限制，因此这里进行控制
        if (count > maxSubmittedTaskCount)
        {
            submittedTasksCount.decrementAndGet();
            getRejectedExecutionHandler().rejectedExecution(command, this);
        }

        try
        {
            super.execute(command);
        }
        catch (RejectedExecutionException rx)
        {
            // there could have been contention around the queue
            if (!((ExecutorQueue) getQueue()).force(command))
            {
                submittedTasksCount.decrementAndGet();

                getRejectedExecutionHandler().rejectedExecution(command, this);
            }
        }
    }

    public int getSubmittedTasksCount()
    {
        return this.submittedTasksCount.get();
    }

    public int getMaxSubmittedTaskCount()
    {
        return maxSubmittedTaskCount;
    }

    protected void afterExecute(Runnable r, Throwable t)
    {
        submittedTasksCount.decrementAndGet();
    }
}

/**
 * LinkedTransferQueue 能保证更高性能，相比与LinkedBlockingQueue有明显提升,j7 dougLea写的
 * <p>
 * 1) 不过LinkedTransferQueue的缺点是没有队列长度控制，需要在外层协助控制
 * </p>
 * 
 * @author smartlv
 */
class ExecutorQueue extends LinkedTransferQueue<Runnable>
{
    private static final long serialVersionUID = -265236426751004839L;
    StandardThreadExecutor threadPoolExecutor;

    public ExecutorQueue()
    {
        super();
    }

    public void setStandardThreadExecutor(StandardThreadExecutor threadPoolExecutor)
    {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    // 注：代码来源于 TaskQueue
    public boolean force(Runnable o)
    {
        if (threadPoolExecutor.isShutdown())
        {
            throw new RejectedExecutionException("Executor not running, can't force a command into the queue");
        }
        // forces the item onto the queue, to be used if the task is rejected
        return super.offer(o);
    }

    // 注：tomcat的代码进行一些小变更
    public boolean offer(Runnable o)
    {
        // 当前线程数
        int poolSize = threadPoolExecutor.getPoolSize();

        // we are maxed out on threads, simply queue the object
        if (poolSize == threadPoolExecutor.getMaximumPoolSize())
        {
            return super.offer(o);
        }
        // 线程闲的蛋疼, just add it to the queue
        // note that we don't use getActiveCount(), see BZ 49730
        if (threadPoolExecutor.getSubmittedTasksCount() <= poolSize)
        {
            return super.offer(o);
        }
        // ，奥妙再次，不同于jdk的
        if (poolSize < threadPoolExecutor.getMaximumPoolSize())
        {
            return false;
        }
        // if we reached here, we need to add it to the queue
        return super.offer(o);
    }
}
