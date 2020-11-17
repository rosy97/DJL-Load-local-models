构建目标检测模型的web应用：用户在浏览器端发送检测图片的url地址，后台将处理结果返回。

1.环境
linux、jdk8、springboot 2.1.8

2.依赖配置
· springboot-web中排除logging

3.启动服务
{host}:8888/test
返回：
[ class: "dog", probability: 0.96709, bounds: [x=0.165, y=0.348, width=0.249, height=0.539] class: "bicycle", probability: 0.66796, bounds: [x=0.152, y=0.244, width=0.574, height=0.562] class: "truck", probability: 0.64912, bounds: [x=0.609, y=0.132, width=0.284, height=0.166] ]