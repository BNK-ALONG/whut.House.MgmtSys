package com.computerdesign.whutHouseMgmt.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * ͨ�õķ�����,����json
 * 
 * @author Administrator
 *
 */
public class Msg {

	// ״̬�� ����100��ʾ�ɹ���200��ʾʧ��
	private int code;

	private String message;

	// �û�Ҫ���ظ������������
	private Map<String, Object> data = new HashMap<String, Object>();

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	// ����ɹ�
	public static Msg success() {
		Msg result = new Msg();
		result.setCode(100);
		result.setMessage("����ɹ�");
		return result;
	}

	// �Զ���message,����ɹ�
	public static Msg success(String message) {
		Msg result = new Msg();
		result.setCode(100);
		result.setMessage(message);
		return result;
	}

	// ����ʧ��
	public static Msg error() {
		Msg result = new Msg();
		result.setCode(200);
		result.setMessage("����ʧ��");
		return result;
	}

	// �Զ���message,����ʧ��
	public static Msg error(String message) {
		Msg result = new Msg();
		result.setCode(200);
		result.setMessage(message);
		return result;
	}

	// �����Ҫ���ص����ݣ����ڷ�װ
	public Msg add(String key, Object value) {
		this.getData().put(key, value);
		return this;
	}

}
