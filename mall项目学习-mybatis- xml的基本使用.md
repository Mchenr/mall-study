## mall项目学习-Mybatis-xml基本用法

以mall项目中专题推荐功能部分为例，不使用自动生成的mapper，通过编写xml文件映射的方式自定义dao层，实现增删改查，学习Mybatis-xml的基本用法以及sql语句的基本用法。

### 1. springboot集成Mybatis

这里的集成指不使用代码生成器，只实现最基本的使用。

MyBatis官方为了方便Springboot集成Mybatis，专门提供了一个符合Springboot规范的starter项目`mybatis-spring-boot-starter`，只需添加Maven依赖即可，不需要进行额外的配置就集成好了。

Maven

~~~xml
<!--Mybatis-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.1</version>
</dependency>
~~~

为了方便使用，这里可以进行简单配置。建立MybatisConfig配置类

~~~java
/**
 * Mybatis配置类
 * createTime:2020年3月7日20:56:29
 */

@Configuration
@MapperScan({"com.chenj.mall.mbg.mapper", "com.chenj.mall.dao"})
public class MybatisConfig {
}
~~~

这个配置类的作用是自动扫描`"com.chenj.mall.mbg.mapper", "com.chenj.mall.dao"`两个包下的mapper接口，就不用再每个mapper接口添加@Mapper注解了。

在application.yml文件中添加

~~~yml
mybatis:
  mapper-locations:
    - classpath:dao/*.xml
~~~

这里配置了xml映射文件的路径。

### 2. xml方式的基本使用

项目中model是已经生成好的了，在dao层添加其mapper接口SmsHomeRecommendSubjectDao。然后在resources/dao下建立xml文件。目前两者都是空的，先打开xml文件，并添加这些内容

xml文件

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.chenj.mall.dao.SmsHomeRecommendSubjectDao">

</mapper>
~~~

namespace属性需要配置成接口的全限定名称，如`"com.chenj.mall.dao.SmsHomeRecommendSubjectDao"`，Mybatis内部就是通过这个属性将xml和接口关联起来的。

#### select的使用

先在SmsHomeRecommendSubjectDao接口中添加一个selectById方法

~~~java
package com.chenj.mall.dao;

import com.chenj.mall.mbg.model.SmsHomeRecommendSubject;

public interface SmsHomeRecommendSubjectDao {

    /**
     * 通过id查询首页标题推荐
     * @param id
     * @return
     */
    SmsHomeRecommendSubject selectById(Long id);
}
~~~

然后在SmsHomeRecommendSubjectDao.xml文件中添加如下代码

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.chenj.mall.dao.SmsHomeRecommendSubjectDao">
    <resultMap id="smsHomeRecommendSubjectMap" type="com.chenj.mall.mbg.model.SmsHomeRecommendSubject">
        <id property="id" jdbcType="BIGINT" column="id"/>
        <result property="subjectId" jdbcType="BIGINT" column="subject_id"/>
        <result property="subjectName" jdbcType="VARCHAR" column="subject_name"/>
        <result property="recommendStatus" jdbcType="INTEGER" column="recommend_status"/>
        <result property="sort" jdbcType="INTEGER" column="sort"/>
    </resultMap>

    <select id="selectById" resultMap="smsHomeRecommendSubjectMap">
        select * from sms_home_recommend_subject where id = #{id}
    </select>
</mapper>
~~~

`<select>` ：映射查询语句使用的标签。

- id值对应着接口的方法名；
- resultMap用于设置返回值类型和映射关系；
- 标签内`select * from sms_home_recommend_subject where id = #{id}`是查询语句。

`<resultMap>`：是一种很重要的配置结果映射的方法，一般填写id和type两个属性，而且属于必填属性。type用于配置查询列所映射的java对象类型。resultMap包含一些标签，这里主要用到了<id\>和<result\>，这两个标签包含的属性相同，不同的地方在于id代表的是主键的字段，他们的属性值是由setter方法注入的。

id和result标签包含的属性：

- column：数据库中的列名；
- property：一般为javaBean的类属性名；
- jdbcType：列对应的数据库类型。JDBC类型仅仅需要对插入、更新、删除操作可能为空的列进行处理，这是JDBC jdbcType的需要，而不是Mybatis的需要。一般来说最好都加上。

接口返回值类型由resultMap中的type决定，而不是由接口定义的返回值类型决定。

再加入一个selectAll方法，通过resultType直接配置返回结果类型。

接口

~~~java
/**
     * 查询所有首页标题推荐
     * @return
     */
List<SmsHomeRecommendSubject> selectAll();
~~~

xml

~~~xml
<select id="selectAll" resultType="com.chenj.mall.mbg.model.SmsHomeRecommendSubject">
    select id,
    subject_id subjectId,
    subject_name subjectName,
    recommend_status recommendStatus,
    sort sort
    from sms_home_recommend_subject
