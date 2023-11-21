package github.mirrentools.app.model;

import io.vertx.core.MultiMap;
/**
 * 数据类对应表student
 * 
 */
public class StudentModel {
	/** 学生的id */
	private Integer id;
	/** 班级id */
	private Integer cid;
	/** 昵称 */
	private String nickname;
	/** 年龄 */
	private Integer age;

	/**数据检验是否通过*/
  private boolean modelCheckIsInvalid;

  /**检查不通过的原因*/
  private String modelCheckIsInvalidMessage;

	/**
	 * 实例化
	 */
	public StudentModel() {
		super();
	}
	/**
	 * 实例化
	 */
	public StudentModel(MultiMap params) {
		super();
		try {
		if(params.get("id") != null){
			try {
				this.setId(Integer.parseInt(params.get("id").trim()));
			} catch (Exception e) {
			  this.modelCheckIsInvalid=true;
			  this.modelCheckIsInvalidMessage="无法识别参数:id,请检查是否符合要求!";
			}
		}
		if(params.get("cid") != null){
			try {
				this.setCid(Integer.parseInt(params.get("cid").trim()));
			} catch (Exception e) {
			  this.modelCheckIsInvalid=true;
			  this.modelCheckIsInvalidMessage="无法识别参数:cid,请检查是否符合要求!";
			}
		}
		this.setNickname(params.get("nickname"));
		if(params.get("age") != null){
			try {
				this.setAge(Integer.parseInt(params.get("age").trim()));
			} catch (Exception e) {
			  this.modelCheckIsInvalid=true;
			  this.modelCheckIsInvalidMessage="无法识别参数:age,请检查是否符合要求!";
			}
		}
		} catch (Exception e) {
			this.modelCheckIsInvalid=true;
			this.modelCheckIsInvalidMessage=e.toString();
		}
	}

	/**
	 * 检查数据是否不符合要求,如果true代表数据不符合要求,false代表符合要求
	 */
  public boolean checkIsInvalid(){
    return modelCheckIsInvalid;
  }

	/**
	 * 检查数据不符合要求的原因,如果检查符合则Message为null
	 */
  public String checkIsInvalidMessage(){
    return modelCheckIsInvalidMessage;
  }

	/**
	 * 获取学生的id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置学生的id
	 * @param id 数据
	 */
	public StudentModel setId(Integer id) {
		this.id = id;
		if(this.id==null){
      modelCheckIsInvalid=true;
      modelCheckIsInvalidMessage="id检查不通过!";
		}
		return this;
	}

	/**
	 * 获取班级id
	 */
	public Integer getCid() {
		return cid;
	}

	/**
	 * 设置班级id
	 * @param cid 数据
	 */
	public StudentModel setCid(Integer cid) {
		this.cid = cid;
		return this;
	}

	/**
	 * 获取昵称
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * 设置昵称
	 * @param nickname 数据
	 */
	public StudentModel setNickname(String nickname) {
		this.nickname = nickname;
		return this;
	}

	/**
	 * 获取年龄
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * 设置年龄
	 * @param age 数据
	 */
	public StudentModel setAge(Integer age) {
		this.age = age;
		return this;
	}

	@Override
	public String toString() {
		return "StudentModel [id=" + id + " , cid=" + cid + " , nickname=" + nickname + " , age=" + age + "  ]";
	}
}
