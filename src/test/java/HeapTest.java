import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author za-xuzhiping
 * @Date 2019/2/12
 * @Time 17:39
 */
public class HeapTest {

    private List<User> list = new ArrayList<>();

    /**
     * JVM: -Xmx32M -Xms32M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:/
     */
    public static void main(String[] args) {
        HeapTest test = new HeapTest();
        Long id = 1L;
        while(true){
            User user = new User(id++, UUID.randomUUID().toString());
            test.add(user);
        }
    }

    public void add(User user){
        this.list.add(user);
    }

    static class User{
        private Long id;
        private String code;

        public User(Long id, String code){
            this.id = id;
            this.code = code;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
