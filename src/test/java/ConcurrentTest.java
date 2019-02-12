import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
class ConcurrentTest {

    private static int thread_num = 20;
    private static int client_num = 40;
    private static Map keywordMap = new HashMap();

//    static {
//        try {
//            InputStreamReader isr = new InputStreamReader(new FileInputStream(
//                    new File("clicks.txt")), "GBK");
//            BufferedReader buffer = new BufferedReader(isr);
//            String line = "";
//            while ((line = buffer.readLine()) != null) {
//                keywordMap.put(line.substring(0, line.lastIndexOf(":")), "");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        // thread_num个线程可以同时访问
        final Semaphore semp = new Semaphore(thread_num);
        // 模拟client_num个客户端访问
        for (int index = 0; index < client_num; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 获取许可
                        semp.acquire();
                        System.out.println("Thread:" + NO);
                        String host = "https://www.baidu.com/s?";
                        String para = "wd=" + getRandomSearchKey(NO);
                        System.out.println(host + para);
                        URL url = new URL(host);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
// connection.setRequestMethod("POST");
// connection.setRequestProperty("Proxy-Connection",
// "Keep-Alive");
                        connection.setDoOutput(true);
                        connection.setDoInput(true);
                        PrintWriter out = new PrintWriter(connection
                                .getOutputStream());
                        out.flush();
                        out.close();
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(connection
                                        .getInputStream()));
//                        String line = "";
//                        String result = "";
//                        while ((line = in.readLine()) != null) {
//                            result += line;
//                        }
// System.out.println(result);
// Thread.sleep((long) (Math.random()) * 1000);
// 释放
                        System.out.println("第：" + NO + " 个");
                        semp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
// 退出线程池
        exec.shutdown();
    }

    private static String getRandomSearchKey(final int no) {
        String ret = "";
        ret = RandomStringUtils.randomAlphabetic(4);
        System.out.println("\t" + ret);
        return ret;
    }

    @Test
    public void test() {

    }
}  