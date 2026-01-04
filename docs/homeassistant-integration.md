# 优麦 ESP32-开源服务端与 HomeAssistant 集成指南

[TOC]

---

## 简介

本文档将指导您如何将 ESP32 设备与 HomeAssistant 进行集成。

## 前提条件

- 已安装并配置好`HomeAssistant`
- 本次我选择的模型是：免费的 ChatGLM，它支持 functioncall 函数调用

## 开始前的操作（必要）

### 1. 获取 HA 的网络网络地址信息

请访问你 Home Assistant 的网络地址，例如，我的 HA 的地址是 192.168.4.7，端口则是默认的 8123，则在浏览器打开

```
http://192.168.4.7:8123
```

> 手动查询 HA 的 IP 地址方法**（仅限优麦 esp32-server 和 HA 部署在同一个网络设备[例如同一个 wifi]下）**：
>
> 1. 进入 Home Assistant（前端）。
>
> 2. 点击左下角 **设置（Settings）** → **系统（System）** → **网络（Network）**。
>
> 3. 滑到最底部`Home Assistant 网址(Home Assistant website)`区域，在`本地网络(local network)`中，点击`眼睛`按钮，可以看到当前使用的 IP 地址（如 `192.168.1.10`）和网络接口。点击`复制连接(copy link)`可以直接复制。
>
>    ![image-20250504051716417](images/image-ha-integration-01.png)

或，您已经设置了直接可以访问的 Home Assistant 的 OAuth 地址，您也可以在浏览器内直接访问

```
http://homeassistant.local:8123
```

### 2. 登录`Home Assistant`拿到开发密钥

登录`HomeAssistant`，点击`左下角头像 -> 个人`，切换`安全`导航栏，划到底部`长期访问令牌`生成 api_key，并复制保存，后续的方法都需要使用这个 api key 且仅出现一次（小 tips: 您可以保存生成的二维码图像，后续可以扫描二维码再此提取 api key）。

## 方法 1：优麦社区共建的 HA 调用功能

### 功能描述

- 如您后续需要增加新的设备，该方法需要手动重启`mdtg-esp32-server服务端`以此更新设备信息**（重要**）。

- 需要您确保已经在 HomeAssistant 中集成`Xiaomi Home`，并将米家的设备导入进`HomeAssistant`。

- 需要您确保`mdtg-esp32-server智控台`能正常使用。

- 我的`mdtg-esp32-server智控台`和`HomeAssistant`部署在同一台机器的另一个端口，版本是`0.3.10`

  ```
  http://192.168.4.7:8002
  ```

### 配置步骤

#### 1. 登录`HomeAssistant`整理需要控制的设备清单

登录`HomeAssistant`，点击`左下角的设置`，然后进入`设备与服务`，再点击顶部的`实体`。

然后在实体中搜索你相关控制的开关，结果出来后，在列表中，点击其中一个结果，这是会出现一个开关的界面。

在开关的界面，我们尝试点击开关，看看是开发会随着我们的点击开/关。如果能操作，说明是正常联网的。

接着在开关面板找到设置按钮，点击后，可以查看这个开关的`实体标识符`。

我们打开一个记事本，按照这样格式整理一条数据：

位置+英文逗号+设备名称+英文逗号+`实体标识符`+英文分号

例如，我在公司，我有一个玩具灯，他的标识符是 switch.cuco_cn_460494544_cp1_on_p_2_1，那么就这个写这一条数据

```
公司,玩具灯,switch.cuco_cn_460494544_cp1_on_p_2_1;
```

当然最后我可能要操作两个灯，我的最终的结果是：

```
公司,玩具灯,switch.cuco_cn_460494544_cp1_on_p_2_1;
公司,台灯,switch.iot_cn_831898993_socn1_on_p_2_1;
```

这段字符，我们称为“设备清单字符”需要保存好，等一下有用。

#### 2. 登录`智控台`

![image-20250504051716417](images/image-ha-integration-06.png)

使用管理员账号，登录`智控台`。在`智能体管理`，找到你的智能体，再点击`配置角色`。

将意图识别设置成`外挂的大模型意图识别`或`大模型自主函数调用`。这时你会看到右侧有一个`编辑功能`。点击`编辑功能`按钮，会弹出`功能管理`的框。

在`功能管理`的框里，你需要勾选`HomeAssistant设备状态查询`和`HomeAssistant设备状态修改`。

勾选后，在`已选功能`点击`HomeAssistant设备状态查询`，然后在`参数配置`里配置你的`HomeAssistant`地址、密钥、设备清单字符。

编辑好后，点击`保存配置`，这时`功能管理`的框会隐藏，这时你再点击保存智能体配置。

保存成功后，即可唤醒设备操作。

#### 3. 唤醒设别进行控制

尝试和 esp32 说，“打开 XXX 灯”

## 方法 2：优麦将 Home Assistant 的语音助手作为 LLM 工具

### 功能描述

