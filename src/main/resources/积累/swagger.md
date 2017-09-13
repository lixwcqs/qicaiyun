markdown参考资料：https://github.com/younghz/Markdown

##swagger注解
### 参考资料
- Annotations-1.5.X:https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X
### 常用注解
1. **@ApiImplicitParam**

方法上修饰单个参数 配合 **@ApiImplicitParam** 使用
- name: 和形参名对应
- value: 参数描述
- dataType: 参数类型(如"string","long","integer")
- dataTypeClass: 目前没发现配置之后能起啥作用(不懂)
- paramType: 表示参数的种类,有效种类为path, query, body, header or form.(小写)
  * path 对应类型 /u/{path}
  * query 对应类型 /u?p=1
  * body
  * header
  * form
  * 说明 https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#parameter-object
  