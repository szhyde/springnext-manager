package org.springnext.manager.base.vo;

/**
 * 通过AJAX进行CUD操作时，返回的消息类
 * @author HyDe
 *
 */
public class AjaxMessage implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5452146022606559337L;
	
	/**
	 * 操作成功的消息返回
	 * @param msg
	 * @return
	 */
	public static AjaxMessage createSuccessMsg(String msg){
		return new AjaxMessage(true,msg);
	}
	
	public static AjaxMessage createSuccessMsg(){
		return createSuccessMsg("");
	}
	
	/**
	 * 操作失败的消息返回
	 * @param msg
	 * @return
	 */
	public static AjaxMessage createFailureMsg(String msg){
		return new AjaxMessage(false,msg);
	}
	
	public static AjaxMessage createFailureMsg(){
		return createFailureMsg("");
	}

	public AjaxMessage(Boolean succ, String msg) {
		this.success = succ;
		this.message = msg;
	}

	/**
	 * 操作是否成功
	 */
	private Boolean success;
	
	/**
	 * 回传的消息
	 */
	private String message;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
