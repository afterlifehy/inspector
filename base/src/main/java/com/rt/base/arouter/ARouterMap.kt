package com.rt.base.arouter

/**
 * Created by huy  on 2022/8/4.
 */
object ARouterMap {

    const val MAIN = "/inspector_app/main"

    const val LOGIN = "/inspector_app/login"

    const val PARKING_MANAGEMENT = "/inspector_app/parkingManagement"

    const val PARKING_LOT = "/inspector_app/parkingLot"
    const val PARKING_LOT_STREET_NAME = "streetName"
    const val PARKING_LOT_STREET_NO = "streetNo"

    const val TRAFFIC_ASSISTANT_LIST = "/inspector_app/trafficAssistantList"
    const val TRAFFIC_ASSISTANT_STREET_NO = "streetNo"

    const val INCOME_COUNTING = "/inspector_app/incomeCounting"
    const val INCOME_COUNTING_STREET_NO = "streetNo"

    const val PARKING_ORDER_DETAIL = "/inspector_app/parkingOrderDetail"
    const val PARKING_ORDER_NO = "orderNo"

    const val VIOLATION_REPORT_MAIN = "/inspector_app/violationReportMain"

    const val ASSISTANT_VIOLATION_REPORT = "/inspector_app/assistantViolationReport"

    const val ASSISTANT_VIOLATION_HISTORY = "/inspector_app/assistantViolationHistory"

    const val ASSISTANT_VIOLATION_DETAIL = "/inspector_app/assistantViolationDetail"
    const val ASSISTANT_MVIOLATEID = "mviolateId"

    const val ENTERPRISE_VIOLATION_REPORT = "/inspector_app/enterpriseViolationReport"

    const val ENTERPRISE_VIOLATION_HISTORY = "/inspector_app/enterpriseViolationHistory"

    const val ENTERPRISE_VIOLATION_DETAIL = "/inspector_app/enterpriseViolationDetail"
    const val ENTERPRISE_MVIOLATEID = "mviolateId"

    const val WORK_ATTENDANCE = "/inspector_app/workAttendance"

    const val INFO_VERIFY_MAIN = "/inspector_app/infoVerify"

    const val BUSINESS_LICENSE = "/inspector_app/businessLicense"

    const val FEE_STANDARD = "/inspector_app/feeStandard"

    const val PARKING_INFO = "/inspector_app/parkingInfo"

    const val TASK_RECEPTION = "/inspector_app/taskReception"

    const val ABNORMAL_DETAIL = "/inspector_app/abnormalDetail"

    const val PREVIEW_IMAGE = "/inspector_app/previewImage"
    const val IMG_TYPE = "imgType"
    const val IMG_INDEX = "imgIndex"
    const val IMG_LIST = "imgList"

    const val ROAD_BIND = "/inspector_app/roadBind"

    const val MINE = "/inspector_app/mine"

    const val PERSONAL_INFO = "/inspector_app/personalInfo"

    class common {
        companion object {

        }
    }
}