## mall项目学习-Mybatis-动态sql

这一章是接xml基本使用之后，继续以mall项目中专题推荐功能部分为例，不使用自动生成的mapper，通过编写xml文件映射的方式自定义dao层，实现增删改查，学习Mybatis-动态sql。

### 1. if的用法

if标签通常用于where语句中，通过判断参数值来决定是否使用某个查询条件，也用于update和insert中，通过判断决定是否更新或插入某个字段的值。

#### （1）在where中使用if

目前我的项目有这样一个需求：实现一个专题推荐的高级查询功能，根据输入的条件去检索专题推荐的信息。这个功能需要满足以下三种情况：当只输入专题名称时，需要根据名称进行模糊查询；当只输入推荐状态时，根据推荐状态进行完全匹配；当两者都输入时，筛选这两个条件都匹配的信息。

这时就需要if标签解决问题

~~~xml
<select id="selectBySubjectNameOrRecommendStatus" resultType="com.chenj.mall.mbg.model.SmsHomeRecommendSubject">
    select id,
    subject_id subjectId,
    subject_name subjectName,
    recommend_status recommendStatus,
    sort sort
    from sms_home_recommend_subject
    <where>
        <if test="subjectName != null and subjectName != ''">
            and subject_name like concat('%', #{subjectName}, '%')
        </if>
        <if test="recommendStatus != null">
            and recommend_status = #{recommendStatus}
        </if>
    </where>
</select>
~~~

- where标签是为了防止if语句都不生效会只剩下where导致sql语句不符合规范而报错的情况，有了where标签就会自动拼接sql语句。
- if标签有一个必填属性test，属性值一般写判断表达式，表达式非0值均为true，不过一般推荐以true或false为结果。`subjectName != null`判断属性值是否为空，适用于任何类型的字段；`subjectName != ''`判断是否为空字符串，只适用于字符串。多个条件用and或or连接。
- `concat('%', #{subjectName}, '%')`：模糊查询需要拼接字符串，因为需要经过dao层判断，不方便在service层拼接，所以在dao层拼接，concat()是sql中拼接字符串的函数。

#### （2）在update中使用if

原来的接口实现更新操作时，我们发现如果新数据某一项为空也会更新到数据库中，一般业务中的需求都是更新那些不为空的属性，也就是选择性更新，这时候if标签会帮我们完成这项需求。

~~~xml
<update id="updateByIdSelective">
    update sms_home_recommend_subject
    set
    <if test="subjectId != null">
        subject_id = #{subjectId, jdbcType = BIGINT},
    </if>
    <if test="subjectName != null and subjectName != ''">
        subject_name = #{subjectName, jdbcType = VARCHAR},
    </if>
    <if test="recommendStatus != null">
        recommend_status = #{recommendStatus, jdbcType = INTEGER},
    </if>
    <if test="sort != null">
        sort = #{sort, jdbcType = INTEGER},
    </if>
    id = #{id, jdbcType = BIGINT}
    where id = #{id}
</update>
~~~

这里要注意两点：一个是每个if元素里面sql语句后面的逗号；一个是where前面的`id = #{id, jdbcType = BIGINT}`语句。最终的目的就是使最后生成的sql语句的正确性。

#### （3）在insert中使用if

当插入一条数据时，如果希望某一个属性值为空的时候使用数据库中的默认值（一般为空），而不是传入空值，就需要使用if。

这里就需要配合业务需求来做了，比如我希望用户没有指定专题的排序大小，那么就默认为0（不设置的话是null），首先需要在数据库中设置sort字段的默认值，这个结合navicat很容易实现。然后编写选择性插入接口。

~~~xml
<insert id="insertSelective" useGeneratedKeys="true" keyProperty="id">
    insert into sms_home_recommend_subject(subject_id, subject_name, recommend_status
    <if test="sort != null">
        ,sort
    </if>
    )
    values (#{subjectId, jdbcType = BIGINT}, #{subjectName, jdbcType = VARCHAR}, #{recommendStatus, jdbcType = INTEGER}
    <if test="sort != null">
        ,#{sort, jdbcType = INTEGER}
    </if>
    )
</insert>
~~~

这边也是要注意最后生成的sql语句要正确，因为sort是最后一个字段，所以逗号要放在if里面。使用时在列的部分添加if条件，在values部分也要对应添加。

### 2. choose的用法

choose用法本质是if-else、if-else的实现。有时候只有if不能实现我们期望的功能，比如在设计专题推荐高级查询功能时，两者都不输入就会把所有的结果都查询出来，然而有时候我们希望如果用户输入的内容都为空时，就返回一个空的结果。这时就需要choose来实现，目前设计了这样一个需求：需要通过专题Id或专题名称来查询，专题id为空就用专题名称来查询，专题名称为空就用专题Id来查询，都为空则使sql查询无结果。

~~~xml
<select id="selectBySubjectIdOrSubjectName" resultMap="smsHomeRecommendSubjectMap">
    select * from sms_home_recommend_subject
    <where>
        <choose>
            <when test="subjectId != null">
                and subject_id = #{subjectId}
            </when>
            <when test="subjectName != null and subjectName != ''">
                and subject_name = #{subjectName}
            </when>
            <otherwise>
                and 1=2
            </otherwise>
        </choose>
    </where>
</select>
~~~

因为接口的返回值是`SmsHomeRecommendSubject`，如果没有最后otherwise里的内容，两者都为空的时候就会把所有对象全都查出来，所有查询为多个结果时就会报错。

### 3. trim的用法

这部分比较简单，也没有具体功能实现，就把使用方式记录下来。

前面用过where标签的功能就可以用trim来实现，而且底层就是通过TrimSqlNode实现的。set标签也是，不过上面没有使用，因为set标签并没有达到像where标签那样精简美化代码的作用，不过使用也比不使用要便于维护。

where标签对应的trim实现如下

~~~xml
<trim prefix="WHERE" prefixOverrides="AND | OR ">
    ...
</trim>
~~~

这里AND和OR后面的空格不能省略，为了避免匹配到andes、orsers等单词。

set标签对应的trim实现如下

~~~xml
<trim prefix="SET" suffixOverrides=",">
    ...
</trim>
~~~

trim标签有如下属性：

- prefix：当trim元素内包含内容时，会给内容增加prefix指定的前缀。
- prefixOverrides：当trim元素内包含内容时，会把内容中匹配的前缀字符串去掉。
- suffix：当trim元素内包含内容时，会给内容增加prefix指定的后缀。
- suffixOverrides：当trim元素内包含内容时，会把内容中匹配的后缀字符串去掉。

### 4. foreach的用法

#### （1）foreach实现in集合

主要完成了批量更新和批量删除操作。

批量更新：

接口

~~~java
int updateRecommendStatus(@Param("ids") List<Long> ids,
                          @Param("recommendStatus") Integer recommendStatus);
~~~

xml

~~~xml
<update id="updateRecommendStatus">
    update sms_home_recommend_subject
    set  recommend_status = #{recommendStatus, jdbcType = INTEGER}
    where id in
    <foreach collection="ids" open="(" close=")" separator="," item="id" index="i">
        #{id}
    </foreach>
</update>
~~~

批量删除与之类似：

接口

~~~java
int deleteByIds(List<Long> ids);
~~~

xml

~~~xml
<delete id="deleteByIds">
    delete from sms_home_recommend_subject
    where id in
    <foreach collection="list" open="(" close=")" separator="," item="id" index="i">
        #{id}
    </foreach>
</delete>
~~~

collection的属性值在单个参数时可以直接使用list，如果多个参数就使用接口中@Param定义的属性名。

到这，这个专题推荐部分功能基本做完了，就差一个分页查询，这是用Mybatis分页插件完成的，和学习内容关系就不大了，后面还有一点点内容，就只作为学习参考，不再实现具体功能。

#### （2）foreach实现批量插入



### 附.分页的实现

Mybatis通过分页插件PageHelper实现分页，使用需要导入相关依赖。这里只记录代码实现。

核心在service层：

~~~java
@Override
public List<SmsHomeRecommendSubject> list(Integer pageNum, Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    return homeRecommendSubjectDao.selectAll();
}
~~~

`PageHelper.startPage(pageNum, pageSize);`语句会将之后的第一个select语句进行分页。一般在dao层查询时定义其排序

~~~xml
<select id="selectAll" resultType="com.chenj.mall.mbg.model.SmsHomeRecommendSubject">
    select id,
    subject_id subjectId,
    subject_name subjectName,
    recommend_status recommendStatus,
    sort sort
    from sms_home_recommend_subject
    order by sort desc
</select>
~~~

`order by sort desc`指结果按sort字段倒序排列。

在controller层：

~~~java
@ApiOperation("分页查询首页专题推荐列表")
@ResponseBody
@RequestMapping(value = "/list", method = RequestMethod.GET)
public CommonResult list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
    List<SmsHomeRecommendSubject> homeRecommendSubjects = homeRecommendSubjectService.list(pageNum, pageSize);
    return CommonResult.success(CommonPage.restPage(homeRecommendSubjects));
}
~~~

需要返回分页对象。

