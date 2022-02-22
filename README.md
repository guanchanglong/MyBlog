# 个人博客系统
## 博客线上地址
欢迎大家访问我的博客！[https://www.xiaoguantongxue.com/](https://www.xiaoguantongxue.com/)
## 项目介绍
实现了一个简单的个人博客系统，原来是跟着B站的一个教程（[开发个小而美的博客](https://www.bilibili.com/video/BV1Pt4y1U7hv?spm_id_from=333.999.0.0)）敲的。这段时间将原来的用的 JPA 替换成了 MyBatis，并且重新构建了一遍数据表；去除了所有的外键依赖，改成由程序逻辑实现原来的功能。

技术栈为
后端：SpringBoot + MyBatis + MySQL + Redis(正在构思中)
前端：Semantic UI + Echarts
系统分为前台展示和后台管理两个部分，前台部分主要分为了首页，分类，标签，归档，留言，流量统计和关于我页面，使用响应式布局，能够自适应移动端（部分...），后台有首页，博客管理，撰写博客，分类管理，标签管理。

## 项目截图
首页：总览
![在这里插入图片描述](https://img-blog.csdnimg.cn/9ccdc9e287794c6c9e19982fc97bc5ee.png)
分类：按类型来对文章进行分类
![在这里插入图片描述](https://img-blog.csdnimg.cn/18be2f718a484f0abdd945b2459f5097.png)
标签：按标签来对文章进行分类
![在这里插入图片描述](https://img-blog.csdnimg.cn/00062d7152204565a0629785d54af0f0.png)
归档：按年份对已经发布的文章进行分类
![在这里插入图片描述](https://img-blog.csdnimg.cn/90a6ec7f8729488c818673b31ce60230.png)
留言：访客留言
![在这里插入图片描述](https://img-blog.csdnimg.cn/ba5cf79951c64629a419aa1e35a4097e.png)
流量统计：Echarts展示最近7天内的访问人数统计
![在这里插入图片描述](https://img-blog.csdnimg.cn/22d7c58af7e848c8be33db0a357221e5.png)
关于我：展示我的一些个人资料
![在这里插入图片描述](https://img-blog.csdnimg.cn/5fd5fbd56f364877a09f1df620e85135.png)
后台首页
![在这里插入图片描述](https://img-blog.csdnimg.cn/3c4efe54690042f18e358ae2bbbec6cd.png)
博客管理
![在这里插入图片描述](https://img-blog.csdnimg.cn/21b00bd5004c446ca1b4cc3d6dbed365.png)
分类管理
![在这里插入图片描述](https://img-blog.csdnimg.cn/f51700c0c3ec4b59af32ffeec8b8b80e.png)
标签管理
![在这里插入图片描述](https://img-blog.csdnimg.cn/da2daeb0bf8548a68444d623917aa0ab.png)
## 新数据库表设计
![在这里插入图片描述](https://img-blog.csdnimg.cn/3d9e16d4530e4c468f6c57697f913787.png)

经过这次Dao层框架改变后，数据表彻底做到在数据库层面间毫无关系了，所有的外键都被剔除了（个人讨厌外键操作...）。

## 有几点要说明的
1、关于前端的样式，由于自己的审美太差劲了，所以我借鉴了这位大佬的博客上面的一些样式、布局和功能：[http://xiongsihao.com/](http://xiongsihao.com/)（大佬原谅我...）；
2、关于留言和评论功能，评论和留言功能在上线的版本中是没有的...因为交互式网站备案比较麻烦（已经被公安局的哥哥打了好几个电话了...），所以在前端页面中取消了留言和评论功能，但是在 Github 的版本中这两个功能还是有的；
3、关于项目部署，额，这个我属实不想多说了，因为后续我大概率还会添加新的功能或者修改架构的，那时候可能部署方式就跟现在不一样了；
4、关于 Redis 在项目中的使用，这个我正在初步想着怎么用以减少对数据库的查询（也可以用它实现点赞功能）；
5、现存的已知 Bug 还有一个，是文章提交的评论可以为空（这个我暂时懒得修改，主要是因为评论功能我用不上了）。

## 最后
**本项目将长期更新与维护，欢迎拉取和star！**
