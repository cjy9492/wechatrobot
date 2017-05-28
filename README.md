# wechatrobot -- 用Java来实现微信机器人的功能
 

### 来源

[itchat4j](https://github.com/yaphone/itchat4j)是一个非常优秀的开源微信个人号接口，使用了java语言开发，提供了简单易用的API，可以很方便地对个人微信号进行扩展。

wechatrobot是在基于[itchat4j](https://github.com/yaphone/itchat4j)的基础上开发而成的，可以轻松的把自己的个人微信号，变成能够处理各种消息的机器人。
 
## 项目介绍
 
使用[wechatrobot](https://github.com/cjy9492/wechatrobot)你可以很轻松的将其布置在你的个人微信号上，只需要简单的配置后，你就可以让你的个人微信号成为自动回复的智能机器人，还不快去朋友面前装个X。

## 如何使用

wechatrobot是一个Maven项目，下载源码后，可以以Maven项目的形式导入，导入后的项目结构如下图:

![wechatrobot项目结构](http://a1.qpic.cn/psb?/7d196e8d-bcb0-4b10-96f0-58980105d3ae/8TMgqbWy0EMCzoBcbP70ZvYDZ1E.TJnKyu14VwyKc2Y!/m/dGwBAAAAAAAAnull&bo=ngHOAQAAAAADB3I!&rf=photolist&t=5)
 

wechatrobot-itchat是itchat4j的项目源码，为了方便开发，我直接将itchat4j的源码也引入了进来，wechatrobot-main就是我们的机器人了，在src/main/java/路径下就是机器人的源码啦，主要消息处理类放在`MessageHandler`中，该类实现了itchat4j中的`IMsgHandlerFace`中的方法用来处理文本消息，目前版本只支持处理文本消息，其他消息的处理还在开发中。
 
 
为了确保机器人能正确运行，你需要将`wechat.properties`中的配置进行相应的修改。
 
```
#二维码保存地址（linux地址存放在wechatstart）
locationpath=D:
#图灵机器人的apikey
apiKey=你的机器人api
#排除用户的名称用|隔开
exceptname=好友的昵称A|好友的昵称B
#群聊时候响应的名字用|隔开
groupname=关键词A|关键词B|
#启动机器人的指令
wechatstart=机器人开启
#关闭机器人的指令
wechatclose=机器人关闭
#是否开启增肥计划相关设置 true开启 flash关闭（这个设置直接关闭就行）
fertilizers=flash
```
 
 在使用机器人的过程中，对于有些人我们并不希望机器人对他的消息进行自动回复，这时候就可以将他的昵称添加进入`exceptname`中，这样wechatrobot就不会对他的消息进行自动回复啦。
 
 在群聊中，由于逐条回复很容易造成大家的反感，所以特定添加了逻辑，你只需要在`groupname`中设定了关键词，当有人说出了设定好的关键词，wechatrobot才会对此进行自动回复。由于每次说话都要带上关键词，会很容易让别人的调戏过程产生厌烦感，所以在首次携带关键词后，说出关键词的那个人接下来的三句消息，就算不带上关键词也会进行回复。
 
在配置好相关的内容后你可以直接在wechatrobot主目录下使用maven指令
```
mvn clean install -DskipTests
```
将项目打包成为jar包，在任意有jdk的环境直接使用下面命令运行即可。
```
java -jar 工程名字
```
 
在运行程序后如果是在windows环境下，会自动弹出二维码窗口，使用手机微信扫一扫确认登录即可。在使用过程中，切记不要让手机微信处于离线状态。


## 类似项目
[itchat4j](https://github.com/yaphone/itchat4j) ：基于java的微信个人号API,本项目就是在此基础上开发的。

[itchat](https://github.com/littlecodersh/ItChat) ：优秀的、基于Python的微信个人号API。

[WeixinBot](https://github.com/Urinx/WeixinBot): 网页版微信API，包含终端版微信及微信机器人


## 问题和建议

本项目长期更新、维护，功能不断扩展与完善中，欢迎star。

项目使用过程中遇到问题，或者有好的建议，欢迎随时反馈。

任何问题或者建议都可以在Issue中提出来
