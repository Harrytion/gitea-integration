# Gitea Integration

[![Maven Central](https://maven-badges.herokuapp.com/maven-target/com.kerbores/gitea-integration/badge.svg)](https://maven-badges.herokuapp.com/maven-target/com.kerbores/gitea-integration)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Java](https://img.shields.io/badge/Java-21+-orange.svg)](https://www.oracle.com/java/)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()

## 简介

Gitea Integration 是一个功能完整的 Java 客户端库，用于与 Gitea REST API 进行交互。该库提供了对 Gitea 平台所有主要功能的访问，包括仓库管理、用户管理、组织管理、问题跟踪、标签管理、里程碑管理等。

## 特性

- 🚀 **完整 API 覆盖** - 支持 Gitea 平台的所有主要功能
- 📦 **丰富的模型类** - 完整的 POJO 模型，支持序列化/反序列化
- 🔧 **灵活的 API 客户端** - 支持自定义配置和认证
- 🌟 **现代化设计** - 基于 Java 21，使用最新的语言特性
- 📚 **完整的 Javadoc** - 提供详细的 API 文档
- 🏗️ **Maven 集成** - 轻松集成到现有的 Maven 项目

## 功能模块

### 核心 API
- **AdminApi** - 管理员功能
- **IssueApi** - 问题管理
- **MiscellaneousApi** - 杂项功能
- **OrganizationApi** - 组织管理
- **RepositoryApi** - 仓库管理
- **UserApi** - 用户管理

### 认证支持
- HTTP 基本认证
- OAuth 认证
- 自定义认证提供程序

### 模型类
- 完整的 Gitea 数据模型
- 包括但不限于：
  - 仓库相关：`Repository`, `Branch`, `Commit`, `Tag`
  - 用户相关：`User`, `AccessToken`, `PublicKey`
  - 问题相关：`Issue`, `Comment`, `Label`, `Milestone`
  - 组织相关：`Organization`, `Team`, `Permission`
  - API 响应：`APIError`, `Response`

## 快速开始

### 添加依赖

```xml
<dependency>
    <groupId>com.kerbores</groupId>
    <artifactId>gitea-integration</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 基本使用

```java
import com.kerbores.gitea.client.api.*;
import com.kerbores.gitea.client.request.ApiClient;
import com.kerbores.gitea.client.model.*;

// 1. 创建 API 客户端
ApiClient apiClient = new ApiClient();
apiClient.setBasePath("https://your-gitea-instance.com/api/v1");

// 2. 配置认证
apiClient.setAuthentication("your-username", "your-password");

// 3. 创建 API 实例
UserApi userApi = new UserApi(apiClient);
RepositoryApi repoApi = new RepositoryApi(apiClient);
IssueApi issueApi = new IssueApi(apiClient);

// 4. 调用 API
List<User> users = userApi.getUserList();
List<Repository> repos = repoApi.getRepoList("username");
List<Issue> issues = issueApi.getIssueList("owner", "repo");
```

## 高级配置

### 自定义 API 客户端

```java
// 创建自定义配置的客户端
ApiClient apiClient = new ApiClient();
apiClient.setBasePath("https://your-gitea.com/api/v1");
apiClient.setReadTimeout(30000);
apiClient.setConnectionTimeout(10000);

// 自定义认证
apiClient.setAuthentication(new CustomAuthenticationProvider());
```

### 错误处理

```java
try {
    Response response = apiClient.get("/user");
    if (response.isSuccessful()) {
        User user = response.getBody(User.class);
        System.out.println("用户信息: " + user);
    }
} catch (ApiErrorException e) {
    System.err.println("API 错误: " + e.getError().getMessage());
}
```

## API 文档

### 用户管理 API

```java
UserApi userApi = new UserApi(apiClient);

// 获取用户列表
List<User> users = userApi.getUserList();

// 获取当前用户信息
User currentUser = userApi.getCurrentUser();

// 获取用户仓库
List<Repository> repos = userApi.getUserRepoList("username");
```

### 仓库管理 API

```java
RepositoryApi repoApi = new RepositoryApi(apiClient);

// 创建仓库
CreateRepoOption createRepo = new CreateRepoOption();
createRepo.setName("my-new-repo");
createRepo.setDescription("这是一个新仓库");
createRepo.setPrivate(true);

Repository newRepo = repoApi.createRepo(createRepo);

// 获取仓库信息
Repository repo = repoApi.getRepo("owner", "repo-name");

// 创建分支
Branch newBranch = repoApi.createBranch("owner", "repo-name", "new-branch-name");
```

### 问题管理 API

```java
IssueApi issueApi = new IssueApi(apiClient);

// 创建问题
CreateIssueOption createIssue = new CreateIssue();
createIssue.setTitle("新问题");
createIssue.setBody("问题的详细描述");
createIssue.setLabels(Arrays.asList(1L, 2L)); // 标签ID列表

Issue issue = issueApi.createIssue("owner", "repo-name", createIssue);

// 获取问题列表
List<Issue> issues = issueApi.getIssueList("owner", "repo-name");

// 添加评论
Comment comment = issueApi.createComment("owner", "repo-name", 1L, "这是一个评论");
```

### 组织管理 API

```java
OrganizationApi orgApi = new OrganizationApi(apiClient);

// 创建组织
CreateOrgOption createOrg = new CreateOrg();
createOrg.setUsername("new-org");
createOrg.setFullName("新组织");

Organization org = orgApi.createOrg(createOrg);

// 创建团队
CreateTeamOption createTeam = new CreateTeam();
createTeam.setName("developers");
createTeam.set.permission(PermissionEnum.WRITE);

Team team = orgApi.createTeam("org-name", createTeam);
```

## 示例代码

### 获取用户信息并列出其仓库

```java
import com.kerbores.gitea.client.api.*;
import com.kerbores.gitea.client.model.*;

public class Example {
    public static void main(String[] args) {
        // 初始化客户端
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("https://your-gitea.com/api/v1");
        apiClient.setAuthentication("username", "password");
        
        // 创建 API 实例
        UserApi userApi = new UserApi(apiClient);
        RepositoryApi repoApi = new RepositoryApi(apiClient);
        
        try {
            // 获取当前用户信息
            User currentUser = userApi.getCurrentUser();
            System.out.println("当前用户: " + currentUser.getFullName());
            
            // 获取用户的仓库列表
            List<Repository> repos = repoApi.getRepoList(currentUser.getLogin());
            
            System.out.println("用户仓库 (" + repos.size() + " 个):");
            for (Repository repo : repos) {
                System.out.println("- " + repo.getName() + " (" + 
                    (repo.isPrivate() ? "私有" : "公开") + ")");
            }
            
        } catch (Exception e) {
            System.err.println("操作失败: " + e.getMessage());
        }
    }
}
```

### 创建新问题

```java
import com.kerbores.gitea.client.api.*;
import com.kerbores.gitea.client.model.*;

public class IssueExample {
    public void createNewIssue(String owner, String repo, String title, 
                                String description, Long[] labels) {
        IssueApi issueApi = new IssueApi(apiClient);
        
        CreateIssueOption createIssue = new CreateIssue();
        createIssue.setTitle(title);
        createIssue.setBody(description);
        createIssue.setLabels(Arrays.asList(labels));
        
        try {
            Issue newIssue = issueApi.createIssue(owner, repo, createIssue);
            System.out.println("问题创建成功: #" + newIssue.getNumber() + " - " + 
                               newIssue.getTitle());
        } catch (Exception e) {
            System.err.println("创建问题失败: " + e.getMessage());
        }
    }
}
```

## 构建要求

- Java 21+
- Maven 3.6+

## 开发

### 本地构建

```bash
# 编译项目
mvn clean compile

# 运行测试
mvn test

# 打包
mvn clean package

# 生成 Javadoc
mvn javadoc:javadoc
```

### 代码质量

项目使用以下工具确保代码质量：
- Maven Compiler Plugin (Java 21)
- Maven Javadoc Plugin (带 `-Xdoclint:none` 配置)
- Maven Surefire Plugin 用于测试

## 版本历史

### v1.0.0
- 初始版本发布
- 完整的 Gitea REST API 支持
- 包含所有主要功能模块
- 支持多种认证方式

## 许可证

本项目采用 [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) 许可证。

## 贡献

欢迎提交 Issue 和 Pull Request！

## 联系方式

- **作者**: 王贵源
- **邮箱**: kerbores@gmail.com
- **项目主页**: https://github.com/Kerbores/gitea-integration

## 致谢

感谢 Gitea 团队提供的优秀平台和 REST API，以及所有为开源社区做出贡献的开发者们。