</select>
~~~

使用resultType直接配置返回结果类型时，需要在sql语句中为所有列名与属性名不一致的列设置别名，通过设置别名使最终的查询列与resultType指定的对象属性名保持一致，实现自动映射。

Mybatis可以配置一个全局属性mapUnderscoreTocamelCase，默认为false，通过配置为true，可以自动将下划线命名的数据库列映射为驼峰命名的java对象属性名。

测试过程就不记录了。

**这是最简单的select语句，多表联查暂时跳过**。

#### insert的使用

先记录最简单的insert的使用

接口

~~~java
/**
     * 新增首页标题推荐
     * @param smsHomeRecommendSubject
     * @return
     */
int insert(SmsHomeRecommendSubject smsHomeRecommendSubject);
~~~

xml

~~~xml
<insert id="insert">
    insert into sms_home_recommend_subject(id, subject_id, subject_name, recommend_status, sort)
    values (#{id, jdbcType = BIGINT}, #{subjectId, jdbcType = BIGINT}, #{subjectName, jdbcType = VARCHAR}, #{recommendStatus, jdbcType = INTEGER}, #{sort, jdbcType = INTEGER})
</insert>
~~~

一般建议指定jdbcType，例如createTime指定TIMESTAMP类型。由于数据库区分date、time、datetime类型，但是java中一般都使用java.util.Date类型，为了数据类型正确，需要手动指定日期类型。date、time、datetime类型对应的JDBC类型分别为DATE、TIME、TIMESTAMP。

xml也可以这样写

~~~java
<insert id="insert" useGeneratedKeys="true" keyProperty="id">
    insert into sms_home_recommend_subject(subject_id, subject_name, recommend_status, sort)
    values (#{subjectId, jdbcType = BIGINT}, #{subjectName, jdbcType = VARCHAR}, #{recommendStatus, jdbcType = INTEGER}, #{sort, jdbcType = INTEGER})
    </insert>
~~~

主要的变化是配置了useGeneratedKeys和keyProperty两个属性，useGeneratedKeys设置为true后，MyBatis会使用JDBC的getGenerateKeys方法来取出由数据库内部生成的逐渐，获得主键值后将其赋给keyProperty配置的id属性。由于使用数据库返回的主键值，所以sql上下两部分的列去掉了id列和对应的#{id}属性。

测试的时候发现，这种方式的结果和第一种方式不填写id值的结果一样，如果第一种方式指定id值，则id列为指定的id值。说明第一种方式如果不填写id，会默认使用主键值。而且主键值不会因为指定了id就变为指定值，主键值仍为之前自增之后的值，指定id值生成的数据，主键不会自增。

总结：

- 第一种方式，指定id值，主键不自增，数据库id列为指定值；不指定id值，使用主键值，主键自增。

- 第二种方式，使用主键生成的id。

#### update的使用

接口

~~~java
int updateById(SmsHomeRecommendSubject smsHomeRecommendSubject);
~~~

xml

~~~xml
<update id="updateById">
    update sms_home_recommend_subject
    set subject_id = #{subjectId, jdbcType = BIGINT},
    subject_name = #{subjectName, jdbcType = VARCHAR},
    recommend_status = #{recommendStatus, jdbcType = INTEGER},
    sort = #{sort, jdbcType = INTEGER}
    where id = #{id, jdbcType = BIGINT}
</update>
~~~

当输入字段内容为null的时候也会被更新到数据库，这种方式是全覆盖式的，不能选择性的将不为空的数据更新，为空的数据保留原来的。接口调用成功返回值为1。

#### delete的使用

接口

~~~java
int deleteById(Long id);
~~~



xml

~~~xml
<delete id="deleteById">
    delete from sms_home_recommend_subject where id = #{id}
</delete>
~~~

比较简单，接口调用成功返回值为1。

#### 多个接口参数的用法

多个接口参数时一般使用@Param注解。

接口

~~~java
List<SmsHomeRecommendSubject> selectBySubjectNameOrRecommendStatus(@Param("subjectName") String subjectName,
                                                                   @Param("recommendStatus") Integer recommendStatus);
~~~

xml

~~~xml
<select id="selectBySubjectNameOrRecommendStatus" resultType="com.chenj.mall.mbg.model.SmsHomeRecommendSubject">
    select id,
    subject_id subjectId,
    subject_name subjectName,
    recommend_status recommendStatus,
    sort sort
    from sms_home_recommend_subject where subject_name like #{subjectName} or recommend_status = #{recommendStatus}
</select>
~~~

这个xml中还未实现所需功能，后续还需学习多条件查询以及多表联查。此外，like查询需要在service实现层自行拼接好query查询语句：`"%" + "要查询的字符串" + "%"`。

xml的基本用法基本是这些，下一部分是注解的基本用法。