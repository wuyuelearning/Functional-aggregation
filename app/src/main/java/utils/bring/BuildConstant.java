package utils.bring;

/**
 * Created by ltyangyang on 2016/5/3.
 * modify zzf 20160628 重命名,只包含编译常量
 */
public class BuildConstant {
    private BuildConstant() {
    }

    /**
     * 跟随build-extension.gradle中的lvmmDebug开关，开发可手动更改为true，但是不要提交
     */
    public static boolean DEBUG /*= BuildConfig.DEBUG*/;

    /**
     * 跟随build-extension.gradle中的cmDebug开关，开发可手动更改为false，但是不要提交
     * CM、UM 使用DEBUG统一开关，去除开发阶段冗余统计，测试统计置成false即可
     *
     * 听云SDK线上KEY与测试KEY，默认true使用测试KEY，false使用线上KEY 7.10.5
     *
     * 神策分析SDK 测试与正式环境url和模式 8.2.60
     */
    public static boolean CMDEBUG /*= BuildConfig.CM_DEBUG*/;

    public static String LVMM_BUILD_TIMESTAMP /*= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(new Date(BuildConfig.LVMM_BUILD_TIMESTAMP))*/;

    public static String LVMM_BUILD_COMMIT_ID; /*build git commit id*/

    public static String LVMM_BUILD_USER /*= BuildConfig.LVMM_BUILD_USER*/;

    public static String LVMM_BUILD_ID /*= BuildConfig.LVMM_BUILD_ID*/;

    public static String RUN_MODULE;

    /**debug or release*/
    public static String BUILD_TYPE;
}
