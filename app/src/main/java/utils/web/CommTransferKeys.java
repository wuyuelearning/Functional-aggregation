package utils.web;

/**
 * <p>Author:MrIcefox
 * <p>Email:extremetsa@gmail.com
 * <p>Description: 各bu之间页面跳转 bundle\intent key
 * <p>Date:2017/9/1
 */

public class CommTransferKeys {
    public static final String MAP_PRICE = "price";
    public static final String MAP_NAME = "name";
    public static final String MAP_ADRESS = "adress";
    public static final String MAP_ID = "id";
    public static final String HOTEL_WINDAGE = "hotel_windage";// v7.0酒店搜索半径
    public static final String TICKET_WINDAGE = "ticket_windage";// v7.0景点搜索半径
    public static final String HOLIDAY_WINDAGE = "holiday_windage";// v7.0线路搜索半径
    public static final String TAG_HOLIDAY_LIST = "holidayList";
    public static final String FROM_HOLIDAY_DETAIL = "from_holiday_detail";
    public static final String FROM_DETAIL_TYPEFLAG = "packageTypeFlag";
    public static final String TRANSFER_BUNDLE = "bundle";
    public static final String TRANSFER_ALLOW_UNLOGIN = "allowUnLogin";
    public static final String TRANSFER_FROM = "from";
    public static final String TRANSFER_LEGOTITLE = "lego";
    public static final String TRANSFER_LEGOSOTID = "searchOfTemplate";
    public static final String TRAVER_NAME = "TRAVER_NAME";
    public static final String TRANSFER_COMEFROM = "comefrom";
    public static final String TRANSFER_SUBCHANNEL = "subChannel";
    public static final String TRANSFER_CATEGORY_COMB_HOTEL = "category_route_hotelcomb";
    public static final String TRANSFER_BASE_ADULT_QUANTITY = "baseAdultQuantity";
    public static final String TRANSFER_BASE_CHILD_QUANTITY = "baseChildQuantity";
    public static final String TRANSFER_CLIENT_QUANTITY = "clientQuantity";
    public static final String TRANSFER_CLIENT_QUANTITY_SPECIAL = "clientQuantityFromSpecial";
    public static final String TRANSFER_FORUSECOUPON = "parameterForUseCoupon";
    public static final String TRANSFER_SAVE_MONEY = "saveMoney";  //  仅优惠券 优惠的金额
    public static final String TRANSFER_SAVE_MONEY_ALL = "saveMoneyAll";  //  优惠券加无敌券共同优惠的金额
    public static final String TRANSFER_TOTLA_MONEY = "totalMoney";
    public static final String TRANSFER_OPERATE_COUPON = "operateCoupon";
    public static final String TRANSFER_ORDERID = "orderId";
    public static final String TRANSFER_POIID = "poiId";
    public static final String TRANSFER_GUARANTEE = "guarantee";
    public static final String TRANSFER_ORDER_BIZTYPE = "bizType";
    public static final String NEED_REFRESH = "need_refresh";
    public static final String TRANSFER_ORDER_SUM = "sum";
    public final static String TRANSFER_ORDER_TRAVERID = "travelId";
    public static final String TRANSFER_ORDER_SELECTED_LIST = "selectedList";
    public static final String TRANSFER_DEST_CITY = "destCity";
    public static final String TRANSFER_CHOSE_TO_SHOW_ENG_NAME = "transfer_chose_to_show_eng_name";
    public static final String DOMAIN_NAME_MODEL = "domain_name_model";
    public static final String SENSOR_MAP = "sensorMap";
    //762 游玩人后置
    public static final String TRANSFER_PLAYMANS = "playmanmodel";
    public static final String TRANSFER_ORDERMAINID = "orderMainId";
    public static final String TRANSFER_QUERYTYPE = "queryType";
    public static final String TRANSFER_REGISTER_FROM = "registerFrom";
    public static final String INVITATION_CODE = "invitationCode";
    public static final String SHIP_COMPANY_PRODUCTID = "shipProductId";
    public static final String SHIP_HISTORY_DATA = "historydata";
    public static final String TRANSFER_GOODSID = "goodsId";
    public static final String TRANSFER_COMBPRODUCTID = "combProductId";
    public static final String TRANSFER_BRANCHID = "branchId";
    // v7.0门票详情跳转到线路
    // categoryId(15：跟团游，16：当地游，17：酒店套餐，18:自由行)
    public static final String TRANSFER_CATEGORYID = "categoryId";
    public static final String TRANSFER_SUB_CATEGORYID = "subCategoryId";
    public static final String TRANSFER_BU = "bu";// 7.6.0 各bu模块
    public static final String TRANSFER_BUNAME = "buName";// 7.6.0 各bu模块中文
    // 选择邮寄人地址(v7.0)
    public static final String GET_ADDRESS_INFO = "getAddressInfo";// 如果为true，则表明是要选择邮寄人地址
    public static final String GET_TRAVEL_INFO = "getTraverInfo";//   如果为true，则表明是要选择邮寄人地址
    public static final String TRANSFRE_ADDRESS_ITEM = "addressItem";// 选择完毕后回填用到
    // banner
    public static final String TRANSFER_TITLE = "titleName";
    // 点评
    public static final String TRANSFER_DEST_ID = "dest_id";
    public static final String TRANSFER_COMMENT_LATITUDE = "commentLatitude";
    public static final String TRANSFER_COMMENT_MODEL = "commentModel";
    public static final String TRANSFER_COMMENT_SCORE = "commentScore";// 分数
    public static final String TRANSFER_CATEGORY_CODE = "categoryCode";
    public static final String TRANSFER_COMMENT_TYPE = "commentType";
    public static final String TRANSFER_PLAY_TIME = "playTime";
    public static final String TRANSFER_INDEX = "selected_index";
    public static final String TRANSFER_LIST = "data_list";
    // 优惠券
    public static final String TRANSFER_USED_COUPON_ID = "usedCouponId";
    public static final String TRANSFER_IS_EDIT_COUPON = "isEditCoupon";
    public static final String TRANSFER_VISIT_TIME = "visitTime";
    public static final String TRANSFER_INVINCIBLE_COUPON_IDS = "invincibleCouponIds";  //  8.1.3  无敌券
    /**
     * extra key 目的地选择城市需要result
     */
    public static final String EXTRA_KEY_NEED_RESULT = "need_result";
    /**
     * extra key 目的地城市选择只显示国内城市
     */
    public static final String EXTRA_KEY_DOMESTIC_ONLY = "domestic_only";
    public static final String TRANSFER_KEYWORD = "keyword";
    public static final String TRANSFER_HIDE_TOP = "hiddenTop";
    public static final String TRANSFER_HIDE_TAB = "hiddenTab";
    public static final String TICKET_CHECK_ORDER_ENTITY = "checkOrderEntity";
    public static final String TRANSFER_REQUEST_PARAMS = "request_params";
    public static final String TRANSFER_PRODUCTID = "productId";
    public static final String TRANSFER_PRODUCT_DESTID = "productDestId";
    public static final String TRANSFER_SUPPGOODID = "suppGoodsId";
    public static final String TRANSFER_PRODUCTTYPE = "productType";
    public static final String TRANSFER_PRODUCTNAME_LOW = "productName";
    public static final String TRANSFER_PRODUCTURL = "productURL";
    public static final String TRANSFER_SHARE_IMAGE_URL = "shareImage_url";
    public static final String TRANSFER_BRANCH_TYPE = "branchType";
    public static final String TRANSFER_SEEK_GROUP_ID = "groupId";
    /**
     * 订单 *
     */
    public static final String TRANSFER_SUM_MONEY = "sum_money";
    /* --------------------hotel------------------------ */
    public static final String TRANSFER_HOTEL_CITY_ID = "cityId";
    public static final String TRANSFER_HOTEL_CITY_NAME = "cityName";
    public static final String TRANSFER_HOTEL_DISTANCE = "distance";
    public static final String TRANSFER_HINT_INFO = "hint_info";
    public static final String TRANSFER_HOTEL_FROM_YUYIN = "from_yuyin";
    public static final String TRANSFER_HOTEL_LIVE_IN = "liveIn";
    public static final String TRANSFER_HOTEL_LIVE_OUT = "liveOut";
    public static final String TRANSFER_HOTEL_ID = "hotelId";
    public static final String TRANSFER_ROOMTYPE_ID = "roomTypeId";
    public static final String TRANSFER_RATEPLAN_ID = "ratePlanId";
    public static final String TRANSFER_HOTEL_DETAIL_MODEL = "hotel_detail_model";
    public static final String TRANSFER_HOTEL_NAME = "hotel_name";
    public static final String TRANSFER_HOTEL_STAR = "hotelStar";
    public static final String TRANSFER_HOTEL_PRICE = "price";
    public static final String TRANSFER_HOTEL_CARDGUARANTEE = "hotelcardguarantee";
    public static final String FROM_OUTSIDE_TICKET = "outSideTicket";// 是否境外
    public static final String ROUTE_TYPE = "routeType";
    // TRANSFER_ORDER_QUERYTYPE对应的有根据[状态]：WAIT_APPROVE, WAIT_COMMENT, WAIT_PAY,
    // WAIT_PERFORM, BE_COMMENT
    // 或者根据[类型]：TICKET, ROUTE, HOTEL等等
    public static final String TRAN_ORDER_QUERY_TYPE = "orderQueryType";
    public static final String TRAN_ACTIONBAR_TITLE = "actionbar_title";
    public static final String GOORDERDETAILTYPE = "order_catecode";
    /**
     * ************************** 收藏 *****************************
     */
    public static final String SHOULD_REFRESH = "shouldRefresh";// 收藏前后未登录到登陆时刷新使用。
    public static final String FAVORITE_TYPE = "favorite_type";
    public static final String FAVORITE_HOLIDAY = "holiday";
    public static final String FAVORITE_SHIP = "ship";
    public static final String FAVORITE_HOTEL = "hotel";
    /**
     * ************************** 其他 *********************************
     */
    public static final String SHOW_LEFT_BAR = "show_left_bar";
    public static final String MY_LOCATION = "myLocation";// 地图是否移动到自己的位置还是移动到所传点的中心点位置
    /**
     * ************************** 首页搜索 *****************************
     */
    public static final String AUTO_SEARCH_TYPE = "auto_search_type";
    public static final String WORD_BELONG = "word_belong";
    //771 PushUtil
    public static final String PUSHTAILCODE = "tailCode";
    public static final String GOTO_LOGIN = "goto_login";
    public static final String FATHER_CODE = "FatherCategoryCode";//7.10.4
    public static final String TRAFFIC_H5URL = "Traffic_H5Url";//7.10.4
    public static final String HOME_SEARCH = "homeSearch";
    public static final String TRANSFER_SUBJECTID = "subjectId";
    public static final String IS_LOSC = "is_losc";
    public static final String PAY_PRODUCT_ID = "pay_product_id";
    public static final String PAY_GOODS_ID = "pay_goods_id";
    public static final String PAY_QUANTITIES = "pay_quantitles";
    public static final String PAY_CATEGORY_ID = "pay_category_id";
    public static final String PAY_BOCCREDIT_TYPE = "pay_boccredit_type";

    private CommTransferKeys() {
    }

    public static final String COMMON_TITLE = "title";
    public static final String COMMON_SIZE = "pageSize";
    public static final String COMMON_DATE = "date";

    public static final String MAP_LAT = "lat";
    public static final String MAP_LON = "lon";
    public static final String MAP_GOOGLE_LAT = "google_lat";
    public static final String MAP_GOOGLE_LON = "google_lon";
    public static final String MAP_URL = "url";
    public static final String FIRST_COME = "first_come";

    //先游后付 进入分期付款页面时 选择立即支付选项 进入老收银台请求接口入参 v8.1.9
    public static final String USECTSPAY = "usedCtsPay";
}
