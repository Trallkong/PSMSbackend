# PSMSbackend – 物业管理系统后端

基于 Spring Boot 4.0.0、Java 17、PostgreSQL 的物业管理后端服务。

## 技术栈

- **Spring Boot 4.0.0** / Java 17
- **Spring Data JPA** — ORM，操作 PostgreSQL
- **Spring Security** — Basic Auth 认证，无状态会话
- **PostgreSQL** — 关系型数据库
- **Lombok** — 简化样板代码
- **Apache Commons Codec** — AES-GCM 加密 / HMAC-SHA256 签名

## 项目架构

```
entity/ → repository/ (JPA) → service/ → controller/ (REST)
```

## 快速开始

### 环境要求

- JDK 17+（推荐 JDK 21）
- PostgreSQL 运行在 `localhost:5432`
- 创建数据库 `PSMS_db`

### 启动

```bash
# 开发环境（端口 8080，无 SSL）
mvn spring-boot:run -Dspring.profiles.active=local

# 生产环境（端口 443，SSL）
mvn spring-boot:run -Dspring.profiles.active=prod

# 默认配置（硬编码开发账号）
mvn spring-boot:run
```

## API 接口

除 `/api/owners` 外，所有接口均需 Basic Auth 认证。

| 路由前缀 | 说明 |
|---|---|---|
| `/api/owners` | 业主增删改查（公开接口） |
| `/api/property` | 房产增删改查，支持按业主查询 `/owner/{oid}` |
| `/api/charge_item` | 收费项目，创建时自动生成账单 |
| `/api/bill` | 账单，支持按 `/property/{pid}/owner/{oid}[/unpaid]` 筛选 |
| `/api/payment` | 缴费记录 |
| `/api/payment_detail` | 缴费明细（更新账单已缴金额） |
| `/api/staff` | 员工登录 `POST /login` |
| `/api/dashboard` | 数据看板（未缴总额、已缴总额、欠费户数、业主总数） |
| `/api/crypto` | AES 加解密、HMAC 签名验签、密钥生成 |

## 环境配置

| Profile | 端口 | SSL | 账号凭证 |
|---|---|---|---|
| `local` | 8080 | 无 | 环境变量，有默认值兜底 |
| `prod`  | 443  | 有 (keystore.p12) | 环境变量，无默认值 |
| 默认 | 8080 | 无 | 硬编码开发账号 |

## 测试

```bash
mvn clean test
```

## 许可证

[MIT](LICENSE)
