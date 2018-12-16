package servlets;

import java.util.Date;








//线程睡眠 sleep()
//class SleepTest{
//    public static void main(String[] args) throws Exception{
//        for (int i =1;i<10;i++)
//        {
//            System.out.println("当前时间" + new Date());
//            //调用主线程的 sleep() 方法让当前线程暂停1秒
//            Thread.sleep(1000);
//        }
//
//    }
//}

//实现 Runnable 接口创建线程类, 此接口可以共享 线程类的实例变量
//public class FirstThread implements Runnable{
//    private int i;
//    //run()方法同样是线程执行体
//    public void run()
//    {
//        for (;i<100;i++)
//        {
//            //只能用 Thread.currentThread() 方法
//            System.out.println(Thread.currentThread().getName() + " " + i);
//        }
//    }
//
//    public static void main(String[] args) {
//        for (int i =0;i<100;i++)
//        {
//            System.out.println(Thread.currentThread().getName() + " " + i);
//            if (i==20)
//            {
//                FirstThread st = new FirstThread();
//                //注意这里接口的区别,通过 new Thread(target , name)方法创建新线程
//                new Thread(st,"新线程1").start();
//                new Thread(st,"新线程2").start();
//            }
//        }
//    }
//}





























 //继承 Thread类创建并启动多线程    每次需要创建一个新的 FirstThread对象，所以两个子线程不共享该实例变量

public class FirstThread implements Runnable {
    private int i = 0;
    public void run(){
        for (;i<30;i++)
        {
            System.out.println(Thread.currentThread()   + " " + i);
        }
    }

    public static void main(String[] args) {
        for (int i =1;i<50;i++)
        {

            System.out.println(Thread.currentThread().getName() + "  " + i);
            if (i ==20)
            {
//                new FirstThread().start();
//                new FirstThread().start();
                FirstThread firstThread = new FirstThread();
                new Thread(firstThread, "111111").start();
                new Thread(firstThread, "222222").start();
            }
        }
    }
}
