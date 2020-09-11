package com.imeng.web.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RepairStatus {

  UN_DISTRIBUTE(0, "未配单"),
  DISTRIBUTE(1, "已派单"),
  SUBSCRIBE(2, "已预约"),
  OPTING(3, "处理中"),
  REPAIRING(4, "外修中"),
  FINISH(5, "已完成");

  private Integer status;
  private String desc;

  public static String getStatusText(Integer status) {
    if (null != status) {
      for (RepairStatus repairStatus : values()) {
        if (status.intValue() == repairStatus.status.intValue()) {
          return repairStatus.desc;
        }
      }
    }
    return "";
  }
}
