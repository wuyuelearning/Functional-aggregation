package utils.web;



import java.util.List;

/**
 * Created by lc on 2018/8/15.
 */

public class DomainNameModel extends BaseModel {
    public DomainNameBean datas;
    public long time;

    public class DomainNameBean {
        public List<DomainName> list;
    }

    public class DomainName {
        public String name; //"http://www.lvmama.com",
        public String content;//"lvmama"
    }
}
