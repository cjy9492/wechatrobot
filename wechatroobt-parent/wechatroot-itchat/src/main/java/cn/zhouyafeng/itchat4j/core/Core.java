package cn.zhouyafeng.itchat4j.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import cn.zhouyafeng.itchat4j.utils.enums.parameters.BaseParaEnum;

/**
 * 核心存储类，全局只保存一份，单例模式
 * 
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月23日 下午2:33:56
 * @version 1.0
 *
 */
public class Core {

	private static Core instance;

	private Core() {

	}

	public static Core getInstance() {
		if (instance == null) {
			synchronized (Core.class) {
				instance = new Core();
			}
		}
		return instance;
	}

	boolean alive = false;
	private int memberCount = 0;

	private String userName;
	private String nickName;
	private List<JSONObject> msgList = new ArrayList<JSONObject>();

	private JSONObject userSelf; // 登陆账号自身信息
	private List<JSONObject> memberList = new ArrayList<JSONObject>(); // 好友+群聊+公众号+特殊账号
	private List<JSONObject> contactList = new ArrayList<JSONObject>();;// 好友
	private List<JSONObject> groupList = new ArrayList<JSONObject>();; // 群
	private List<JSONObject> groupMemeberList = new ArrayList<JSONObject>();; // 群聊成员字典
	private List<JSONObject> publicUsersList = new ArrayList<JSONObject>();;// 公众号／服务号
	private List<JSONObject> specialUsersList = new ArrayList<JSONObject>();;// 特殊账号
	private List<String> groupIdList = new ArrayList<String>();
	private Map<String, JSONObject> userInfoMap = new HashMap<String, JSONObject>();

	Map<String, Object> loginInfo = new HashMap<String, Object>();
	// CloseableHttpClient httpClient = HttpClients.createDefault();
	MyHttpClient myHttpClient = MyHttpClient.getInstance();
	String uuid = null;

	boolean useHotReload = false;
	String hotReloadDir = "itchat.pkl";
	int receivingRetryCount = 5;

	/**
	 * 请求参数
	 */
	public Map<String, Object> getParamMap() {
		return new HashMap<String, Object>(1) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				Map<String, String> map = new HashMap<String, String>();
				for (BaseParaEnum baseRequest : BaseParaEnum.values()) {
					map.put(baseRequest.para(), getLoginInfo().get(baseRequest.value()).toString());
				}
				put("BaseRequest", map);
			}
		};
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public List<JSONObject> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<JSONObject> memberList) {
		this.memberList = memberList;
	}

	public Map<String, Object> getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(Map<String, Object> loginInfo) {
		this.loginInfo = loginInfo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public boolean isUseHotReload() {
		return useHotReload;
	}

	public void setUseHotReload(boolean useHotReload) {
		this.useHotReload = useHotReload;
	}

	public String getHotReloadDir() {
		return hotReloadDir;
	}

	public void setHotReloadDir(String hotReloadDir) {
		this.hotReloadDir = hotReloadDir;
	}

	public int getReceivingRetryCount() {
		return receivingRetryCount;
	}

	public void setReceivingRetryCount(int receivingRetryCount) {
		this.receivingRetryCount = receivingRetryCount;
	}

	public MyHttpClient getMyHttpClient() {
		return myHttpClient;
	}

	public List<JSONObject> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<JSONObject> msgList) {
		this.msgList = msgList;
	}

	public void setMyHttpClient(MyHttpClient myHttpClient) {
		this.myHttpClient = myHttpClient;
	}

	public List<String> getGroupIdList() {
		return groupIdList;
	}

	public void setGroupIdList(List<String> groupIdList) {
		this.groupIdList = groupIdList;
	}

	public List<JSONObject> getContactList() {
		return contactList;
	}

	public void setContactList(List<JSONObject> contactList) {
		this.contactList = contactList;
	}

	public List<JSONObject> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<JSONObject> groupList) {
		this.groupList = groupList;
	}

	public List<JSONObject> getGroupMemeberList() {
		return groupMemeberList;
	}

	public void setGroupMemeberList(List<JSONObject> groupMemeberList) {
		this.groupMemeberList = groupMemeberList;
	}

	public List<JSONObject> getPublicUsersList() {
		return publicUsersList;
	}

	public void setPublicUsersList(List<JSONObject> publicUsersList) {
		this.publicUsersList = publicUsersList;
	}

	public List<JSONObject> getSpecialUsersList() {
		return specialUsersList;
	}

	public void setSpecialUsersList(List<JSONObject> specialUsersList) {
		this.specialUsersList = specialUsersList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public JSONObject getUserSelf() {
		return userSelf;
	}

	public void setUserSelf(JSONObject userSelf) {
		this.userSelf = userSelf;
	}

	public Map<String, JSONObject> getUserInfoMap() {
		return userInfoMap;
	}

	public void setUserInfoMap(Map<String, JSONObject> userInfoMap) {
		this.userInfoMap = userInfoMap;
	}

}
