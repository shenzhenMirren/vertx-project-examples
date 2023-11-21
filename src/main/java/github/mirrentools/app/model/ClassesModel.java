package github.mirrentools.app.model;

import io.vertx.core.MultiMap;
/**
 * 数据类对应表classes
 *
 */
public class ClassesModel {
	/** 班级的id */
	private Integer id;
	/** 班级的名称 */
	private String classRooms;

	/**数据检验是否通过*/
  private boolean modelCheckIsInvalid;

  /**检查不通过的原因*/
  private String modelCheckIsInvalidMessage;

	/**
	 * 实例化
	 */
	public ClassesModel() {
		super();
	}
	/**
	 * 实例化
	 */
	public ClassesModel(MultiMap params) {
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
		this.setClassRooms(params.get("classRooms"));
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
	 * 获取班级的id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置班级的id
	 * @param id 数据
	 */
	public ClassesModel setId(Integer id) {
		this.id = id;
		if(this.id==null){
      modelCheckIsInvalid=true;
      modelCheckIsInvalidMessage="id检查不通过!";
		}
		return this;
	}

	/**
	 * 获取班级的名称
	 */
	public String getClassRooms() {
		return classRooms;
	}

	/**
	 * 设置班级的名称
	 * @param classRooms 数据
	 */
	public ClassesModel setClassRooms(String classRooms) {
		this.classRooms = classRooms;
		return this;
	}

	@Override
	public String toString() {
		return "ClassesModel [id=" + id + " , classRooms=" + classRooms + "  ]";
	}
}
