package bean;

import java.util.List;

/**
 * Created by wuyue on 2018/6/4.
 */

public class InvincibleCouponModel extends BaseModel {

    public Data data;
    public static class  Data {
        public String otherParames;   //  接收其他参数
        List<InvincibleCouponVo> invincibleCouponVosList ;  //  把这个参数传入
        // 在UseInvincibleCouponFragment 中 通过 invincibleCouponVosList 对 couponVoList初始化 得到数据
    }
}

