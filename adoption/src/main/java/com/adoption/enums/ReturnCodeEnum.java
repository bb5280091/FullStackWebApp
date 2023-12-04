package com.adoption.enums;

public enum ReturnCodeEnum {

  SUCCESS("0000", "交易成功"),
  ERROR_INPUT("E001", "必填欄位不完整"),     
  UPDATE_FAIL("E002", "資料更新失敗"),    
  INSERT_FAIL("E003", "資料新增失敗"), 
  DELETE_FAIL("E004", "資料刪除失敗"),     
  DATA_NOT_FOUND("E005", "查無資料"),     
  DATABASE_INTERACT_FAIL("E006", "資料庫寫入或查詢失敗");
  
  private String code;
  private String desc;

  ReturnCodeEnum(String code, String  desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }
 
}
