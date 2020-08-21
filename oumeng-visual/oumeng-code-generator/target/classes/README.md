1.使用 代码生成器前，先修改config目录下WebConfig配置。改成所在项目路径下
例如：使用代码生成器 生成crm项目下代码则修改：
registry.addResourceHandler("/crm/**").addResourceLocations( "classpath:/static/*");

2.再修改generator.properties文件 
    包名、作者、数据库表前缀等
    

3.启动generator生成器服务
http://localhost:8019/index
