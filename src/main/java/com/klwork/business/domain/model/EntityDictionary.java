package com.klwork.business.domain.model;

public interface EntityDictionary {

	// 新建
	static final String OUTSOURCING_STATUS_NEW = "0";

	// 需求正在审核
	static final String OUTSOURCING_STATUS_PUBLISHING = "1";

	// 需求已经发布
	static final String OUTSOURCING_STATUS_PUBLISHED = "2";

	// 扩展的流程或任务的人员类型
	public static final String IDENTITY_LINK_TYPE_AUDITOR = "auditor";// 审核人

	// 参与者类型
	public static final String PARTICIPANTS_TYPE_USER = "0";// 参与的普通用户

	public static final String PARTICIPANTS_TYPE_SCORER = "1";// 评分者
	
	// 参与任务状态
	public static final String PARTICIPANTS_STATUS_START = "0";// 刚刚参与

	// 参与任务状态
	public static final String PARTICIPANTS_STATUS_UPLOADED = "1";// 已经上传
	
	// 参与任务状态
	public static final String PARTICIPANTS_STATUS_SCORED = "2";// 已经评分

	// 用户组类型
	public static final String TEAM_GROUP_TYPE_COMM = "0";// 用户组普通
	public static final String TEAM_GROUP_TYPE_INVITE = "1";// 正在邀请
	public static final String TEAM_GROUP_TYPE_FORMAL = "2";// 正式成员

	// 静态变量
	public static final String OUTSOURCING_PROJECT_ID = "outsourcingProjectId";// 项目id标志
	public static final String UP_LOADTASK_ID = "upLoadtaskId";// 上传任务标志
	public static final String CLAIM_USER_ID = "claimUserId";
	public static final String CHECKER_USER_ID = "checker";
	public static final String NEED_PRE_CHECKED = "needPreChecked";// 需求审核
	public static final String GRADE_TEAM = "gradeTeam";
}
