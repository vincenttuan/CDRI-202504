package com.example.demo.controller;

/**
請求方法 URL 路徑              功能說明      請求參數                                   回應
-----------------------------------------------------------------------------------------------------
GET    /rest/room          取得所有房間列表 無                                       成功時返回所有房間的列表及成功訊息。
GET    /rest/room/{roomId} 取得指定房間資料 roomId (路徑參數，房間 ID)                  成功時返回指定房間資料及成功訊息。
POST   /rest/room          新增房間       請求體包含 roomDto                         成功時返回成功訊息，無回傳資料。
PUT    /rest/room/{roomId} 更新指定房間資料 roomId (路徑參數，房間 ID)，請求體包含 roomDto 成功時返回成功訊息，無回傳資料。
DELETE /rest/room/{roomId} 刪除指定房間    roomId (路徑參數，房間 ID)                  成功時返回成功訊息，無回傳資料。
*/

public class RoomRestController {

}
