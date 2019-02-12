import java.util.ArrayList;
import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2019/2/12
 * @Time 17:52
 */
public class NonHeapTest {

    static List<Class<?>> classList = new ArrayList<>();

    /**
     * JVM: -XX:MetaspaceSize=32M -XX:MaxMetaspaceSize=32M
     */
    public static void main(String[] args) {
        while(true){
            classList.addAll(Metaspace.createClasses());
        }
    }
}