- 该方法有一个比较严重的缺点——**该方法无法使用优麦开源生态的 function_call 插件功能的能力**，因为使用 Home Assistant 作为优麦的 LLM 工具会将意图识别能力转让给 Home Assistant。但是**这个方法是能体验到原生的 Home Assistant 操作功能，且优麦的聊天能力不变**。如实在介意可以使用同样是 Home Assistant 支持的[方法 3](##方法 3：使用 Home Assistant 的 MCP 服务（推荐）)，能够最大程度体验到 Home Assistant 的功能。

### 配置步骤：

#### 1. 配置 Home Assistant 的大模型语音助手。

**需要您提前配置好 Home Assistant 的语音助手或大模型工具。**

#### 2. 获取 Home Assistant 的语言助手的 Agent ID.

1. 进入 Home Assistant 页面内。左侧点击`开发者助手`。
2. 在打开的`开发者助手`内，点击`动作`选项卡（如图示操作 1），在页面内的选项栏`动作`中，找到或输入`conversation.process（对话-处理）`并选择`对话（conversation）: 处理`（如图示操作 2）。

![image-20250504043539343](images/image-ha-integration-02.png)

3. 在页面内勾选`代理(agent)`选项，在变成常亮的`对话代理(conversation agent)`内选择您步骤一配置好的语音助手名称，如图示，我这边配置好的是`ZhipuAi`并选择。

![image-20250504043854760](images/image-ha-integration-03.png)

4. 选中后，点击表单左下方的`进入YAML模式`。

![image-20250504043951126](images/image-ha-integration-04.png)

5. 复制其中的 agent-id 的值，例如图示中我的是`01JP2DYMBDF7F4ZA2DMCF2AGX2`(仅供参考)。

![image-20250504044046466](images/image-ha-integration-05.png)

6. 切换到优麦开源服务端`mdtg-esp32-server`的`config.yaml`文件内，在 LLM 配置中，找到 Home Assistant，设置您的 Home Assistant 的网络地址，Api key 和刚刚查询到的 agent_id。
7. 修改`config.yaml`文件内的`selected_module`属性的`LLM`为`HomeAssistant`，`Intent`为`nointent`。
8. 重启优麦开源服务端`mdtg-esp32-server`即可正常使用。

## 方法 3：使用 Home Assistant 的 MCP 服务（推荐）

### 功能描述

- 需要您提前在 Home Assistant 内集成并安装好 HA 集成——[Model Context Protocol Server](https://www.home-assistant.io/integrations/mcp_server/)。

- 这个方法与方法 2 都是 HA 官方提供的解决方法，与方法 2 不同的是，您可以正常使用优麦开源服务端`mdtg-esp32-server`的开源共建的插件，同时允许您随意使用任何一个支持 function_call 功能的 LLM 大模型。

### 配置步骤

#### 1. 安装 Home Assistant 的 MCP 服务集成。

集成官方网址——[Model Context Protocol Server](https://www.home-assistant.io/integrations/mcp_server/)。。

或跟随以下手动操作。

> - 前往 Home Assistant 页面的**[设置 > 设备和服务（Settings > Devices & Services.）](https://my.home-assistant.io/redirect/integrations)**。
>
> - 在右下角，选择 **[添加集成（Add Integration）](https://my.home-assistant.io/redirect/config_flow_start?domain=mcp_server)**按钮。
>
> - 从列表中选择**模型上下文协议服务器（Model Context Protocol Server）**。
>
> - 按照屏幕上的说明完成设置。

#### 2. 配置优麦开源服务端 MCP 配置信息

进入`data`目录，找到`.mcp_server_settings.json`文件。

如果你的`data`目录下没有`.mcp_server_settings.json`文件，

- 请把在`mdtg-server`文件夹根目录的`mcp_server_settings.json`文件复制到`data`目录下，并重命名为`.mcp_server_settings.json`
- 或[下载这个文件](https://github.com/beer-on-ice/mdtg-esp32-server/blob/main/main/mdtg-server/mcp_server_settings.json)，下载到`data`目录下，并重命名为`.mcp_server_settings.json`

修改`"mcpServers"`里的这部分的内容：

```json
"Home Assistant": {
      "command": "mcp-proxy",
      "args": [
        "http://YOUR_HA_HOST/mcp_server/sse"
      ],
      "env": {
        "API_ACCESS_TOKEN": "YOUR_API_ACCESS_TOKEN"
      }
},
```

注意：

1. **替换配置：**
   - 替换`args`内的`YOUR_HA_HOST`为您的 HA 服务地址，如果你的服务地址已经包含了 https/http 字样（例如`http://192.168.1.101:8123`)，则只需要填入`192.168.1.101:8123`即可。
   - 将`env`内`API_ACCESS_TOKEN`的`YOUR_API_ACCESS_TOKEN`替换成您之前获取到的开发密钥 api key。
2. **如果你添加配置是在`"mcpServers"`的括号内后续没有新的`mcpServers`的配置时，需要把最后的逗号`,`移除**，否则可能会解析失败。

**最后效果参考以下（参考如下）**：

```json
 "mcpServers": {
    "Home Assistant": {
      "command": "mcp-proxy",
      "args": [
        "http://192.168.1.101:8123/mcp_server/sse"
      ],
      "env": {
        "API_ACCESS_TOKEN": "abcd.efghi.jkl"
      }
    }
  }
```

#### 3. 配置优麦开源服务端的系统配置

1. **选择任意一款支持 function_call 的 LLM 大模型作为优麦的 LLM 聊天助手（但不要选择 Home Assistant 作为 LLM 工具）**，本次我选择的模型是：免费的 ChatGLM，它支持 functioncall 函数调用，但部分时候调用不太稳定，如果像追求稳定建议把 LLM 设置成：DoubaoLLM，使用的具体 model_name 是：doubao-1-5-pro-32k-250115。

2. 切换到优麦开源服务端`mdtg-esp32-server`的`config.yaml`文件内，设置您的 LLM 大模型配置，并且将`selected_module`配置的`Intent`调整为`function_call`。

3. 重启优麦开源服务端`mdtg-esp32-server`即可正常使用。
