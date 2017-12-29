# calcite
sql 解析引擎 探索
## 最大功能通过sql 访问任意结构数据 网上的一个示列

### 几个重要的组件
>> 1 AbstractSchema 通过schema实现从数据源自己的概念（DataBase及Table）向Calcite的概念(MemoryTable)进行转换的过程    
>> 2 AbstractTable  实际上就是对Java 类的封装，将原始数据源中的Column概念(列数据类型等)转换为Calcite中的Column概念(RelDataType)        
>> 3 ScannableTable 调用scan函数构建emurator用于读取数据，使用Enumerator来读取数据。再Enumerator中会实现从原始数据源的数据到实际我们需要的数据之间的转换过程。     
>> 4 Enumerator     数据迭代器，用于将从数据源中查询出来的数据进行迭代取出。      

```java
        生成一些内存数据
        student.data.add(Arrays.asList("fengysh","A000001", "1", "1989-06-10", "anhui"));
        student.data.add(Arrays.asList("wyshz","A000002", "1", "1989-03-04", "henan"));
        student.data.add(Arrays.asList("hesk","A000003", "1", "1992-02-10", "anhui"));
        student.data.add(Arrays.asList("whst","A000004", "2", "1993-04-08", "hebei"));
        student.data.add(Arrays.asList("wush","B000005", "2", "1998-02-26", "beijing"));
        student.data.add(Arrays.asList("ehsn","C000006", "3", "1990-06-18", "sichuan"));
        student.data.add(Arrays.asList("wisyh","D000007", "3", "1991-03-06", "zhejiang"));
        student.data.add(Arrays.asList("helsj","D000008", "4", "1993-09-10", "jiangsu"));

```

### 通过将内存的数据 转换为calcite  datasource, table cloumn

```java
        //通过标准的sql语句访问
        result = st.executeQuery("select S.\"id\", SUM(S.\"classId\") from \"Student\" as S group by S.\"id\"");
        while(result.next()) {
        	System.out.println(result.getString(1) + "\t" + result.getString(2));
        }

```

##### 功能很强大的 包括hive都在使用