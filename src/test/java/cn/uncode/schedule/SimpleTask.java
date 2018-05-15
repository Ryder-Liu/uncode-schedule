package cn.uncode.schedule;

import com.alibaba.fastjson.JSON;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author juny.ye
 */
@Component
public class SimpleTask {

    private static int i = 0;

    @Scheduled(fixedDelay = 1000)
    public void print() {
        System.out.println("===========start!=========");
        System.out.println("I:"+i);i++;
        System.out.println("=========== end !=========");
    }

    public void print1() {
        System.out.println("===========start!=========");
        System.out.println("print1:"+i);i++;
        System.out.println("=========== end !=========");
    }

    public void print2() {
        System.out.println("===========start!=========");
        System.out.println("print2:"+i);i++;
        System.out.println("=========== end !=========");
    }

    public void print3() {
        System.out.println("===========start!=========");
        System.out.println("print3:"+i);i++;
        System.out.println("=========== end !=========");
    }

    public void print4() {
        System.out.println("===========start!=========");
        System.out.println("print4:"+i);i++;
        System.out.println("=========== end !=========");
    }


    public void print5(String param) {
        Map params = (Map) JSON.parse(param);
        System.out.println("===========start!=========");
        System.out.println("print<<5>>:"+i+"-"+params.get("name") + "-" + params.get("age"));i++;
        System.out.println("=========== end !=========");
    }



}
