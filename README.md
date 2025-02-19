# 專案說明
這是一個訂單與庫存管理系統，主要功能包括：

訂單管理：新增、修改、刪除及查詢訂單。
產品庫存管理：維護產品資訊，更新庫存數量。
員工資料管理：管理員工帳戶與權限設定。
會員資料管理：處理客戶註冊與資料維護。
系統採用 MVC（Model-View-Controller） 設計模式，以確保架構清晰、可維護性高。此外，系統實作 DAO（Data Access Object）層 負責數據存取，並透過 Service 層 處理業務邏輯。後端使用 MySQL 作為資料庫，確保數據管理的穩定性與效率。

## 目錄結構如下
```src
├─controller
│  ├─customer
│  │      MemberMainUI.java
│  │      MemberReadDialog.java
│  │      MemberReadUI.java
│  │      
│  ├─employ
│  │      EmployManagerUI.java
│  │      EmployUpdateUI.java
│  │      
│  ├─member
│  │      MemberManagerUI.java
│  │      
│  ├─porder
│  │      PorderCreate1.java
│  │      PorderCreate2.java
│  │      PorderCreate3.java
│  │      PorderDeleteUI.java
│  │      PorderReadDialog.java
│  │      PorderReadUI.java
│  │      PorderUpdateUI.java
│  │      
│  ├─portal
│  │      LoginUI.java
│  │      MainUI.java
│  │      MemberLoginUI.java
│  │      PortalUI.java
│  │      RegisterUI.java
│  │      
│  └─product
│          ProductManagerUI.java
│          
├─dao
│  │  EmployDao.java
│  │  MemberDao.java
│  │  PorderDao.java
│  │  PorderSummaryDao.java
│  │  ProductDao.java
│  │  
│  └─impl
│          EmployDaoImpl.java
│          MemberDaoImpl.java
│          PorderDaoImpl.java
│          PorderSummaryDaoImpl.java
│          ProductDaoImpl.java
│          
├─model
│      Employ.java
│      Member.java
│      Porder.java
│      PorderSummary.java
│      Product.java
│      
├─service
│  │  EmployService.java
│  │  MemberService.java
│  │  PorderService.java
│  │  ProductService.java
│  │  
│  └─impl
│          EmployServiceImpl.java
│          MemberServiceImpl.java
│          PorderServiceImpl.java
│          ProductServiceImpl.java
│          
└─util
        DbConnection.java
        Tool.java
