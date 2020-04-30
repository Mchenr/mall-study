## mall项目学习-Mybatis-注解的基本使用

以mall项目中人气推荐功能部分为例，不使用自动生成的mapper，通过使用Mybatis注解方式自定义dao层，实现增删改查，学习Mybatis注解的基本用法以及sql语句的基本用法。

注解的使用就是在接口上直接添加注解。

### @Select的用法

接口

~~~java
/**
* 通过id查询首页人气推荐
* @param id
* @return
*/
@Select({"select id, product_id productId, product_name productName, recommend_status recommendStatus, sort sort " +
    "from sms_home_recommend_product " +
    "where id = #{id}"})
SmsHomeRecommendProduct selectById(Long id);
~~~

这是在注解中定义了字段的映射，如果希望这样写

~~~java
@Select({"select * " +
    "from sms_home_recommend_product " +
    "where id = #{id}"})
SmsHomeRecommendProduct selectById(Long id);
~~~

用*代替查询的对象或者只写数据库字段名，不写对象属性名，也就是说自动映射，和xml一样，有两种方法。一种是在yml配置中使map-underscore-to-camel-case为true。

~~~yml
mybatis:
	configuration:
		map-underscore-to-camel-case: true
~~~

不过推荐另一种，使用resultMap方式。这种方式是直接使用@resultMap注解。

在selectById接口上定义

~~~java
@Results(id = "smsHomeRecommendProductMap", value = {
    @Result(property = "id", column = "id", id = true, jdbcType = BIGINT),
    @Result(property = "productId", column = "product_id", jdbcType = BIGINT),
    @Result(property = "productName", column = "product_name", jdbcType = VARCHAR),
    @Result(property = "recommendStatus", column = "recommend_status", jdbcType = INTEGER),
    @Result(property = "sort", column = "sort", jdbcType = INTEGER)
})
@Select({"select * " +
    "from sms_home_recommend_product " +
    "where id = #{id}"})
SmsHomeRecommendProduct selectById(Long id);
~~~

如果其他接口也要用，就可以通过id值引用，比如selectAll接口中

~~~java
@ResultMap("smsHomeRecommendProductMap")
@Select({"select * " +
    "from sms_home_recommend_product "})
List<SmsHomeRecommendProduct> selectAll();
~~~

### @Insert的用法

接口

~~~java
//不需要返回主键，自定义主键，根据业务这里需要返回自增主键，这一段仅学习使用
/*
@Insert({"insert into sms_home_recommend_product(id, product_id, product_name, recommend_status, sort) " +
    "values(#{id, jdbcType = BIGINT}, #{productId, jdbcType = BIGINT}, " +
    "#{productName, jdbcType = VARCHAR}, #{recommendStatus, jdbcType = INTEGER}, " +
    "#{sort, jdbcType = INTEGER})"})
*/
//返回自增主键
@Insert({"insert into sms_home_recommend_product(id, product_id, product_name, recommend_status, sort) " +
    "values(#{id, jdbcType = BIGINT}, #{productId, jdbcType = BIGINT}, " +
    "#{productName, jdbcType = VARCHAR}, #{recommendStatus, jdbcType = INTEGER}, " +
    "#{sort, jdbcType = INTEGER})"})
@Options(useGeneratedKeys = true, keyProperty = "id")
int insert(SmsHomeRecommendProduct smsHomeRecommendProduct);
~~~

返回自增主键只是加了一个@Options注解，设置了一下useGeneratedKeys和keyProperty。与xml用法类似。

### @Update的用法和@Delete的用法

接口

~~~java
@Update({"update sms_home_recommend_product " +
    "set product_id = #{productId, jdbcType = BIGINT}, " +
    "product_name = #{productName, jdbcType = VARCHAR}, " +
    "recommend_status = #{recommendStatus, jdbcType = INTEGER}, " +
    "sort = #{sort, jdbcType = INTEGER} " +
    "where id = #{id}"})
int updateById(SmsHomeRecommendProduct smsHomeRecommendProduct);

@Delete({"delete from sms_home_recommend_product where id = #{id}"})
int deleteById(Long id);
~~~

需要注意update语句set 字段时，每个字段后都有个逗号，sql语法要清晰。由于有之前xml的基础，这部分还是很简单的，Mybatis还提供了4中Provider注解，@SelectProvider、@InsertProvider、@UpdateProvider、@DeleteProvider，也可以实现增删查改操作。

注解也就用来实现一些简答的sql操作，主流还是xml。注解方式需要手动拼接字符串，没有代码提示，需要编写代码重新编译，不方便维护，一般都不建议使用注解方式。