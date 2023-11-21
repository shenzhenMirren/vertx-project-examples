<#assign assign_ClassName = content.items.model.className>
package ${content.items.model.packageName};

<#if content.content.imports??>
	<#list content.content.imports as impt>
import ${impt};
	</#list>
</#if>
import io.vertx.core.MultiMap;
/**
 * 数据类对应表${content.content.tableName}
<#if content.content.remark??> * ${content.content.remark}</#if>
 */
<#if content.content.annotations??>
	<#list content.content.annotations as anno>
${anno}
	</#list>
</#if>
public class ${assign_ClassName} {
	<#-- 添加属性 -->
	<#list content.content.fields as item>
	<#if item.fieldRemark??>/** ${item.fieldRemark} */</#if>
	<#if item.annotations??>
		<#list item.annotations as anno>
	${anno}
		</#list>
	</#if>
	private ${item.fieldType} ${item.fieldName};
	</#list>
	<#if content.content.additionalField??>
	<#list content.content.additionalField as item>
	<#if item.fieldRemark??>/** ${item.fieldRemark} */</#if>
	<#if item.annotations??>
		<#list item.annotations as anno>
	${anno}
		</#list>
	</#if>
	private ${item.fieldType} ${item.fieldName};
	</#list>
	</#if>

	/**数据检验是否通过*/
  private boolean modelCheckIsInvalid;

  /**检查不通过的原因*/
  private String modelCheckIsInvalidMessage;

	/**
	 * 实例化
	 */
	public ${assign_ClassName}() {
		super();
	}
	/**
	 * 实例化
	 */
	public ${assign_ClassName}(MultiMap params) {
		super();
		try {
		<#list content.content.fields as item>
		<#if item.fieldType  ==  "Integer" || item.fieldType  ==  "int">
		if(params.get("${item.fieldName}") != null){
			try {
				this.set${item.fieldNamePascal}(Integer.parseInt(params.get("${item.fieldName}").trim()));
			} catch (Exception e) {
			  this.modelCheckIsInvalid=true;
			  this.modelCheckIsInvalidMessage="无法识别参数:${item.fieldName},请检查是否符合要求!";
			}
		}
		<#elseif item.fieldType  ==  "Long" || item.fieldType  ==  "long">
		if(params.get("${item.fieldName}") != null){
			try {
				this.set${item.fieldNamePascal}(Long.parseLong(params.get("${item.fieldName}").trim()));
			} catch (Exception e) {
			  this.modelCheckIsInvalid=true;
			  this.modelCheckIsInvalidMessage="无法识别参数:${item.fieldName},请检查是否符合要求!";
			}
		}
		<#elseif item.fieldType  ==  "Float" || item.fieldType  ==  "float">
		if(params.get("${item.fieldName}") != null){
			try {
				this.set${item.fieldNamePascal}(Float.parseFloat(params.get("${item.fieldName}").trim()));
			} catch (Exception e) {
			  this.modelCheckIsInvalid=true;
			  this.modelCheckIsInvalidMessage="无法识别参数:${item.fieldName},请检查是否符合要求!";
			}
		}
		<#elseif item.fieldType  ==  "Double" || item.fieldType  ==  "double">
		if(params.get("${item.fieldName}") != null){
			try {
				this.set${item.fieldNamePascal}(Double.parseDouble(params.get("${item.fieldName}").trim()));
			} catch (Exception e) {
			  this.modelCheckIsInvalid=true;
			  this.modelCheckIsInvalidMessage="无法识别参数:${item.fieldName},请检查是否符合要求!";
			}
		}
		<#else>
		this.set${item.fieldNamePascal}(params.get("${item.fieldName}"));
		</#if>
		</#list>
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
	<#-- 添加get与set -->
	<#list content.content.fields as item>

	<#if item.fieldRemark??>
	/**
	 * 获取${item.fieldRemark}
	 */
	</#if>
	public ${item.fieldType} <#if item.fieldType == "boolean">is<#else>get</#if>${item.fieldNamePascal}() {
		return ${item.fieldName};
	}

	<#if item.fieldRemark??>
	/**
	 * 设置${item.fieldRemark}
	 * @param ${item.fieldName} 数据
	 */
	</#if>
	public ${assign_ClassName} set${item.fieldNamePascal}(${item.fieldType} ${item.fieldName}) {
		this.${item.fieldName} = ${item.fieldName};
		<#if !item.nullable>
		if(this.${item.fieldName}==null<#if item.fieldType == "String" && (item.length > 0) > || this.${item.fieldName}.length() > ${item.length}</#if>){
      modelCheckIsInvalid=true;
      modelCheckIsInvalidMessage="${item.fieldName}检查不通过!";
		}
		</#if>
		return this;
	}
	</#list>
	<#if content.content.additionalField??>
	<#list content.content.additionalField as item>
	<#if item.fieldRemark??>
	/**
	 * 获取${item.fieldRemark}
	 *
	 * @return
	 */
	</#if>
	public ${item.fieldType} <#if item.fieldType == "boolean">is<#else>get</#if>${item.fieldNamePascal}() {
		return ${item.fieldName};
	}
	<#if item.fieldRemark??>
	/**
	 * 设置${item.fieldRemark}
	 *
	 * @param ${item.fieldName}
	 */
	</#if>
	public ${assign_ClassName} set${item.fieldNamePascal}(${item.fieldType} ${item.fieldName}) {
		this.${item.fieldName} = ${item.fieldName};
		return this;
	}
	</#list>
	</#if>

	@Override
	public String toString() {
		return "${content.items.model.className} [<#list content.content.fields as item>${item.fieldName}=" + ${item.fieldName} + " <#if item?has_next>,</#if> </#list><#if content.content.additionalField??><#list content.content.additionalField as item>, ${item.fieldName}=" + ${item.fieldName} + " <#if item?has_next>,</#if> </#list></#if>]";
	}
}
